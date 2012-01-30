package cz.cvut.fit.mi_mpr_dip.admission.domain.education;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class School {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long schoolId;
	
	private String name;
}
