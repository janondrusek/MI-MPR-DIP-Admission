package cz.cvut.fit.mi_mpr_dip.admission.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

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
@RooJpaActiveRecord
@XmlRootElement
public class Admission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long admissionId;

	@NotNull
	@Column(unique = true)
	private String code;

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
}
