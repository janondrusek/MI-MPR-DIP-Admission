package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UserIdentityEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.mail.PasswordResetService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserPasswordService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@Path(UserEndpointImpl.ENDPOINT_PATH)
@RooJavaBean
public class UserEndpointImpl implements UserEndpoint {

	protected static final String ENDPOINT_PATH = "/user";

	private static final String PASSWORD_PATH = "/password";
	private static final String PERSON_PATH = "/person";
	private static final String ROLES_PATH = "/roles";
	private static final String RESET_PASSWORD_PATH = "/reset_password";
	private static final String SESSION_PATH = "/session";

	private static final String FULL_RESET_PASSWORD_PATH = PERSON_PATH + StringPool.SLASH + URIKeys.EMAIL_ATTRIBUTE
			+ "{email}" + RESET_PASSWORD_PATH;
	private static final String FULL_UPDATE_PASSWORD_PATH = URIKeys.IDENTITY_PATH + StringPool.SLASH + "{userIdentity}"
			+ PASSWORD_PATH + StringPool.SLASH + URIKeys.OLD_ATTRIBUTE + "{oldPassword:.*?}" + StringPool.SLASH
			+ URIKeys.NEW_ATTRIBUTE + "{newPassword:.*?}";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private PasswordResetService passwordResetService;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Autowired
	private UserIdentityEndpointHelper userIdentityEndpointHelper;

	@Autowired
	private UserIdentityService userIdentityService;

	@Autowired
	private UserPasswordService userPasswordService;

	@Path(URIKeys.IDENTITY_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getUserIdentity() {
		return getUserIdentityEndpointHelper().getUserIdentity();
	}

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
	@Path(AdmissionEndpointImpl.ENDPOINT_PATH + AdmissionEndpointImpl.ADMISSION_PATH + FULL_RESET_PASSWORD_PATH)
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
	public Response updatePassword(@PathParam("userIdentity") String username,
			@PathParam("oldPassword") String oldPassword, @PathParam("newPassword") String newPassword) {
		doUpdatePassword(username, oldPassword, newPassword);
		return Response.ok().build();
	}

	@Secured("PERM_DELETE_SESSION")
	@Path(URIKeys.IDENTITY_PATH + "/{userIdentity}" + SESSION_PATH + StringPool.SLASH + URIKeys.IDENTIFIER_ATTRIBUTE
			+ URIKeys.IDENTIFIER)
	@Produces
	@DELETE
	@Override
	public Response deleteUserSession(@PathParam("userIdentity") String username,
			@PathParam("identifier") String identifier) {
		return getUserIdentityEndpointHelper().deleteUserSession(username, identifier);
	}

	@Secured("PERM_DELETE_USER_ROLE")
	@Path(URIKeys.IDENTITY_PATH + "/{userIdentity}" + ROLES_PATH + "/{userRoleName}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteUserRole(@PathParam("userIdentity") String username,
			@PathParam("userRoleName") String userRoleName) {
		return getUserIdentityEndpointHelper().deleteUserRole(username, userRoleName);
	}

	@Secured("PERM_WRITE_USER_ROLES")
	@Path(URIKeys.IDENTITY_PATH + "/{userIdentity}" + ROLES_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response updateUserRoles(@PathParam("userIdentity") String username, UserRoles userRoles) {
		return getUserIdentityEndpointHelper().updateUserRoles(username, userRoles);
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
		return AdmissionEndpointImpl.ENDPOINT_PATH;
	}

}
