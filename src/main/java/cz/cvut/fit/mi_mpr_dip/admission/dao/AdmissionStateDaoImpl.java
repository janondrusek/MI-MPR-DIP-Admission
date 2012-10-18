package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionState;

@Repository
public class AdmissionStateDaoImpl extends Dao<AdmissionState> implements AdmissionStateDao {

	@Transactional(readOnly = true)
	@Override
	public AdmissionState getAdmissionState(String code) {
		return getAdmissionStateQuietly(code);
	}

	private AdmissionState getAdmissionStateQuietly(String code) {
		AdmissionState admissionState;
		try {
			admissionState = uniqueResult(AdmissionState.findAdmissionStatesByCodeEquals(code));
		} catch (Exception e) {
			admissionState = processException(e);
		}
		return admissionState;
	}

	@Override
	protected AdmissionState createEmptyResult() {
		return new AdmissionState();
	}

}
