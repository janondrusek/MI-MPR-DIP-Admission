package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appeal;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class MSPProcessEvaluator extends DefaultProcessEvaluator {

	private final String CZ = "Česká republika"; // CZ or something else
	private final String SK = "Slovenská republika"; // CZ or something else

	private String admissionTestPoints = "h2";
	private Integer limitAdmissionTestPoints = 70; // h2
	private String neededDocuments = "h3";
	private Integer limitNeededDocuments = 1; // h3
	private String czechLanguageExamination = "h4";
	private Integer limitCzechLanguageExamination = 1; // h4
	private String bachelorStudyAverageGrade = "h8";
	private Double limitBachelorStudyAverageGrade = 1.9; // h4

	@Override
	public Boolean evalAcceptWithoutAT(Admission admission) {
		if (admission.getAccepted()) {
			return true;
		}

		if (admission.getEvaluations() == null) {
			return false;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName().toLowerCase();

			if (type.equals(bachelorStudyAverageGrade) && Double.valueOf(value) > 0
					&& Double.valueOf(value) <= limitBachelorStudyAverageGrade) {
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

		if (admission.getEvaluations() == null) {
			return false;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName().toLowerCase();
			String citizenship = admission.getPerson().getCitizenship().getName();

			if (citizenship.equals(CZ) || citizenship.equals(SK)) {
				if (type.equals(neededDocuments) && Double.valueOf(value) > limitNeededDocuments) {
					return true;
				}
			} else {
				if (type.equals(neededDocuments) && Double.valueOf(value) > limitNeededDocuments) {
					h3 = true;
				} else if (type.equals(czechLanguageExamination)
						&& Double.valueOf(value) > limitCzechLanguageExamination) {
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
	public Boolean evalAdmissionSWCTwo(Admission admission) {
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
		if (admission.getEvaluations() == null) {
			return false;
		}

		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName().toLowerCase();

			if (type.equals(admissionTestPoints) && Double.valueOf(value) >= limitAdmissionTestPoints) {
				return true;
			}
		}

		return false;
	}

	public void testGeneratingDecisionType(Admission admission) {
		for (Evaluation evaluation : admission.getEvaluations()) {
			String value = evaluation.getValue();
			String type = evaluation.getEvaluationType().getName().toLowerCase();
			System.out.println("> TYPE: " + type + " / VALUE: " + value);
		}

		boolean result = false;
		String type = StringPool.BLANK;

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
