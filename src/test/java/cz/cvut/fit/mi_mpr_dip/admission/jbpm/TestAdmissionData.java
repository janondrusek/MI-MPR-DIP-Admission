package cz.cvut.fit.mi_mpr_dip.admission.jbpm;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.cvut.fit.mi_mpr_dip.admission.BaseSpringJbpmTest;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionState;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appeal;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AppealType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;
import cz.cvut.fit.mi_mpr_dip.admission.domain.EvaluationType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.AddressType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.PrintLine;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.PrintLineType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Document;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DocumentType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Faculty;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Language;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

public class TestAdmissionData extends BaseSpringJbpmTest {

	private Admission admission;
	
	public TestAdmissionData() {
		admission = createAdmission();
	}
	
	public Admission getAdmission() {
		return admission;
	}
	
	public void addEvaluation(String value, String type) {
		EvaluationType evaluationType = new EvaluationType();
		evaluationType.setName(type);
		
		Evaluation evaluation = new Evaluation();
		evaluation.setValue(value);
		evaluation.setEvaluationType(evaluationType);
		
		admission.getEvaluations().add(evaluation);
	}

	public void addAppeal(Boolean accepted, String type) {
		AppealType appealType = new AppealType();
		appealType.setName(type);
		
		Appeal appeal = new Appeal();
		appeal.setAccepted(accepted);
		appeal.setAppealType(appealType);

		admission.getAppeals().add(appeal);
	}
	
	public void addAdmissionResult(Double value) {
		AdmissionResult admissionResult = new AdmissionResult();
		admissionResult.setValue(value);
		
		admission.setResult(admissionResult);
	}
	
	public void setAdmissionState(String code, String name) {
		AdmissionState admissionState = new AdmissionState();
		admissionState.setCode(code);
		admissionState.setName(name);
		
		admission.setAdmissionState(admissionState);
	}
	
	public void setProgramme(String degree, String studyMode, String language, String programme) {
		Degree d = new Degree();
		d.setName(degree);

		StudyMode sm = new StudyMode();
		sm.setName(studyMode);

		Language l = new Language();
		l.setName(language);

		Programme p = new Programme();
		p.setDegree(d);
		p.setStudyMode(sm);
		p.setLanguage(l);
		p.setName(programme);
		
		admission.setProgramme(p);
	}

	private Admission createAdmission() {
		Set<Appeal> appeals = new HashSet<Appeal>();
		Set<Evaluation> evaluations = new HashSet<Evaluation>();
		
		Admission a = new Admission();
		a.setCode("73935282");
		a.setType("P");
		a.setAccepted(Boolean.FALSE);
		a.setAppeals(appeals);
		a.setDormitoryRequest(Boolean.TRUE);
		a.setAdmissionState(createState("S01", "NEW"));
		a.setEvaluations(evaluations);
		a.setFaculty(createFaculty("FIT"));
		a.setPerson(createPerson());
		a.setProgramme(createProgramme());
		
		return a;
	}
	
	private AdmissionState createState(String code, String name) {
		AdmissionState admissionState = new AdmissionState();
		admissionState.setCode(code);
		admissionState.setName(name);
		return admissionState;
	}
	
	private Faculty createFaculty(String name) {
		Faculty faculty = new Faculty();
		faculty.setName(name);
		return faculty;
	}
	
	private Programme createProgramme() {
		Degree degree = new Degree();
		degree.setName("Bc.");

		StudyMode studyMode = new StudyMode();
		studyMode.setName("Presence");

		Language language = new Language();
		language.setName("Czech");

		Programme programme = new Programme();
		programme.setDegree(degree);
		programme.setStudyMode(studyMode);
		programme.setLanguage(language);
		programme.setName("BI");
		
		return programme;
	}
	
	private Person createPerson() {
		City city = createCity("Testov");
		Country country = createCountry("Česká republika");
		
		Person person = new Person();
		person.setFirstname("Frantisek");
		person.setLastname("Vomáčka");
		person.setBirthIdentificationNumber("8203151111");
		person.setPhone("737999999");
		person.setEmail("f.vomacka123@mail.cz");
		person.setAddresses(createAddresses());
		person.setCitizenship(country);
		person.setCityOfBirth(city);
		person.setCountryOfBirth(country);
		person.setDocuments(createDocuments());
		
		return person;
	}
	
	private City createCity(String name) {
		City city = new City();
		city.setName(name);
		
		return city;
	}
	
	private Country createCountry(String name) {
		Country country = new Country();
		country.setName(name);
		
		return country;
	}
	
	private Set<Document> createDocuments() {
		Set<Document> documents = new HashSet<Document>();
		
		DocumentType documentType = new DocumentType();
		documentType.setName("OP");
		
		Document document = new Document();
		document.setNumber("111222333");
		document.setDocumentType(documentType);

		documents.add(document);
		
		return documents;
	}
	
	private Set<Address> createAddresses() {
		Set<Address> addresses = new HashSet<Address>();
		
		City city = createCity("Testov");
		Country country = createCountry("Česká republika");

		AddressType adt1 = new AddressType();
		adt1.setName("main");

		AddressType adt2 = new AddressType();
		adt2.setName("contact");

		Set<PrintLine> printLines = createPrintLines();

		Address ad = new Address();
		ad.setStreet("Ulice");
		ad.setHouseNumber("37");
		ad.setCity(city);
		ad.setPostalCode("99988");
		ad.setCountry(country);
		ad.setAddressType(adt1);
		ad.setPrintLines(printLines);

		Address adc = new Address();
		adc.setStreet("Ulice");
		adc.setHouseNumber("37");
		adc.setCity(city);
		adc.setPostalCode("99988");
		adc.setCountry(country);
		adc.setAddressType(adt2);
		adc.setPrintLines(printLines);

		addresses.add(ad);
		addresses.add(adc);
		
		return addresses;
	}

	private Set<PrintLine> createPrintLines() {
		String[][] printLines = { { "1. řádek", "František Vomáčka" }, { "2. řádek", "Ulice 37" },
				{ "3. řádek", "99988 Město v ČR" }, { "4. řádek", StringPool.BLANK }, { "5. řádek", StringPool.BLANK } };

		return createPrintLines(printLines);
	}

	private Set<PrintLine> createPrintLines(String[][] lines) {
		Set<PrintLine> printLines = new HashSet<PrintLine>();
		for (String[] line : lines) {
			printLines.add(createPrintLine(line));
		}
		return printLines;
	}

	private PrintLine createPrintLine(String[] line) {
		PrintLine printLine = new PrintLine();
		printLine.setPrintLineType(new PrintLineType());
		printLine.getPrintLineType().setName(line[0]);
		printLine.setValue(line[1]);
		return printLine;
	}
	
	@Test
	public void testValidAdmissionData() {
		isAdmissionValid(admission);
	}
	
	private void isAdmissionValid(Admission admission) {
		assertNotNull(admission);
		assertNotNull(admission.getCode());
		assertNotNull(admission.getAccepted());
		isAdmissionStateValid(admission.getAdmissionState());

		if (!admission.getEvaluations().isEmpty()) {
			for (Evaluation evaluation : admission.getEvaluations()) {
				isEvaluationValid(evaluation);
			}
		}
		isPersonValid(admission.getPerson());
		isProgrammeValid(admission.getProgramme());
	}

	private void isAdmissionStateValid(AdmissionState admissionState) {
		assertNotNull(admissionState);
		assertNotNull(admissionState.getCode());
		assertNotNull(admissionState.getName());
		// assertNotNull(admissionState.getDesciption());
	}

	private void isProgrammeValid(Programme programme) {
		assertNotNull(programme);
		assertNotNull(programme.getDegree());
		assertNotNull(programme.getStudyMode());
		assertNotNull(programme.getLanguage());
	}

	private void isPersonValid(Person person) {
		assertNotNull(person);
		assertNotNull(person.getFirstname());
		assertNotNull(person.getLastname());
		assertNotNull(person.getBirthIdentificationNumber());
		assertNotNull(person.getEmail());
		assertNotNull(person.getPhone());
		assertNotNull(person.getCitizenship());
		assertNotNull(person.getCountryOfBirth());
		assertNotNull(person.getCityOfBirth());
		assertNotNull(person.getAddresses());
		assertTrue(!person.getAddresses().isEmpty());
		if (!person.getAddresses().isEmpty()) {
			for (Address address : person.getAddresses()) {
				isAddressValid(address);
			}
		}
		assertNotNull(person.getDocuments());
		assertTrue(!person.getDocuments().isEmpty());
		if (!person.getDocuments().isEmpty()) {
			for (Document document : person.getDocuments()) {
				isDocumentValid(document);
			}
		}
	}

	private void isDocumentValid(Document document) {
		assertNotNull(document);
		assertNotNull(document.getDocumentType());
	}

	private void isAddressValid(Address address) {
		assertNotNull(address);
		assertNotNull(address.getCity());
		assertNotNull(address.getPostalCode());
		assertNotNull(address.getCountry());
		assertNotNull(address.getAddressType());
		assertNotNull(address.getPrintLines());
	}

	private void isEvaluationValid(Evaluation evaluation) {
		assertNotNull(evaluation.getValue());
		assertNotNull(evaluation.getEvaluationType());
		assertNotNull(evaluation.getEvaluationType().getName());
		assertEquals(2, evaluation.getEvaluationType().getName().length());
		assertEquals('h', evaluation.getEvaluationType().getName().charAt(0));
	}
}
