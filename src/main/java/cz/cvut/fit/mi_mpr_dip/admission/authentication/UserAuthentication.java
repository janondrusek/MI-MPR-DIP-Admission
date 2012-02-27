package cz.cvut.fit.mi_mpr_dip.admission.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -17061259773042535L;

	private String credentials;

	private String principal;

	public UserAuthentication(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.credentials = credentials;
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public String getCredentials() {
		return credentials;
	}

	@Override
	public String getPrincipal() {
		return principal;
	}

}
