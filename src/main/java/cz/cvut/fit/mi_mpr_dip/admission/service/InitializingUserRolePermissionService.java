package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.List;

import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.RoleAccessiblePropertyConfigurer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;

@Service
@PersistenceContext
public class InitializingUserRolePermissionService implements UserRolePermissionService {

	@Autowired
	@Qualifier("userRoleDeduplicationService")
	private DeduplicationService<UserRole> deduplicationService;
	@Autowired
	private RoleAccessiblePropertyConfigurer propertyConfigurer;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		insertDefaultRolesAndPermissions();
	}

	@Override
	public void insertDefaultRolesAndPermissions() {
		List<UserRole> userRoles = propertyConfigurer.getProperties();
		for (UserRole userRole : userRoles) {
			deduplicationService.deduplicateAndStore(userRole);
		}
	}
}