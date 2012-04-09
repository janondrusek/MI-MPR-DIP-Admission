package cz.cvut.fit.mi_mpr_dip.admission.domain;

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
public class ErrorResponse {

	private String message;

	private String internalRequestId;
}
