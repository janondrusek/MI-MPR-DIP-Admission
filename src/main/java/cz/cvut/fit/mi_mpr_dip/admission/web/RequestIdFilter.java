package cz.cvut.fit.mi_mpr_dip.admission.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public class RequestIdFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader(WebKeys.X_CTU_FIT_ADMISSION_INTERNAL_REQUEST_ID,
				MDC.get(WebKeys.MDC_KEY_INTERNAL_REQUEST_ID));
		chain.doFilter(request, httpResponse);
	}

	@Override
	public void destroy() {

	}

}
