package cz.cvut.fit.mi_mpr_dip.admission.validation;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.same;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

public class AdmissionCodeValidatorTest {

	private static final String CODE = "code-123";

	private AdmissionCodeValidator admissionCodeValidator;
	private Admission admission;
	private AdmissionDao admissionDao;

	private Object[] mocks;

	@Before
	public void setUp() {
		admissionCodeValidator = new AdmissionCodeValidator();

		initMocks();
	}

	private void initMocks() {
		admission = createMock(Admission.class);
		admissionDao = createMock(AdmissionDao.class);
		admissionCodeValidator.setAdmissionDao(admissionDao);

		mocks = new Object[] { admission, admissionDao };
	}

	@Test
	public void testValidateDuplicateCode() {
		Admission duplicate = new Admission();
		duplicate.setCode(CODE);

		setExpectations(duplicate);

		try {
			doTestValidate();
			fail();
		} catch (BusinessException e) {
			assertEquals(HttpServletResponse.SC_BAD_REQUEST, e.getResponseCode().intValue());
			assertEquals(1, e.getConstraintViolations().size());
		}
	}

	@Test
	public void testValidate() {
		setExpectations(new Admission());

		doTestValidate();
	}

	private void setExpectations(Admission returned) {
		expect(admission.getCode()).andReturn(CODE).atLeastOnce();
		expect(admissionDao.getAdmission(same(CODE))).andReturn(returned);
	}

	private void doTestValidate() {
		replay(mocks);
		try {
			admissionCodeValidator.validate(admission);
		} finally {
			verify(mocks);
		}
	}
}
