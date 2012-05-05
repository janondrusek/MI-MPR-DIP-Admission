package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;

@Repository
public class UserIdentityDaoImpl extends AbstractDao<UserIdentity> implements UserIdentityDao {

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username) {
		return getUserIdentityQuietly(username);
	}

	private UserIdentity getUserIdentityQuietly(String username) {
		UserIdentity userIdentity;
		try {
			userIdentity = uniqueResult(UserIdentity.findUserIdentitysByUsernameEquals(username));
		} catch (Exception e) {
			userIdentity = processException(e);
		}
		return userIdentity;
	}

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username, UserIdentityAuthentication authentication) {
		return getUserIdentityQuietly(username, authentication);
	}

	private UserIdentity getUserIdentityQuietly(String username, UserIdentityAuthentication authentication) {
		UserIdentity userIdentity;
		try {
			userIdentity = uniqueResult(UserIdentity.findUserIdentitysByUsernameEqualsAndAuthenticationEquals(username,
					authentication));

		} catch (Exception e) {
			userIdentity = processException(e);
		}
		return userIdentity;
	}

	@Override
	protected UserIdentity createEmptyResult() {
		return new UserIdentity();
	}

}
