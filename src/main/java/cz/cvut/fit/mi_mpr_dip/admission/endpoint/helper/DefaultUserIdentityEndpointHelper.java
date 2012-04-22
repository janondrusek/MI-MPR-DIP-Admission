package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserSessionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.validation.BeanValidator;
import cz.cvut.fit.mi_mpr_dip.admission.validation.PrincipalValidator;

@RooJavaBean
public class DefaultUserIdentityEndpointHelper implements UserIdentityEndpointHelper {

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	@Autowired
	private UserIdentityService userIdentityService;
	
	@Autowired
	private UserIdentityDao userIdentityDao;
	
	@Autowired
	private UserSessionDao userSessionDao;

	@Autowired
	private BeanValidator beanValidator;

	@Autowired
	private PrincipalValidator principalValidator;

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
		}
		getPrincipalValidator().validatePrincipal(userIdentity.getUsername());
	}

	private void throwBusinessException(Integer code, String message) {
		throw new BusinessException(code, message);
	}

	private boolean isNotEqual(String one, String two) {
		return !StringUtils.equals(one, two);
	}

	@Transactional
	@Override
	public Response updateUserRoles(String username, UserRoles userRoles) {
		UserIdentity userIdentity = getUserIdentityDao().getUserIdentity(username);
		validateUserRoles(userIdentity, userRoles);
		getUserIdentityService().updateUserRoles(userIdentity, userRoles);
		return Response.ok().build();
	}

	private void validateUserRoles(UserIdentity userIdentity, UserRoles userRoles) {
		getPrincipalValidator().validatePrincipal(userIdentity.getUsername());
		getBeanValidator().validate(userRoles);
	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}
}
