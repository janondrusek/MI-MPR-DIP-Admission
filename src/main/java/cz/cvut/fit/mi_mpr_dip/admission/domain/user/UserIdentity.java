package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord(finders = { "findUserIdentitysByUsernameEquals" })
public class UserIdentity {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long userIdentityId;

	@NotNull
	@Column(unique = true)
	private String username;

	@ManyToMany
	@JoinTable(name = "user_identity_role", joinColumns = { @JoinColumn(name = "user_identity_id", referencedColumnName = "userIdentityId") }, inverseJoinColumns = { @JoinColumn(name = "user_role_id", referencedColumnName = "userRoleId") })
	private List<UserRole> roles;

	@OneToMany(mappedBy = "userIdentity")
	private List<UserSession> sessions;

	private static final String[] excludeFields = new String[] { "userIdentityId", "roles", "sessions" };

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, excludeFields);
	}
}
