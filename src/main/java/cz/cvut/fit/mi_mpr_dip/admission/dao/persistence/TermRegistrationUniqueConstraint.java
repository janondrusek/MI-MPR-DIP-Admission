package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

@RooJavaBean
public class TermRegistrationUniqueConstraint implements UniqueConstraint<TermRegistration> {

	private String admissionCode;
	private Date dateOfTerm;
	private String room;

	public TermRegistrationUniqueConstraint(TermRegistration termRegistration) {
		this(termRegistration.getAdmission().getCode(), termRegistration.getTerm().getDateOfTerm(), termRegistration
				.getTerm().getRoom());
	}

	public TermRegistrationUniqueConstraint(String admissionCode, Date dateOfTerm, String room) {
		this.admissionCode = admissionCode;
		this.dateOfTerm = dateOfTerm;
		this.room = room;
	}

	@Override
	public Boolean isDuplicate(TermRegistration duplicate) {
		return null;
	}

	@Override
	public Boolean isFound() {
		return !isFound();
	}

	@Override
	public Boolean isNotFound() {
		return getAdmissionCode() == null || getDateOfTerm() == null || getRoom() == null;
	}

}
