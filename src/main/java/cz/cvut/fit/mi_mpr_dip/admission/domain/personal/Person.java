package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;

@RooJavaBean
@RooToString
@RooEquals(excludeFields = { "personId" })
@RooJpaActiveRecord(finders = { "findPeopleByEmailEquals" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long personId;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Country citizenship;

	private String prefix;

	private String suffix;

	@NotEmpty
	@NotNull
	private String firstname;

	private String middlename;

	@NotEmpty
	@NotNull
	private String lastname;

	private String maidenname;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private MaritalStatus maritalStatus;

	private Boolean permanentResidenceGranted;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "person_document", joinColumns = { @JoinColumn(name = "person_id",
			referencedColumnName = "personId") }, inverseJoinColumns = { @JoinColumn(name = "document_id",
			referencedColumnName = "documentId") })
	@Valid
	@XmlElementWrapper(name = "documents")
	@XmlElement(name = "document")
	private Set<Document> documents;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "person_address", joinColumns = { @JoinColumn(name = "person_id",
			referencedColumnName = "personId") }, inverseJoinColumns = { @JoinColumn(name = "address_id",
			referencedColumnName = "addressId") })
	@Valid
	@XmlElementWrapper(name = "addresses")
	@XmlElement(name = "address")
	private Set<Address> addresses;

	private Date birthdate;

	private String birthIdentificationNumber;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Gender gender;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private City cityOfBirth;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Country countryOfBirth;

	private String phone;

	@Index(name = "person_email")
	private String email;

	@XmlElementWrapper(name = "disabilities")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@JoinTable(name = "person_disability_type", joinColumns = { @JoinColumn(name = "person_id",
			referencedColumnName = "personId") }, inverseJoinColumns = { @JoinColumn(name = "disability_type_id",
			referencedColumnName = "disabilityTypeId") })
	@Valid
	@XmlElement(name = "disability")
	private Set<DisabilityType> disabilities;
}
