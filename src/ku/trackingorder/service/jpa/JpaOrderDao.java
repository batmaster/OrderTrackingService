package ku.trackingorder.service.jpa;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import ku.trackingorder.entity.Order;
import ku.trackingorder.entity.Orders;
import ku.trackingorder.service.OrderDao;

/**
 * Data access object for saving and retrieving contacts,
 * using JPA.
 * To get an instance of this class use:
 * <p>
 * <tt>
 * dao = DaoFactory.getInstance().getContactDao()
 * </tt>
 * 
 * @author jim
 */
public class JpaOrderDao extends OrderDao {

	/** the EntityManager for accessing JPA persistence services. */
	private final EntityManager em;
	/**
	 * Constructor with injected EntityManager to use.
	 * @param em an EntityManager for accessing JPA services
	 */
	public JpaOrderDao(EntityManager em) {
		this.em = em;
	}

	/**
	 * @see ku.trackingorder.service.OrderDao#find(long)
	 */
	@Override
	public Order find(long id) {
		Order order = em.find(Order.class, id);
			return order;
	}

	/**
	 * @return 
	 * @see ku.trackingorder.service.OrderDao#findAll()
	 */
	@Override
	public Orders findAll() {
		Query query = em.createQuery("SELECT o FROM Order o");
		List<Order> resultList = (List<Order>)query.getResultList();
		return new Orders(resultList);
	}

	@Override
	public boolean containID(long l) {
		Query query = em.createQuery("SELECT o FROM Order o where o.id = :id");
		query.setParameter("id", l);
		return !query.getResultList().isEmpty();	
	}

	@Override
	public boolean update(Order order) {
		EntityTransaction tx  = em.getTransaction();
		try{
			tx.begin();
			if(!containID(order.getId()))
				return false;
			em.find(Order.class, order.getId());
			em.merge(order);
			tx.commit();
			return true;
		}catch(EntityExistsException ex){
			Logger.getLogger(this.getClass().getName()).warning(ex.getMessage());
			if(tx.isActive())try{ tx.rollback(); } catch (Exception e) {}
			return false;
		}
	}

	@Override
	public void save(Order order) {
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			em.persist(order);
			tx.commit();	
		}catch(EntityExistsException ex){
			Logger.getLogger(this.getClass().getName()).warning(ex.getMessage());
			if(tx.isActive())try{ tx.rollback(); } catch (Exception e) {}
		}
	}
}
