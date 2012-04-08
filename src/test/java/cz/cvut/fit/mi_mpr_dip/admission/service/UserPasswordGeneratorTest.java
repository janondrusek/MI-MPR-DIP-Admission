package cz.cvut.fit.mi_mpr_dip.admission.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserPassword;
import cz.cvut.fit.mi_mpr_dip.admission.util.RandomStringGenerator;

public class UserPasswordGeneratorTest {

	private static final Integer SALT_BEGINNING = 0;
	private static final Integer SALT_END = 6;

	private static final Integer PLAINTEXT_BEGINNING = 6;
	private static final Integer PLAINTEXT_END = 12;

	private static final String HASH = "hash";
	private static final String PLAINTEXT = "34qwer";
	private static final String SALT = "asdf12";
	private static final String RANDOM_STRING = SALT + PLAINTEXT;

	private UserPasswordGenerator generator;

	private RandomStringGenerator randomStringGenerator;
	private PasswordEncoder passwordEncoder;

	private Object[] mocks;

	@Before
	public void setUp() {
		generator = new UserPasswordGenerator();

		generator.setSaltBeginning(SALT_BEGINNING);
		generator.setSaltEnd(SALT_END);
		generator.setPlaintextBeginning(PLAINTEXT_BEGINNING);
		generator.setPlaintextEnd(PLAINTEXT_END);

		initMocks();
	}

	private void initMocks() {
		randomStringGenerator = createMock(RandomStringGenerator.class);
		generator.setRandomStringGenerator(randomStringGenerator);
		passwordEncoder = createMock(PasswordEncoder.class);
		generator.setPasswordEncoder(passwordEncoder);

		mocks = new Object[] { randomStringGenerator, passwordEncoder };
	}

	@Test
	public void testCreateUserPassword() {
		expect(randomStringGenerator.generateRandomAlphanumeric()).andReturn(RANDOM_STRING);
		expect(passwordEncoder.encodePassword(PLAINTEXT, SALT)).andReturn(HASH);

		UserPassword userPassword = doTestCreateUserPassword();
		assertEquals(SALT, userPassword.getSalt());
		assertEquals(HASH, userPassword.getHash());
		assertEquals(PLAINTEXT, userPassword.getPlaintext());
	}

	private UserPassword doTestCreateUserPassword() {
		replay(mocks);
		UserPassword userPassword = generator.createUserPassword();
		verify(mocks);

		return userPassword;
	}
}
