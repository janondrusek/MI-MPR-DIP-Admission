package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Country;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long personId;

	@OneToOne
	private Country citizenship;

}
