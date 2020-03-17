package basf.knowledge.omf.ontology_xref_finder.core.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigParser {
	private static final Logger LOGGER = Logger.getLogger(ConfigParser.class.getName());
	private static final String OLS_API = "ols_api";
	private Properties properties;
	
	public ConfigParser() {
		this.properties = new Properties();
	}
	
	public void loadDefaultConfig() throws IOException, URISyntaxException {
		StringBuilder sb = new StringBuilder();
		sb.append(OLS_API + "=\"https://www.ebi.ac.uk/ols/api\"");
		
		try {
			properties.load(new StringReader(sb.toString()));
		} catch (FileNotFoundException e) {
			LOGGER.severe(e.getMessage());
			throw e;
		}
		LOGGER.info("Default configuration loaded: " + toString());
	}
	
	public void loadConfig(String configFilePath) throws IOException {
		loadConfig(new File(configFilePath));
	}
	
	public void loadConfig(File configFile) throws IOException {
		try (InputStream input = new FileInputStream(configFile)) {
            this.properties.load(input);
        } catch (IOException e) {
        	LOGGER.severe(e.getMessage());
			throw e;
        }
		LOGGER.info("Custom configuration loaded" + toString());
	}
	
	public String getOlsApi() {
		return properties.getProperty(OLS_API);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigParser [properties=");
		builder.append(properties);
		builder.append("]");
		return builder.toString();
	}
	
	
}
