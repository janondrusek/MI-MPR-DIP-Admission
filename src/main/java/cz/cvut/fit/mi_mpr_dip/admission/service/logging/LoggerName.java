package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

public enum LoggerName {

	REQUEST("admission-request"), REQUEST_BODY("admission-request-body"), RESPONSE("admission-response"), RESPONSE_BODY(
			"admission-response-body");

	private String keyword;

	private LoggerName(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
