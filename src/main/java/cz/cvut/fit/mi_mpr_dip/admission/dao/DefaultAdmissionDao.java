package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.HashSet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Photo;

@Repository
public class DefaultAdmissionDao extends AbstractDao implements AdmissionDao {

	@Transactional(readOnly = true)
	@Override
	public Admission getAdmission(String code) {
		Admission admission = uniqueResult(Admission.class, Admission.findAdmissionsByCodeEquals(code));
		if (admission.getPhotos() == null) {
			admission.setPhotos(new HashSet<Photo>());
		}
		return admission;
	}
	
	public void merge(Admission admission) {
		admission.merge();
		//jbpmProcessor.doSomething(admission);
	}
	
	public void persist() {
		
	}
}