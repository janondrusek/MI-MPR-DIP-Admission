package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public interface ProcessEvaluator {

	public Boolean evalAcceptWithoutAT(Admission admission);

	public Boolean evalNeededDocuments(Admission admission);

	public Boolean evalAppealPossibility(Admission admission);

	public Boolean evalAdmissionAcceptance(Admission admission);

	public Boolean evalRegisterForAT(Admission admission);

	public Boolean evalApologyFromAT(Admission admission);

	public Boolean evalBackFromAT(Admission admission);

	public Boolean evalAdmissionTestEvaluated(Admission admission);

	public Boolean evalApologyApproval(Admission admission);

	public Boolean evalAdmissionSWCOne(Admission admission);

	public Boolean evalAdmissionSWCTwo(Admission admission);

	public Boolean evalRegisterForREG(Admission admission);

	public Boolean evalApologyFromREG(Admission admission);

	public Boolean evalBackFromREG(Admission admission);

	public Boolean EnoughTestPoints(Admission admission);

	public Boolean evalRegistrationDone(Admission admission);

	public Boolean evalRegistrationApologyApproval(Admission admission);

	public void testGeneratingDecisionType(Admission admission);

}
