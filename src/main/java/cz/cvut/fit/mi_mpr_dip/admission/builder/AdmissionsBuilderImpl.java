package cz.cvut.fit.mi_mpr_dip.admission.builder;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admissions;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdmissionsBuilderImpl implements AdmissionsBuilder {

	private Admissions admissions;

	private Integer limit;

	private Integer offset;

	@Override
	public Admissions get() {
		return admissions;
	}

	@Override
	public void buildLimit(Integer page, Integer count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildAdmissions() {
		// TODO Auto-generated method stub

	}

}
