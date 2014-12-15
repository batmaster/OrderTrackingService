package ku.trackingorder;



import ku.trackingorder.service.DaoFactory;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.DigestAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.glassfish.jersey.server.ServerProperties;

/**
 * <p>
 * This example shows how to deploy a RESTful web service
 * using a Jetty server that is created and started via code.
 * </p><p>
 * In this example, the resource classes are in the package "demo.resource".
 * Each resource class should be annotated with JAX-RS @Path("/something")
 * and the request handler methods should be annotated with @GET, @POST, etc.
 * </p>
 * <p>
 * This class creates a Jetty server on the specified port (default is PORT),
 * a ContextHandler that represents a Context containing a context path and
 * mapping of pathspecs to Servlets.
 * <p><tt>
 * handler.setContextPath("/")
 * </tt>/p><p>
 * Then the servlet holder is mapped to a path component (inside the context
 * path) using:
 * <p><tt>
 * handler.addServlet( servletHolder, "/*" );
 * </tt></p><p>
 * which means "map" everything inside this context to servletHolder.
 * In a more complex application (context), you could have many servlets
 * and map different pathspecs to different servlets.
 * <p>
 * In the case of a JAX-RS web service, each "resource" class also has
 * a @Path("/something") annotation, which can be used to map different
 * paths to different resources, so one ServletHolder can manage all your
 * resource classes.
 * </p>
 * 
 * <p>
 * I tested this with Jersey 2.12 and Jetty 9.2.  I used the following
 * JARs, referenced as libraries in Eclipse:
 * Jersey: lots of JARs! I included everything from:
 * jersey/lib directory, 
 * jersey/ext directory,
 * and
 * jersey/api/jaxrs.ws.rs-api-2.01.jar 
 * Some of these JARs probably aren't necessary, but I got runtime errors
 * about missing classes when I omitted JARs from the ext/ directory.
 * jersey/ext contains jars from other projects; this
 * may cause a problem if you have another version of the same JARs in
 * your project!  If you do, compare the JARs, or switch to a Maven
 * project so Maven will manage your dependencies. 
 * 
 * @author jim
 *
 */
public class JettyMain {
	
	static final int PORT = 11111;
	static Server server;

	public static void main(String[] args) throws Exception {
		start();
		int ch = System.in.read();
		stopServer();
	}
	
	public static void start() throws Exception {
		server = new Server( PORT );
		ServletContextHandler context = new ServletContextHandler( ServletContextHandler.SESSIONS );
		context.setContextPath("/");
		ServletHolder holder = new ServletHolder( org.glassfish.jersey.servlet.ServletContainer.class );
		holder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "ku.trackingorder.resource");
//		holder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, OrderApplication.class.getName());
		context.addFilter(HeaderFilter.class, "/*", null);
		context.addServlet( holder, "/*" );
//		server.setHandler( getSecurityHandler(context) );
		server.setHandler(context);
		System.out.println("Starting Jetty server on port " + PORT);
		server.start();
		System.out.println("Server started.  Press ENTER to stop it.");
	}
	
	public static Handler getSecurityHandler(Handler handler) {
		 // params to LoginService are realm name and properties file.

		 LoginService loginService = new HashLoginService("realm", "src/realm.properties");
		 server.addBean( loginService );

		 Constraint constraint = new Constraint();
		 constraint.setName("auth");
		 constraint.setAuthenticate( true );
		 // Only allow users that have these roles.
		 // It is more appropriate to specify this in the resource
		 // itself using annotations.
		 // But if I comment this out, Jetty returns 403 Forbidden
		 // instead of 401 Unauthorized.
		 constraint.setRoles( new String[] {"user", "admin"} );

		 // A mapping of resource paths to constraints
		 ConstraintMapping mapping = new ConstraintMapping();
		 mapping.setPathSpec("/*");
		 mapping.setConstraint( constraint );
		 ConstraintSecurityHandler securityHandler =
		 new ConstraintSecurityHandler();
		 // setConstraintMappings requires an array or List as argument
		 securityHandler.setConstraintMappings(
		 new ConstraintMapping[] { mapping } );
		 securityHandler.setAuthenticator(new DigestAuthenticator());
		 securityHandler.setLoginService(loginService);

		 // finally: wrap the parameter (Handler) in securityHandler
		 securityHandler.setHandler(handler);
		 return securityHandler;
		}

	
	public static void stopServer() throws Exception {
		System.out.println("Stopping server.");
		DaoFactory.getInstance().shutdown();
		server.stop();
	}
	
	public static String getURL() {
		return server.getURI().toString();
	}
	
}

