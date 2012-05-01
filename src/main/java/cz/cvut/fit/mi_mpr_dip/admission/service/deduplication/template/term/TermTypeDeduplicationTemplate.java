package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermType;

@Service
public class TermTypeDeduplicationTemplate implements TermDeduplicationTemplate {

	@Override
	public void deduplicate(Term term) {
		List<TermType> termTypes = TermType.findTermTypesByNameEquals(term.getTermType().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(termTypes)) {
			term.setTermType(termTypes.get(0));
		}
	}
}
