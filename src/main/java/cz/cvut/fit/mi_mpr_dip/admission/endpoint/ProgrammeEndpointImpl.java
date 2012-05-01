package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.ProgrammeEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;

@RooJavaBean
@Path(AdmissionEndpointImpl.ENDPOINT_PATH)
public class ProgrammeEndpointImpl implements ProgrammeEndpoint {

	protected static final String ENDPOINT_PATH = "/programme";

	private static final String PROGRAMME_PATH = "/name:{name}/degree:{degree}/language:{language}/studyMode:{studyMode}";

	@Autowired
	private ProgrammeEndpointHelper programmeEndpointHelper;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Secured("PERM_READ_PROGRAMS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getPrograms() {
		return null;
	}

	@Secured("PERM_READ_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}

	@Secured("PERM_WRITE_PROGRAMME")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addProgramme(Programme programme) {
		return null;
	}

	@Secured("PERM_WRITE_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateProgramme(String name, String degree, String language, String studyMode, Programme programme) {
		return null;
	}

	@Secured("PERM_DELETE_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}
}
