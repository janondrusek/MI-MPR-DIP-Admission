package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.builder.AdmissionsBuilder;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Evaluation;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Admissions;
import cz.cvut.fit.mi_mpr_dip.admission.domain.education.Accomplishment;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.service.AdmissionService;
import cz.cvut.fit.mi_mpr_dip.admission.service.AppendixService;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;
import cz.cvut.fit.mi_mpr_dip.admission.util.Action;
import cz.cvut.fit.mi_mpr_dip.admission.validation.unique.AdmissionCodeValidator;

@Service
@RooJavaBean
public class AdmissionEndpointHelperImpl extends CommonEndpointHelper<Admission> implements AdmissionEndpointHelper,
		ApplicationContextAware {

	@Autowired
	private AdmissionCodeValidator admissionCodeValidator;

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private AppendixService appendixService;

	private ApplicationContext applicationContext;

	@Autowired
	private TermService termService;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Transactional(readOnly = true)
	@Override
	public Admissions getAdmissions(Integer count, Integer page) {
		return buildAdmissions(count, page);
	}

	private Admissions buildAdmissions(Integer count, Integer page) {
		AdmissionsBuilder admissionsBuilder = getAdmissionsBuilder();

		admissionsBuilder.createNew();
		admissionsBuilder.buildLimit(count, page);
		admissionsBuilder.buildAdmissions();
		admissionsBuilder.buildLinks();

		return admissionsBuilder.get();
	}

	private AdmissionsBuilder getAdmissionsBuilder() {
		return getApplicationContext().getBean(AdmissionsBuilder.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		getTermService().addLinks(admission.getRegistrations());
		getAppendixService().addLinks(admission);

		return getOkResponse(admission);
	}

	private Admission getAdmissionOrThrowNotFound(String admissionCode) {
		return admissionService.get(new AdmissionUniqueConstraint(admissionCode));
	}

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		admission.remove();
		return getOkResponse();
	}

	@Transactional
	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		action.performAction(admission, actor);
		getBeanValidator().validate(actor);

		admission.merge();

		return getSeeOtherResponse(getUriEndpointHelper().getAdmissionLocation(baseLocation, admission));
	}

	@Override
	protected boolean isNotFound(Admission admission) {
		return admission.getCode() == null;
	}

	@Override
	public void update(Admission admission, Admission dbAdmission) {
		AdmissionUniqueConstraint original = new AdmissionUniqueConstraint(dbAdmission);
		AdmissionUniqueConstraint updated = new AdmissionUniqueConstraint(admission);
		validate(original, updated);

		setUpdatedToOriginal(admission, dbAdmission);
		dbAdmission.persist();
	}

	private void setUpdatedToOriginal(Admission admission, Admission dbAdmission) {
		dbAdmission.setAccepted(admission.getAccepted());
		removeAndAddAccomplishments(admission, dbAdmission);
		dbAdmission.setAdmissionState(admission.getAdmissionState());
		dbAdmission.setAppeals(admission.getAppeals());
		dbAdmission.setDormitoryRequest(admission.getDormitoryRequest());
		removeAndAddEvaluations(admission, dbAdmission);
		dbAdmission.setFaculty(admission.getFaculty());
		removeAndAddPerson(admission, dbAdmission);
		dbAdmission.setProgramme(admission.getProgramme());
		dbAdmission.setReferenceNumbers(admission.getReferenceNumbers());
		removeAndAddRegistrations(admission, dbAdmission);
		dbAdmission.setResult(admission.getResult());
		dbAdmission.setType(admission.getType());
	}

	private void removeAndAddAccomplishments(Admission admission, Admission dbAdmission) {
		removeAndAdd(dbAdmission.getAccomplishments(), new Action<Accomplishment>() {

			@Override
			public void perform(Accomplishment accomplishment) {
				accomplishment.remove();
			}

		}, admission, dbAdmission);
		dbAdmission.setAccomplishments(admission.getAccomplishments());
	}

	private void removeAndAddEvaluations(Admission admission, Admission dbAdmission) {
		removeAndAdd(dbAdmission.getEvaluations(), new Action<Evaluation>() {

			@Override
			public void perform(Evaluation evaluation) {
				evaluation.remove();
			}

		}, admission, dbAdmission);
		dbAdmission.setEvaluations(admission.getEvaluations());
	}

	private void removeAndAddPerson(Admission admission, Admission dbAdmission) {
		dbAdmission.getPerson().remove();
		dbAdmission.setPerson(admission.getPerson());
	}

	private void removeAndAddRegistrations(Admission admission, Admission dbAdmission) {
		removeAndAdd(dbAdmission.getRegistrations(), new Action<TermRegistration>() {

			@Override
			public void perform(TermRegistration termRegistration) {
				termRegistration.remove();
			}

		}, admission, dbAdmission);
		dbAdmission.setRegistrations(admission.getRegistrations());
	}

	private <T> void removeAndAdd(Collection<T> collection, Action<T> action, Admission admission, Admission dbAdmission) {
		for (Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {
			T item = iterator.next();
			iterator.remove();
			action.perform(item);
		}
	}

	@Override
	public void validate(Admission admission) {
		super.validate(admission);
		getAdmissionCodeValidator().validate(admission);
	}

	@Override
	public void validate(Appendix appendix) {
		getBeanValidator().validate(appendix);
	}

	@Override
	public Admission validate(String admissionCode, Admission admission) {
		super.validate(admission);
		validateSameInstant(admissionCode, admission);
		Admission dbAdmission = getAdmissionOrThrowNotFound(admissionCode);
		return dbAdmission;
	}

	private void validateSameInstant(String admissionCode, Admission admission) {
		if (!StringUtils.equals(admissionCode, admission.getCode())) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_BAD_REQUEST,
					"Unique constraint change requested");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
