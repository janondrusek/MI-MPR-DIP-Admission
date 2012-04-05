package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appeal;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;

public class MSPProcessEvaluator extends DefaultProcessEvaluator implements ProcessEvaluator {

	private final String cz = "Česká republika"; // CZ or something else
	private Double h8_value = 1.9;
	private Integer h3_value = 0;
	private Integer h4_value = 1;
	private Integer h2_value = 70;

	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		if (admission.getAccepted()) {
			return true;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();

			if (type.equals("H8") && Double.valueOf(value) <= h8_value) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean evalNeededDocuments(Admission admission) {
		boolean h3 = false;
		boolean h4 = false;
		if (admission.getAccepted()) {
			return true;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();
			String citizenship = admission.getPerson().getCitizenship().getName();

			if (citizenship.equals(cz)) {
				if (type.equals("H3") && Double.valueOf(value) > 0) {
					return true;
				}
			} else {
				if (type.equals("H3") && Double.valueOf(value) > 0) {
					h3 = true;
				} else if (type.equals("H4") && Double.valueOf(value) > 0) {
					h4 = true;
				}
			}

			if (h3 && h4) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public Boolean evalAdmissionSWC_I(Admission admission) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean evalAdmissionSWC_II(Admission admission) {
		boolean prep = this.evalAcceptWithoutAT(admission);
		boolean docs = this.evalNeededDocuments(admission);
		boolean test = this.EnoughTestPoints(admission);

		if ((prep && docs) || (test && docs)) {
			admission.setAccepted(true);
			return true;
		}
		return false;
	}

	@Override
	public Boolean EnoughTestPoints(Admission admission) {
		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();

			if (type.equals("H2") && Double.valueOf(value) >= h2_value) {
				return true;
			}
		}

		return false;
	}
	
	public void testGeneratingDecisionType(Admission admission) {
		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();
			System.out.println("> TYPE: " + type + " / VALUE: " + value);
		}
		
		boolean result = false;
		String type = "";
		
		for (Appeal appeal : admission.getAppeals()) {
			result = appeal.getAccepted();
			type = appeal.getAppealType().getName();
		}

		if (admission.getAccepted()) {
			if (result) {
				System.out.println("> ROZHODNUTI O PRIJETI (NA ODVOLANI: " + type + ")");
			} else {
				System.out.println("> ROZHODNUTI O PRIJETI");
			}
		} else {
			if (result) {
				System.out.println("> ROZHODNUTI O NEPRIJETI (NA ODVOLANI: " + type + ")");
			} else {
				System.out.println("> ROZHODNUTI O NEPRIJETI");
			}
		}
	}
}
