package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;

import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.RegistrationEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@RooJavaBean
@Path(RegistrationEndpointImpl.ENDPOINT_PATH)
public class RegistrationEndpointImpl implements RegistrationEndpoint {

	public static final String ENDPOINT_PATH = AdmissionEndpointImpl.ENDPOINT_PATH
			+ AdmissionEndpointImpl.ADMISSION_PATH + URIKeys.REGISTRATION_PATH;
	public static final String REGISTRATION_PATH = TermEndpointImpl.ENDPOINT_PATH + TermEndpointImpl.TERM_PATH;

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

}
