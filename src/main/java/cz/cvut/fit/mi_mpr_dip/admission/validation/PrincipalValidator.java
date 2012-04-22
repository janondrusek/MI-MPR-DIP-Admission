package cz.cvut.fit.mi_mpr_dip.admission.validation;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@RooJavaBean
public class PrincipalValidator {

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	public void validatePrincipal(String username) {
		if (isNotEqual(getSecurityContextHolderStrategy().getContext().getAuthentication().getName(), username)) {
			throwBusinessException(HttpServletResponse.SC_FORBIDDEN, new AccessDeniedException("Access denied"));
		}
	}

	private void throwBusinessException(Integer code, Throwable t) {
		throw new BusinessException(code, t);
	}

	private boolean isNotEqual(String one, String two) {
		return !StringUtils.equals(one, two);
	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}

}
