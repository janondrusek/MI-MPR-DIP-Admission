package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
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

	@ManyToOne(cascade = CascadeType.ALL)
	private Country citizenship;

	private String prefix;

	private String suffix;

	@NotNull
	private String firstname;

	private String middlename;

	@NotNull
	private String lastname;

	private String maidenname;

	@ManyToOne(cascade = CascadeType.ALL)
	private MaritalStatus maritalStatus;

	private Boolean permanentResidenceGranted;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@XmlElementWrapper(name = "documents")
	// maps each member of this list to an XML element named appointment
	@XmlElement(name = "document")
	private List<Document> documents;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@XmlElementWrapper(name = "addresses")
	// maps each member of this list to an XML element named appointment
	@XmlElement(name = "address")
	private List<Address> addresses;

	private Date birthdate;

	private String birthIdentificationNumber;

	@ManyToOne(cascade = CascadeType.ALL)
	private Gender gender;

	@ManyToOne(cascade = CascadeType.ALL)
	private City cityOfBirth;

	@ManyToOne(cascade = CascadeType.ALL)
	private Country countryOfBirth;

	private String phone;

	private String email;

	@ManyToOne(cascade = CascadeType.ALL)
	private DisabilityType disability;
}
