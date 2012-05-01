package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
public class LanguageTermDeduplicationTemplate extends CommonTermDeduplicationTemplate<Language> {

	@Override
	protected Set<Language> collect(Programme programme) {
		return wrap(programme.getLanguage());
	}

	@Override
	protected List<Language> findByNameEquals(Language language) {
		return Language.findLanguagesByNameEquals(language.getName()).getResultList();
	}

	@Override
	protected void deduplicateItem(Language language, Programme programme) {
		if (language.equals(programme.getLanguage())) {
			programme.setLanguage(language);
		}
	}

}
