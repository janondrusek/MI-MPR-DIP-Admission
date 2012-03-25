package cz.cvut.fit.mi_mpr_dip.admission.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.service.LdapService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service("userIdentityAuthenticationProvider")
public class UserIdentityAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AuthenticationUtil authenticationUtil;

	@Autowired
	private LdapService ldapService;

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return createAuthentication(authentication);
	}

	private Authentication createAuthentication(Authentication authentication) {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		boolean authentified = ldapService.authenticate(username, password);
		if (authentified) {
			return createAuthentication(username, password);
		} else {
			throw new BadCredentialsException(username + StringPool.COLON + password);
		}
	}

	private Authentication createAuthentication(String username, String password) {
		UserIdentity userIdentity = userIdentityDao.getUserIdentity(username);
		return new UsernamePasswordAuthenticationToken(username, password,
				authenticationUtil.getAuthorities(userIdentity.getRoles()));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
