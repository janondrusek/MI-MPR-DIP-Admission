package cz.cvut.fit.mi_mpr_dip.admission.adapter;

public class DummyAuthenticationAdapter implements AuthenticationAdapter {

	@Override
	public boolean authenticate(String username, String password) {
		return true;
	}

}
