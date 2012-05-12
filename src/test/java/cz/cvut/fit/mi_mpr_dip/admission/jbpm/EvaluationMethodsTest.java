package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.BSPProcessEvaluator;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.MSPProcessEvaluator;
import cz.cvut.fit.mi_mpr_dip.admission.jbpm.eval.ProcessEvaluator;

public class EvaluationMethodsTest extends BaseSpringJbpmTest {
	
	private ProcessEvaluator bspProcessEvaluator;
	private ProcessEvaluator mspProcessEvaluator;
	
	@Override
	@Before
	public void setUp() {
		bspProcessEvaluator = new BSPProcessEvaluator();
		mspProcessEvaluator = new MSPProcessEvaluator();
	}
	
	@Test
	public void acceptWithoutAT() {
		bspAcceptWithoutAT_01();
		bspAcceptWithoutAT_02();
		bspAcceptWithoutAT_03();
		mspAcceptWithoutAT();
	}
	
	private void bspAcceptWithoutAT_01() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h5");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("1", "h5");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("2", "h5");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
	}
	
	private void bspAcceptWithoutAT_02() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h6");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);

		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69", "h6");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("70", "h6");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("81", "h6");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69.9999", "h6");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
	}
	
	private void bspAcceptWithoutAT_03() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h7");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);

		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69", "h7");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("70", "h7");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("81", "h7");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69.9999", "h7");
		result = bspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
	}
	
	private void mspAcceptWithoutAT() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h8");
		result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);

		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("1", "h8");
		result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("1.9", "h8");
		result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.addEvaluation("1.90001", "h8");
		result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("2.5", "h8");
		result = mspProcessEvaluator.evalAcceptWithoutAT(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
	}
	
	@Test
	public void neededDocuments() {
		
	}
	
	@Test
	public void admissionTestPoints() {
		bspAdmissionTestPoints();
		mspAdmissionTestPoints();
	} 
	
	private void bspAdmissionTestPoints() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = bspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h2");
		result = bspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);

		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69", "h2");
		result = bspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("70", "h2");
		result = bspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.addEvaluation("81", "h2");
		result = bspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
	}
	
	private void mspAdmissionTestPoints() {
		TestAdmissionData data = new TestAdmissionData();
		Boolean result = mspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.addEvaluation("0", "h2");
		result = mspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);

		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("69", "h2");
		result = mspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.FALSE, result);
		
		data.getAdmission().getEvaluations().clear();
		data.addEvaluation("70", "h2");
		result = mspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
		
		data.addEvaluation("81", "h2");
		result = mspProcessEvaluator.EnoughTestPoints(data.getAdmission());
		assertEquals(Boolean.TRUE, result);
	}
	
	@Test
	public void admissionSWCTwo() {
		
	}
}
