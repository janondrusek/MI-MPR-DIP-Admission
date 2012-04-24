package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Service
@RooJavaBean
public class UserPasswordServiceImpl implements UserPasswordService {

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Override
	public void createRandomPassword(UserIdentity userIdentity) {
		if (userIdentity.getUserPassword() == null) {
			userIdentity.setUserPassword(getPasswordGenerator().createUserPassword());
		} else {
			getPasswordGenerator().resetUserPassword(userIdentity.getUserPassword());
		}
	}
}
