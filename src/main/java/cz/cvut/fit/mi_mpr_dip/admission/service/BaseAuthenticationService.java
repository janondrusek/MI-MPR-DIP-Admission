package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.adapter.AuthenticationAdapter;

public abstract class BaseAuthenticationService implements AuthenticationService {

	private AuthenticationAdapter authenticationAdapter;

	@Override
	public boolean authenticate(String username, String password) {
		return getAuthenticationAdapter().authenticate(username, password);
	}

	public AuthenticationAdapter getAuthenticationAdapter() {
		return authenticationAdapter;
	}

	public void setAuthenticationAdapter(AuthenticationAdapter authenticationAdapter) {
		this.authenticationAdapter = authenticationAdapter;
	}
}