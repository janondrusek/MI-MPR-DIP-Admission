package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Country;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Person {
	
	private Country citizenship;
	
	
}
