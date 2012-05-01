package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.term;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
public class DegreeTermDeduplicationTemplate extends CommonTermDeduplicationTemplate<Degree> {

	@Override
	protected Set<Degree> collect(Programme programme) {
		return wrap(programme.getDegree());
	}

	@Override
	protected List<Degree> findByNameEquals(Degree degree) {
		return Degree.findDegreesByNameEquals(degree.getName()).getResultList();
	}

	@Override
	protected void deduplicateItem(Degree degree, Programme programme) {
		if (degree.equals(programme.getDegree())) {
			programme.setDegree(degree);
		}
	}
}
