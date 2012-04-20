package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Group;
import org.jbpm.task.TaskService;
import org.jbpm.task.User;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.LocalTaskService;

public class JbpmTaskServiceImpl implements JbpmTaskService {

	private StatefulKnowledgeSession knowledgeSession;
	private org.jbpm.task.service.TaskService taskService;

	public JbpmTaskServiceImpl(StatefulKnowledgeSession kSession, org.jbpm.task.service.TaskService tService) {
		this.knowledgeSession = kSession;
		this.taskService = tService;
	}

	@Override
	public TaskService getTaskService() {
		TaskServiceSession taskServiceSession = taskService.createSession();
		taskServiceSession.setTransactionType("local-JTA");
		taskServiceSession.addUser(new User("krisv"));
		taskServiceSession.addGroup(new Group("developers"));
		
		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(new LocalTaskService(taskServiceSession),
				knowledgeSession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		knowledgeSession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return new LocalTaskService(taskServiceSession);
	}

	@Override
	public StatefulKnowledgeSession getKnowledgeSession() {
		return knowledgeSession;
	}

}
