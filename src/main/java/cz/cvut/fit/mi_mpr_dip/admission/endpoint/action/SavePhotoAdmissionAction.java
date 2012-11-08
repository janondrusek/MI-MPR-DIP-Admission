package cz.cvut.fit.mi_mpr_dip.admission.endpoint.action;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.service.crud.AppendixService;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.AppendixDeduplicationSevice;

@Component
public class SavePhotoAdmissionAction implements AdmissionAction<Appendix> {

	@Autowired
	private AppendixService appendixService;

	@Autowired
	private AppendixDeduplicationSevice appendixDeduplicationSevice;

	@Override
	public void performAction(Admission admission, Appendix photo) {
		appendixService.addContent(photo);
		appendixDeduplicationSevice.deduplicate(photo);
		appendixService.addIdentifier(photo);
		ensurePhotos(admission);
		admission.getPhotos().add(photo);
	}

	private void ensurePhotos(Admission admission) {
		if (admission.getPhotos() == null) {
			admission.setPhotos(new HashSet<Appendix>());
		}
	}
}