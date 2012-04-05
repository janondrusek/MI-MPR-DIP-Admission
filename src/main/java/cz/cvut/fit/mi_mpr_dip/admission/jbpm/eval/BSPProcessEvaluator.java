package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;

public class BSPProcessEvaluator extends DefaultProcessEvaluator implements ProcessEvaluator {
	
	private final String cz = "Česká republika"; // CZ or something else
	private Integer h2_value = 70;
	private Integer h3_value = 0;
	private Integer h4_value = 1;
	private Integer h5_value = 0;
	private Integer h6_value = 70;
	private Integer h7_value = 70;
	
	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		if (admission.getAccepted()) {
			return true;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName();

			if (type.equals("H5") && value.length() > h5_value) {
				return true;
			} else if (type.equals("H6") && Double.valueOf(value) >= h6_value) {
				return true;
			} else if (type.equals("H7") && Double.valueOf(value) >= h7_value) {
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
}