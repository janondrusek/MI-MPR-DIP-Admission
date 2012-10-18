package cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;

public class BSPProcessEvaluator extends DefaultProcessEvaluator {

	private final String CZ = "Česká republika"; // CZ or something else
	private final String SK = "Slovenská republika"; // CZ or something else

	private String admissionTestPoints = "h2";
	private Integer limitAdmissionTestPoints = 70; // h2
	private String neededDocuments = "h3";
	private Integer limitNeededDocuments = 1; // h3
	private String czechLanguageExamination = "h4";
	private Integer limitCzechLanguageExamination = 1; // h4
	private String olympiadAchievement = "h5";
	private Integer limitOlympiadAchievement = 1; // h5
	private String graduationMathPoints = "h6";
	private Integer limitGraduationMathPoints = 70; // h6
	private String scioPoints = "h7";
	private Integer limitScioPoints = 70; // h7

	// private Map<String, String> jbpmProperties;

	// @Autowired
	// private JbpmAccessiblePropertyConfigurer propertyConfigurer;

	public BSPProcessEvaluator() {
		// jbpmProperties = new HashMap<String, String>();
		// jbpmProperties = propertyConfigurer.getProperties();
		//
		// admissionTestPoints =
		// jbpmProperties.get("process.evaluation.type.admission_test_points");
		// limitAdmissionTestPoints =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h2"));
		// neededDocuments =
		// jbpmProperties.get("process.evaluation.type.needed_documents");
		// limitNeededDocuments =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h3"));
		// czechLanguageExamination =
		// jbpmProperties.get("process.evaluation.type.czech_lang_exam");
		// limitCzechLanguageExamination =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h4"));
		// olympiadAchievement =
		// jbpmProperties.get("process.evaluation.type.olympiad_achievement");
		// limitOlympiadAchievement =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h5"));
		// graduationMathPoints =
		// jbpmProperties.get("process.evaluation.type.graduation_math_points");
		// limitGraduationMathPoints =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h6"));
		// scioPoints =
		// jbpmProperties.get("process.evaluation.type.scio_points");
		// limitScioPoints =
		// Integer.valueOf(jbpmProperties.get("process.evaluation.h7"));
	}

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

			if (type.equals(olympiadAchievement) && Integer.valueOf(value) >= limitOlympiadAchievement) {
				return true;
			} else if (type.equals(graduationMathPoints) && Double.valueOf(value) >= limitGraduationMathPoints) {
				return true;
			} else if (type.equals(scioPoints) && Double.valueOf(value) >= limitScioPoints) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Boolean evalNeededDocuments(Admission admission) {
		boolean documentsOk = false;
		boolean czLangExamOk = false;

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
				if (type.equals(neededDocuments) && Double.valueOf(value) >= limitNeededDocuments) {
					return true;
				}
			} else {
				if (type.equals(neededDocuments) && Double.valueOf(value) >= limitNeededDocuments) {
					documentsOk = true;
				} else if (type.equals(czechLanguageExamination)
						&& Double.valueOf(value) >= limitCzechLanguageExamination) {
					czLangExamOk = true;
				}
			}

			if (documentsOk && czLangExamOk) {
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

			if (type.equals(admissionTestPoints) && Integer.valueOf(value) >= limitAdmissionTestPoints) {
				return true;
			}
		}

		return false;
	}
}