package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentityAuthentication;

@Repository
public class DefaultUserIdentityDao extends AbstractDao implements UserIdentityDao {

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username) {
		return uniqueResult(UserIdentity.class, UserIdentity.findUserIdentitysByUsernameEquals(username));
	}

	@Transactional(readOnly = true)
	@Override
	public UserIdentity getUserIdentity(String username, UserIdentityAuthentication authentication) {
		return uniqueResult(UserIdentity.class,
				UserIdentity.findUserIdentitysByUsernameEqualsAndAuthenticationEquals(username, authentication));
	}

}
