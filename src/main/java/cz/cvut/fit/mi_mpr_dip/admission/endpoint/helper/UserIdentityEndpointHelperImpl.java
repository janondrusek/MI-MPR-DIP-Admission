package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserRoleDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserSessionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.UserRoleUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.service.user.UserSessionService;
import cz.cvut.fit.mi_mpr_dip.admission.validation.PrincipalValidator;

@RooJavaBean
public class UserIdentityEndpointHelperImpl extends CommonEndpointHelper<UserIdentity> implements
		UserIdentityEndpointHelper {

	private static final Logger log = LoggerFactory.getLogger(UserIdentityEndpointHelperImpl.class);

	@Autowired
	private PrincipalValidator principalValidator;

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	@Autowired
	private UserIdentityService userIdentityService;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private UserSessionDao userSessionDao;

	@PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
	@Override
	public Response getUserIdentity() {
		Authentication authentication = getAuthentication();
		UserIdentity userIdentity = doGetUserIdentityWithRetry(authentication);
		getUserIdentityService().addAdmissionLink(userIdentity);

		return getOkResponse(userIdentity);
	}

	private UserIdentity doGetUserIdentityWithRetry(Authentication authentication) {
		try {
			return doGetUserIdentity(authentication);
		} catch (Exception e) {
			log.info("Failed to get UserIdentity, retrying [{}]", String.valueOf(e));
			return doGetUserIdentityWithRetry(authentication);
		}
	}

	@Transactional
	private UserIdentity doGetUserIdentity(Authentication authentication) {
		UserIdentity userIdentity = getUserIdentityService().getUserIdentity(authentication.getPrincipal().toString());
		getUserSessionService().removeExpired(userIdentity);
		getUserSessionService().ensureUserSession(userIdentity);
		getUserSessionService().prolong(userIdentity);

		return userIdentity;
	}

	private Authentication getAuthentication() {
		return getSecurityContextHolderStrategy().getContext().getAuthentication();
	}

	@Override
	public Response deleteUserSession(String username, String identifier) {
		UserSession userSession = getUserSessionDao().getUserSession(identifier);
		UserIdentity userIdentity = userSession.getUserIdentity();
		validateUserSession(username, userIdentity, userSession);
		getUserSessionDao().remove(username, identifier);

		return getOkResponse();
	}

	private void validateUserSession(String username, UserIdentity userIdentity, UserSession userSession) {
		if (userSession.getIdentifier() == null || isNotEqual(username, userIdentity.getUsername())) {
			throwNotFoundBusinessException();
		}
		getPrincipalValidator().validatePrincipal(userIdentity.getUsername());
	}

	@Transactional
	@Override
	public Response deleteUserRole(String username, String userRoleName) {
		UserIdentity userIdentity = getUserIdentityOrThrowNotFound(username);
		UserRole userRole = getUserRoleOrThrowNotFound(userRoleName);
		validate(userRole, userIdentity);
		userIdentity.getRoles().remove(userRole);
		userIdentity.persist();

		return getOkResponse();
	}

	private UserRole getUserRoleOrThrowNotFound(String userRoleName) {
		UserRole userRole = getUserRoleDao().getUserRole(userRoleName);
		UserRoleUniqueConstraint uniqueConstraint = new UserRoleUniqueConstraint(userRole);
		if (uniqueConstraint.isNotFound()) {
			throwNotFoundBusinessException();
		}
		return userRole;
	}

	private void validate(UserRole userRole, UserIdentity userIdentity) {
		Set<UserRole> roles = userIdentity.getRoles();
		if (CollectionUtils.isEmpty(roles) || !roles.contains(userRole)) {
			throwNotFoundBusinessException();
		}
	}

	@Transactional
	@Override
	public Response updateUserRoles(String username, UserRoles userRoles) {
		UserIdentity userIdentity = getUserIdentityOrThrowNotFound(username);

		validateUserRoles(username, userIdentity, userRoles);
		getUserIdentityService().updateUserRoles(userIdentity, userRoles);
		return getOkResponse();
	}

	private void validateUserRoles(String username, UserIdentity userIdentity, UserRoles userRoles) {
		if (isNotEqual(username, userIdentity.getUsername())) {
			throwNotFoundBusinessException();
		}
		getPrincipalValidator().validatePrincipal(userIdentity.getUsername());
		getBeanValidator().validate(userRoles);
	}

	private UserIdentity getUserIdentityOrThrowNotFound(String username) {
		UserIdentity userIdentity = getUserIdentityDao().getUserIdentity(username);
		validateNotFound(userIdentity);
		return userIdentity;
	}

	private boolean isNotEqual(String one, String two) {
		return !StringUtils.equals(one, two);
	}

	@Override
	protected boolean isNotFound(UserIdentity userIdentity) {
		return userIdentity.getUsername() == null;
	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}
}
