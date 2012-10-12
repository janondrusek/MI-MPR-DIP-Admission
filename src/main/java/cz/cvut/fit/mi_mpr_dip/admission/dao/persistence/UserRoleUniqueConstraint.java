package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

import org.apache.commons.lang3.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;

@RooJavaBean
public class UserRoleUniqueConstraint extends BaseUniqueConstraint<UserRole> {

	private String name;

	public UserRoleUniqueConstraint(UserRole userRole) {
		this(userRole.getName());
	}
	
	private UserRoleUniqueConstraint(String name) {
		this.name = name;
	}

	@Override
	public Boolean isDuplicate(UserRole duplicate) {
		return StringUtils.equals(getName(), duplicate.getName());
	}

	@Override
	public Boolean isNotFound() {
		return getName() == null;
	}

}
