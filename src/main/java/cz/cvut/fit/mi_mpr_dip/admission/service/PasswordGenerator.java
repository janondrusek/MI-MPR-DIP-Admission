package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;

public interface PasswordGenerator {

	public UserPassword createUserPassword();
}
