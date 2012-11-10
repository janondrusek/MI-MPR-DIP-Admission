package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields = { "appendixContent" })
@RooEquals(excludeFields = { "appendixId", "appendixContent" })
@RooJpaActiveRecord(finders = { "findAppendixesByIdentifierEquals" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Appendix implements Serializable {

	private static final long serialVersionUID = 6318449602166904443L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long appendixId;

	@Column(unique = true)
	private String identifier;

	private String filename;

	@Transient
	private Link link;

	@NotEmpty
	@NotNull
	private String mimeType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotNull
	@Valid
	@XmlTransient
	private AppendixContent appendixContent;

	@Transient
	private String content;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
			fetch = FetchType.LAZY)
	@Valid
	private AppendixType appendixType;
}
