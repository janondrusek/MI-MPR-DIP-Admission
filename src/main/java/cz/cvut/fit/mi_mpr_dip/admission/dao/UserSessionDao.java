package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;

public interface UserSessionDao {
	public UserSession getUserSession(String identifier);

	public void remove(String username, String identifier);

	public void removeExpired(UserIdentity userIdentity);
}
