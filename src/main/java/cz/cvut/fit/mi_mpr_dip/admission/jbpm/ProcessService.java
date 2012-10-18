package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.drools.runtime.StatefulKnowledgeSession;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface ProcessService {

	public StatefulKnowledgeSession getSession();

	public void setSession(StatefulKnowledgeSession ksession);

	public void runProcess(Admission admission);
}