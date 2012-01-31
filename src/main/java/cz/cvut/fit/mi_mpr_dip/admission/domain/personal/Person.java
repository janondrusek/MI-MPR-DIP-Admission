package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Country;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@OneToMany(cascade = CascadeType.ALL)
	private List<Document> documents;

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

	private Boolean disability;
}
