package cz.cvut.fit.mi_mpr_dip.admission.adapter;

public interface LdapAdapter {
	
	public boolean authenticate(String username, String password);
}
