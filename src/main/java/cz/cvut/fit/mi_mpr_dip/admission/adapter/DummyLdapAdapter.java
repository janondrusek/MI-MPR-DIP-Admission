package cz.cvut.fit.mi_mpr_dip.admission.adapter;

public class DummyLdapAdapter implements LdapAdapter {

	@Override
	public boolean authenticate(String username, String password) {
		return true;
	}

}
