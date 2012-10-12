package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;

@Repository
public class ProgrammeDaoImpl extends Dao<Programme> implements ProgrammeDao {

	@Transactional(readOnly = true)
	@Override
	public Programme getProgramme(String name, String degreeName, String languageName, String studyModeName) {
		return getProgrammeQuietly(name, degreeName, languageName, studyModeName);
	}

	private Programme getProgrammeQuietly(String name, String degreeName, String languageName, String studyModeName) {
		Programme programme;
		try {
			programme = findProgramme(name, degreeName, languageName, studyModeName);
		} catch (Exception e) {
			programme = processException(e);
		}
		return programme;
	}

	private Programme findProgramme(String name, String degreeName, String languageName, String studyModeName) {
		return uniqueResult(Programme.findProgrammesByNameEqualsAndStudyModeAndDegreeAndLanguage(name,
				getStudyMode(studyModeName), getDegree(degreeName), getLanguage(languageName)));
	}

	private StudyMode getStudyMode(String studyModeName) {
		return StudyMode.findStudyModesByNameEquals(studyModeName).getSingleResult();
	}

	private Language getLanguage(String languageName) {
		return Language.findLanguagesByNameEquals(languageName).getSingleResult();
	}

	private Degree getDegree(String degreeName) {
		return Degree.findDegreesByNameEquals(degreeName).getSingleResult();
	}

	@Override
	protected Programme createEmptyResult() {
		return new Programme();
	}
}
