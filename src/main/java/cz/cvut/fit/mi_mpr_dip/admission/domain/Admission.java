package cz.cvut.fit.mi_mpr_dip.admission.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class Admission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long admissionId;

	private String code;

	@OneToOne
	private Faculty faculty;

	@OneToOne
	private Degree degree;

	@OneToOne
	private StudyMode studyMode;

	@OneToOne
	private Programme programme;

	@OneToOne
	private Person person;
}
