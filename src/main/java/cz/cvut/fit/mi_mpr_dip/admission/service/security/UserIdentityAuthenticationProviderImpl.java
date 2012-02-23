package cz.cvut.fit.mi_mpr_dip.admission.service.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Service("userIdentityAuthenticationProvider")
public class UserIdentityAuthenticationProviderImpl implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserIdentity.class.isAssignableFrom(authentication);
	}

}
