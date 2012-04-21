package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.JbpmAccessiblePropertyConfigurer;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.MSPProcessEvaluator;

@RooJavaBean
public class JbpmProcessService implements ProcessService {

	private static final String EMAIL_TO = "emailTo";

	private StatefulKnowledgeSession knowledgeSession;
	@Autowired
	private JbpmAccessiblePropertyConfigurer propertyConfigurer;

	private Boolean mailDebug;
	private String mailDebugAddressTo;

	private Map<String, String> degreeProcessMapping;

	@Override
	public StatefulKnowledgeSession getSession() {
		return knowledgeSession;
	}

	@Override
	public void setSession(StatefulKnowledgeSession ksession) {
		this.knowledgeSession = ksession;
	}

	@Override
	public void runProcess(Admission admission) {
		String degree = admission.getProgramme().getDegree().getName();
		Map<String, Object> processParameters = getProcessParameters(admission);

		startProcess(degree, processParameters);
	}

	private Map<String, Object> getProcessParameters(Admission admission) {
		Map<String, Object> processParameters = new HashMap<String, Object>();
		processParameters.put("admission", admission);
		processParameters.put("evaluator", new MSPProcessEvaluator());
		processParameters.put("jbpmProperties", propertyConfigurer.getProperties());

		if (getMailDebug()) {
			processParameters.put(EMAIL_TO, getMailDebugAddressTo());
		} else {
			processParameters.put(EMAIL_TO, admission.getPerson().getEmail());
		}
		return processParameters;
	}

	private void startProcess(String degree, Map<String, Object> processParameters) {
		knowledgeSession.startProcess(getDegreeProcessMapping().get(degree), processParameters);
	}

	@Required
	public void setMailDebug(Boolean mailDebug) {
		this.mailDebug = mailDebug;
	}

	@Required
	public void setMailDebugAddressTo(String mailDebugAddressTo) {
		this.mailDebugAddressTo = mailDebugAddressTo;
	}

	@Required
	public void setDegreeProcessMapping(Map<String, String> degreeProcessMapping) {
		this.degreeProcessMapping = degreeProcessMapping;
	}

}