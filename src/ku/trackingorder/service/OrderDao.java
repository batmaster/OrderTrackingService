package ku.trackingorder.service;

import ku.trackingorder.entity.Order;
import ku.trackingorder.entity.Orders;

/**
 * An abstract for contact dao contains method for handling
 * with contact.
 * 
 * @author Poramate Homprakob 5510546077
 *
 */
public abstract class OrderDao {

	/**
	 * Get the exist contact in the list with specific id.
	 * @param id id of the contact that want to find
	 * @return contact with specific id, null if not found
	 */
	public abstract Order find(long id);
	
	/**
	 * Get list of all contacts.
	 * @return list of all contacts
	 */
	public abstract Orders findAll();

	
	public abstract boolean containID(long l);
	
	public abstract boolean update(Order order);
	
	public abstract void save(Order order);
}

