package cz.cvut.fit.mi_mpr_dip.admission.service.initializing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.RoleAccessiblePropertyConfigurer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.DeduplicationService;

@RooJavaBean
@Service
public class UserRolePermissionServiceImpl implements UserRolePermissionService,
		ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(UserRolePermissionServiceImpl.class);

	private boolean refreshed;
	private int refreshCount;

	@Autowired
	@Qualifier("userRoleDeduplicationService")
	private DeduplicationService<UserRole> deduplicationService;
	@Autowired
	private RoleAccessiblePropertyConfigurer propertyConfigurer;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		refreshCount++;
		if (!isRefreshed()) {
			refreshed = true;
			log.debug("Calling insert default roles on Context Refresh, refreshed={}, refreshCount={}", isRefreshed(),
					getRefreshCount());
			insertDefaultRolesAndPermissions();
		} else {
			log.debug("Skipping insert, already done, refreshed={}, refreshCount={}", isRefreshed(), getRefreshCount());
		}
	}

	@Override
	public void insertDefaultRolesAndPermissions() {
		List<UserRole> userRoles = propertyConfigurer.getProperties();
		log.debug("Inserting default roles [{}]", userRoles);
		for (UserRole userRole : userRoles) {
			deduplicationService.deduplicateAndStore(userRole);
		}
	}
}