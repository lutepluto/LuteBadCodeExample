package runtime;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Jetty Server starter. Use mockMode(Enable mock service implement)
 * 
 * @author Downpour
 */
public class Bane {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();
        Connector connector = new SelectChannelConnector();
        connector.setPort(Integer.getInteger("jetty.port", 8080).intValue());

		WebAppContext webapp = new WebAppContext("webapp", "/darth");
		webapp.setDefaultsDescriptor("./src/test/resources/runtime/webdefault.xml");
		
		// add mock web.xml here to replace the default web.xml under webapp and enable mockMode
		webapp.setDescriptor("./src/test/resources/runtime/web.xml");

        Server server = new Server();
        server.setConnectors(new Connector[] { connector });
        server.setHandler(webapp);
        server.start();
        System.out.println("Jetty Server started, use " + (System.currentTimeMillis() - begin) + " ms");
    }
}
