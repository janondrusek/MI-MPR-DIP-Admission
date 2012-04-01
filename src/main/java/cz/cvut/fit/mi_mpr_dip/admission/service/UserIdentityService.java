package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserIdentityService {

	public UserIdentity getUserIdentity(String username);

	public void buildUserIdentity(Admission admission);
}
