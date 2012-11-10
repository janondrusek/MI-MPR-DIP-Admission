package cz.cvut.fit.mi_mpr_dip.admission.domain.collection;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;

@RooJavaBean
@RooToString
@RooEquals
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Admissions extends DomainCollection {

	private static final long serialVersionUID = 3818506599882565787L;

	@XmlElement(name = "admission")
	private List<Admission> admissions;
}
