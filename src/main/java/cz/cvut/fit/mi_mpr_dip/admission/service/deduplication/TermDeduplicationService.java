package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

@Service
public class TermDeduplicationService implements DeduplicationService<Term> {

	@Transactional
	@Override
	public void deduplicateAndStore(Term term) {

		term.persist();
	}

}
