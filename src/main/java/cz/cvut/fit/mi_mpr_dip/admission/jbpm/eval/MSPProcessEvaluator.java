package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;

public class MSPProcessEvaluator implements ProcessEvaluator {

	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		boolean b = false;
		if (admission.getAccepted()) {
			b = true;
			return true;
		}

		// System.out.println("COUNTER: " + admission.getEvaluations().size());
		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();
			System.out.println("value: " + value + " type: " + type);

			if (type.equals("H8") && Double.valueOf(value) <= 1.9) {
				System.out.println("prijat z duvodu: [" + type + "] Studijni prumer pred. studia - " + value);
				b = true;
				return true;
			}
		}
		if (b) {
			System.out.println("PRIJAT BEZ PZ");
		} else {
			System.out.println("POZVAN K PZ");
		}

		return b;
	}

	@Override
	public Boolean evalNeededDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalAppealRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalRegisterOrAppology() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean evalApologyApproval() {
		// TODO Auto-generated method stub
		return null;
	}

}
