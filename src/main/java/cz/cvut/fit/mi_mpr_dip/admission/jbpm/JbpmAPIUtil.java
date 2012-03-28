package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.audit.JPAProcessInstanceDbLog;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.LocalTaskService;

import bitronix.tm.TransactionManagerServices;



public class JbpmAPIUtil {

	static Logger logger = Logger.getLogger(JbpmAPIUtil.class);

	public static StatefulKnowledgeSession createKnowledgeSession(String process,EntityManagerFactory emf) {
		KnowledgeBase kbase = createKnowledgeBase(process);
		return createKnowledgeSession(kbase,emf);
	}

	/*
	 * Load the bpmn file into knowledgebase
	 */
	public static  KnowledgeBase createKnowledgeBase(String process) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
            kbuilder.add(ResourceFactory.newClassPathResource(process), ResourceType.BPMN2);
        
        
        // Check for errors
        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                boolean errors = false;
                for (KnowledgeBuilderError error : kbuilder.getErrors()) {
                	logger.warn(error.toString());

                    errors = true;
                }
               
            }
        }
        return kbuilder.newKnowledgeBase();
    }
	
	
	/*
	 * Create the knowledge session that uses JPA to persists runtime state
	 */
	public static  StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase,EntityManagerFactory emf) {
	    StatefulKnowledgeSession result;
        final KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
	   	
		    Environment env = createEnvironment(emf);
		    result = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, conf, env);
		    new JPAWorkingMemoryDbLogger(result);
		   
		    	JPAProcessInstanceDbLog log = new JPAProcessInstanceDbLog(result.getEnvironment());
		    
		    
		
		return result;
	}

	protected static Environment createEnvironment(EntityManagerFactory emf) { 
        Environment env = EnvironmentFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
        env.set(EnvironmentName.TRANSACTION_MANAGER, TransactionManagerServices.getTransactionManager());
        return env;
    }
	
	/*
	 * Get local task service
	 */
	public static  TaskService getTaskService(StatefulKnowledgeSession ksession,org.jbpm.task.service.TaskService taskService,EntityManagerFactory emf) {
    	if (taskService == null) {
    		taskService = new org.jbpm.task.service.TaskService(
				emf, SystemEventListenerFactory.getSystemEventListener());
    	}
		TaskServiceSession taskServiceSession = taskService.createSession();
		taskServiceSession.setTransactionType("local-JTA");
		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(
			new LocalTaskService(taskServiceSession), ksession);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		return new LocalTaskService(taskServiceSession);
    }
	
	public static org.jbpm.task.service.TaskService getService(EntityManagerFactory emf) {
		return new org.jbpm.task.service.TaskService(emf, SystemEventListenerFactory.getSystemEventListener());
	}
}
