package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedRequestWrapper;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedResponseWrapper;

public interface LoggingService {

	public void logRequest(BufferedRequestWrapper httpRequest);

	public void logRequestBody(BufferedRequestWrapper httpRequest);

	public void logResponse(BufferedResponseWrapper httpResponse);

	public void logResponseBody(BufferedResponseWrapper httpResponse);

	public void logErrorResponse(BusinessException exception);

	public void logErrorResponse(TechnicalException exception);

	public void logErrorResponse(Throwable throwable, Integer httpResponseCode);
}
