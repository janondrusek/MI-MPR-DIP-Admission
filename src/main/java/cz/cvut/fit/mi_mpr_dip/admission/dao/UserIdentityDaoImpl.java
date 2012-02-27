package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Repository
public class UserIdentityDaoImpl extends AbstractDao implements UserIdentityDao {

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username) {
		return uniqueResult(UserIdentity.class, UserIdentity.findUserIdentitysByUsernameEquals(username));
	}

}
