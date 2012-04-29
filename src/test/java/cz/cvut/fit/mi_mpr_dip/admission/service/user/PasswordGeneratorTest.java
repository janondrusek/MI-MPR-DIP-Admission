package cz.cvut.fit.mi_mpr_dip.admission.service.user;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringGenerator;

public class PasswordGeneratorTest {

	private static final Integer SALT_BEGINNING = 0;
	private static final Integer SALT_END = 4;

	private static final Integer PLAINTEXT_BEGINNING = 4;
	private static final Integer PLAINTEXT_END = 14;

	private static final String HASH = "hash";
	private static final String SALT = "salt";
	private static final String PLAINTEXT = "plaintext";
	private static final String SALT_AND_PLAINTEXT = SALT + PLAINTEXT;

	private UserPasswordGenerator passwordGenerator;

	private PasswordEncoder passwordEncoder;
	private StringGenerator stringGenerator;
	private UserPassword userPassword;

	private Object[] mocks;

	@Before
	public void setUp() {
		passwordGenerator = new UserPasswordGenerator();
		passwordGenerator.setSaltBeginning(SALT_BEGINNING);
		passwordGenerator.setSaltEnd(SALT_END);
		passwordGenerator.setPlaintextBeginning(PLAINTEXT_BEGINNING);
		passwordGenerator.setPlaintextEnd(PLAINTEXT_END);

		initMocks();
	}

	private void initMocks() {
		passwordEncoder = createMock(PasswordEncoder.class);
		passwordGenerator.setPasswordEncoder(passwordEncoder);
		stringGenerator = createMock(StringGenerator.class);
		passwordGenerator.setStringGenerator(stringGenerator);
		userPassword = createMock(UserPassword.class);

		mocks = new Object[] { passwordEncoder, stringGenerator, userPassword };
	}

	@Test
	public void testCreateUserPassword() {
		setRandomStringGeneratorExpectations();
		setPasswordEncoderExpectations();

		replay(mocks);
		UserPassword userPassword = passwordGenerator.createUserPassword();
		verify(mocks);

		verifyUserPassword(userPassword);
	}

	@Test
	public void testCreateUserPasswordWithPlaintext() {
		setRandomStringGeneratorExpectations();
		expect(passwordEncoder.encodePassword(same(PLAINTEXT), eq(SALT))).andReturn(HASH);
		userPassword.setPlaintext(same(PLAINTEXT));
		setSaltAndHashExpectations();

		replay(mocks);
		passwordGenerator.createUserPassword(PLAINTEXT, userPassword);
		verify(mocks);
	}

	@Test
	public void testResetUserPassword() {
		setRandomStringGeneratorExpectations();
		setPasswordEncoderExpectations();
		userPassword.setPlaintext(eq(PLAINTEXT));
		setSaltAndHashExpectations();

		replay(mocks);
		passwordGenerator.resetUserPassword(userPassword);
		verify(mocks);
	}

	private void setRandomStringGeneratorExpectations() {
		expect(stringGenerator.generateRandomAlphanumeric()).andReturn(SALT_AND_PLAINTEXT);
	}

	private void setPasswordEncoderExpectations() {
		expect(passwordEncoder.encodePassword(eq(PLAINTEXT), eq(SALT))).andReturn(HASH);
	}

	private void setSaltAndHashExpectations() {
		userPassword.setSalt(eq(SALT));
		userPassword.setHash(same(HASH));
	}

	private void verifyUserPassword(UserPassword userPassword) {
		assertEquals(PLAINTEXT, userPassword.getPlaintext());
		assertEquals(SALT, userPassword.getSalt());
		assertEquals(HASH, userPassword.getHash());
	}
}
