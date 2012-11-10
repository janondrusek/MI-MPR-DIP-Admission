package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields = { "userIdentity" })
@RooEquals(excludeFields = { "userSessionId", "grantValidTo", "userIdentity" })
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord(finders = { "findUserSessionsByIdentifierEqualsAndGrantValidToGreaterThan" })
public class UserSession implements Serializable {

	private static final long serialVersionUID = 5160715381874909222L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long userSessionId;

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String identifier;

	@NotNull
	private Date grantValidTo;

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userIdentityId")
	private UserIdentity userIdentity;
}
