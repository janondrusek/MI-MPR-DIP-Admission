package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
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
	private final String BC = "Bc.";
	private final String ING = "Ing.";

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
	
	public void runProcessEmail() {
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

		if (degree.equals(BC)) {
			knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.2012_bsp_main", processParameters);
		} else if (degree.equals(ING)) {
			knowledgeSession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.2012_msp_main", processParameters);
		}
	}

	// WARNING
	public void disposeSession() {
		knowledgeSession.dispose();
	}

	private KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("process/blank.bpmn"), ResourceType.BPMN2);
		// other process resources
		return kbuilder.newKnowledgeBase();
	}
}