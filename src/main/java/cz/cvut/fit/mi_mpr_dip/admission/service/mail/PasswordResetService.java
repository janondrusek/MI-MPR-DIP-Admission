package cz.cvut.fit.mi_mpr_dip.admission.service.mail;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface PasswordResetService {

	public void send(UserIdentity userIdentity, String[] emails);
}
