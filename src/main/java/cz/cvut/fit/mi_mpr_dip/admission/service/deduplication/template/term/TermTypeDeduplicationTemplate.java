package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermType;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class TermTypeDeduplicationTemplate extends SimpleDeduplicationTemplate<Term, TermType> implements
		TermDeduplicationTemplate {

	@Override
	protected TypedQuery<TermType> findDegreesByNameEquals(Term term) {
		return TermType.findTermTypesByNameEquals(term.getTermType().getName());
	}

	@Override
	protected void setFound(Term term, TermType termType) {
		term.setTermType(termType);
	}

}
