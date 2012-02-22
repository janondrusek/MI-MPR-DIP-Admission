package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class UserIdentityServiceImpl implements UserIdentityService {

	private Long grantValidSeconds;

	@Transactional
	@Override
	public UserIdentity getUserIdentity(String username) {
		UserIdentity userIdentity = UserIdentity.findUserIdentitysByUsernameEquals(username).getSingleResult();
		if (userIdentity == null) {
			userIdentity = createUserIdentity(username);
		}
		ensureSession(userIdentity);
		userIdentity.persist();
		return userIdentity;
	}

	private UserIdentity createUserIdentity(String username) {
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUsername(username);
		return userIdentity;
	}

	private void ensureSession(UserIdentity userIdentity) {
		List<UserSession> sessions = userIdentity.getSessions();
		if (shouldCreateNewSession(sessions)) {
			sessions.add(createSession());
		}
		deleteExpired(sessions);

		userIdentity.setSessions(sessions);
	}

	private UserSession createSession() {
		UserSession session = new UserSession();
		session.setGrantValidTo(getGrantValidTo());
		session.setIdentifier(getRandomIdentifier());

		return session;
	}

	private Date getGrantValidTo() {
		return new Date(getNow().getTime() + grantValidSeconds);
	}

	private String getRandomIdentifier() {
		return UUID.randomUUID().toString().replaceAll(StringPool.DASH, StringPool.BLANK);
	}

	private void deleteExpired(List<UserSession> sessions) {
		Iterator<UserSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			UserSession session = iterator.next();
			if (isExpired(session)) {
				sessions.remove(session);
				session.remove();
			}
		}

	}

	private boolean isExpired(UserSession session) {
		return session.getGrantValidTo().before(getNow());
	}

	private boolean shouldCreateNewSession(List<UserSession> sessions) {
		return sessions.size() == 0;
	}

	private Date getNow() {
		return new Date();
	}

	@Required
	public void setGrantValidSeconds(Long grantValidSeconds) {
		this.grantValidSeconds = grantValidSeconds;
	}

}
