package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@RooEquals(excludeFields = { "maritalStatusId", "persons" })
@RooJpaActiveRecord(finders = { "findMaritalStatusesByNameEquals" })
@XmlAccessorType(XmlAccessType.FIELD)
public class MaritalStatus implements Serializable {

	private static final long serialVersionUID = 2292185386656623085L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long maritalStatusId;

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String name;

	@OneToMany(orphanRemoval = true, mappedBy = "maritalStatus")
	@XmlTransient
	private Set<Person> persons;
}
