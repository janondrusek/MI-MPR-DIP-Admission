package cz.cvut.fit.mi_mpr_dip.admission.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public interface UserRolePermissionService extends ApplicationListener<ContextRefreshedEvent> {

	public void insertDefaultRolesAndPermissions();
}
