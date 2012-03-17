package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Admissions {

	private List<Admission> admissions;
	
}
