package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserSessionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
public class AdmissionEndpointHelper implements EndpointHelper {

	public static final String IDENTITY_PATH = "/identity";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private UserSessionDao userSessionDao;

	@Autowired
	private UserIdentityService userIdentityService;

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	@PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
	@Override
	public Response getUserIdentity() {
		Authentication authentication = getAuthentication();
		return Response.ok(getUserIdentityService().getUserIdentity(authentication.getPrincipal().toString())).build();
	}

	private Authentication getAuthentication() {
		return getSecurityContextHolderStrategy().getContext().getAuthentication();
	}

	@Override
	public Response deleteUserSession(String username, String identifier) {
		UserSession userSession = getUserSessionDao().getUserSession(identifier);
		UserIdentity userIdentity = userSession.getUserIdentity();
		validateUserSession(username, userIdentity, userSession);
		ResponseBuilder builder;
		getUserSessionDao().remove(username, identifier);
		builder = Response.ok();

		return builder.build();
	}

	private void validateUserSession(String username, UserIdentity userIdentity, UserSession userSession) {
		if (userSession.getIdentifier() == null || isNotEqual(username, userIdentity.getUsername())) {
			throwBusinessException(HttpServletResponse.SC_NOT_FOUND, "Not Found");
		} else if (isSomeoneElsesSession(userIdentity.getUsername())) {
			throwBusinessException(HttpServletResponse.SC_FORBIDDEN, new AccessDeniedException("Access denied"));
		}
	}
	
	private void throwBusinessException(Integer code, String message) {
		throw new BusinessException(code, message);
	}

	private void throwBusinessException(Integer code, Throwable t) {
		throw new BusinessException(code, t);
	}

	private boolean isSomeoneElsesSession(String username) {
		return isNotEqual(getSecurityContextHolderStrategy().getContext().getAuthentication().getName(), username);
	}

	private boolean isNotEqual(String one, String two) {
		return !StringUtils.equals(one, two);
	}

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			builder = Response.ok(admission);
		}
		return builder.build();
	}

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			admission.remove();
			builder = Response.ok();
		}
		return builder.build();
	}

	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			action.performAction(admission, actor);
			admission.merge();
			builder = Response.seeOther(new URI(baseLocation + StringPool.SLASH + admission.getCode()));
		}
		return builder.build();
	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}
}
