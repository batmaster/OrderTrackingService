package ku.trackingorder.entity;

/**
 * List of status type.
 * @author KURel
 *
 */
public enum StatusType {
	ORDER_RETRIEVE("ORDER_RETRIEVE")
	,ORDER_PICK("ORDER_PICK")
	,ORDER_READY("ORDER_READY")
	,ORDER_SCAN("ORDER_SCAN")
	,ORDER_TRANSIT("ORDER_TRANSIT");
	
	private String type;
	
	private StatusType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type;
	}

	/**
	 * Check whether have the q type.
	 * @param q the type want to check
	 * @return true if found
	 */
	public static boolean has(String q) {
		for (StatusType type : StatusType.values()) {
			if(q.equals(type.toString())) 
				return true;
		}
		return false;
	}
	
	/**
	 * Get next status from the specific status.
	 * @param s the current status
	 * @return the next status, null if end of status list
	 */
	public static StatusType getNext(StatusType s){
		if(s.equals(StatusType.ORDER_RETRIEVE))
			return StatusType.ORDER_PICK;
		else if(s.equals(StatusType.ORDER_PICK))
			return StatusType.ORDER_READY;
		else if(s.equals(StatusType.ORDER_READY))
			return StatusType.ORDER_SCAN;
		else if(s.equals(StatusType.ORDER_SCAN))
			return StatusType.ORDER_TRANSIT;
		else
			return null;
	}
}
