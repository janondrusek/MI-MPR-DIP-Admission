package cz.cvut.fit.mi_mpr_dip.admission.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import cz.cvut.fit.mi_mpr_dip.admission.authentication.AuthenticationUtil;
import cz.cvut.fit.mi_mpr_dip.admission.authentication.UserAuthentication;
import cz.cvut.fit.mi_mpr_dip.admission.dao.UserSessionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserSession;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@Component
public class SessionAuthenticationFilter extends GenericFilterBean {

	@Autowired
	AuthenticationUtil authenticationUtil;

	@Autowired
	private UserSessionDao userSessionDao;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;

		String session = request.getHeader(WebKeys.X_CTU_FIT_ADMISSION_SESSION);
		SecurityContextHolder.getContext().setAuthentication(createAuthentication(session));
		chain.doFilter(req, res);
	}

	private Authentication createAuthentication(String session) {
		Authentication authentication = null;
		if (StringUtils.isNotBlank(session)) {
			UserSession userSession = userSessionDao.getUserSession(session);
			if (userSession.getIdentifier() != null) {
				UserIdentity userIdentity = userSession.getUserIdentity();
				authentication = new UserAuthentication(userIdentity.getUsername(), userSession.getIdentifier(),
						authenticationUtil.getAuthorities(userIdentity.getRoles()));
			}
		}
		return authentication;
	}
}
