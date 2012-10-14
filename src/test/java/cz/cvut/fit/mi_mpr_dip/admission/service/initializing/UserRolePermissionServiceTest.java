package cz.cvut.fit.mi_mpr_dip.admission.service.initializing;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.config.RoleAccessiblePropertyConfigurer;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserRole;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.user.UserRoleDeduplicationService;
import eu.prvaci.util.test.annotation.Mock;
import eu.prvaci.util.test.annotation.Tested;
import eu.prvaci.util.test.mock.EasyMockTest;

public class UserRolePermissionServiceTest extends EasyMockTest {

	@Tested
	private UserRolePermissionServiceImpl userRolePermissionService;

	@Mock
	private UserRoleDeduplicationService deduplicationService;
	@Mock
	private RoleAccessiblePropertyConfigurer propertyConfigurer;

	@Test
	public void testMultipleRefreshes() {
		int refreshCount = 5;
		List<UserRole> userRoles = new ArrayList<>();
		userRoles.add(new UserRole());

		assertInitialState();
		setExpectations(userRoles);

		replay(getMocks());
		for (int i = 0; i < refreshCount; i++) {
			userRolePermissionService.onApplicationEvent(null);
		}
		assertFinalState(refreshCount);
		verify(getMocks());
	}

	private void assertInitialState() {
		assertEquals(0, userRolePermissionService.getRefreshCount());
		assertFalse(userRolePermissionService.isRefreshed());
	}

	private void setExpectations(List<UserRole> userRoles) {
		expect(propertyConfigurer.getProperties()).andReturn(userRoles);
		deduplicationService.deduplicateAndStore(same(userRoles.get(0)));
	}

	private void assertFinalState(int refreshCount) {
		assertEquals(refreshCount, userRolePermissionService.getRefreshCount());
		assertTrue(userRolePermissionService.isRefreshed());
	}
}
