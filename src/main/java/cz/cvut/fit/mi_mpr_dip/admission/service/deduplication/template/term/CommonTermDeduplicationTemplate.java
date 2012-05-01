package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.Set;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.CollectionDeduplicationTemplate;

public abstract class CommonTermDeduplicationTemplate<T> extends CollectionDeduplicationTemplate<T, Programme, Term>
		implements TermDeduplicationTemplate {

	@Override
	protected Set<Programme> getCollection(Term term) {
		return term.getPrograms();
	}
}
