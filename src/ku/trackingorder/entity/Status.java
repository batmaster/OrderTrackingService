package ku.trackingorder.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

/**
 * Status entity. Contains string indicate status of the order.
 * Also have description if provide, date create, updater name.
 * @author KURel
 * @version 14/12/2014
 *
 */
@Entity
@Table(name = "status")
@XmlAccessorType (XmlAccessType.FIELD)
public class Status {
	
	@XmlAttribute
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@XmlElement(name="type")
	private StatusType statusType;
	@XmlElement
	private String date;
	@XmlElement
	private String description;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="status_id",referencedColumnName="id")
	@XmlInverseReference(mappedBy="statuslist")
	@XmlTransient
	private Order order;
	@XmlElement
	private String updatedby;
	public StatusType getStatusType() {
		return statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public Status() {
		this(0,null,null,null,null);
	}
	
	public Status(long l,StatusType statusType,String date,String description,String updatedby){
		id = l;
		this.statusType = statusType;
		this.date = date;
		this.description = description;
		this.updatedby = updatedby;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "id :"+id+" date :"+date+" type :"+statusType;
	}
	
	@Override
	public int hashCode() {
		return (id+date+description+updatedby+statusType).hashCode();
	}
}
