package basf.knowledge.omf.ontology_xref_finder.core.xrefclient;

import java.io.File;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearch;
import basf.knowledge.omf.ontology_xref_finder.core.pojo.OLSSearchItem;
import basf.knowledge.omf.ontology_xref_finder.core.utils.APIQueryParams;

// TODO Refactor this in a new package with some interface
class OLSEndpoint {
	private static String SEARCH = "/search";

	public static APIQueryParams search(String query, String queryFields, Integer rows) {
		APIQueryParams apiQueryparams = new APIQueryParams();
		apiQueryparams.setContextPath(SEARCH);
		apiQueryparams.addQueryParam("q", query); // q=Destillation
		if (queryFields != null && !query.isEmpty()) {
			apiQueryparams.addQueryParam("queryFields", queryFields); // queryFields=label,synonym
		}
		if (rows != null && rows > 0) {
			apiQueryparams.addQueryParam("rows", rows); // rows=2
		}

		return apiQueryparams;
	}
}

public class OLSXrefClient extends AbstractXrefClient {
	private static final Logger LOGGER = Logger.getLogger(OLSXrefClient.class.getName());
	private static final String QUERY_FIELDS = "label,synonym";

	public OLSXrefClient(String url, File ontology, Integer max_xrefs) throws OWLOntologyCreationException {
		super(url, ontology, max_xrefs);
	}

	public OLSXrefClient(String url, String ontology, Integer max_xrefs) throws OWLOntologyCreationException {
		super(url, ontology, max_xrefs);
	}
	
	public OLSXrefClient(String url, File ontology) throws OWLOntologyCreationException {
		super(url, ontology);
	}
	
	public OLSXrefClient(String url, String ontology) throws OWLOntologyCreationException {
		super(url, ontology);
	}

	@Override
	protected List<IRI> search(OWLAnnotation annotation) throws SocketException {
		OWLLiteral literal = annotation.getValue().asLiteral().get();
		String literalValue = literal.getLiteral();
		WebTarget client = createClient(OLSEndpoint.search(literalValue, QUERY_FIELDS, this.max_xrefs));
		Response response = client.request(MediaType.APPLICATION_JSON).get();
		OLSSearch olsSearch = null;
        //System.out.println(response.readEntity(String.class));
        if (Status.OK.getStatusCode() != response.getStatus()) {
        	String error = "Could not process request, received status '" + response.getStatus() + "'";
        	LOGGER.warning(error);
        	throw new SocketException(error);
        }
        olsSearch = response.readEntity(OLSSearch.class);
        List<OLSSearchItem> items = olsSearch.getResponse().getDocs();
        if (items.isEmpty()) {
        	LOGGER.warning("Could not find XRefs for annotation '" + literalValue + "'");
        	return  new LinkedList<IRI>();
        } else {
        	LOGGER.info("Found '" + items.size() + "' possible XRefs for '" + literalValue + "'");
        }
        List<IRI> result = new LinkedList<IRI>();
        for (OLSSearchItem olsSearchItem : items) {
        	result.add(IRI.create(olsSearchItem.getIri()));
		}
		return result;
	}

}
