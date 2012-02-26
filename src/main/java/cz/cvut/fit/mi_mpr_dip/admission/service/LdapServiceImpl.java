package cz.cvut.fit.mi_mpr_dip.admission.service;

import org.springframework.beans.factory.annotation.Required;

import cz.cvut.fit.mi_mpr_dip.admission.adapter.LdapAdapter;

public class LdapServiceImpl implements LdapService {

	private LdapAdapter ldapAdapter;

	@Override
	public boolean authenticate(String username, String password) {
		return ldapAdapter.authenticate(username, password);
	}

	@Required
	public void setLdapAdapter(LdapAdapter ldapAdapter) {
		this.ldapAdapter = ldapAdapter;
	}

}
