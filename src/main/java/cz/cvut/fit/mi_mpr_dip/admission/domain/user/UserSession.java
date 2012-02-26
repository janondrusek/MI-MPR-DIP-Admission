package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSession {
	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long userSessionId;

	@NotNull
	@Column(unique = true)
	private String identifier;

	@NotNull
	private Date grantValidTo;

	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "userIdentityId")
	private UserIdentity userIdentity;

	private static final String[] excludeFields = new String[] { "userSessionId", "grantValidTo", "userIdentity" };

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, excludeFields);
	}
}
