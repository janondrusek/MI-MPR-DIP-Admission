package cz.cvut.fit.mi_mpr_dip.admission.adapter;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public class DummyPwdAuthenticationAdapter extends DummyAuthenticationAdapter implements PwdAuthenticationAdapter {

	@Override
	public boolean authenticate(UserIdentity userIdentity, String password) {
		return true;
	}

}
