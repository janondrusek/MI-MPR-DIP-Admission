package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;
import cz.cvut.fit.mi_mpr_dip.admission.service.crud.AppendixService;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@RooJavaBean
@Service
public class TermServiceImpl implements TermService {

	private static final Logger log = LoggerFactory.getLogger(TermServiceImpl.class);

	@Autowired
	private AppendixService appendixService;

	@Autowired
	private BusinessExceptionUtil businessExceptionUtil;

	@Autowired
	private LinkService linkService;

	@Autowired
	private TermDao termDao;

	@Autowired
	private TermDateUtils termDateUtils;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Override
	public void addLinks(Term term) {
		Set<TermRegistration> registrations = term.getRegistrations();
		addLinks(registrations);
	}

	@Override
	public void addLinks(Set<TermRegistration> registrations) {
		if (CollectionUtils.isNotEmpty(registrations)) {
			for (TermRegistration termRegistration : registrations) {
				addLink(termRegistration);
			}
		}
	}

	private void addLink(TermRegistration termRegistration) {
		addAdmissionLink(termRegistration);
		addApologyLink(termRegistration);
		addTermLink(termRegistration);
	}

	private void addAdmissionLink(TermRegistration termRegistration) {
		Admission admission = termRegistration.getAdmission();
		if (admission != null) {
			termRegistration.setAdmissionLink(getAdmissionLink(admission));
		}
	}

	private void addApologyLink(TermRegistration termRegistration) {
		getAppendixService().addLinks(termRegistration);
	}

	private Admission getAdmissionLink(Admission admission) {
		Admission admissionLink = new Admission();
		admissionLink.setLink(getLinkService().getAdmissionLink(admission));

		return admissionLink;
	}

	private void addTermLink(TermRegistration termRegistration) {
		Term term = termRegistration.getTerm();
		if (term != null) {
			termRegistration.setTermLink(getTermLink(term));
		}
	}

	private Term getTermLink(Term term) {
		Term termLink = new Term();
		termLink.setLink(getLinkService().getTermLink(term));

		return termLink;
	}

	@Override
	public Term getTerm(String dateOfTerm, String room) {
		Date date = getDate(dateOfTerm);
		return getTerm(date, room);
	}

	@Override
	public Term getTerm(Date dateOfTerm, String room) {
		return getTermOrThrowNotFound(dateOfTerm, room);
	}

	public Term getTermOrThrowNotFound(Date dateOfTerm, String room) {
		Term term = findTerm(dateOfTerm, room);
		validateNotFound(term);
		addLinks(term);

		return term;
	}

	private Term findTerm(Date dateOfTerm, String room) {
		return getTermDao().getTerm(dateOfTerm, room);
	}

	private void validateNotFound(Term term) {
		if (isNotFound(term)) {
			throwNotFoundBusinessException();
		}
	}

	private boolean isNotFound(Term term) {
		return term.getDateOfTerm() == null || term.getRoom() == null;
	}

	private void throwNotFoundBusinessException() {
		getBusinessExceptionUtil().throwException(HttpServletResponse.SC_NOT_FOUND, WebKeys.NOT_FOUND);
	}

	@Override
	public Date getDate(String dateOfTerm) {
		Date date = null;
		try {
			date = getTermDateUtils().fromUnderscoredIso(dateOfTerm);
		} catch (Exception e) {
			log.info("Unable to parse date [{}]", String.valueOf(e));
		}
		return date;
	}

}
