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

import cz.cvut.fit.mi_mpr_dip.admission.domain.Apology;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AppendixEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.RegistrationEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@RooJavaBean
@Path(RegistrationEndpointImpl.ENDPOINT_PATH)
public class RegistrationEndpointImpl implements RegistrationEndpoint {

	public static final String ENDPOINT_PATH = AdmissionEndpointImpl.ENDPOINT_PATH
			+ AdmissionEndpointImpl.ADMISSION_PATH + URIKeys.REGISTRATION_PATH;
	public static final String REGISTRATION_PATH = TermEndpointImpl.ENDPOINT_PATH + TermEndpointImpl.TERM_PATH;
	public static final String APOLOGY_PATH = REGISTRATION_PATH + URIKeys.APOLOGY_PATH;
	public static final String APPENDIX_PATH = APOLOGY_PATH + URIKeys.APPENDIX_PATH + StringPool.SLASH
			+ URIKeys.IDENTIFIER_ATTRIBUTE + URIKeys.IDENTIFIER;

	@Autowired
	private AppendixEndpointHelper appendixEndpointHelper;

	@Autowired
	private RegistrationEndpointHelper registrationEndpointHelper;

	@Secured("PERM_WRITE_REGISTRATION")
	@Path(REGISTRATION_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addRegistration(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return getRegistrationEndpointHelper().addRegistration(admissionCode, dateOfTerm, room);
	}

	@Secured("PERM_DELETE_REGISTRATION")
	@Path(REGISTRATION_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteRegistration(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return getRegistrationEndpointHelper().deleteRegistration(admissionCode, dateOfTerm, room);
	}

	@Secured("PERM_WRITE_APOLOGY")
	@Path(APOLOGY_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addApology(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room, Apology apology) {
		return getRegistrationEndpointHelper().addApology(admissionCode, dateOfTerm, room, apology);
	}

	@Secured("PERM_DELETE_APOLOGY")
	@Path(APOLOGY_PATH)
	@DELETE
	@Override
	public Response deleteApology(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room) {
		return getRegistrationEndpointHelper().deleteApology(admissionCode, dateOfTerm, room);
	}

	@Secured("PERM_WRITE_APOLOGY")
	@Path(APOLOGY_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateApology(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room, Apology apology) {
		return getRegistrationEndpointHelper().updateApology(admissionCode, dateOfTerm, room, apology);
	}

	@Secured("PERM_READ_APOLOGY_APPENDIX")
	@Path(APPENDIX_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getApologyAppendix(@PathParam("admissionCode") String admissionCode,
			@PathParam("dateOfTerm") String dateOfTerm, @PathParam("room") String room, String identifier) {
		return getAppendixEndpointHelper().getApologyAppendix(admissionCode, dateOfTerm, room, identifier);
	}
}
