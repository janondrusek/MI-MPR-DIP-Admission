package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
public class LanguageDeduplicationTemplate implements ProgrammeDeduplicationTemplate {

	@Override
	public void deduplicate(Programme programme) {
		List<Language> languages = Language.findLanguagesByNameEquals(programme.getLanguage().getName())
				.getResultList();
		if (CollectionUtils.isNotEmpty(languages)) {
			programme.setLanguage(languages.get(0));
		}
	}

}
