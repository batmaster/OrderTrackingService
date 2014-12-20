package ku.trackingorder.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Order entity. Store Status entities.
 * Use when get the result of single-order.
 * @author KURel
 * @version 14/12/2014
 *
 */

@XmlRootElement
@Entity
@Table(name="orders")
@XmlAccessorType (XmlAccessType.FIELD)
public class Order {

	@Id
	@XmlAttribute
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	/**	Id of fullFillment. */
	@XmlTransient
	private long fullfillmentId;
	
	@XmlElement
	private String order_status;
	
	@XmlElement
	private String payment_status;
	
	@XmlElement
	private String shipping_Status;
	
	public String getShipping_Status() {
		return shipping_Status;
	}

	public void setShipping_Status(String shipping_Status) {
		this.shipping_Status = shipping_Status;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	/**
	 * Use for setId.
	 */
	@XmlTransient
	private long eCommerceOrderID;
//	public Order() {
//		this(0,new ArrayList<Status>());
//	}
	
	public long geteCommerceOrderID() {
		return eCommerceOrderID;
	}

	public void seteCommerceOrderID(long eCommerceOrderID) {
		this.eCommerceOrderID = eCommerceOrderID;
	}

	public Order(){
		this(0, "");
	}
	
	public void setfulfillmentId(long fullfillmentId){
		this.fullfillmentId = fullfillmentId;
	}
	public long getFulfillmentId() {
		return fullfillmentId;
	}
	
	public Order(long id,String status){
		this.id = id;
		this.order_status = status;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		int hascode = (id+" "+order_status).hashCode();
		return hascode;
	}
}
