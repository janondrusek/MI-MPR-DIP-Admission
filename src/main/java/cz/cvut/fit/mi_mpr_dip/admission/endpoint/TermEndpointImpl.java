package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.TermEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.TermDeduplicationService;

@RooJavaBean
@Path(TermEndpointImpl.ENDPOINT_PATH)
public class TermEndpointImpl implements TermEndpoint {

	protected static final String ENDPOINT_PATH = "/term";

	private static final String TERM_PATH = "/dateOfTerm:{dateOfTerm}/room:{room}";

	@Autowired
	private TermDeduplicationService termDeduplicationService;

	@Autowired
	private TermEndpointHelper termEndpointHelper;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Secured("PERM_READ_TERMS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getTerms() {
		return getTermEndpointHelper().getTerms();
	}

	@Secured("PERM_READ_TERM")
	@Path(TERM_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getTerm(@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return getTermEndpointHelper().getTerm(dateOfTerm, room);
	}

	@Secured("PERM_WRITE_TERM")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addTerm(Term term) {
		validateAndDeduplicateAndStore(term);
		return null;
	}

	@Transactional
	private void validateAndDeduplicateAndStore(Term term) {
		getTermEndpointHelper().validate(term);
		deduplicateAndStore(term);
	}

	private void deduplicateAndStore(Term term) {
		getTermDeduplicationService().deduplicateAndStore(term);
	}

	@Secured("PERM_WRITE_TERM")
	@Path(TERM_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateTerm(@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room, Term term) {
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
