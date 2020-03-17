package basf.knowledge.omf.core;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;


import basf.knowledge.omf.ontology_xref_finder.core.parser.ConfigParser;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ConfigTest extends TestCase {
	private static final Logger LOGGER = Logger.getLogger(ConfigTest.class.getName());
	

	public ConfigTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ConfigTest.class);
	}


	public void testConfig() throws IOException, URISyntaxException {
		File configFile = new File("D:/Program Files/Eclipse JEE/workspace-jee/ontology_xref_finder/core/src/main/resources/example.config");
		ConfigParser configParser = new ConfigParser();
		configParser.loadDefaultConfig();
		LOGGER.info(configParser.getOlsApi());
		configParser = new ConfigParser();
		configParser.loadConfig(configFile);
		LOGGER.info(configParser.getOlsApi());
		
	}
}
