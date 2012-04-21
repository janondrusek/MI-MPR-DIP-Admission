package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@XmlRootElement
public class UserRoles {

	@XmlElementWrapper(name = "roles")
	@XmlElement(name = "role")
	private Set<UserRole> userRoles;
}
