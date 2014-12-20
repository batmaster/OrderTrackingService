package ku.trackingorder.resource;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ku.trackingorder.entity.Order;
import ku.trackingorder.service.DaoFactory;
import ku.trackingorder.service.OrderDao;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.json.JSONObject;
import org.json.XML;

/**
 * Handler for order operations.
 * @author KURel
 * @version 14/12/2014
 *
 */
@Path("/orders")
@Singleton
public class OrderResource {
	@Context
	private UriInfo uriinfo;

	/** Cache proxy for response. **/
	private CacheControl cache;

	private static final int NOT_FOUND = 404;
	private static final int BAD_REQUEST = 400;
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	/** Location of Fullfillment Service **/
	private String service = "http://128.199.132.197/dntk/api/v1/orders";
	//			"http://128.199.175.223:8000/fulfillment/orders/";

	private static HttpClient client;
	private OrderDao dao;

	/**
	 * Constructor require nothing.
	 * Initial cache proxy
	 */
	public OrderResource() {
		dao = DaoFactory.getInstance().getOrderDao();
		cache = new CacheControl();
		cache.setMaxAge(-1);
		cache.setPrivate(true);
		client = new HttpClient();
		try {
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("OrderResource Created");
	}

	/**
	 * Handle GET by specify id.
	 * Return an order with that id.
	 * @param id id of ecommerceOrderId
	 * @param request context or request
	 * @return an order with specify id
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrder(@PathParam("id") long id, @Context Request request) {
		System.out.println("in method");
		if(!dao.containID(id))
			return Response.status(NOT_FOUND).build();
		Order order = dao.find(id);
		long fullfillmentId = order.getFulfillmentId();
		org.eclipse.jetty.client.api.Request req = client.newRequest(service +"/"+fullfillmentId);
		req.method(HttpMethod.GET);
		req.accept(MediaType.APPLICATION_XML);
		ContentResponse res;
		try {
			res = req.send();
			JSONObject json = new JSONObject(res.getContentAsString()).getJSONObject("order");
			order.setOrder_status(json.getString("order_status"));
			order.setPayment_status(json.getString("payment_status"));
			if(json.has("shipping_status"))
				order.setShipping_Status(json.getString("shipping_status"));
			return Response.ok(jsonParser(order)).build();
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			System.out.println(e.toString());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	/**
	 * Sent request to orderFullfillment and save order.
	 * @param body http body.
	 * @return 201 Created if success else 400 Bad Request.
	 * @throws URISyntaxException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrder(String body,@HeaderParam("Content-Type") String contentType) throws URISyntaxException{
		org.eclipse.jetty.client.api.Request req = client.newRequest(service);
		req.method(HttpMethod.POST);
		StringContentProvider content = new StringContentProvider(body);
		System.out.println(body);
		Order order = null;
		req.content(content, MediaType.APPLICATION_JSON);
		order = new Order();
		JSONObject json = new JSONObject(body);
		// need id for orderId
		order.setId(json.getJSONObject("order").getLong("id"));
		try {
			ContentResponse res = req.send();
			System.out.println(res.getStatus());
			if(res.getStatus() == Response.Status.OK.getStatusCode() && order != null && !dao.containID(order.getId())){
				System.out.println("test");
				JSONObject responsebody = new JSONObject(res.getContentAsString());
				long fulfillmentId =  responsebody.getLong("order_id");
				order.setfulfillmentId(fulfillmentId);
				dao.save(order);
				System.out.println(dao.find(order.getId()).getFulfillmentId());
				return Response.created(new URI(uriinfo.getAbsolutePath()+""+order.getId())).build();
			}
			else{
				System.out.println("in else");
				return Response.status(BAD_REQUEST).build();
			}
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			System.out.println("in catch");
			return Response.status(BAD_REQUEST).build();
		}				
	}

	/**
	 * Get hashcode of an oder.
	 * @param order order that want to get etag code
	 * @return etag code
	 */
	public String getEtag(Order order){
		return Integer.toString(order.hashCode());
	}

	/**
	 * Parse Order object to json.
	 * @param order object want to parse
	 * @return string text in a form of json
	 */
	public String jsonParser(Order order){
		JAXBContext jaxbContext;
		java.io.StringWriter sw = new StringWriter();
		try {
			jaxbContext = JAXBContext.newInstance(Order.class);
			Marshaller jaxbMarshaller = (Marshaller) jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			jaxbMarshaller.marshal(order, sw);

			JSONObject xmlJSONObj = XML.toJSONObject(sw.toString());
			return xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Parse back json to Order object.
	 * @param s json string
	 * @return Order object
	 */
	public Order jsontoOrder(String s){
		JSONObject json = new JSONObject(s);
		return xmltoOrder(XML.toString(json));
	}

	/**
	 * Parse back xml to Order object.
	 * @param s xml string
	 * @return Order object
	 */
	public Order xmltoOrder(String s){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(s);
			return (Order) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			System.out.println("Can't convert to order");
			e.printStackTrace();
		}
		return null;
	}
}