package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Terms;

@RooJavaBean
@Path(TermEndpointImpl.ENDPOINT_PATH)
public class TermEndpointImpl implements TermEndpoint {

	protected static final String ENDPOINT_PATH = "/term";

	private static final String TERM_PATH = "/dateOfTerm:{dateOfTerm}/room:{room}";

	@Secured("PERM_READ_TERMS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getTerms() {
		Terms terms = createTerms();

		return Response.ok(terms).build();
	}

	private Terms createTerms() {
		Terms terms = new Terms();

		populateTerms(terms);

		return terms;
	}

	private void populateTerms(Terms terms) {
		List<Term> dbTerms = Term.findAllTerms();

		terms.setTerms(new HashSet<Term>(dbTerms));

		Long count = new Long(dbTerms.size());
		terms.setCount(count);
		terms.setTotalCount(count);
	}

	@Secured("PERM_READ_TERM")
	@Path(TERM_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getTerm(@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return null;
	}

	@Secured("PERM_WRITE_TERM")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addTerm() {
		return null;
	}

	@Secured("PERM_WRITE_TERM")
	@Path(TERM_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateTerm(@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return null;
	}

	@Secured("PERM_DELETE_TERM")
	@Path(TERM_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteTerm(@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return null;
	}

}
