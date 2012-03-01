package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class UserIdentityServiceImpl implements UserIdentityService {

	private Long grantValidSeconds;

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Transactional
	@Override
	public UserIdentity getUserIdentity(String username) {
		UserIdentity userIdentity = userIdentityDao.getUserIdentity(username);
		if (userIdentity.getUsername() == null) {
			userIdentity.setUsername(username);
		}
		ensureSession(userIdentity);
		userIdentity.persist();
		return userIdentity;
	}

	private void ensureSession(UserIdentity userIdentity) {
		Set<UserSession> sessions = getSessions(userIdentity);

		deleteExpired(sessions);
		if (isNullOrEmpty(sessions)) {
			sessions.add(createSession());
		}
		userIdentity.setSessions(sessions);
		for (UserSession session : sessions) {
			session.setUserIdentity(userIdentity);
		}
	}

	private Set<UserSession> getSessions(UserIdentity userIdentity) {
		Set<UserSession> sessions = userIdentity.getSessions();
		if (isNullOrEmpty(sessions)) {
			sessions = new HashSet<UserSession>();
		}
		return sessions;
	}

	private void deleteExpired(Set<UserSession> sessions) {
		Iterator<UserSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			UserSession session = iterator.next();
			if (isExpired(session)) {
				sessions.remove(session);
				session.remove();
			}
		}
	}

	private UserSession createSession() {
		UserSession session = new UserSession();
		session.setGrantValidTo(getGrantValidTo());
		session.setIdentifier(getRandomIdentifier());

		return session;
	}

	private Date getGrantValidTo() {
		return new Date(getNow().getTime() + grantValidSeconds * 1000);
	}

	private String getRandomIdentifier() {
		return UUID.randomUUID().toString().replaceAll(StringPool.DASH, StringPool.BLANK);
	}

	private boolean isExpired(UserSession session) {
		Date now = getNow();
		return session.getGrantValidTo().before(now);
	}

	private boolean isNullOrEmpty(Set<UserSession> sessions) {
		return CollectionUtils.isEmpty(sessions);
	}

	private Date getNow() {
		return new Date();
	}

	@Required
	public void setGrantValidSeconds(Long grantValidSeconds) {
		this.grantValidSeconds = grantValidSeconds;
	}

}
