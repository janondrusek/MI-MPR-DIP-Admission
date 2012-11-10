package cz.cvut.fit.mi_mpr_dip.admission.domain.education;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals(excludeFields = { "accomplishmentId" })
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
public class Accomplishment implements Serializable {

	private static final long serialVersionUID = 3205944952905890660L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long accomplishmentId;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
			fetch = FetchType.LAZY)
	@Valid
	private AccomplishmentType accomplishmentType;

	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "accomplishment_accomplishment_value", joinColumns = { @JoinColumn(name = "accomplishment_id",
			referencedColumnName = "accomplishmentId") }, inverseJoinColumns = { @JoinColumn(
			name = "accomplishment_value_id", referencedColumnName = "accomplishmentValueId") })
	@Valid
	@XmlElementWrapper(name = "accomplishmentValues")
	@XmlElement(name = "accomplishmentValue")
	private Set<AccomplishmentValue> accomplishmentValues;
}
