package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.AppendixDeduplicationTemplate;

@Service
public class AppendixDeduplicationSevice implements DeduplicationService<Appendix> {

	@Autowired
	private Set<AppendixDeduplicationTemplate> deduplicationTemplates;

	@Transactional
	@Override
	public void deduplicateAndStore(Appendix appendix) {
		deduplicate(appendix);

		appendix.persist();
	}

	@Transactional
	@Override
	public void deduplicateAndMerge(Appendix appendix) {
		deduplicate(appendix);

		appendix.merge();
	}

	@Override
	public void deduplicate(Appendix appendix) {
		for (AppendixDeduplicationTemplate deduplicationTemplate : deduplicationTemplates) {
			deduplicationTemplate.deduplicate(appendix);
		}
	}

}
