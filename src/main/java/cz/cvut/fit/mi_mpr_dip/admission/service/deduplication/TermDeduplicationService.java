package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term.TermDeduplicationTemplate;

@Service
public class TermDeduplicationService implements DeduplicationService<Term> {

	@Autowired
	private Set<TermDeduplicationTemplate> deduplicationTemplates;

	@Transactional
	@Override
	public void deduplicateAndStore(Term term) {
		deduplicate(term);

		term.persist();
	}

	@Transactional
	public void deduplicateAndMerge(Term term) {
		deduplicate(term);

		term.merge();
	}

	private void deduplicate(Term term) {
		for (TermDeduplicationTemplate deduplicationTemplate : deduplicationTemplates) {
			deduplicationTemplate.deduplicate(term);
		}
	}

}
