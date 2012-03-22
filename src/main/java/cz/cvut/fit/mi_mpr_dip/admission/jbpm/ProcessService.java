package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.process.workitem.email.EmailWorkItemHandler;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DavidCh
 * 
 */
@Service
public class ProcessService {

	@Autowired
	private StatefulKnowledgeSession ksession;

	private final String processName = "2012_BSP_main";

	public void setSession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
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
		ksession = kbase.newStatefulKnowledgeSession();
	}

	public ProcessInstance runProcess() {
		return ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.blank");
	}

	public ProcessInstance runProcess(Map<String, Object> parameters) {
		KnowledgeRuntimeLogger logger = createLogger(ksession);

		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new WSHumanTaskHandler());
//		ksession.getWorkItemManager().registerWorkItemHandler("Email", null);		
		ProcessInstance processInstance = ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.2012_main", parameters);

		logger.close();
		
		return processInstance;
	}
	
	public ProcessInstance runEmailProcess() {
		EmailWorkItemHandler emailHandler = new EmailWorkItemHandler();
		emailHandler.setConnection("${mail.smtp.host}", "${mail.smtp.port}", "${mail.username}", "${mail.password}");

		ksession.getWorkItemManager().registerWorkItemHandler("Email", emailHandler);
		
		return ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.test_email");
	}
	
	// WARNING
	public void disposeSession() {
		ksession.dispose();
	}

	private KnowledgeRuntimeLogger createLogger(StatefulKnowledgeSession ksession) {
		int logIntervalInMilliseconds = 500;
		String logName = processName;
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
