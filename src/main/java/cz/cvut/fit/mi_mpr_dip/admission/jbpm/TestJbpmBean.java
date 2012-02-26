package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestJbpmBean {

	@Autowired
	private StatefulKnowledgeSession ksession;

	public void setSession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public void runProcess() {
		ksession.startProcess("cz.cvut.fit.mi_mpr_dip.admission.hello");
	}
}
