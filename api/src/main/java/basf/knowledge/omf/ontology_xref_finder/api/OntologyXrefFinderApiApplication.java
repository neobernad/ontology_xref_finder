package basf.knowledge.omf.ontology_xref_finder.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:application.properties")
public class OntologyXrefFinderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OntologyXrefFinderApiApplication.class, args);
	}

}
