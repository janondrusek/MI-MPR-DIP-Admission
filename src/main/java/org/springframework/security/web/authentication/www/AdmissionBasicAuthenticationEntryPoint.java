package org.springframework.security.web.authentication.www;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;

@RooJavaBean
public class AdmissionBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private String errorPage;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authenticationException) throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		// Put exception into request scope (perhaps of use to a view)
		request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, authenticationException);

		// Set the 401 status code.
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		// forward to error page.
		RequestDispatcher dispatcher = request.getRequestDispatcher(getErrorPage());
		dispatcher.forward(request, response);
	}

	@Required
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}
}
