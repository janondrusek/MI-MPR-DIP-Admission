package cz.cvut.fit.mi_mpr_dip.admission.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.service.LdapService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service("userIdentityAuthenticationProvider")
public class UserIdentityAuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	private LdapService ldapService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return createAuthentication(authentication);
	}

	private Authentication createAuthentication(Authentication authentication) {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();
		boolean authentified = ldapService.authenticate(username, password);
		if (authentified) {
			return authentication;
		} else {
			throw new BadCredentialsException(username + StringPool.COLON + password);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
