package ku.trackingorder.resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Used with authenficate system.
 * @author KURel
 * @version 14/12/2014
 *
 */
public class OrderApplication extends ResourceConfig {
	public OrderApplication() {
		super(OrderResource.class);
		register(RolesAllowedDynamicFeature.class);
	}

}
