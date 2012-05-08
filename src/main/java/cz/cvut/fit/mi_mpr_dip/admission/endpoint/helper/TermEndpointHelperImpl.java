package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Terms;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;
import cz.cvut.fit.mi_mpr_dip.admission.validation.TermUniqueConstraintValidator;

@Service
@RooJavaBean
public class TermEndpointHelperImpl extends CommonEndpointHelper<Term> implements TermEndpointHelper {

	private static final Logger log = LoggerFactory.getLogger(TermEndpointHelperImpl.class);

	@Autowired
	private TermDao termDao;

	@Autowired
	private TermDateUtils termDateUtils;

	@Autowired
	private TermUniqueConstraintValidator uniqueConstraintValidator;

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

		updateCollectionDomainCounters(new Long(dbTerms.size()), terms);
	}

	@Override
	public Response getTerm(String dateOfTerm, String room) {
		Date date = getDate(dateOfTerm);
		return getTerm(date, room);
	}

	@Override
	public Response getTerm(Date dateOfTerm, String room) {
		Term term = getTermOrThrowNotFound(dateOfTerm, room);
		return getOkResponse(term);
	}

	@Transactional
	@Override
	public Response deleteTerm(String dateOfTerm, String room) {
		Date date = getDate(dateOfTerm);
		Term term = getTermOrThrowNotFound(date, room);

		term.remove();
		return getOkResponse();
	}

	@Override
	public Term validate(String dateOfTerm, String room, Term term) {
		Date date = getDate(dateOfTerm);
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

	private Date getDate(String dateOfTerm) {
		Date date = null;
		try {
			date = getTermDateUtils().fromUnderscoredIso(dateOfTerm);
		} catch (Exception e) {
			log.info("Unable to parse date [{}]", String.valueOf(e));
		}
		return date;
	}

	private Term getTermOrThrowNotFound(Date dateOfTerm, String room) {
		Term term = findTerm(dateOfTerm, room);
		validateNotFound(term);
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
