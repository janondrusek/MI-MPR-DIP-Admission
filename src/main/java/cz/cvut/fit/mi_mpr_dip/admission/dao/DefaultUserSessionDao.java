package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;

@Repository
public class DefaultUserSessionDao extends AbstractDao implements UserSessionDao {

	@Transactional(readOnly = true)
	@Override
	public UserSession getUserSession(String identifier) {
		return uniqueResult(UserSession.class,
				UserSession.findUserSessionsByIdentifierEqualsAndGrantValidToGreaterThan(identifier, getNow()));
	}

	private Date getNow() {
		return new Date();
	}

}
