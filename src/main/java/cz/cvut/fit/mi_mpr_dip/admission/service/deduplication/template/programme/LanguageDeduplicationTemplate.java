package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.programme;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class LanguageDeduplicationTemplate extends SimpleDeduplicationTemplate<Programme, Language> implements
		ProgrammeDeduplicationTemplate {

	@Override
	protected TypedQuery<Language> findDegreesByNameEquals(Programme programme) {
		return Language.findLanguagesByNameEquals(programme.getLanguage().getName());
	}

	@Override
	protected void setFound(Programme programme, Language language) {
		programme.setLanguage(language);
	}

}
