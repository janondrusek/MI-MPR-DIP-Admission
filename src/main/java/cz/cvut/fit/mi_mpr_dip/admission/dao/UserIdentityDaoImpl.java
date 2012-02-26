package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Repository
public class UserIdentityDaoImpl implements UserIdentityDao {

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username) {
		UserIdentity userIdentity;
		try {
			userIdentity = UserIdentity.findUserIdentitysByUsernameEquals(username).getSingleResult();
		} catch (EmptyResultDataAccessException e) {
			userIdentity = createUserIdentity();
		}
		return userIdentity;
	}

	private UserIdentity createUserIdentity() {
		return new UserIdentity();
	}

}
