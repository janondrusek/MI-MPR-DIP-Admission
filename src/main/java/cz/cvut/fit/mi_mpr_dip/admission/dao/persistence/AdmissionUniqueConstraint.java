package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@RooEquals
@RooJavaBean
@RooToString
public class AdmissionUniqueConstraint implements UniqueConstraint<Admission> {

	private String admissionCode;

	public AdmissionUniqueConstraint(Admission admission) {
		this(admission.getCode());
	}

	public AdmissionUniqueConstraint(String admissionCode) {
		this.admissionCode = admissionCode;
	}

	@Override
	public Boolean isDuplicate(Admission duplicate) {
		return admissionCode == duplicate.getCode();
	}

	@Override
	public Boolean isFound() {
		return !isNotFound();
	}

	@Override
	public Boolean isNotFound() {
		return admissionCode == null;
	}
}
