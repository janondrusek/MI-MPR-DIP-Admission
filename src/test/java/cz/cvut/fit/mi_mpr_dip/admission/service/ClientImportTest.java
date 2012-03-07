package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.web.client.DataLoader;
import cz.cvut.fit.mi_mpr_dip.admission.web.client.TestClient;

public class ClientImportTest {

	private TestClient testClient;

	@Before
	public void setUp() {
		testClient = new TestClient();
		testClient.setBaseURI("http://localhost:9090/admission/services/processing/");
	}

	@After
	public void tearDown() {
		testClient = null;
	}

	@Test
	public void testGetBook() {
		// load Admissions from csv
		DataLoader dl = new DataLoader();
		dl.readCSVFile(new File("_data/POCHOVA03022409(1).csv"), StringPool.SEMICOLON);
		
		// Post of admissions
		testClient.testImport(dl.getAdmissions());
	}

}
