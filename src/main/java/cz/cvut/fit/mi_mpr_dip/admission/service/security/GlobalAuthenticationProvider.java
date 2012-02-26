package cz.cvut.fit.mi_mpr_dip.admission.service.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class GlobalAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
