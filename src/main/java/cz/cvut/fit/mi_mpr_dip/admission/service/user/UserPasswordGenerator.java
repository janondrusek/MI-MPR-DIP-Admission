package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;
import cz.cvut.fit.mi_mpr_dip.admission.util.RandomStringGenerator;

public class UserPasswordGenerator implements PasswordGenerator {

	private PasswordEncoder passwordEncoder;
	@Autowired
	private RandomStringGenerator randomStringGenerator;

	private Integer saltBeginning;
	private Integer saltEnd;

	private Integer plaintextBeginning;
	private Integer plaintextEnd;

	@Override
	public UserPassword createUserPassword() {
		UserPassword userPassword = new UserPassword();
		generateHashAndSalt(userPassword);

		return userPassword;
	}

	@Override
	public void resetUserPassword(UserPassword userPassword) {
		generateHashAndSalt(userPassword);
	}

	private void generateHashAndSalt(UserPassword userPassword) {
		String random = randomStringGenerator.generateRandomAlphanumeric();
		userPassword.setPlaintext(substring(random, plaintextBeginning, plaintextEnd));
		userPassword.setSalt(substring(random, saltBeginning, saltEnd));
		userPassword.setHash(passwordEncoder.encodePassword(userPassword.getPlaintext(), userPassword.getSalt()));
	}

	private String substring(String text, Integer beginning, Integer end) {
		return StringUtils.substring(text, beginning, end);
	}

	@Required
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setRandomStringGenerator(RandomStringGenerator randomStringGenerator) {
		this.randomStringGenerator = randomStringGenerator;
	}

	@Required
	public void setSaltBeginning(Integer saltBeginning) {
		this.saltBeginning = saltBeginning;
	}

	@Required
	public void setSaltEnd(Integer saltEnd) {
		this.saltEnd = saltEnd;
	}

	@Required
	public void setPlaintextBeginning(Integer plaintextBeginning) {
		this.plaintextBeginning = plaintextBeginning;
	}

	@Required
	public void setPlaintextEnd(Integer plaintextEnd) {
		this.plaintextEnd = plaintextEnd;
	}

}
