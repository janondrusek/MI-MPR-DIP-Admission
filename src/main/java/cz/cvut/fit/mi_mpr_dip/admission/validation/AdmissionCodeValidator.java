package cz.cvut.fit.mi_mpr_dip.admission.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@RooJavaBean
@Service
public class AdmissionCodeValidator extends BaseUniqueConstraintValidator<Admission> {

	@Autowired
	private AdmissionDao admissionDao;

	@Override
	protected String getDuplicateValue(Admission admission) {
		return admission.getCode();
	}

	@Override
	protected boolean isDuplicate(Admission admission) {
		Admission duplicate = admissionDao.getAdmission(admission.getCode());

		return StringUtils.equals(admission.getCode(), duplicate.getCode());
	}

	@Override
	protected String getPath() {
		return "code";
	}

}
