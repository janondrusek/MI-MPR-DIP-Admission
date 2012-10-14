package org.springframework.beans.factory.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPermission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class RoleAccessiblePropertyConfigurer extends PropertyResourceConfigurer implements
		AccessiblePropertyConfigurer<List<UserRole>> {

	private List<UserRole> defaultRoles = new ArrayList<>();

	@Override
	public List<UserRole> getProperties() {
		return defaultRoles;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		for (Object key : props.keySet()) {
			String role = key.toString();
			defaultRoles.add(createUserRole(role, props.get(key)));
		}
	}

	private UserRole createUserRole(String role, Object value) {
		UserRole userRole = new UserRole();
		userRole.setName(role);
		userRole.setPermissions(createUserPermissions(value));
		return userRole;
	}

	private Set<UserPermission> createUserPermissions(Object value) {
		return createUserPermissions(StringUtils.split(ObjectUtils.toString(value), StringPool.COMMA));
	}

	private Set<UserPermission> createUserPermissions(String[] names) {
		Set<UserPermission> permissions = new HashSet<>();
		for (String name : names) {
			UserPermission userPermission = new UserPermission();
			userPermission.setName(name);
			permissions.add(userPermission);
		}
		return permissions;
	}
}
