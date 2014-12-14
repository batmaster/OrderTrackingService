package ku.trackingorder.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Orders entity, wrapper of Order.
 * Use when get the result of multi-order.
 * @author KURel
 * @version 14/12/2014
 *
 */
@Entity
@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Orders {
	
	/** List of Order. **/
	@XmlElement(name="order")
	private List<Order> orders;

	/**
	 * Constructor require nothing,
	 * create empty list of Orders.
	 */
	public Orders() {
		this(new ArrayList<Order>());
	}
	
	/**
	 * Constructor with the exist list of Orders.
	 * @param list initial of Orders list
	 */
	public Orders(List<Order> list) {
		orders = list;
	}
	
	/**
	 * Constructor with an Order.
	 * @param order an Order object
	 */
	public Orders(Order order) {
		orders = new ArrayList<Order>();
		orders.add(order);
	}

	/**
	 * Add Order to the list.
	 * @param Order a new order
	 */
	public void addOrder(Order Order) {
		orders.add(Order);
	}

	/**
	 * Get list of the Orders.
	 * @return list of all Orders
	 */
	public List<Order> getOrderList(){
		return orders;
	}
}
