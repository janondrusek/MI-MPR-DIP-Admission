package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import org.apache.commons.lang3.StringUtils;

import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class LoggableHash implements Loggable {

	private String key;
	private String value;

	public LoggableHash(String key, String value) {
		this(key, value, true);
	}

	public LoggableHash(String key, String value, boolean replaceSpaces) {
		if (replaceSpaces) {
			key = replaceSpaces(key);
			value = replaceSpaces(value);
		}
		this.key = key;
		this.value = value;
	}

	private String replaceSpaces(String text) {
		return StringUtils.replace(text, StringPool.SPACE, StringPool.UDERSCORE);
	}

	@Override
	public String toLogString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(key).append(StringPool.EQUALS).append(value).toString();
	}

}
