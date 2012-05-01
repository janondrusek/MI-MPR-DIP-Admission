package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Terms;
import cz.cvut.fit.mi_mpr_dip.admission.exception.util.BusinessExceptionUtil;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@Service
@RooJavaBean
public class TermEndpointHelperImpl extends CommonEndpointHelper<Term> implements TermEndpointHelper {

	private static final Logger log = LoggerFactory.getLogger(TermEndpointHelperImpl.class);

	@Autowired
	private BusinessExceptionUtil businessExceptionUtil;

	@Autowired
	private TermDao termDao;

	@Autowired
	private TermDateUtils termDateUtils;

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

		Long count = new Long(dbTerms.size());
		terms.setCount(count);
		terms.setTotalCount(count);
	}

	@Override
	public Response getTerm(String dateOfTerm, String room) {
		Response response = null;
		try {
			Date date = getTermDateUtils().fromUnderscoredIso(dateOfTerm);
			response = getTerm(date, room);
		} catch (Exception e) {
			log.info("Unable to fetch Term [{}]", String.valueOf(e));
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		}
		return response;
	}

	@Override
	public Response getTerm(Date dateOfTerm, String room) {
		Term term = getTermDao().getTerm(dateOfTerm, room);
		validateNotFound(term);
		return getOkResponse(term);
	}

	private void validateNotFound(Term term) {
		if (term.getDateOfTerm() == null || term.getRoom() == null) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_NOT_FOUND, WebKeys.NOT_FOUND);
		}
	}

	@Override
	public void validate(Term term) {
		super.validate(term);
	}

}
