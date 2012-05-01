package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.CollectionDeduplicationTemplate;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.util.ProgrammeDeduplicationUtil;

@Service
public class ProgrammeTermDeduplicationTemplate extends CollectionDeduplicationTemplate<Programme, Term> implements
		TermDeduplicationTemplate {

	@Autowired
	private ProgrammeDeduplicationUtil programmeDeduplicationUtil;

	@Override
	protected Set<Programme> getCollection(Term term) {
		return term.getPrograms();
	}

	@Override
	protected Programme findDuplicate(Programme programme) {
		return programmeDeduplicationUtil.deduplicate(programme);
	}

}
