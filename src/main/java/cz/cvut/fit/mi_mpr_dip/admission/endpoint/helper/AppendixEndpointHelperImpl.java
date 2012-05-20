package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AdmissionUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AppendixUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
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

	@Transactional(readOnly = true)
	@Override
	public Response getAdmissionPhoto(String admissionCode, String identifier) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		Appendix appendix = getPhotoOrThrowNotFound(admission, identifier);
		getAppendixService().addContent(appendix);
		
		return getOkResponse(appendix);
	}

	@Transactional(readOnly = true)
	@Override
	public Response getApologyAppendix(String admissionCode, String dateOfTerm, String room, String identifier) {
		Admission admission = getAdmissionOrThrowNotFound(admissionCode);
		Term term = getTermOrThrowNotFound(dateOfTerm, room);
		verifyRegistration(admission, term);
		Appendix appendix = getAppendixOrThrowNotFound(admission, identifier);
		getAppendixService().addContent(appendix);
		
		return getOkResponse(appendix);
	}

	private void verifyRegistration(Admission admission, Term term) {
		Set<Term> terms = collectTerms(admission);
		if (!terms.contains(term)) {
			throwNotFoundBusinessException();
		}
	}

	private Set<Term> collectTerms(Admission admission) {
		Set<Term> terms = new HashSet<Term>();
		if (CollectionUtils.isNotEmpty(admission.getRegistrations())) {
			for (TermRegistration termRegistration : admission.getRegistrations()) {
				terms.add(termRegistration.getTerm());
			}
		}
		return terms;
	}

	@Override
	protected boolean isNotFound(Appendix appendix) {
		AppendixUniqueConstraint uniqueConstraint = new AppendixUniqueConstraint(appendix);
		return uniqueConstraint.isNotFound();
	}

	private Appendix getPhotoOrThrowNotFound(Admission admission, String identifier) {
		Set<Appendix> photos = admission.getPhotos();
		Appendix appendix = findAppendixOrThrowNotFound(identifier, photos);

		return appendix;
	}

	private Appendix getAppendixOrThrowNotFound(Admission admission, String identifier) {
		Set<Appendix> appendices = collectAppendices(admission.getRegistrations());
		Appendix appendix = findAppendixOrThrowNotFound(identifier, appendices);

		return appendix;
	}

	private Set<Appendix> collectAppendices(Set<TermRegistration> registrations) {
		Set<Appendix> appendices = new HashSet<Appendix>();
		if (CollectionUtils.isNotEmpty(registrations)) {
			for (TermRegistration termRegistration : registrations) {
				if (termRegistration.getApology() != null) {
					appendices.addAll(termRegistration.getApology().getAppendices());
				}
			}
		}
		return appendices;
	}

	private Appendix findAppendixOrThrowNotFound(String identifier, Set<Appendix> photos) {
		Appendix appendix = findAppendix(identifier, photos);
		verifyNotNull(appendix);
		return appendix;
	}

	private Appendix findAppendix(String identifier, Set<Appendix> appendices) {
		for (Appendix appendix : appendices) {
			if (appendix.getIdentifier().equals(identifier)) {
				return appendix;
			}
		}
		return null;
	}

	private void verifyNotNull(Appendix appendix) {
		if (appendix == null) {
			throwNotFoundBusinessException();
		}
	}

	private Admission getAdmissionOrThrowNotFound(String admissionCode) {
		return admissionService.get(new AdmissionUniqueConstraint(admissionCode));
	}

	private Term getTermOrThrowNotFound(String dateOfTerm, String room) {
		return termService.getTerm(dateOfTerm, room);
	}
}
