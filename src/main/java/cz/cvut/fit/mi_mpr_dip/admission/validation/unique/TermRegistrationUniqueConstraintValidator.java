package cz.cvut.fit.mi_mpr_dip.admission.validation.unique;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermRegistrationDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
@Service
public class TermRegistrationUniqueConstraintValidator extends BaseUniqueConstraintValidator<TermRegistration> {

	@Autowired
	private TermRegistrationDao termRegistrationDao;

	@Override
	protected String getPath() {
		return "term";
	}

	@Override
	protected String getDuplicateValue(TermRegistration termRegistration) {
		String[] values = new String[3];
		values[0] = termRegistration.getAdmission().getCode();
		values[1] = termRegistration.getTerm().getRoom();
		values[2] = String.valueOf(termRegistration.getTerm().getDateOfTerm());

		return StringUtils.join(values, StringPool.DASH);
	}

	@Override
	protected boolean isDuplicate(TermRegistration termRegistration) {
		TermRegistration dbTermRegistration = getTermRegistrationDao().getTermRegistration(
				termRegistration.getAdmission().getCode(), termRegistration.getTerm().getDateOfTerm(),
				termRegistration.getTerm().getRoom());
		return dbTermRegistration.equals(termRegistration);
	}
}
