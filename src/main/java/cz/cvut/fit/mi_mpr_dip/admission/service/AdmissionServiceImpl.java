package cz.cvut.fit.mi_mpr_dip.admission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.UniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@RooJavaBean
@Service
public class AdmissionServiceImpl extends BaseCrudService<Admission> implements AdmissionService {

	@Autowired
	private AdmissionDao admissionDao;

	@Override
	public void add(Admission admission) {

	}

	@Override
	public Admission get(UniqueConstraint<Admission> uniqueConstraint) {
		AdmissionUniqueConstraint admissionUniqueConstraint = getUniqueConstraint(uniqueConstraint);

		return getAdmissionOrThrowNotFound(admissionUniqueConstraint);
	}

	@Override
	public void update(UniqueConstraint<Admission> uniqueConstraint, Admission admission) {

	}

	@Override
	public void delete(UniqueConstraint<Admission> uniqueConstraint) {

	}

	private AdmissionUniqueConstraint getUniqueConstraint(UniqueConstraint<Admission> uniqueConstraint) {
		return (AdmissionUniqueConstraint) uniqueConstraint;
	}

	private Admission getAdmissionOrThrowNotFound(AdmissionUniqueConstraint uniqueConstraint) {
		Admission admission = getAdmissionDao().getAdmission(uniqueConstraint.getAdmissionCode());
		validateNotFound(new AdmissionUniqueConstraint(admission));

		return admission;
	}

}
