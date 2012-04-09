package cz.cvut.fit.mi_mpr_dip.admission.exception;

public abstract class AdmissionException extends RuntimeException {

	private static final long serialVersionUID = 6440534023347904569L;

	private Integer responseCode;

	public AdmissionException(Integer responseCode) {
		this(responseCode, null);
	}

	public AdmissionException(Integer responseCode, Throwable cause) {
		super(cause);
		this.responseCode = responseCode;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

}
