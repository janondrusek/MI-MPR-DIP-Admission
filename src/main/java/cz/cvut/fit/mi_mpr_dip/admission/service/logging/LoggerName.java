package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

public enum LoggerName {

	REQUEST("admission-request"), RESPONSE("admission-response");

	private String keyword;

	private LoggerName(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
