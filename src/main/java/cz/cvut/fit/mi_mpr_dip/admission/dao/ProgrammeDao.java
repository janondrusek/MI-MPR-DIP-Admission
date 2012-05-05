package cz.cvut.fit.mi_mpr_dip.admission.dao;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

public interface ProgrammeDao {

	public Programme getProgramme(String name, String degreeName, String languageName, String studyModeName);
}
