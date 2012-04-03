package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface ProcessEvaluator {

	public Boolean evalAcceptWithoutAT(Admission admission);
	
	public Boolean evalNeededDocuments();
	
	public Boolean evalAppealRequest();
	
	public Boolean evalRegisterOrAppology();
	
	public Boolean evalApologyApproval();
	
}
