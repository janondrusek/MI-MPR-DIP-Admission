package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermRegistrationDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.TermRegistrationUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.TermEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.service.AdmissionService;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;
import cz.cvut.fit.mi_mpr_dip.admission.validation.unique.TermRegistrationUniqueConstraintValidator;

@RooJavaBean
@Service
public class RegistrationEndpointHelperImpl extends CommonEndpointHelper<TermRegistration> implements
		RegistrationEndpointHelper {

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private TermRegistrationDao termRegistrationDao;

	@Autowired
	private TermService termService;

	@Autowired
	private TermRegistrationUniqueConstraintValidator uniqueConstraintValidator;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Transactional
	@Override
	public Response addRegistration(String admissionCode, String dateOfTerm, String room) {
		validate(admissionCode, dateOfTerm, room);
		Admission admission = getAdmissionService().get(new AdmissionUniqueConstraint(admissionCode));
		Term term = getTermService().getTerm(dateOfTerm, room);

		TermRegistration termRegistration = new TermRegistration();
		termRegistration.setAdmission(admission);
		termRegistration.setTerm(term);

		termRegistration.persist();

		return getSeeOtherResponse(getUriEndpointHelper().getTermLocation(TermEndpointImpl.ENDPOINT_PATH, term));
	}

	@Override
	public Response deleteRegistration(String admissionCode, String dateOfTerm, String room) {
		TermRegistration termRegistration = getTermRegistrationOrThrowNotFound(admissionCode, dateOfTerm, room);

		termRegistration.remove();
		return getOkResponse();
	}

	private TermRegistration getTermRegistrationOrThrowNotFound(String admissionCode, String dateOfTerm, String room) {
		return null;
	}

	private void validate(String admissionCode, String dateOfTerm, String room) {
		Date date = getTermService().getDate(dateOfTerm);
		TermRegistration termRegistration = getTermRegistrationDao().getTermRegistration(admissionCode, date, room);
		if (isFound(termRegistration)) {
			getUniqueConstraintValidator().validate(termRegistration);
		}
	}

	@Override
	protected boolean isNotFound(TermRegistration termRegistration) {
		return !isFound(termRegistration);
	}

	private boolean isFound(TermRegistration termRegistration) {
		TermRegistrationUniqueConstraint uniqueConstraint = new TermRegistrationUniqueConstraint(termRegistration);
		return !uniqueConstraint.isNotFound();
	}

	@Override
	public void validate(TermRegistration termRegistration) {
		super.validate(termRegistration);
		getUniqueConstraintValidator().validate(termRegistration);
	}

}
