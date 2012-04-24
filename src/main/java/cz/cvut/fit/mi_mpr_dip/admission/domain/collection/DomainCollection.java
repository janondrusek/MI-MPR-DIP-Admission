package cz.cvut.fit.mi_mpr_dip.admission.domain.collection;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
public abstract class DomainCollection {

	private Long count;

	private Long totalCount;
}
