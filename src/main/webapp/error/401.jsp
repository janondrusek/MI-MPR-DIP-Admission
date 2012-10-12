<%@ include file="include.jsp"%>
<c:set var="errorMessage"
	value="<%=((AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
					.getMessage()%>" />
<%@ include file="error.jspf"%>
