package cz.cvut.fit.mi_mpr_dip.admission.domain.address;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
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
@RooEquals(excludeFields = { "printLineId" })
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
public class PrintLine implements Serializable {

	private static final long serialVersionUID = -7769331260314408821L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long printLineId;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
			fetch = FetchType.LAZY)
	@Valid
	private PrintLineType printLineType;

	@NotEmpty
	@NotNull
	private String value;
}
