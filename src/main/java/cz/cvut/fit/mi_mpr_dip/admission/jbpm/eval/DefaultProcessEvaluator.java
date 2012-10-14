package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public class DefaultProcessEvaluator implements ProcessEvaluator {

	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		return null;
	}

	@Override
	public Boolean evalNeededDocuments(Admission admission) {
		return null;
	}

	@Override
	public Boolean evalAppealPossibility(Admission admission) {
		// Limit for appeals is 2 (to Dean / to Rector)
		return !admission.getAccepted() && admission.getAppeals().size() < 2;
	}

	@Override
	public Boolean evalAdmissionAcceptance(Admission admission) {
		return admission.getAccepted();
	}

	@Override
	public Boolean evalRegisterForAT(Admission admission) {
		return true;
		// TODO
		// if (admission.getAdmissionState().getCode().equals("S10")) { //
		// getName REGISTRED_TO_ADMISSION_TEST
		// return true;
		// } else {
		// return false;
		// }
	}

	@Override
	public Boolean evalApologyFromAT(Admission admission) {
		// APOLOGY_REQUEST
		return admission.getAdmissionState().getCode().equals("S11");
	}

	@Override
	public Boolean evalBackFromAT(Admission admission) {
		// INVITED_TO_ADMISSION_TEST
		return admission.getAdmissionState().getCode().equals("S09");
	}

	@Override
	public Boolean evalAdmissionTestEvaluated(Admission admission) {
		// ADMISSION_TEST_EVALUATED
		return admission.getAdmissionState().getCode().equals("S17");
	}

	@Override
	public Boolean evalApologyApproval(Admission admission) {
		// APOLOGY_APPROVED
		return admission.getAdmissionState().getCode().equals("S12");
	}

	@Override
	public Boolean evalAdmissionSWCOne(Admission admission) {
		return null;
	}

	@Override
	public Boolean evalAdmissionSWCTwo(Admission admission) {
		return null;
	}

	@Override
	public Boolean evalRegisterForREG(Admission admission) {
		return true;
		// TODO
		// if (admission.getAdmissionState().getCode().equals("S30")) { //
		// getName REGISTRED_TO_REGISTRATION
		// return true;
		// } else {
		// return false;
		// }
	}

	@Override
	public Boolean evalApologyFromREG(Admission admission) {
		// REGISTRATION_APOLOGY_APPROVED
		return admission.getAdmissionState().getCode().equals("S32");
	}

	@Override
	public Boolean evalBackFromREG(Admission admission) {
		// DECISION_SENT
		return admission.getAdmissionState().getCode().equals("S26");
	}

	@Override
	public Boolean EnoughTestPoints(Admission admission) {
		return null;
	}

	@Override
	public Boolean evalRegistrationDone(Admission admission) {
		// REGISTRED
		return admission.getAdmissionState().getCode().equals("S35");
	}

	@Override
	public Boolean evalRegistrationApologyApproval(Admission admission) {
		// REGISTRATION_APOLOGY_APPROVED
		return admission.getAdmissionState().getCode().equals("S32");
	}

	@Override
	public void testGeneratingDecisionType(Admission admission) {

	}
}
