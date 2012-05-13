package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermRegistrationDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.TermRegistrationUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Apology;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.TermEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.service.AdmissionService;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.ApologyDeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.validation.unique.TermRegistrationUniqueConstraintValidator;

@RooJavaBean
@Service
public class RegistrationEndpointHelperImpl extends CommonEndpointHelper<TermRegistration> implements
		RegistrationEndpointHelper {

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private ApologyDeduplicationService apologyDeduplicationService;

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

		return getSeeOtherResponse(term);
	}

	@Transactional
	@Override
	public Response deleteRegistration(String admissionCode, String dateOfTerm, String room) {
		TermRegistration termRegistration = getTermRegistrationOrThrowNotFound(admissionCode, dateOfTerm, room);
		getTermRegistrationDao().delete(termRegistration);

		return getOkResponse();
	}

	private TermRegistration getTermRegistrationOrThrowNotFound(String admissionCode, String dateOfTerm, String room) {
		Date date = getTermService().getDate(dateOfTerm);
		TermRegistration termRegistration = getTermRegistration(admissionCode, date, room);
		validateNotFound(termRegistration);

		return termRegistration;
	}

	private void validate(String admissionCode, String dateOfTerm, String room) {
		Date date = getTermService().getDate(dateOfTerm);
		TermRegistration termRegistration = getTermRegistration(admissionCode, date, room);
		if (isFound(termRegistration)) {
			getUniqueConstraintValidator().validate(termRegistration);
		}
	}

	private TermRegistration getTermRegistration(String admissionCode, Date dateOfTerm, String room) {
		TermRegistration termRegistration = getTermRegistrationDao().getTermRegistration(admissionCode, dateOfTerm,
				room);
		return termRegistration;
	}

	@Transactional
	@Override
	public Response addApology(String admissionCode, String dateOfTerm, String room, Apology apology) {
		TermRegistration termRegistration = getTermRegistrationOrThrowNotFound(admissionCode, dateOfTerm, room);
		if (isApologyUpdate(termRegistration)) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_BAD_REQUEST, "Update required");
		}
		addApology(termRegistration, apology);

		return getSeeOtherResponse(termRegistration.getTerm());
	}

	@Transactional
	@Override
	public Response deleteApology(String admissionCode, String dateOfTerm, String room) {
		TermRegistration termRegistration = getTermRegistrationOrThrowNotFound(admissionCode, dateOfTerm, room);
		validate(termRegistration.getApology());

		termRegistration.getApology().setRegistration(null);
		termRegistration.getApology().remove();
		termRegistration.setApology(null);
		termRegistration.persist();

		return getOkResponse();
	}

	private void validate(Apology apology) {
		if (apology == null) {
			throwNotFoundBusinessException();
		}
	}

	@Transactional
	@Override
	public Response updateApology(String admissionCode, String dateOfTerm, String room, Apology apology) {
		TermRegistration termRegistration = getTermRegistrationOrThrowNotFound(admissionCode, dateOfTerm, room);
		if (isApologyUpdate(termRegistration)) {
			updateApology(termRegistration, apology);
		} else {
			throwNotFoundBusinessException();
		}

		return getSeeOtherResponse(termRegistration.getTerm());
	}

	private boolean isApologyUpdate(TermRegistration termRegistration) {
		return termRegistration.getApology() != null;
	}

	private void updateApology(TermRegistration termRegistration, Apology apology) {
		removeAppendices(termRegistration);
		setAppendices(apology);
		getApologyDeduplicationService().deduplicate(apology);

		termRegistration.getApology().setApproved(apology.getApproved());
		termRegistration.getApology().setAppendices(apology.getAppendices());
		termRegistration.getApology().setText(apology.getText());

		termRegistration.getApology().merge();
	}

	private void removeAppendices(TermRegistration termRegistration) {
		for (Iterator<Appendix> iterator = termRegistration.getApology().getAppendices().iterator(); iterator.hasNext();) {
			Appendix appendix = iterator.next();
			iterator.remove();
			appendix.remove();
		}
		termRegistration.persist();
	}

	private void addApology(TermRegistration termRegistration, Apology apology) {
		setAppendices(apology);
		apology.setRegistration(termRegistration);
		getApologyDeduplicationService().deduplicateAndStore(apology);
	}

	private void setAppendices(Apology apology) {
		apology.setAppendices(apology.getMarshalledAppendices());
	}

	private Response getSeeOtherResponse(Term term) {
		return getSeeOtherResponse(getUriEndpointHelper().getTermLocation(TermEndpointImpl.ENDPOINT_PATH, term));
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
