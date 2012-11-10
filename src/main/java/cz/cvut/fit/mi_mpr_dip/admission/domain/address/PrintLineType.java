package cz.cvut.fit.mi_mpr_dip.admission.domain.address;

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
@RooEquals(excludeFields = { "printLineTypeId", "printLines" })
@XmlAccessorType(XmlAccessType.FIELD)
@RooJpaActiveRecord(finders = { "findPrintLineTypesByNameEquals" })
public class PrintLineType implements Serializable {

	private static final long serialVersionUID = -124063138145229471L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long printLineTypeId;

	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String name;

	@OneToMany(orphanRemoval = true, mappedBy = "printLineType")
	@XmlTransient
	private Set<PrintLine> printLines;
}
