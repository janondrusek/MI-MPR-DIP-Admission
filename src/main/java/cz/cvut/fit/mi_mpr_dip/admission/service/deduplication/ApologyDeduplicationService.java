package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Apology;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.AppendixDeduplicationTemplate;

@Service
public class ApologyDeduplicationService implements DeduplicationService<Apology> {

	@Autowired
	private AppendixDeduplicationTemplate appendixDeduplicationTemplate;

	@Transactional
	@Override
	public void deduplicateAndStore(Apology apology) {
		deduplicate(apology);

		apology.persist();
	}

	@Transactional
	@Override
	public void deduplicateAndMerge(Apology apology) {
		deduplicate(apology);

		apology.merge();
	}

	@Override
	public void deduplicate(Apology apology) {
		if (CollectionUtils.isNotEmpty(apology.getAppendices())) {
			for (Appendix appendix : apology.getAppendices()) {
				appendixDeduplicationTemplate.deduplicate(appendix);
			}
		}
	}
}
