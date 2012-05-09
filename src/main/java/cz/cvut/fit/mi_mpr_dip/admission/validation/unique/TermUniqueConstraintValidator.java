package cz.cvut.fit.mi_mpr_dip.admission.validation.unique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.TermDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
@Service
public class TermUniqueConstraintValidator extends BaseUniqueConstraintValidator<Term> {

	@Autowired
	private TermDao termDao;

	@Override
	protected String getPath() {
		return "dateOfTerm";
	}

	@Override
	protected String getDuplicateValue(Term o) {
		return o.getDateOfTerm() + StringPool.DASH + o.getRoom();
	}

	@Override
	protected boolean isDuplicate(Term term) {
		Term duplicate = getTermDao().getTerm(term.getDateOfTerm(), term.getRoom());
		return duplicate.getDateOfTerm() != null && duplicate.getRoom() != null;
	}
}
