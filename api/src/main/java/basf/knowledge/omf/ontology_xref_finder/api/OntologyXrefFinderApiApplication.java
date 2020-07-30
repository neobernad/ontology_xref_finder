package basf.knowledge.omf.ontology_xref_finder.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("basf.knowledge.omf.ontology_xref_finder.api.rest")
public class OntologyXrefFinderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OntologyXrefFinderApiApplication.class, args);
	}

}
