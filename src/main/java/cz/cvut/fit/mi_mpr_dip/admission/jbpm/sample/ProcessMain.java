package cz.cvut.fit.mi_mpr_dip.admission.jbpm.sample;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
//import org.jbpm.test.JBPMHelper;

/**
 * This is a sample file to launch a process.
 */
public class ProcessMain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static final void main(String[] args) throws Exception {
		Attendant att = new Attendant("Frantisek", "Vomacka", "f.vomacka@fit.test.cvut.cz", "777 804 017", "32584501", "Chlumec 60, 37341 Hluboká nad Vltavou, Česká republika", 5, 269335, 'P', 'B', "BI", false, false);
		
		/*ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"jbpm-conf.xml");
		StatefulKnowledgeSession ksession = (StatefulKnowledgeSession) context
				.getBean("ksession");
	    */
		//JBPMHelper.startTaskService();
		
		// load up the knowledge base
		KnowledgeBase kbase = readKnowledgeBase();
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		//StatefulKnowledgeSession ksession = JBPMHelper.newStatefulKnowledgeSession(kbase);

		// start a new process instance
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("attendant", att);
		params.put("name", att.getName());
		params.put("surname", att.getSurname());
		params.put("id", att.getId());
		params.put("init", "pz");
		
		params.put("pz_action_01", "omluven");
		params.put("pz_action_02", "prihlasen");
		params.put("pz_date_01", new Date(2012, 04, 06));
		params.put("actDate", att.getActDate());
		
		params.put("zap_action_01", "prihlasen");
		
		System.out.println("Process started ...");
		ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.ld_process", params);
		//ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.zapis", params);
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("process/ld_process.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/pz.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/zapis.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("process/rozhodnuti.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}