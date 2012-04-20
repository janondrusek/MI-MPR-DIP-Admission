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
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
public class JbpmProcessService implements ProcessService {

	private static final String EMAIL_TO = "emailTo";

	private StatefulKnowledgeSession knowledgeSession;
	@Autowired
	private JbpmAccessiblePropertyConfigurer propertyConfigurer;

	private Boolean mailDebug;
	private String mailDebugAddressTo;
	private Boolean mailDisable;

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

		Map<String, Object> processParameters = new HashMap<String, Object>();
		processParameters.put("admission", admission);
		processParameters.put("evaluator", new MSPProcessEvaluator());
		processParameters.put("jbpmProperties", propertyConfigurer.getProperties());

		if (mailDisable) {
			processParameters.put(EMAIL_TO, StringPool.BLANK);
		} else if (mailDebug) {
			processParameters.put(EMAIL_TO, mailDebugAddressTo);
		} else {
			processParameters.put(EMAIL_TO, admission.getPerson().getEmail());
		}

		startProcess(degree, processParameters);
	}

	private void startProcess(String degree, Map<String, Object> processParameters) {
		knowledgeSession.startProcess(degreeProcessMapping.get(degree));
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
	public void setMailDisable(Boolean mailDisable) {
		this.mailDisable = mailDisable;
	}

	@Required
	public void setDegreeProcessMapping(Map<String, String> degreeProcessMapping) {
		this.degreeProcessMapping = degreeProcessMapping;
	}

}