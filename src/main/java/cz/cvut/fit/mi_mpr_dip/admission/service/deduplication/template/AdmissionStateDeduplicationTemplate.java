package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionState;

@Service
public class AdmissionStateDeduplicationTemplate extends SimpleDeduplicationTemplate<Admission, AdmissionState>
		implements AdmissionDeduplicationTemplate {

	@Override
	public void deduplicate(Admission admission) {
		if (admission.getAdmissionState() != null) {
			super.deduplicate(admission);
		}
	}

	@Override
	protected TypedQuery<AdmissionState> findByNameEquals(Admission admission) {
		return AdmissionState.findAdmissionStatesByCodeEquals(admission.getAdmissionState().getCode());
	}

	@Override
	protected void setFound(Admission admission, AdmissionState admissionState) {
		admission.setAdmissionState(admissionState);
	}

}
