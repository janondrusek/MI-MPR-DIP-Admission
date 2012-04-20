<%@ include file="include.jsp"%>
<jsp:include page="error.jspf">
	<jsp:param name="message"
		value="<%=((AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
					.getMessage()%>" />
</jsp:include>
