package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import javax.persistence.EntityManagerFactory;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.LocalTaskService;

public class JbpmTaskServiceImpl implements JbpmTaskService {

	StatefulKnowledgeSession knowledgeSession;
	org.jbpm.task.service.TaskService taskService;
	EntityManagerFactory entityManagerFactory;

	public JbpmTaskServiceImpl(StatefulKnowledgeSession kSession, org.jbpm.task.service.TaskService tService,
			EntityManagerFactory emf) {
		this.knowledgeSession = kSession;
		this.taskService = tService;
		this.entityManagerFactory = emf;
	}

	@Override
	public TaskService getTaskService() {
		if (taskService == null) {
			taskService = new org.jbpm.task.service.TaskService(entityManagerFactory,
					SystemEventListenerFactory.getSystemEventListener());
		}
		TaskServiceSession taskServiceSession = taskService.createSession();
		taskServiceSession.setTransactionType("local-JTA");
		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(new LocalTaskService(taskServiceSession),
				knowledgeSession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return new LocalTaskService(taskServiceSession);
	}

}
