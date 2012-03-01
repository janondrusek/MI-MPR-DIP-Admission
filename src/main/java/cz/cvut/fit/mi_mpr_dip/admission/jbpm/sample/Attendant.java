package cz.cvut.fit.mi_mpr_dip.admission.jbpm.sample;

import java.util.Date;

public class Attendant {
	
	private String name;
	private String surname;
	private String email;
	private String tel;
	private String code;
	private String address;
	private int numb;
	private long id;
	private char studyForm;
	private char programType;
	private String studyProgram;
	
	private boolean scio;
	private boolean olymp;
	
	private Date actDate;
	
	public Attendant(String name, String surname, String email, String tel,
			String code, String address, int numb, long id, char studyForm,
			char programType, String studyProgram, boolean scio, boolean olymp) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.tel = tel;
		this.code = code;
		this.address = address;
		this.numb = numb;
		this.id = id;
		this.studyForm = studyForm;
		this.programType = programType;
		this.studyProgram = studyProgram;
		this.scio = scio;
		this.olymp = olymp;
		
		this.actDate = new Date(2012, 05, 21);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getActDate() {
		return actDate;
	}
	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNumb() {
		return numb;
	}
	public void setNumb(int numb) {
		this.numb = numb;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public char getStudyForm() {
		return studyForm;
	}
	public void setStudyForm(char studyForm) {
		this.studyForm = studyForm;
	}
	public char getProgramType() {
		return programType;
	}
	public void setProgramType(char programType) {
		this.programType = programType;
	}
	public String getStudyProgram() {
		return studyProgram;
	}
	public void setStudyProgram(String studyProgram) {
		this.studyProgram = studyProgram;
	}
	public boolean isScio() {
		return scio;
	}
	public void setScio(boolean scio) {
		this.scio = scio;
	}
	public boolean isOlymp() {
		return olymp;
	}
	public void setOlymp(boolean olymp) {
		this.olymp = olymp;
	}

	
	
}
