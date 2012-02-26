package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

public interface UserIdentityDao {

	public UserIdentity getUserIdentity(String username);
}
