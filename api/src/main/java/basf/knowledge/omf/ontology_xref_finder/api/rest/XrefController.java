package basf.knowledge.omf.ontology_xref_finder.api.rest;

import java.net.SocketException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import basf.knowledge.omf.ontology_xref_finder.api.dto.XrefProcessReporterDto;
import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.model.OntologyMetadata;
import basf.knowledge.omf.ontology_xref_finder.core.model.XrefMatch;
import basf.knowledge.omf.ontology_xref_finder.core.utils.ArgumentsDefaultValues;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.AbstractXrefClient;
import basf.knowledge.omf.ontology_xref_finder.core.xrefclient.OLSXrefClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/rest/ols")
@Api(value = "XrefController", description = "Crossreference finder API")
public class XrefController {
	private static final Logger LOGGER = Logger.getLogger(XrefController.class.getName());
	
	public XrefController() {
		LOGGER.info("Initializing " + XrefController.class.getSimpleName());
	}
	
	@GetMapping("/getEbiOntologies")
	@ApiOperation(value = "Returns a list of ontologies located in the EBI OLS",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<OntologyMetadata> getEbiOntologies() throws SocketException {
		AbstractXrefClient xrefClient = new OLSXrefClient(ArgumentsDefaultValues.OLS_URL_DEFAULT, ArgumentsDefaultValues.MAX_XREFS_DEFAULT);
		List<OntologyMetadata> ontologyMetadataList = xrefClient.getAllAvailableOntologies();
		return ontologyMetadataList;
	}
	
	@GetMapping("/getOntologies")
	@ApiOperation(value = "Returns a list of ontologies located in an OLS endpoint",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<OntologyMetadata> getOntologies(
			@ApiParam(value="URL to an OLS API endpoint", example = "https://www.ebi.ac.uk/ols/api")
			@RequestParam(value = "url", defaultValue = "https://www.ebi.ac.uk/ols/api")
			final String url
		) throws SocketException {
		AbstractXrefClient xrefClient = new OLSXrefClient(url, ArgumentsDefaultValues.MAX_XREFS_DEFAULT);
		List<OntologyMetadata> ontologyMetadataList = xrefClient.getAllAvailableOntologies();
		return ontologyMetadataList;
	}

		
	@GetMapping("/search")
	@ApiOperation(value = "Returns the crossreferences found under the input criteria",
	produces = MediaType.APPLICATION_JSON_VALUE
)
	public XrefProcessReporterDto search(
			@ApiParam(value="URL to an OLS API endpoint", example = "https://www.ebi.ac.uk/ols/api")
			@RequestParam(value = "url", required = true)
			final String url,
			@ApiParam(value="List of terms", example = "water,mol")
			@RequestParam(value = "terms", required = true)
			final List<String> terms,
			@ApiParam(value="Maximum number of crossreferences to search per term", example = "4")
			@RequestParam(value = "max_xrefs", defaultValue = "1")
			final Integer max_xrefs,
			@ApiParam(value="Performs an exact search in the OLS, if true")
			@RequestParam(value = "exact", defaultValue = "true")
			final Boolean exact,
			@ApiParam(value="List of ontology names to perform the search. If the list is empty, it searches in the whole OLS", example = "ncit")
			@RequestParam(value = "ontologies", defaultValue = "")
			final List<String> ontologies
		) throws SocketException {
		LOGGER.info("/search -"
				+ " url: '" + url + "'"
				+ " term: '" + terms + "'"
				+ " max_xrefs: '" + max_xrefs + "'"
				+ " exact: '" + exact + "'"
				+ " ontologies: '" + ontologies + "'"
		);
		AbstractXrefClient xrefClient = new OLSXrefClient(url, max_xrefs);
		xrefClient.setOntologiesFilter(ontologies);
		xrefClient.setExactMatch(exact);
		Stream<XrefMatch> xrefStream = xrefClient.findXrefByText(terms);
		IXrefProcessReporter report = xrefClient.processXrefs(xrefStream);
		XrefProcessReporterDto reporterDto = new XrefProcessReporterDto(report);
		return reporterDto;
	}
}
