package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;

@Repository
public class DefaultUserSessionDao extends AbstractDao<UserSession> implements UserSessionDao {

	@Transactional(readOnly = true)
	@Override
	public UserSession getUserSession(String identifier) {
		return getUserSessionQuietly(identifier);
	}

	private UserSession getUserSessionQuietly(String identifier) {
		UserSession userSession;
		try {
			userSession = uniqueResult(UserSession.findUserSessionsByIdentifierEqualsAndGrantValidToGreaterThan(
					identifier, getNow()));
		} catch (Exception e) {
			userSession = processException(e);
		}
		return userSession;
	}

	private Date getNow() {
		return new Date();
	}

	@Override
	protected UserSession createEmptyResult() {
		return new UserSession();
	}
}
