package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.programme;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
public class DegreeDeduplicationTemplate implements ProgrammeDeduplicationTemplate {

	@Override
	public void deduplicate(Programme programme) {
		List<Degree> degrees = Degree.findDegreesByNameEquals(programme.getDegree().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(degrees)) {
			programme.setDegree(degrees.get(0));
		}
	}

}
