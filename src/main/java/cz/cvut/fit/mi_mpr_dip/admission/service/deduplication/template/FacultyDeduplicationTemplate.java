package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Faculty;

@Service
public class FacultyDeduplicationTemplate implements AdmissionDeduplicationTemplate {

	@Override
	public void deduplicate(Admission admission) {
		List<Faculty> faculties = Faculty.findFacultysByNameEquals(admission.getFaculty().getName()).getResultList();
		if (CollectionUtils.isNotEmpty(faculties)) {
			admission.setFaculty(faculties.get(0));
		}
	}

}
