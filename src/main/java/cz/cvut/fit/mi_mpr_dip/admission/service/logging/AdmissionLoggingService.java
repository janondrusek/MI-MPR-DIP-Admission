package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedRequestWrapper;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedResponseWrapper;

@Service
public class AdmissionLoggingService implements LoggingService {

	@Override
	public void logRequest(BufferedRequestWrapper httpRequest) {

	}

	@Override
	public void logResponse(BufferedResponseWrapper httpResponse) {

	}

	@Override
	public void logErrorResponse() {
		// TODO Auto-generated method stub

	}

}
