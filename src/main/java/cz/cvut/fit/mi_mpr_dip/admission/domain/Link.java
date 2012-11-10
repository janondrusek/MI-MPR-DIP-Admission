package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@XmlAccessorType(XmlAccessType.FIELD)
public class Link implements Serializable {

	private static final long serialVersionUID = -7366014812417405862L;

	@XmlAttribute
	private String href;

	@XmlAttribute
	private String method;

	@XmlAttribute
	private String rel;
}
