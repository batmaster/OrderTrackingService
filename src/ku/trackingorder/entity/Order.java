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
	@XmlElement
	private long fullfillmentId;
	
	@XmlElement
	private String status;
	
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
		this.status = status;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		int hascode = (id+" "+status).hashCode();
		return hascode;
	}
}
