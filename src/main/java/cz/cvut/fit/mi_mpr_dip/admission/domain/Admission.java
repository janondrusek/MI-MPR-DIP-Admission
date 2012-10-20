package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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

import org.hibernate.validator.constraints.NotEmpty;
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
@RooToString(excludeFields = { "photos" })
@RooEquals(excludeFields = { "admissionId" })
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord(
		finders = { "findAdmissionsByCodeEquals", "findAdmissionsByPerson", "findAdmissionsByUserIdentity" })
public class Admission {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long admissionId;

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String code;

	// Maybe paper or electronic form
	private String type;

	// Mark if attendant was successful in admission process
	@NotNull
	private Boolean accepted = Boolean.FALSE;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admission_accomplishment", joinColumns = { @JoinColumn(name = "admission_id",
			referencedColumnName = "admissionId") }, inverseJoinColumns = { @JoinColumn(name = "accomplishment_id",
			referencedColumnName = "accomplishmentId") })
	@Valid
	@XmlElementWrapper(name = "accomplishments")
	@XmlElement(name = "accomplishment")
	private Set<Accomplishment> accomplishments;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admission_appeal", joinColumns = { @JoinColumn(name = "admission_id",
			referencedColumnName = "admissionId") }, inverseJoinColumns = { @JoinColumn(name = "appeal_id",
			referencedColumnName = "appealId") })
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admission_evaluation", joinColumns = { @JoinColumn(name = "admission_id",
			referencedColumnName = "admissionId") }, inverseJoinColumns = { @JoinColumn(name = "evaluation_id",
			referencedColumnName = "evaluationId") })
	@Valid
	@XmlElementWrapper(name = "evaluations")
	@XmlElement(name = "evaluation")
	private Set<Evaluation> evaluations;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Faculty faculty;

	@Transient
	private Link link;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@Valid
	private Person person;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
	@Valid
	private Programme programme;

	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "admission_appendix", joinColumns = { @JoinColumn(name = "admission_id",
			referencedColumnName = "admissionId") }, inverseJoinColumns = { @JoinColumn(name = "appendix_id",
			referencedColumnName = "appendixId") })
	@Valid
	private Set<Appendix> photos;

	@Transient
	@XmlElementWrapper(name = "photos")
	@XmlElement(name = "photo")
	private Set<Appendix> photoLinks;

	@OneToMany(mappedBy = "admission", orphanRemoval = true)
	@Valid
	@XmlElementWrapper(name = "registrations")
	@XmlElement(name = "registration")
	private Set<TermRegistration> registrations;

	@OneToOne(cascade = CascadeType.ALL)
	private UserIdentity userIdentity;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admission_reference_number", joinColumns = { @JoinColumn(name = "admission_id",
			referencedColumnName = "admissionId") }, inverseJoinColumns = { @JoinColumn(name = "reference_number_id",
			referencedColumnName = "referenceNumberId") })
	@Valid
	@XmlElementWrapper(name = "referenceNumbers")
	@XmlElement(name = "referenceNumber")
	private Set<ReferenceNumber> referenceNumbers;
}
