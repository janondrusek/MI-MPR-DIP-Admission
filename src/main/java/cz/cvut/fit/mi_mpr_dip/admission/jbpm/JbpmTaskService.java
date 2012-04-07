package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.task.TaskService;

public interface JbpmTaskService {
	
	public TaskService getTaskService();
	
	public StatefulKnowledgeSession getKnowledgeSession();

}