package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.City;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Country;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
	
	@Version
	@Transient
	@XmlTransient
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlTransient
	private Long addressId;

	@NotNull
	private boolean isContact;
	
	private String street;
	
	private String houseNumber;
	
	private String cityPart;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private City city;
	
	private String postNumber;
	
	private String postalCode;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	private Country country;
	
	@XmlTransient
	private String printLine01;
	
	@XmlTransient
	private String printLine02;
	
	@XmlTransient
	private String printLine03;
	
	@XmlTransient
	private String printLine04;
	
	@XmlTransient
	private String printLine05;

	private static final String[] excludeFields = new String[] { "addressId" };

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, excludeFields);
	}
}
