package cz.cvut.fit.mi_mpr_dip.admission.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooEquals(excludeFields = { "appendixContentId" })
@RooToString
@RooJpaActiveRecord
public class AppendixContent implements Serializable {

	private static final long serialVersionUID = -671296367230269773L;

	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long appendixContentId;

	@NotEmpty
	@NotNull
	@Lob
	private String content;
}
