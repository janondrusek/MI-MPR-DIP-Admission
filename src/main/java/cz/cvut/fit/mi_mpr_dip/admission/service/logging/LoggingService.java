package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedRequestWrapper;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedResponseWrapper;

public interface LoggingService {

	public void logRequest(BufferedRequestWrapper httpRequest);
	
	public void logResponse(BufferedResponseWrapper httpResponse);
	
	public void logErrorResponse();
}
