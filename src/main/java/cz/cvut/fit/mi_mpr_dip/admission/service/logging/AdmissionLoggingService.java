package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedRequestWrapper;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedResponseWrapper;

@Service
public class AdmissionLoggingService implements LoggingService {

	private static final Logger requestLog = LoggerFactory.getLogger(LoggerName.REQUEST.getKeyword());
	private static final Logger responseLog = LoggerFactory.getLogger(LoggerName.RESPONSE.getKeyword());

	@Override
	public void logRequest(BufferedRequestWrapper httpRequest) {
		List<Loggable> loggables = createCallAwareLoggables();
		addRequestHeaders(httpRequest, loggables);
		addRequestQuery(httpRequest, loggables);

		log(loggables, requestLog, Level.INFO);
	}

	private void addRequestHeaders(BufferedRequestWrapper httpRequest, List<Loggable> loggables) {
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				loggables.add(createLoggable(headerName, httpRequest.getHeader(headerName)));
			}
		}
	}

	private void addRequestQuery(BufferedRequestWrapper httpRequest, List<Loggable> loggables) {
		loggables.add(createLoggable(
				WebKeys.QUERY,
				StringUtils.replaceEach(httpRequest.getQueryString(), new String[] { StringPool.QUESTIONMARK,
						StringPool.AMPERSAND }, new String[] { StringPool.BLANK, StringPool.UDERSCORE })));
	}

	@Override
	public void logResponse(BufferedResponseWrapper httpResponse) {
		List<Loggable> loggables = createCallAwareLoggables();
		loggables.add(createLoggable(WebKeys.HTTP_RESPONSE_CODE, httpResponse.getStatusCode()));
		loggables.add(createLoggable(WebKeys.DURATION, getDuration()));
		loggables.add(createLoggable(WebKeys.STATUS, WebKeys.OK));

		log(loggables, responseLog, Level.INFO);
	}

	private String getDuration() {
		long start = NumberUtils.toLong(MDC.get(WebKeys.MDC_KEY_REQUEST_STARTED));
		return Long.toString(System.currentTimeMillis() - start);
	}

	@Override
	public void logErrorResponse(BusinessException exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logErrorResponse(TechnicalException exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void logErrorResponse(Throwable throwable) {
		// TODO Auto-generated method stub

	}

	private List<Loggable> createCallAwareLoggables() {
		List<Loggable> loggables = new ArrayList<Loggable>();
		loggables.add(createLoggable(WebKeys.CALL_IDENTIFIER, MDC.get(WebKeys.MDC_KEY_CALL_IDENTIFIER)));

		return loggables;
	}

	private Loggable createLoggable(String key, int value) {
		return createLoggable(key, Integer.toString(value));
	}

	private Loggable createLoggable(String key, String value) {
		return new LoggableHash(key, value);
	}

	private void log(List<Loggable> loggables, Logger log, Level level) {
		log(createMessage(loggables), log, level);
	}

	private String createMessage(List<Loggable> loggables) {
		StringBuilder sb = new StringBuilder();
		for (Loggable loggable : loggables) {
			sb.append(loggable.toLogString());
			sb.append(StringPool.SPACE);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private void log(String message, Logger log, Level level) {
		if (Level.TRACE.equals(level)) {
			log.trace(message);
		} else if (Level.INFO.equals(level)) {
			log.info(message);
		} else if (Level.WARN.equals(level)) {
			log.warn(message);
		} else if (Level.ERROR.equals(level)) {
			log.error(message);
		} else {
			log.debug(message);
		}
	}
}