package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

import org.apache.commons.lang3.StringUtils;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;

@RooJavaBean
public class AppendixUniqueConstraint extends BaseUniqueConstraint<Appendix> {

	private String identifier;

	public AppendixUniqueConstraint(Appendix appendix) {
		this(appendix.getIdentifier());
	}

	public AppendixUniqueConstraint(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public Boolean isDuplicate(Appendix duplicate) {
		return StringUtils.equals(identifier, duplicate.getIdentifier());
	}

	@Override
	public Boolean isNotFound() {
		return identifier == null;
	}

}
