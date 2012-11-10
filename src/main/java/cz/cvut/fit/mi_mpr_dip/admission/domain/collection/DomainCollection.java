package cz.cvut.fit.mi_mpr_dip.admission.domain.collection;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DomainCollection implements Serializable {

	private static final long serialVersionUID = 6009778236009624943L;

	private Long count;

	private Long totalCount;
}
