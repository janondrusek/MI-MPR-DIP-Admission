package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

public class DefaultProcessEvaluator implements ProcessEvaluator {

	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalNeededDocuments(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalAppealPossibility(Admission admission) {
		if (!admission.getAccepted() && admission.getAppeals().size() < 2) { // Limit for appeals is 2 (to Dean / to Rector)
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean evalAdmissionAcceptance(Admission admission) {
		if (admission.getAccepted()) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean evalRegisterForAT(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S10")) { // getName REGISTRED_TO_ADMISSION_TEST
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalApologyFromAT(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S11")) { // getName APOLOGY_REQUEST
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalBackFromAT(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S09")) { // getName INVITED_TO_ADMISSION_TEST
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalAdmissionTestEvaluated(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S17")) { // getName ADMISSION_TEST_EVALUATED
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalApologyApproval(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S12")) { // getName APOLOGY_APPROVED
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalAdmissionSWC_I(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalAdmissionSWC_II(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalRegisterForREG(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S30")) { // getName REGISTRED_TO_REGISTRATION
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalApologyFromREG(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S32")) { // getName REGISTRATION_APOLOGY_APPROVED
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalBackFromREG(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S26")) { // getName DECISION_SENT
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean EnoughTestPoints(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalRegistrationDone(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S35")) { // getName REGISTRED
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean evalRegistrationApologyApproval(Admission admission) {
		if (admission.getAdmissionState().getCode().equals("S32")) { // getName REGISTRATION_APOLOGY_APPROVED
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void testGeneratingDecisionType(Admission admission) {
		// TODO Auto-generated method stub
		
	}

}
