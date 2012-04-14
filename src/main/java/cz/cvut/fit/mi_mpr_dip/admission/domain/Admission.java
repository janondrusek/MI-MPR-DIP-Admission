package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.education.Accomplishment;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Faculty;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.user.UserIdentity;

@RooJavaBean
@RooToString
@RooEquals(excludeFields = { "admissionId" })
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord(finders = { "findAdmissionsByCodeEquals" })
public class Admission {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long admissionId;

	@NotNull
	@Column(unique = true)
	private String code;

	// Maybe paper or electronic form
	private String type;

	// Mark if attendant was successful in admission process
	@NotNull
	private Boolean accepted = Boolean.FALSE;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	@XmlElementWrapper(name = "accomplishments")
	@XmlElement(name = "accomplishment")
	private Set<Accomplishment> accomplishments;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	@XmlElementWrapper(name = "appeals")
	@XmlElement(name = "appeal")
	private Set<Appeal> appeals;

	private Boolean dormitoryRequest;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private AdmissionState admissionState;

	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private AdmissionResult result;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	@XmlElementWrapper(name = "evaluations")
	@XmlElement(name = "evaluation")
	private Set<Evaluation> evaluations;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Faculty faculty;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private Person person;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Programme programme;

	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	private Set<Appendix> photos;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	private Set<TermRegistration> registrations;

	@OneToOne(cascade = CascadeType.ALL)
	private UserIdentity userIdentity;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	@XmlElementWrapper(name = "referenceNumbers")
	@XmlElement(name = "referenceNumber")
	private Set<ReferenceNumber> referenceNumbers;
}
