package cz.cvut.fit.mi_mpr_dip.admission.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals(excludeFields = { "evaluationTypeId" })
@RooJpaActiveRecord(finders = { "findEvaluationTypesByNameEquals" })
public class EvaluationType {

    @Version
    @Transient
    @XmlTransient
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long evaluationTypeId;

    @NotNull
    @Column(unique = true)
    private String name;
}
