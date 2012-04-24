package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserIdentityService {

	public UserIdentity getUserIdentity(String username);

	public void buildUserIdentity(Admission admission);

	public void updateUserRoles(UserIdentity userIdentity, UserRoles userRoles);
}
