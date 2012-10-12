<%@ include file="include.jsp"%>
<c:set var="errorMessage"
	value="<%=((AuthenticationException) request.getAttribute(WebAttributes.ACCESS_DENIED_403)).getMessage()%>" />
<%@ include file="error.jspf"%>
