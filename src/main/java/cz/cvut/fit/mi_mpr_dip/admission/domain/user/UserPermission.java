package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields = { "roles" })
@RooEquals(excludeFields = { "userPermissionId", "roles" })
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
public class UserPermission {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long userPermissionId;

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String name;

	@ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
	@XmlTransient
	private Set<UserRole> roles;
}
