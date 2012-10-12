package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

import java.util.Date;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

@RooEquals
@RooJavaBean
@RooToString
public class TermRegistrationUniqueConstraint extends BaseUniqueConstraint<TermRegistration> {

	private String admissionCode;
	private Date dateOfTerm;
	private String room;

	public TermRegistrationUniqueConstraint(TermRegistration termRegistration) {
		this(termRegistration.getAdmission().getCode(), termRegistration.getTerm().getDateOfTerm(), termRegistration
				.getTerm().getRoom());
	}

	private TermRegistrationUniqueConstraint(String admissionCode, Date dateOfTerm, String room) {
		this.admissionCode = admissionCode;
		this.dateOfTerm = dateOfTerm;
		this.room = room;
	}

	@Override
	public Boolean isDuplicate(TermRegistration duplicate) {
		return null;
	}

	@Override
	public Boolean isNotFound() {
		return getAdmissionCode() == null || getDateOfTerm() == null || getRoom() == null;
	}

}
