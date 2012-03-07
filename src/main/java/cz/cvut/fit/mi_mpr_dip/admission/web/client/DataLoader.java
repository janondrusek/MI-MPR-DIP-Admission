/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.mi_mpr_dip.admission.web.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionState;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Document;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DocumentType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

/**
 * 
 * @author DavidCh
 */
public class DataLoader {

	private Collection<Admission> admissions = new ArrayList<Admission>();

	public static final void main(String[] args) throws Exception {
		DataLoader dl = new DataLoader();
		dl.readCSVFile(new File("_data/POCHOVA03022409(1).csv"), StringPool.SEMICOLON);
	}

	public void readCSVFile(File f, String separator) {
		try {
			// Sets up a file reader to read the file passed on the command line one character at a time
			FileReader fr = new FileReader(f);
			// Filter FileReader through a Buffered read to read a line at a time
			BufferedReader br = new BufferedReader(fr);
			// String that holds current file line
			String line;
			// Read headers
			String[] headers = br.readLine().split(separator);
			// Read first admission data line
			line = br.readLine();
			// Read through file one line at time. Print line # and line
			while (line != null) {
				Admission admission = parseLine(line.split(separator, headers.length));
				//parseLine(line.split(separator, headers.length), headers);

				admissions.add(admission);
				
				// next clauses line
				line = br.readLine(); 
				//break; // only first instance of file
			}
			br.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			/*
			 * If no file was passed on the command line, this expception is
			 * generated. A message indicating how to the class should be called
			 * is displayed
			 */
			System.out.println("Usage: java ReadFile filename\n");
		} catch (IOException e) {
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
	}
	
	public Collection<Admission> getAdmissions() {
		return admissions;
	}

	private static Admission parseLine(String[] data) {
		Admission a = new Admission();
		AdmissionState as = new AdmissionState();
		Person p = new Person();
		Document d = new Document();
		DocumentType dt = new DocumentType();
		List<Document> documents = new ArrayList<Document>();
		Address adr = new Address();
		Address contactAdr = new Address();
		List<Address> addresses = new ArrayList<Address>(); 
		City c = new City();
		Country co = new Country();
		Degree deg = new Degree();
		StudyMode sm = new StudyMode();
		Programme pr = new Programme();
		
		dt.setName("identityNumber");
		as.setCode("S01");
		as.setName("New");

		a.setCode(data[0]);
		a.setAdmissionState(as);
		//a.setAdmissionId(Long.valueOf(data[1]));
		// Person
		p.setFirstname(data[2]);
		p.setLastname(data[3]);
		p.setPrefix(data[4]);
		p.setSuffix(data[5]);
		p.setBirthIdentificationNumber(data[6]);
		d.setNumber(data[7]);
		d.setDocumentType(dt);
		documents.add(d);
		p.setPhone(data[8]);
		p.setEmail(data[9]);
		// Address
		adr.setStreet(data[10]);
		adr.setHouseNumber(data[11]);
		adr.setCityPart(data[12]);
		c.setName(data[13]);
		adr.setCity(c);
		adr.setPostNumber(data[14]);
		adr.setPostalCode(data[15]);
		co.setName(data[16]);
		adr.setCountry(co);
		adr.setPrintLine01(data[17]);
		adr.setPrintLine02(data[18]);
		adr.setPrintLine03(data[19]);
		adr.setPrintLine04(data[20]);
		adr.setPrintLine05(data[21]);
		addresses.add(adr);
		// Address (contact)
		contactAdr.setIsContact(true);
		contactAdr.setStreet(data[22]);
		contactAdr.setHouseNumber(data[23]);
		contactAdr.setCityPart(data[24]);
		c.setName(data[25]);
		contactAdr.setCity(c);
		contactAdr.setPostalCode(data[26]);
		contactAdr.setPostNumber(data[27]);
		co.setName(data[28]);
		contactAdr.setCountry(co);
		contactAdr.setPrintLine01(data[29]);
		contactAdr.setPrintLine02(data[30]);
		contactAdr.setPrintLine03(data[31]);
		contactAdr.setPrintLine04(data[32]);
		contactAdr.setPrintLine05(data[33]);
		addresses.add(contactAdr);
		p.setAddresses(addresses);
		a.setPerson(p);
		// Study
		deg.setName(data[34]);
		a.setDegree(deg);
		sm.setName(data[35]);
		a.setStudyMode(sm);
		pr.setName(data[36]);
		a.setProgramme(pr);
		a.setType(data[37]);
		a.setH1(data[38]);
		a.setH2(data[39]);
		a.setH3(data[40]);
		a.setH4(data[41]);
		a.setH5(data[42]);
		a.setH6(data[43]);
		a.setH7(data[44]);
		a.setH8(data[45]);
		a.setH9(data[46]);
		a.setH10(data[47]);
		
		return a;
	}
	
	private Admission parseLine(String[] data, String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			System.out.println(i + ": " + headers[i]);
		}
		
		Admission a = new Admission();
		
		return a;
	}
}