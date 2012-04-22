<%@ include file="include.jsp"%>
<jsp:include page="error.jspf">
	<jsp:param name="message"
		value="<%=((AuthenticationException) request.getAttribute(WebAttributes.ACCESS_DENIED_403)).getMessage()%>" />
</jsp:include>