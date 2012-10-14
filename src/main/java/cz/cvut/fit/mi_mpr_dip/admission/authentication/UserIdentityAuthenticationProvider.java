package cz.cvut.fit.mi_mpr_dip.admission.authentication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;
import cz.cvut.fit.mi_mpr_dip.admission.service.auth.AuthenticationService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class UserIdentityAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(UserIdentityAuthenticationProvider.class);

	@Autowired
	private AuthenticationUtil authenticationUtil;

	@Autowired
	private UserIdentityDao userIdentityDao;

	private Map<UserIdentityAuthentication, AuthenticationService> authenticationServices;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return createAuthentication(authentication);
	}

	private Authentication createAuthentication(Authentication authentication) {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		UserIdentity userIdentity = userIdentityDao.getUserIdentity(username);
		log.info("Authenticating, found [{}] for username [{}]", userIdentity, username);
		if (isAuthentified(username, password, userIdentity)) {
			log.info("Successfuly authentified [{}]", username);
			return createAuthentication(username, password, userIdentity);
		}
		throw new BadCredentialsException(username + StringPool.COLON + password);
	}

	private boolean isAuthentified(String username, String password, UserIdentity userIdentity) {
		if (userIdentity.getUsername() != null) {
			UserIdentityAuthentication authentication = userIdentity.getAuthentication();
			AuthenticationService authenticationService = authenticationServices.get(authentication);
			log.info("Found authenticationService [{}] for authentication [{}]", authenticationService, authentication);
			return authenticationService.authenticate(username, password);
		}
		return false;
	}

	private Authentication createAuthentication(String username, String password, UserIdentity userIdentity) {
		return new UsernamePasswordAuthenticationToken(username, password,
				authenticationUtil.getAuthorities(userIdentity.getRoles()));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Required
	public void setAuthenticationServices(Map<UserIdentityAuthentication, AuthenticationService> authenticationServices) {
		this.authenticationServices = authenticationServices;
	}
}
