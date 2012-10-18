package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public class ProcessServiceTest extends BaseSpringJbpmTest {

	private static final String BLANK = "blank";

	@Autowired
	private ProcessService processService;

	private Admission admission;

	@Override
	@Before
	public void setUp() {
		TestAdmissionData data = new TestAdmissionData();
		admission = data.getAdmission();
	}

	@Test
	public void testRunBlankProcess() {
		admission.getProgramme().getDegree().setName(BLANK);
		processService.runProcess(admission);
	}

	@Test
	public void testRunProcess() {
		for (int i = 0; i < 3; i++) {
			processService.runProcess(admission);
		}
	}

	// @Test
	// public void testHumanTaskProcess() {
	// try {
	// UserTransaction ut = (UserTransaction) new
	// InitialContext().lookup("java:comp/UserTransaction");
	// ut.begin();
	//
	// /*
	// * Get the local task service
	// */
	// TaskService taskService = jbpmTaskService.getTaskService();
	//
	// processService.runProcess(admission);
	//
	// /*
	// * Retrive the tasks owned by a user
	// */
	// List<TaskSummary> list =
	// taskService.getTasksAssignedAsPotentialOwner("test", "en-UK");
	// TaskSummary task = list.get(0);
	//
	// System.out.println("test is executing task " + task.getName());
	// taskService.start(task.getId(), "test");
	// taskService.complete(task.getId(), "test", null);
	//
	// ut.commit();
	// } catch (Throwable t) {
	// // TODO Auto-generated catch block
	// t.printStackTrace();
	// }
	// }
}