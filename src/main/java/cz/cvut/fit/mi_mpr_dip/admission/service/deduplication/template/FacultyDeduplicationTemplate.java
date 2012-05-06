package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Faculty;

@Service
public class FacultyDeduplicationTemplate extends SimpleDeduplicationTemplate<Admission, Faculty> implements
		AdmissionDeduplicationTemplate {

	@Override
	protected TypedQuery<Faculty> findDegreesByNameEquals(Admission admission) {
		return Faculty.findFacultysByNameEquals(admission.getFaculty().getName());
	}

	@Override
	protected void setFound(Admission admission, Faculty faculty) {
		admission.setFaculty(faculty);
	}

}
