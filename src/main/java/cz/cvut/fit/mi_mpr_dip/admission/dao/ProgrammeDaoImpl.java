package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;

@Repository
public class ProgrammeDaoImpl extends AbstractDao<Programme> implements ProgrammeDao {

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
		StudyMode studyMode = new StudyMode();
		studyMode.setName(studyModeName);

		return studyMode;
	}

	private Language getLanguage(String languageName) {
		Language language = new Language();
		language.setName(languageName);

		return language;
	}

	private Degree getDegree(String degreeName) {
		Degree degree = new Degree();
		degree.setName(degreeName);

		return degree;
	}

	@Override
	protected Programme createEmptyResult() {
		return new Programme();
	}
}
