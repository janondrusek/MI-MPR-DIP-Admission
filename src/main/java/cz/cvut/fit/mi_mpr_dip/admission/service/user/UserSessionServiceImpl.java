package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserSessionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringGenerator;

@RooJavaBean
public class UserSessionServiceImpl implements UserSessionService {

	private Long grantValidSeconds;

	@Autowired
	@Qualifier("UUIDStringGenerator")
	private StringGenerator stringGenerator;

	@Autowired
	private UserSessionDao userSessionDao;

	@Transactional
	@Override
	public void ensureUserSession(UserIdentity userIdentity) {
		Set<UserSession> sessions = getSessions(userIdentity);
		if (isEmpty(sessions)) {
			sessions.add(createSession(userIdentity));
		}
		userIdentity.setSessions(sessions);
		userIdentity.persist();
	}

	private Set<UserSession> getSessions(UserIdentity userIdentity) {
		Set<UserSession> sessions = userIdentity.getSessions();
		if (isEmpty(sessions)) {
			sessions = new HashSet<>();
		}
		return sessions;
	}

	private UserSession createSession(UserIdentity userIdentity) {
		UserSession session = new UserSession();
		session.setIdentifier(getStringGenerator().generateRandomAlphanumeric());
		session.setGrantValidTo(getGrantValidTo());
		session.setUserIdentity(userIdentity);

		return session;
	}

	private Date getGrantValidTo() {
		return new Date(getNow().getTime() + getGrantValidSeconds() * 1000);
	}

	private Date getNow() {
		return new Date();
	}

	private boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	@Transactional
	@Override
	public void removeExpired(UserIdentity userIdentity) {
		getUserSessionDao().removeExpired(userIdentity);
		userIdentity.persist();
	}

	@Transactional
	@Override
	public void prolong(UserIdentity userIdentity) {
		for (UserSession session : userIdentity.getSessions()) {
			session.setGrantValidTo(getGrantValidTo());
		}
		userIdentity.persist();
	}

	@Required
	public void setGrantValidSeconds(Long grantValidSeconds) {
		this.grantValidSeconds = grantValidSeconds;
	}

}
