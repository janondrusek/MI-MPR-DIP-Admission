package cz.cvut.fit.mi_mpr_dip.admission.service.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;

import ch.qos.logback.classic.Level;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedRequestWrapper;
import cz.cvut.fit.mi_mpr_dip.admission.web.BufferedResponseWrapper;

@RooJavaBean
public class AdmissionLoggingService implements LoggingService {

	private static final Logger log = LoggerFactory.getLogger(AdmissionLoggingService.class);
	private static final Logger requestLog = LoggerFactory.getLogger(LoggerName.REQUEST.getKeyword());
	private static final Logger requestBodyLog = LoggerFactory.getLogger(LoggerName.REQUEST_BODY.getKeyword());
	private static final Logger responseLog = LoggerFactory.getLogger(LoggerName.RESPONSE.getKeyword());
	private static final Logger responseBodyLog = LoggerFactory.getLogger(LoggerName.RESPONSE_BODY.getKeyword());

	private String abbreviationKeyword = StringUtils.repeat(StringPool.DOT, 3);
	private Integer requestBodyMaxLength;
	private Integer responseBodyMaxLength;

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
	public void logRequestBody(BufferedRequestWrapper httpRequest) {
		log(abbreviate(getBody(httpRequest), requestBodyMaxLength), requestBodyLog, Level.INFO);
	}

	private String getBody(BufferedRequestWrapper httpRequest) {
		String body = StringPool.BLANK;
		try {
			ServletInputStream inputStream = httpRequest.getInputStream();
			body = IOUtils.toString(inputStream);
			IOUtils.closeQuietly(inputStream);
		} catch (Exception e) {
			log.debug("Unable to log request body", e);
		}
		return body;
	}

	@Override
	public void logResponse(BufferedResponseWrapper httpResponse) {
		logResponse(httpResponse.getStatusCode());
	}

	@Override
	public void logResponseBody(BufferedResponseWrapper httpResponse) {
		log(abbreviate(getBody(httpResponse), responseBodyMaxLength), responseBodyLog, Level.INFO);
	}

	private String getBody(BufferedResponseWrapper httpResponse) {
		String body = StringPool.BLANK;
		try {
			body = httpResponse.getBody();
		} catch (IOException e) {
			log.debug("Unable to get response body", e);
		}
		return body;
	}

	private String abbreviate(String body, Integer length) {
		if (NumberUtils.INTEGER_ZERO.compareTo(length) >= 0) {
			return body;
		}
		return StringUtils.abbreviateMiddle(body, getAbbreviationKeyword(), length);
	}

	@Override
	public void logErrorResponse(BusinessException exception) {
		logResponse(exception.getResponseCode());
	}

	private void logResponse(Integer httpResponseCode) {
		List<Loggable> loggables = createCommonResponseLoggables(httpResponseCode, ResponseStatus.OK);
		log(loggables, responseLog, Level.INFO);
	}

	@Override
	public void logErrorResponse(TechnicalException exception) {
		logErrorResponse(exception, exception.getResponseCode());
	}

	@Override
	public void logErrorResponse(Throwable throwable, Integer httpResponseCode) {
		List<Loggable> loggables = createCommonResponseLoggables(httpResponseCode, ResponseStatus.ERROR);
		log(loggables, responseLog, Level.ERROR, throwable.getClass().getSimpleName(), throwable);
	}

	private List<Loggable> createCommonResponseLoggables(Integer responseCode, ResponseStatus responseStatus) {
		List<Loggable> loggables = createCallAwareLoggables();
		loggables.add(createLoggable(WebKeys.CODE, responseCode));
		loggables.add(createLoggable(WebKeys.DURATION, getDuration()));
		loggables.add(createLoggable(WebKeys.STATUS, responseStatus.name()));

		return loggables;
	}

	private List<Loggable> createCallAwareLoggables() {
		List<Loggable> loggables = new ArrayList<Loggable>();
		loggables.add(createLoggable(WebKeys.CALL_IDENTIFIER, MDC.get(WebKeys.MDC_KEY_CALL_IDENTIFIER)));

		return loggables;
	}

	private String getDuration() {
		long start = NumberUtils.toLong(MDC.get(WebKeys.MDC_KEY_REQUEST_STARTED));

		return Long.toString(System.currentTimeMillis() - start);
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

	private void log(List<Loggable> loggables, Logger log, Level level, String message, Throwable throwable) {
		log(message, throwable);
		log(loggables, log, level);
	}

	private void log(String message, Throwable throwable) {
		log.error(message, throwable);
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

	@Required
	public void setRequestBodyMaxLength(Integer requestBodyMaxLength) {
		this.requestBodyMaxLength = requestBodyMaxLength;
	}

	@Required
	public void setResponseBodyMaxLength(Integer responseBodyMaxLength) {
		this.responseBodyMaxLength = responseBodyMaxLength;
	}
}