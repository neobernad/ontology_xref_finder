package basf.knowledge.omf.ontology_xref_finder.api.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private Set<String> mediaType = new HashSet<>();
	
	public SwaggerConfig() {
		mediaType.add(MediaType.APPLICATION_JSON_VALUE);
	}
	
	@Bean
	public Docket xrefApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(mediaType)
				.consumes(mediaType)
				.select()
				.apis(RequestHandlerSelectors.basePackage("basf.knowledge.omf.ontology_xref_finder.api.rest"))
				.paths(PathSelectors.regex("/rest.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	
	@SuppressWarnings("deprecation")
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Crossreference finder API", "Documentation", "1.0.0", "Terms of Service", "jose-antonio.bernabe-diaz@basf.com", "MIT License", "https://tldrlegal.com/license/mit-license");
		return apiInfo;
	}
}
