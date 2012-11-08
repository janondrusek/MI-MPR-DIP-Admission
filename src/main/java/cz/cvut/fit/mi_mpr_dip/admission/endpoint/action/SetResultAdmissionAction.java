package cz.cvut.fit.mi_mpr_dip.admission.endpoint.action;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;

@Component
public class SetResultAdmissionAction implements AdmissionAction<AdmissionResult> {

	@Override
	public void performAction(Admission admission, AdmissionResult result) {
		admission.setResult(result);
	}
}
