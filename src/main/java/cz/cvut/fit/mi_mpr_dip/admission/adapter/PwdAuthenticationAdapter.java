package cz.cvut.fit.mi_mpr_dip.admission.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import cz.cvut.fit.mi_mpr_dip.admission.dao.UserIdentityDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;

@RooJavaBean
public class PwdAuthenticationAdapter implements AuthenticationAdapter {

	@Autowired
	private UserIdentityDao userIdentityDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public boolean authenticate(String username, String password) {
		UserIdentity userIdentity = getUserIdentityDao().getUserIdentity(username);
		UserPassword userPassword = userIdentity.getUserPassword();
		if (userPassword != null) {
			return getPasswordEncoder().isPasswordValid(userPassword.getHash(), password, userPassword.getSalt());
		}
		return false;
	}
}
