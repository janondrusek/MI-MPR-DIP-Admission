package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AppendixUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.service.AdmissionService;
import cz.cvut.fit.mi_mpr_dip.admission.service.AppendixService;
import cz.cvut.fit.mi_mpr_dip.admission.service.TermService;

@RooJavaBean
@Service
public class AppendixEndpointHelperImpl extends CommonEndpointHelper<Appendix> implements AppendixEndpointHelper {

	@Autowired
	private AdmissionService admissionService;

	@Autowired
	private AppendixService appendixService;

	@Autowired
	private TermService termService;

	@Override
	public Response getAdmissionPhoto(String admissionCode, String identifier) {
		getAdmissionOrThrowNotFound(admissionCode);
		return getOkResponse(getAppendixOrThrowNotFound(identifier));
	}

	@Override
	public Response getApologyAppendix(String admissionCode, String dateOfTerm, String room, String identifier) {
		getAdmissionOrThrowNotFound(admissionCode);
		getTermOrThrowNotFound(dateOfTerm, room);
		return getOkResponse(getAppendixOrThrowNotFound(identifier));
	}

	@Override
	protected boolean isNotFound(Appendix appendix) {
		AppendixUniqueConstraint uniqueConstraint = new AppendixUniqueConstraint(appendix);
		return uniqueConstraint.isNotFound();
	}

	private Appendix getAppendixOrThrowNotFound(String identifier) {
		return appendixService.get(new AppendixUniqueConstraint(identifier));
	}

	private void getAdmissionOrThrowNotFound(String admissionCode) {
		admissionService.get(new AdmissionUniqueConstraint(admissionCode));
	}

	private void getTermOrThrowNotFound(String dateOfTerm, String room) {
		termService.getTerm(dateOfTerm, room);
	}
}
