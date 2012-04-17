package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.MSPProcessEvaluator;
import cz.cvut.fit.mi_mpr_dip.admission.service.AdminService;

public class JbpmProcessService implements ProcessService {
	
	private StatefulKnowledgeSession knowledgeSession;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private AdmissionDao admissionDao;
	
	private Map<String, String> applicationProperties;
	private final String PROCESS_LOG_BSP = "2012_BSP_main";
	private final String PROCESS_LOG_MSP = "2012_MSP_main";

	@Override
	public StatefulKnowledgeSession getSession() {
		return knowledgeSession;
	}
	
	public void setSession(StatefulKnowledgeSession ksession) {
		this.knowledgeSession = ksession;
	}

	public void runProcessBlank() {
		knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.blank");
	}
	
	public void runEmailProcess() {
		applicationProperties = adminService.getApplicationProperties();

		Map<String, Object> processParameters = new HashMap<String, Object>();
		processParameters.put("prop", applicationProperties);

		if (applicationProperties.get("mail.disable").equals("true")) {
			processParameters.put("emailTo", "");
		} else if (applicationProperties.get("mail.debug").equals("true")) {
			processParameters.put("emailTo", applicationProperties.get("mail.debug.address.to"));
		} else {
			// processParameters.put("emailTo", admission.getPerson().getEmail());
		}

		knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.test_email", processParameters);
	}
	
	public void runProcess(String admissionCode) {
		KnowledgeRuntimeLogger logger = null;

		// get Application properties
		applicationProperties = adminService.getApplicationProperties();
				
		// get Admission from DB
		Admission admission = admissionDao.getAdmission(admissionCode);
		
		String degree = admission.getProgramme().getDegree().getName();

		Map<String, Object> processParameters = new HashMap<String, Object>();
		processParameters.put("admission", admission);
		processParameters.put("evaluator", new MSPProcessEvaluator());
		processParameters.put("prop", applicationProperties);

		if (applicationProperties.get("mail.disable").equals("true")) {
			processParameters.put("emailTo", "");
		} else if (applicationProperties.get("mail.debug").equals("true")) {
			processParameters.put("emailTo", applicationProperties.get("mail.debug.address.to"));
		} else {
			 processParameters.put("emailTo", admission.getPerson().getEmail());
		}

		if (degree.equals("Bc.")) {
			logger = createLogger(knowledgeSession, PROCESS_LOG_BSP);
			knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.2012_bsp_main", processParameters);
		} else if (degree.equals("Ing.")) {
			logger = createLogger(knowledgeSession, PROCESS_LOG_MSP);
			knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.2012_msp_main", processParameters);
		}

		logger.close();
	}

	// WARNING
	public void disposeSession() {
		knowledgeSession.dispose();
	}
	
	/**
	 * Method for possibility to set KnowledgeBase (process resources) from code if xml definition fails
	 */
	public void setTestSession() {
		KnowledgeBase kbase = null;
		try {
			kbase = readKnowledgeBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		knowledgeSession = kbase.newStatefulKnowledgeSession();
	}

	private KnowledgeRuntimeLogger createLogger(StatefulKnowledgeSession ksession, String logName) {
		int logIntervalInMilliseconds = 500;
		KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, logName,
				logIntervalInMilliseconds);
		return logger;
	}

	private KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("process/blank.bpmn"), ResourceType.BPMN2);
		// other process resources
		return kbuilder.newKnowledgeBase();
	}
}