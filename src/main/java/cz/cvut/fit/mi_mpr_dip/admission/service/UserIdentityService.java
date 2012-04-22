package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRoles;

public interface UserIdentityService {

	public UserIdentity getUserIdentity(String username);

	public void buildUserIdentity(Admission admission);

	public void updateUserRoles(UserIdentity userIdentity, UserRoles userRoles);
}
