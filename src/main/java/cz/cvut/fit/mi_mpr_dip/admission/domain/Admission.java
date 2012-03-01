package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Degree;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Faculty;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.StudyMode;

@RooJavaBean
@RooToString
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

	@OneToOne(cascade = CascadeType.ALL)
	private AdmissionResult result;

	@ManyToOne(cascade = CascadeType.ALL)
	private Faculty faculty;

	@ManyToOne(cascade = CascadeType.ALL)
	private Degree degree;

	@ManyToOne(cascade = CascadeType.ALL)
	private StudyMode studyMode;

	@ManyToOne(cascade = CascadeType.ALL)
	private Programme programme;

	@OneToOne(cascade = CascadeType.ALL)
	private Person person;

	@XmlTransient
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Photo> photos;
}
