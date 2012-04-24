package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserPasswordService {

	public void createRandomPassword(UserIdentity userIdentity);
}
