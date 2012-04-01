package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;

public interface UserIdentityDao {
	
	public UserIdentity getUserIdentity(String username);

	public UserIdentity getUserIdentity(String username, UserIdentityAuthentication authentication);
}
