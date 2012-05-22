package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Terms;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;
import cz.cvut.fit.mi_mpr_dip.admission.validation.unique.TermUniqueConstraintValidator;

@Service
@RooJavaBean
public class TermEndpointHelperImpl extends CommonEndpointHelper<Term> implements TermEndpointHelper {

	@Autowired
	private TermDao termDao;

	@Autowired
	private TermDateUtils termDateUtils;

	@Autowired
	private TermService termService;

	@Autowired
	private TermUniqueConstraintValidator uniqueConstraintValidator;

	@Transactional(readOnly = true)
	@Override
	public Response getTerms() {
		Terms terms = createTerms();
		return getOkResponse(terms);
	}

	private Terms createTerms() {
		Terms terms = new Terms();
		populateTerms(terms);

		return terms;
	}

	private void populateTerms(Terms terms) {
		List<Term> dbTerms = Term.findAllTerms();
		terms.setTerms(new HashSet<Term>(dbTerms));
		addLinks(terms.getTerms());

		updateCollectionDomainCounters(new Long(dbTerms.size()), terms);
	}

	private void addLinks(Set<Term> terms) {
		for (Term term : terms) {
			addLinks(term);
		}
	}

	private void addLinks(Term term) {
		getTermService().addLinks(term);
	}

	@Override
	public Response getTerm(String dateOfTerm, String room) {
		Date date = getTermService().getDate(dateOfTerm);
		return getTerm(date, room);
	}

	@Transactional(readOnly = true)
	@Override
	public Response getTerm(Date dateOfTerm, String room) {
		Term term = getTermOrThrowNotFound(dateOfTerm, room);
		return getOkResponse(term);
	}

	@Transactional
	@Override
	public Response deleteTerm(String dateOfTerm, String room) {
		Date date = getTermService().getDate(dateOfTerm);
		Term term = getTermOrThrowNotFound(date, room);

		term.remove();
		return getOkResponse();
	}

	@Override
	public Term validate(String dateOfTerm, String room, Term term) {
		Date date = getTermService().getDate(dateOfTerm);
		return validate(date, room, term);
	}

	private Term validate(Date date, String room, Term term) {
		super.validate(term);
		Term dbTerm = getTermOrThrowNotFound(date, room);
		if (!DateUtils.isSameInstant(date, term.getDateOfTerm()) || !StringUtils.equals(room, term.getRoom())) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_BAD_REQUEST,
					"Unique constraint change requested");
		}
		return dbTerm;
	}

	private Term getTermOrThrowNotFound(Date dateOfTerm, String room) {
		Term term = findTerm(dateOfTerm, room);
		validateNotFound(term);
		addLinks(term);

		return term;
	}

	private Term findTerm(Date dateOfTerm, String room) {
		return getTermDao().getTerm(dateOfTerm, room);
	}

	@Override
	protected boolean isNotFound(Term term) {
		return term.getDateOfTerm() == null || term.getRoom() == null;
	}

	@Override
	public void validate(Term term) {
		super.validate(term);
		getUniqueConstraintValidator().validate(term);
	}

}
