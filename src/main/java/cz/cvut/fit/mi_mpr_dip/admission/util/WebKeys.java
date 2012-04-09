package cz.cvut.fit.mi_mpr_dip.admission.util;

public interface WebKeys {

	public static final String CALL_IDENTIFIER = "call-identifier";
	public static final String DURATION = "duration";
	public static final String ERROR = "Error";
	public static final String HTTP_RESPONSE_CODE = "httpResponseCode";
	public static final String OK = "OK";
	public static final String QUERY = "query";
	public static final String STATUS = "status";

	public static final String MDC_KEY_CALL_IDENTIFIER = CALL_IDENTIFIER;
	public static final String MDC_KEY_REQUEST_STARTED = "request-started";
	public static final String MDC_KEY_INTERNAL_REQUEST_ID = "internal-request-id";

	public static final String UNEXPECTED_ERROR = "Unexpected Error";
	public static final String X_CTU_FIT_ADMISSION_SESSION = "X-CTU-FIT-Admission-Session";
}
