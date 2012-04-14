package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPermission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;

@Service
public class UserRoleDeduplicationService implements DeduplicationService<UserRole> {

	@Override
	@Transactional
	public void deduplicateAndStore(UserRole userRole) {
		List<UserPermission> dbPermissions = UserPermission.findAllUserPermissions();

		deduplicate(userRole.getPermissions(), dbPermissions);
		deduplicate(userRole).persist();
	}

	private void deduplicate(Set<UserPermission> permissions, List<UserPermission> dbPermissions) {
		Set<UserPermission> replacements = new HashSet<UserPermission>();
		for (Iterator<UserPermission> iterator = permissions.iterator(); iterator.hasNext();) {
			UserPermission userPermission = iterator.next();
			if (dbPermissions.contains(userPermission)) {
				replacements.add(dbPermissions.get(dbPermissions.indexOf(userPermission)));
				iterator.remove();
			}
		}
		permissions.addAll(replacements);
	}

	private UserRole deduplicate(UserRole userRole) {
		List<UserRole> userRoles = UserRole.findUserRolesByNameEquals(userRole.getName()).getResultList();
		if (CollectionUtils.isNotEmpty(userRoles))  {
			UserRole dbUserRole = userRoles.get(0);
			dbUserRole.getPermissions().addAll(userRole.getPermissions());
			userRole = dbUserRole;
		}
		return userRole;
	}
}
