package cz.cvut.fit.mi_mpr_dip.admission.builder;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;

public class AdmissionsBuilderImpl implements AdmissionsBuilder {

	private Admissions admissions;

	private Integer limit;

	private Integer maxLimit;

	private Integer offset = new Integer(0);

	@Autowired
	private TermService termService;

	@Override
	public void createNew() {
		admissions = new Admissions();
	}

	@Override
	public Admissions get() {
		return admissions;
	}

	@Override
	public void buildLimit(Integer count, Integer page) {
		assignLimit(count);
		assignOffset(page);
	}

	private void assignLimit(Integer limit) {
		if (limit != null && limit <= maxLimit) {
			setLimit(limit);
		}
	}

	private void assignOffset(Integer page) {
		if (page != null && page > 1) {
			offset = (page - 1) * limit;
		}
	}

	@Override
	public void buildAdmissions() {
		List<Admission> admissions = Admission.findAdmissionEntries(offset, limit);
		Integer count = CollectionUtils.isNotEmpty(admissions) ? admissions.size() : 0;
		get().setAdmissions(admissions);
		get().setCount(count.longValue());
		get().setTotalCount(Admission.countAdmissions());
	}

	@Override
	public void buildAdmissions(List<Admission> admissions) {
		this.admissions.setAdmissions(admissions);
	}

	@Override
	public void buildLinks() {
		addLinks(admissions.getAdmissions());
	}

	private void addLinks(List<Admission> admissions) {
		if (CollectionUtils.isNotEmpty(admissions)) {
			for (Admission admission : admissions) {
				addLinks(admission);
			}
		}
	}

	private void addLinks(Admission admission) {
		addLinks(admission.getRegistrations());
	}

	private void addLinks(Set<TermRegistration> registrations) {
		if (CollectionUtils.isNotEmpty(registrations)) {
			termService.addLinks(registrations);
		}
	}

	@Required
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Required
	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}

}
