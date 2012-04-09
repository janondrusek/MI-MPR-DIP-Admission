package cz.cvut.fit.mi_mpr_dip.admission.exception;

import javax.servlet.http.HttpServletResponse;

public class TechnicalException extends AdmissionException {

	private static final long serialVersionUID = -7242210389060280150L;

	public TechnicalException(Throwable cause) {
		this(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause);
	}

	public TechnicalException(Integer responseCode, Throwable cause) {
		super(responseCode, cause);
	}
}
