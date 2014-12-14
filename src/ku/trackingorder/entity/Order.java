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
//	@XmlElement(name="status")
//	@OneToMany(mappedBy="order",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
//	private List<Status> statuslist;
	
	@XmlElement
	private String status;
	
//	public Order() {
//		this(0,new ArrayList<Status>());
//	}
	
	public Order(){
		this(0, "");
	}
	
	public Order(long id,String status){
		this.id = id;
		this.status = status;
	}
	
//	public Order(long id,List<Status> statuslist) {
//		this.id = id;
//		this.statuslist = statuslist;
//	}
//	
//	public void addStatus(Status status){
//		status.setOrder(this);
//		statuslist.add(status);
//	}
//
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public List<Status> getStatuslist() {
//		return statuslist;
//	}
//
//	public void setStatuslist(List<Status> statuslist) {
//		this.statuslist = statuslist;
//		System.out.println("in 4");
//	}
	
//	public void saveOrderInStatus(){
//		for(int i = 0;i<statuslist.size();i++){
//			statuslist.get(i).setOrder(this);
//		}
//	}
//	
//	@Override
//	public String toString() {
//		return id + statuslist.toString();
//	}



//	public void setStatus(Status status) {
//		status.setOrder(this);
//		List<Status> statuslist = new ArrayList<>();
//		statuslist.add(status);
//		this.statuslist = statuslist;
//	}
	
	@Override
	public int hashCode() {
		int hascode = (id+" "+status).hashCode();
//		for(int i = 0; i < statuslist.size();i++){
//			hascode += statuslist.get(i).hashCode();
//		}
		return hascode;
	}
}
