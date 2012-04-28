package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelperImpl;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.mail.PasswordResetService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Path(UserEndpointImpl.ENDPOINT_PATH)
@RooJavaBean
public class UserEndpointImpl implements UserEndpoint {

	protected static final String ENDPOINT_PATH = "/user";

	private static final String PASSWORD_PATH = "/password";
	private static final String PERSON_PATH = "/person";
	private static final String RESET_PASSWORD_PATH = "/reset_password";

	private static final String EMAIL_ATTRIBUTE = "email:";
	private static final String NEW_ATTRIBUTE = "new:";
	private static final String OLD_ATTRIBUTE = "old:";

	private static final String FULL_RESET_PASSWORD_PATH = PERSON_PATH + StringPool.SLASH + EMAIL_ATTRIBUTE + "{email}"
			+ RESET_PASSWORD_PATH;
	private static final String FULL_UPDATE_PASSWORD_PATH = AdmissionEndpointHelperImpl.IDENTITY_PATH
			+ StringPool.SLASH + "{userIdentity}" + PASSWORD_PATH + StringPool.SLASH + OLD_ATTRIBUTE + "{oldPassword}"
			+ StringPool.SLASH + NEW_ATTRIBUTE + "{newPassword}";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private PasswordResetService passwordResetService;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Autowired
	private UserPasswordService userPasswordService;

	@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
	@Path(FULL_RESET_PASSWORD_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response resetPassword(@PathParam("email") String email) {
		createAndStoreRandomPassword(email);
		return Response.ok().build();
	}

	@Secured("PERM_RESET_PASSWORD")
	@Path(ProcessingEndpointImpl.ADMISSION_PATH + "/{admissionCode}" + FULL_RESET_PASSWORD_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response resetPassword(@PathParam("admissionCode") String admissionCode, @PathParam("email") String email) {
		createAndStoreRandomPassword(admissionCode, email);
		return Response.seeOther(getAdmissionUri(admissionCode)).build();
	}

	@Secured("PERM_WRITE_PASSWORD")
	@Path(FULL_UPDATE_PASSWORD_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response updatePassword(String username, String oldPassword, String newPassword) {
		doUpdatePassword(username, oldPassword, newPassword);
		return Response.ok().build();
	}

	@Transactional
	private void createAndStoreRandomPassword(String email) {
		Set<UserIdentity> userIdentities = getUserPasswordService().createRandomPassword(email);
		for (UserIdentity userIdentity : userIdentities) {
			storeAndEmail(userIdentity, email);
		}
	}

	@Transactional
	private void createAndStoreRandomPassword(String admissionCode, String email) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		UserIdentity userIdentity = getUserPasswordService().createRandomPassword(admission);
		storeAndEmail(userIdentity, email, admission.getPerson().getEmail());
	}

	@Transactional
	private void doUpdatePassword(String username, String oldPassword, String newPassword) {
		UserIdentity userIdentity = getUserIdentityDao().getUserIdentity(username);
		userIdentity = getUserPasswordService().updatePassword(userIdentity, oldPassword, newPassword);

		userIdentity.persist();
	}

	private void storeAndEmail(UserIdentity userIdentity, String... emails) {
		userIdentity.persist();
		getPasswordResetService().send(userIdentity, emails);
	}

	private URI getAdmissionUri(String admissionCode) {
		return getUriEndpointHelper().getAdmissionLocation(getAdmissionBaseLocation(), admissionCode);
	}

	private String getAdmissionBaseLocation() {
		return ProcessingEndpointImpl.ENDPOINT_PATH + ProcessingEndpointImpl.ADMISSION_PATH;
	}

}
