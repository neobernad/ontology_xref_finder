package basf.knowledge.omf.ontology_xref_finder.api.rest;

import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Health", description = "Health API endpoint")
public class HealthController {
	private static final Logger LOGGER = Logger.getLogger(HealthController.class.getName());
	
	public HealthController() {
		LOGGER.info("Initializing " + HealthController.class.getSimpleName());
	}
	
	@GetMapping("/rest/health")
	@ApiOperation(value = "Health endpoint to check whether the service is avaiable or not.",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public String health() {
		return "Hi! :)";
	}
	
}
