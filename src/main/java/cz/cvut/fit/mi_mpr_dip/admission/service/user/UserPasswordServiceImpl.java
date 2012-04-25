package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.PersonDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@Service
@RooJavaBean
public class UserPasswordServiceImpl implements UserPasswordService {

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private AdmissionDao admissionDao;
	@Autowired
	private PersonDao personDao;

	@Override
	public void createRandomPassword(String email) {
		List<Person> people = getPersonDao().findByEmail(email);
		if (CollectionUtils.isNotEmpty(people)) {
			createRandomPassword(people);
		}
	}

	private void createRandomPassword(List<Person> people) {
		for (Person person : people) {
			createRandomPassword(person.getAdmission().getUserIdentity());
		}
	}

	@Override
	public void createRandomPassword(UserIdentity userIdentity) {
		if (userIdentity.getUserPassword() == null) {
			userIdentity.setUserPassword(getPasswordGenerator().createUserPassword());
		} else {
			getPasswordGenerator().resetUserPassword(userIdentity.getUserPassword());
		}
	}

	@Override
	public void createRandomPassword(String admissionCode, String email) {

	}

}
