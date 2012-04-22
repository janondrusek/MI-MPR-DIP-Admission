package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.util.Set;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@XmlRootElement(name = "roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRoles {

	@Valid
	@XmlElement(name = "role")
	private Set<UserRole> userRoles;
}
