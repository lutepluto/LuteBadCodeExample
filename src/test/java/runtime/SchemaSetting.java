/**
 *
 */
package runtime;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * @author downpour
 */
public class SchemaSetting {
	
	private static final Log logger = LogFactory.getLog(SchemaSetting.class);
	
	private Properties properties = new Properties();
	
	@Before
	public void setup() {
		try {
			properties.load(new ClassPathResource("jdbc.local.properties").getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void init() {
		
		logger.info("Setting up Schema.");
		
		Resource script = new ClassPathResource("schema/schema.sql");

		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(script);
		populator.setSqlScriptEncoding("UTF-8");
		
		String url = "jdbc:mysql://127.0.0.1:3306/";
		String name = properties.getProperty("connection.username");
		String password = properties.getProperty("connection.password");
		
		DatabasePopulatorUtils.execute(populator, new DriverManagerDataSource(url, name, password));
		
	}
		
}
