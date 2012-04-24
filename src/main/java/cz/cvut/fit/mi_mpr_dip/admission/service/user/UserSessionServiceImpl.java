package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.util.RandomStringGenerator;

@RooJavaBean
public class UserSessionServiceImpl implements UserSessionService {

	private Long grantValidSeconds;

	@Autowired
	private RandomStringGenerator randomStringGenerator;

	@Transactional(readOnly = true)
	@Override
	public void ensureUserSession(UserIdentity userIdentity) {
		Set<UserSession> sessions = getSessions(userIdentity);

		deleteExpired(sessions);
		if (isEmpty(sessions)) {
			sessions.add(createSession());
		}
		userIdentity.setSessions(sessions);
		for (UserSession session : sessions) {
			session.setGrantValidTo(getGrantValidTo());
			session.setUserIdentity(userIdentity);
		}
	}

	private Set<UserSession> getSessions(UserIdentity userIdentity) {
		Set<UserSession> sessions = userIdentity.getSessions();
		if (isEmpty(sessions)) {
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
		session.setIdentifier(getRandomStringGenerator().generateRandomAlphanumeric());

		return session;
	}

	private Date getGrantValidTo() {
		return new Date(getNow().getTime() + getGrantValidSeconds() * 1000);
	}

	private boolean isExpired(UserSession session) {
		Date now = getNow();
		return session.getGrantValidTo().before(now);
	}

	private Date getNow() {
		return new Date();
	}

	private boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	@Required
	public void setGrantValidSeconds(Long grantValidSeconds) {
		this.grantValidSeconds = grantValidSeconds;
	}
}
