package cz.cvut.fit.mi_mpr_dip.admission.jbpm.sample;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is a sample file to launch a process.
 */
public class ProcessMain {

	public static final void main(String[] args) throws Exception {
		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"jbpm-conf.xml");
		StatefulKnowledgeSession ksession = (StatefulKnowledgeSession) context
				.getBean("ksession");
	    */
		// load up the knowledge base
		KnowledgeBase kbase = readKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		// start a new process instance
		ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.hello");
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("sample.bpmn"),
				ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}