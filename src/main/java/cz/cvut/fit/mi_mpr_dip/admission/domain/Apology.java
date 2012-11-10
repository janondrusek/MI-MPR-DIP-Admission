package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields = { "registration", "appendices" })
@RooEquals(excludeFields = { "apologyId", "registration" })
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Apology implements Serializable {

	private static final long serialVersionUID = 4971432809997682262L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long apologyId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@XmlTransient
	private TermRegistration registration;

	@NotNull
	private Boolean approved;

	@NotEmpty
	@NotNull
	@Lob
	private String text;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "apology_appendix", joinColumns = { @JoinColumn(name = "apology_id",
			referencedColumnName = "apologyId") }, inverseJoinColumns = { @JoinColumn(name = "appendix_id",
			referencedColumnName = "appendixId") })
	@XmlTransient
	private Set<Appendix> appendices;

	@Transient
	@Valid
	@XmlElementWrapper(name = "appendices")
	@XmlElement(name = "appendix")
	private Set<Appendix> marshalledAppendices;
}
