package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AppendixType;

@Service
public class AppendixTypeDeduplicationTemplate extends SimpleDeduplicationTemplate<Appendix, AppendixType> implements
		AppendixDeduplicationTemplate {

	@Override
	protected TypedQuery<AppendixType> findByNameEquals(Appendix appendix) {
		return AppendixType.findAppendixTypesByNameEquals(appendix.getAttachmentType().getName());
	}

	@Override
	protected void setFound(Appendix appendix, AppendixType appendixType) {
		appendix.setAttachmentType(appendixType);
	}

}
