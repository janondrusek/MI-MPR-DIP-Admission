package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;

@Repository
public class UserRoleDaoImpl extends AbstractDao<UserRole> implements UserRoleDao {

	@Transactional(readOnly = true)
	@Override
	public UserRole getUserRole(String name) {
		return getUserRoleQuietly(name);
	}

	private UserRole getUserRoleQuietly(String name) {
		UserRole userRole;
		try {
			userRole = uniqueResult(UserRole.findUserRolesByNameEquals(name));
		} catch (Exception e) {
			userRole = processException(e);
		}
		return userRole;
	}

	@Override
	protected UserRole createEmptyResult() {
		return new UserRole();
	}

}
