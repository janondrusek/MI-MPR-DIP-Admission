package cz.cvut.fit.mi_mpr_dip.admission.domain.user;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@RooToString
@RooEquals(excludeFields = { "userPasswordId", "plaintext" })
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord
public class UserPassword implements Serializable {

	private static final long serialVersionUID = -8549517154378126325L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long userPasswordId;

	@NotEmpty
	@NotNull
	private String hash;

	@Transient
	private String plaintext;

	@NotEmpty
	@NotNull
	private String salt;
}
