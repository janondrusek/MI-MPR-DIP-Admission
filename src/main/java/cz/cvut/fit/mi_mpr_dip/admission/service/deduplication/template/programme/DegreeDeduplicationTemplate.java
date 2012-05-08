package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.programme;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.SimpleDeduplicationTemplate;

@Service
public class DegreeDeduplicationTemplate extends SimpleDeduplicationTemplate<Programme, Degree> implements
		ProgrammeDeduplicationTemplate {

	@Override
	protected TypedQuery<Degree> findByNameEquals(Programme programme) {
		return Degree.findDegreesByNameEquals(programme.getDegree().getName());
	}

	@Override
	protected void setFound(Programme programme, Degree degree) {
		programme.setDegree(degree);
	}

}
