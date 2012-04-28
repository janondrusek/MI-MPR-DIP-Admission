package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;

public interface PasswordGenerator {

	public UserPassword createUserPassword();

	public void createUserPassword(String plaintext, UserPassword userPassword);

	public void resetUserPassword(UserPassword userPassword);
}
