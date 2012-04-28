package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;
import cz.cvut.fit.mi_mpr_dip.admission.util.RandomStringGenerator;

@RooJavaBean
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
		generatePlaintextSaltAndHash(userPassword);

		return userPassword;
	}

	@Override
	public void createUserPassword(String plaintext, UserPassword userPassword) {
		String random = randomStringGenerator.generateRandomAlphanumeric();
		String salt = createSalt(random);
		String hash = getHash(plaintext, salt);

		populateUserPassword(userPassword, plaintext, salt, hash);
	}

	@Override
	public void resetUserPassword(UserPassword userPassword) {
		generatePlaintextSaltAndHash(userPassword);
	}

	private void generatePlaintextSaltAndHash(UserPassword userPassword) {
		String random = randomStringGenerator.generateRandomAlphanumeric();
		String plaintext = substring(random, plaintextBeginning, plaintextEnd);
		String salt = createSalt(random);
		String hash = getHash(plaintext, salt);

		populateUserPassword(userPassword, plaintext, salt, hash);
	}

	private String createSalt(String random) {
		return substring(random, saltBeginning, saltEnd);
	}

	private String substring(String text, Integer beginning, Integer end) {
		return StringUtils.substring(text, beginning, end);
	}

	private String getHash(String plaintext, String salt) {
		return passwordEncoder.encodePassword(plaintext, salt);
	}

	private void populateUserPassword(UserPassword userPassword, String plaintext, String salt, String hash) {
		userPassword.setPlaintext(plaintext);
		userPassword.setSalt(salt);
		userPassword.setHash(hash);
	}

	@Required
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
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
