package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.JbpmAccessiblePropertyConfigurer;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.BSPProcessEvaluator;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.DefaultProcessEvaluator;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.ProcessEvaluator;

/**
 * This is a sample file to launch a process.
 */
public class ProcessTest extends BaseSpringJbpmTest {

	private static final String DOMAIN = "cz.cvut.fit.mi_mpr_dip.admission.";
	private static final String BLANK = "blank";
	private static final String BPMN = ".bpmn";
	private static final String EMAIL_TO = "emailTo";

	private static String debugEmail = "root@localhost";

	@Autowired
	private JbpmAccessiblePropertyConfigurer propertyConfigurer;

	private Admission admission;

	@Override
	@Before
	public void setUp() {
		TestAdmissionData data = new TestAdmissionData();
		admission = data.getAdmission();
	}

	@Test
	public void runBlankProcess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("test/" + BLANK + BPMN);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + BLANK);

		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Note", "End");
	}

	@Test
	public void runBSPMainProcess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession(readKnowledgeBase());

		// Test HumanTaskHandler
		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Email", testHandler);

		knowledgeSession.startProcess(DOMAIN + "2012_bsp_main", getProcessParameters(admission));

		for (int i = 0; i < 6; i++) {
			knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);
		}
	}

	@Test
	public void runMSPMainProcess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession(readKnowledgeBase());

		// knowledgeSession.startProcess(DOMAIN + "2012_msp_main", getProcessParameters(admission));
	}

	@Test
	public void runAdmissionTestSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession(readKnowledgeBase());
		
		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Email", testHandler);
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "2012_admission_test",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Gateway - Invited to regular AT", "Register for regular AT");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Register for regular AT");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "Gateway - Regular AT action (REG/APP)");
		
		ProcessEvaluator evaluator = new DefaultProcessEvaluator();
		
		if (evaluator.evalRegisterForAT(admission)) {
			completeNodes(2, knowledgeSession, testHandler);
			assertNodeTriggered(processInstance.getId(), "Gateway - Backtrack from AT");
			
			if (evaluator.evalBackFromAT(admission)) {
				// TODO
			}
		} else if (evaluator.evalApologyFromAT(admission)) {
			completeNodes(1, knowledgeSession, testHandler);
			assertNodeTriggered(processInstance.getId(), "Gateway - Apology from regular AT request result");
			
			if (evaluator.evalApologyApproval(admission)) {
				assertNodeTriggered(processInstance.getId(), "Register for alternative AT");
				assertNodeActive(processInstance.getId(), knowledgeSession, "Register for alternative AT");

				knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

				assertNodeTriggered(processInstance.getId(), "Alternative AT");
				completeNodes(2, knowledgeSession, testHandler);

			} else {
				// TODO
			}
		}
		
		assertNodeTriggered(processInstance.getId(), "Gateway - Test completed", "Admission test result");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Admission test result");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);
		
		assertNodeTriggered(processInstance.getId(), "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);

	}

	@Test
	public void runRegistrationSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession(readKnowledgeBase());

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Email", testHandler);
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "2012_registration",
				getProcessParameters(admission));
		
		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Gateway - Invited to RR", "Register for RR");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Register for RR");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "Gateway - Regular R action (REG/APP)");
		
		ProcessEvaluator evaluator = new DefaultProcessEvaluator();

		if (evaluator.evalRegisterForREG(admission)) {
			completeNodes(1, knowledgeSession, testHandler);
			assertNodeTriggered(processInstance.getId(), "Gateway - Backtrack from RR");
			
			if (evaluator.evalBackFromREG(admission)) {
				// TODO
			} else if (evaluator.evalRegistrationDone(admission)) {
			}
		} else if (evaluator.evalApologyFromREG(admission)) {
			completeNodes(1, knowledgeSession, testHandler);
			assertNodeTriggered(processInstance.getId(), "Gateway - Apology from RR request result");
			
			if (evaluator.evalRegistrationApologyApproval(admission)) {
				assertNodeTriggered(processInstance.getId(), "Gateway - Invited to AR", "Register for AR");
				assertNodeActive(processInstance.getId(), knowledgeSession, "Register for AR");
				
				completeNodes(1, knowledgeSession, testHandler);
				assertNodeTriggered(processInstance.getId(), "Gateway - alternative R action (REG/APP)");
				
				if (evaluator.evalRegisterForREG(admission)) {
					completeNodes(1, knowledgeSession, testHandler);
					assertNodeTriggered(processInstance.getId(), "Gateway - Backtrack from AR");
					
					if (evaluator.evalBackFromREG(admission)) {
						// TODO
					} else if (evaluator.evalRegistrationDone(admission)) {
					}
				} else if (evaluator.evalApologyFromREG(admission)) {
					completeNodes(1, knowledgeSession, testHandler);
					assertNodeTriggered(processInstance.getId(), "Gateway - Apology from AR request result");
					
					if (evaluator.evalRegistrationApologyApproval(admission)) {
						completeNodes(1, knowledgeSession, testHandler);
					} else {
						// TODO
					}
				}
				
			} else {
				// TODO
			}
		}
		
		assertNodeTriggered(processInstance.getId(), "Gateway - Registration complete", "Registration");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Registration");

		WorkItem workItem = testHandler.getWorkItem();
		assertNotNull(workItem);
		assertEquals("Email", workItem.getName());
		assertEquals("pririz@fit.cvut.cz", workItem.getParameter("From"));
		// assertEquals("xxx", workItem.getParameter("Subject"));

		knowledgeSession.getWorkItemManager().completeWorkItem(workItem.getId(), null);

		assertNodeTriggered(processInstance.getId(), "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	@Test
	public void runDecisionSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("process/2012_decision" + BPMN);

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Email", testHandler);
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "2012_decision",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "SW Control II", "Gateway - After SWC II",
				"Decision generate", "Decision sent");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Decision sent");

		WorkItem workItem = testHandler.getWorkItem();
		assertNotNull(workItem);
		assertEquals("Email", workItem.getName());
		assertEquals("pririz@fit.cvut.cz", workItem.getParameter("From"));
		// assertEquals("xxx", workItem.getParameter("Subject"));

		knowledgeSession.getWorkItemManager().completeWorkItem(workItem.getId(), null);

		assertNodeTriggered(processInstance.getId(), "Gateway - Admission acceptation check");

		ProcessEvaluator evaluator = new DefaultProcessEvaluator();

		if (evaluator.evalAdmissionAcceptance(admission)) {
			assertNodeTriggered(processInstance.getId(), "End");
		} else if (evaluator.evalAppealPossibility(admission)) {
			assertNodeTriggered(processInstance.getId(), "Appeal for decision");
			assertNodeActive(processInstance.getId(), knowledgeSession, "Appeal for decision");

			knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

			assertNodeTriggered(processInstance.getId(), "Appeal approval");
			assertNodeActive(processInstance.getId(), knowledgeSession, "Appeal approval");

			knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

			assertNodeTriggered(processInstance.getId(), "Decision generate", "Decision sent");
			assertNodeActive(processInstance.getId(), knowledgeSession, "Decision sent");

			if (evaluator.evalAdmissionAcceptance(admission)) {
				assertNodeTriggered(processInstance.getId(), "End");
			} else if (evaluator.evalAppealPossibility(admission)) {

				assertNodeTriggered(processInstance.getId(), "Appeal for decision");
				assertNodeActive(processInstance.getId(), knowledgeSession, "Appeal for decision");

				knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

				assertNodeTriggered(processInstance.getId(), "Appeal approval");
				assertNodeActive(processInstance.getId(), knowledgeSession, "Appeal approval");

				knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

				assertNodeTriggered(processInstance.getId(), "Decision generate", "Decision sent");
				assertNodeActive(processInstance.getId(), knowledgeSession, "Decision sent");
				
				if (evaluator.evalAdmissionAcceptance(admission)) {
					assertNodeTriggered(processInstance.getId(), "End");
				} else {
					assertNodeTriggered(processInstance.getId(), "Not accepted", "Gateway - decision signal", "End");
				}
			} else {
				assertNodeTriggered(processInstance.getId(), "Not accepted", "Gateway - decision signal", "End");
			}
		} else {
			assertNodeTriggered(processInstance.getId(), "Not accepted", "Gateway - decision signal", "End");
		}

		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	@Test
	public void runTestSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("process/2012_test" + BPMN);

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "2012_test",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Attendance check");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Attendance check");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "Test evaluation");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Test evaluation");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "Gateway - AT evaluated", "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	@Test
	public void runApologyApprovalSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("process/apology_approval" + BPMN);

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "apology_approval",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Decision for apology");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Decision for apology");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	@Test
	public void runDocumentRequestSubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("process/document_request" + BPMN);

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Email", testHandler);
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "document_request",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Docs delivery notification");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Docs delivery notification");

		WorkItem workItem = testHandler.getWorkItem();
		assertNotNull(workItem);
		assertEquals("Email", workItem.getName());
		assertEquals("pririz@fit.cvut.cz", workItem.getParameter("From"));
		// assertEquals("xxx", workItem.getParameter("Subject"));

		knowledgeSession.getWorkItemManager().completeWorkItem(workItem.getId(), null);

		assertNodeTriggered(processInstance.getId(), "Docs delivery");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Docs delivery");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "Gateway - Docs signal", "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	@Test
	public void runRegisterToStudySubprocess() {
		StatefulKnowledgeSession knowledgeSession = createKnowledgeSession("process/register_to_study" + BPMN);

		TestWorkItemHandler testHandler = new TestWorkItemHandler();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", testHandler);

		ProcessInstance processInstance = knowledgeSession.startProcess(DOMAIN + "register_to_study",
				getProcessParameters(admission));

		assertProcessInstanceActive(processInstance.getId(), knowledgeSession);
		assertNodeTriggered(processInstance.getId(), "StartProcess", "Register to study");
		assertNodeActive(processInstance.getId(), knowledgeSession, "Register to study");

		knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);

		assertNodeTriggered(processInstance.getId(), "End");
		assertProcessInstanceCompleted(processInstance.getId(), knowledgeSession);
	}

	private Map<String, Object> getProcessParameters(Admission admission) {
		Map<String, Object> processParameters = new HashMap<String, Object>();
		processParameters.put("admission", admission);
		processParameters.put("evaluator", new BSPProcessEvaluator());
		processParameters.put("jbpmProperties", propertyConfigurer.getProperties());
		processParameters.put(EMAIL_TO, debugEmail);
		return processParameters;
	}
	
	private void completeNodes(int count, StatefulKnowledgeSession knowledgeSession, TestWorkItemHandler testHandler) {
		for (int i = 0; i < count; i++) {
			knowledgeSession.getWorkItemManager().completeWorkItem(testHandler.getWorkItem().getId(), null);
		}
	}

	private static KnowledgeBase readKnowledgeBase() {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("bsp/2012_bsp_main.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("msp/2012_msp_main.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/2012_admission_test.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/2012_decision.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/2012_registration.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/2012_test.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/apology_approval.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/document_request.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/register_to_study.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}
}