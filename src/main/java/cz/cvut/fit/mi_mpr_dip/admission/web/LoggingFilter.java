package cz.cvut.fit.mi_mpr_dip.admission.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import cz.cvut.fit.mi_mpr_dip.admission.service.logging.LoggingService;
import cz.cvut.fit.mi_mpr_dip.admission.util.RandomStringGenerator;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public class LoggingFilter implements Filter {

	private static final String LOGGING_SERVICE_BEAN_NAME = "admissionLoggingService";
	private static final String RANDOM_STRING_GENERATOR_BEAN_NAME = "randomStringGenerator";

	private LoggingService loggingService;
	private RandomStringGenerator randomStringGenerator;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ApplicationContext applicationContext = (ApplicationContext) filterConfig.getServletContext().getAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		loggingService = (LoggingService) applicationContext.getBean(LOGGING_SERVICE_BEAN_NAME);
		randomStringGenerator = (RandomStringGenerator) applicationContext.getBean(RANDOM_STRING_GENERATOR_BEAN_NAME);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		try {
			BufferedRequestWrapper httpRequest = new BufferedRequestWrapper((HttpServletRequest) request);
			addMdcValues(httpRequest);
			log(httpRequest);

			BufferedResponseWrapper httpResponse = new BufferedResponseWrapper((HttpServletResponse) response);
			chain.doFilter(httpRequest, httpResponse);
			log(httpResponse);
		} finally {
			deleteMdcValues();
		}
	}

	private void addMdcValues(BufferedRequestWrapper httpRequest) {
		MDC.put(WebKeys.MDC_KEY_REQUEST_STARTED, getTimestamp().toString());
		MDC.put(WebKeys.MDC_KEY_INTERNAL_REQUEST_ID, randomStringGenerator.generateRandom());
		MDC.put(WebKeys.MDC_KEY_CALL_IDENTIFIER, getCallIdentifier(httpRequest));
	}

	private String getCallIdentifier(BufferedRequestWrapper httpRequest) {
		return httpRequest.getRequestURI() + StringPool.UDERSCORE + httpRequest.getMethod();
	}

	private Long getTimestamp() {
		return System.currentTimeMillis();
	}

	private void log(BufferedRequestWrapper httpRequest) {
		loggingService.logRequest(httpRequest);
	}

	private void log(BufferedResponseWrapper httpResponse) {
		if (shouldNotLogErrorResponse()) {
			loggingService.logResponse(httpResponse);
		}
	}

	private boolean shouldNotLogErrorResponse() {
		return BooleanUtils.isNotTrue(BooleanUtils.toBoolean(MDC.get(WebKeys.MDC_KEY_ERROR_RESPONSE)));
	}

	private void deleteMdcValues() {
		MDC.clear();
	}

	@Override
	public void destroy() {

	}

}
