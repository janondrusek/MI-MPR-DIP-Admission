package cz.cvut.fit.mi_mpr_dip.admission.validation.unique;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.ProgrammeDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
@Service
public class ProgrammeUniqueConstraintValidator extends BaseUniqueConstraintValidator<Programme> {

	@Autowired
	private ProgrammeDao programmeDao;

	@Override
	protected String getPath() {
		return "name";
	}

	@Override
	protected String getDuplicateValue(Programme programme) {
		String[] values = new String[4];
		values[0] = programme.getName();
		values[1] = programme.getDegree().getName();
		values[2] = programme.getLanguage().getName();
		values[3] = programme.getStudyMode().getName();

		return StringUtils.join(values, StringPool.DASH);
	}

	@Override
	protected boolean isDuplicate(Programme programme) {
		Programme dbProgramme = getProgrammeDao().getProgramme(programme.getName(), programme.getDegree().getName(),
				programme.getLanguage().getName(), programme.getStudyMode().getName());

		return programme.equals(dbProgramme);
	}
}
