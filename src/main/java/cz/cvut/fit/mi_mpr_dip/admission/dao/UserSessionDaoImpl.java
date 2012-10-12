package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;

@Repository
public class UserSessionDaoImpl extends Dao<UserSession> implements UserSessionDao {

	private static final Logger log = LoggerFactory.getLogger(UserSessionDaoImpl.class);

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

	@Override
	public void remove(String username, String identifier) {
		try {
			doRemove(username, identifier);
		} catch (Exception e) {
			log.info("Unable to delete [{}]", String.valueOf(e));
			log.debug("Unable to delete", e);
		}
	}

	@Transactional
	private void doRemove(String username, String identifier) {
		UserIdentity userIdentity = UserIdentity.findUserIdentitysByUsernameEquals(username).getSingleResult();
		for (Iterator<UserSession> iterator = userIdentity.getSessions().iterator(); iterator.hasNext();) {
			UserSession userSession = iterator.next();
			if (identifier.equals(userSession.getIdentifier())) {
				iterator.remove();
				userSession.remove();
			}
		}
	}

	@Override
	public void removeExpired(UserIdentity userIdentity) {
		try {
			doRemoveExpired(userIdentity);
		} catch (Exception e) {
			log.info("Unable to delete [{}]", String.valueOf(e));
			log.debug("Unable to delete", e);
		}
	}

	@Transactional
	private void doRemoveExpired(UserIdentity userIdentity) {
		Iterator<UserSession> iterator = userIdentity.getSessions().iterator();
		while (iterator.hasNext()) {
			UserSession session = iterator.next();
			if (isExpired(session)) {
				iterator.remove();
				session.remove();
			}
		}
	}

	private boolean isExpired(UserSession session) {
		Date now = getNow();
		return session.getGrantValidTo().before(now);
	}

	private Date getNow() {
		return new Date();
	}

	@Override
	protected UserSession createEmptyResult() {
		return new UserSession();
	}
}
