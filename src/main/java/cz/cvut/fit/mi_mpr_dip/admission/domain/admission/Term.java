package cz.cvut.fit.mi_mpr_dip.admission.domain.admission;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@RooJavaBean
@RooToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@RooJpaActiveRecord
public class Term {

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long termId;

	@NotNull
	private String room;
	
	@NotNull
	private int capacity;
	
	@NotNull
	private Date registerFrom;
	
	@NotNull
	private Date registerTo;
	
	@NotNull
	private Date AppologyTo;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Programme programme;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private TermType termType;
	
	private static final String[] excludeFields = new String[] { "termId" };

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, excludeFields);
	}
}
