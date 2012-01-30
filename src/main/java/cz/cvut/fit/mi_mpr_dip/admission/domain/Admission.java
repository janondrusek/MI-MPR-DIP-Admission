package cz.cvut.fit.mi_mpr_dip.admission.domain;

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
	
	private Faculty faculty;
	
	private Degree degree;
	
	private StudyMode studyMode;
	
	private Programme programme;
	
	private Person person;
}
