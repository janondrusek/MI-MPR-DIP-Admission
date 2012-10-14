package cz.cvut.fit.mi_mpr_dip.admission.service.crud;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AppendixDao;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AppendixUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.UniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Apology;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AppendixContent;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Link;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;
import cz.cvut.fit.mi_mpr_dip.admission.service.LinkService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringGenerator;

@Service
public class AppendixServiceImpl extends BaseCrudService<Appendix> implements AppendixService {

	@Autowired
	@Qualifier("UUIDStringGenerator")
	private StringGenerator stringGenerator;

	@Autowired
	private AppendixDao appendixDao;

	@Autowired
	private LinkService linkService;

	@Override
	public void addLinks(Admission admission) {
		addPhotoLinks(admission);
		addTermRegistrationLinks(admission);
	}

	private void addPhotoLinks(Admission admission) {
		Set<Appendix> photos = admission.getPhotos();
		if (CollectionUtils.isNotEmpty(photos)) {
			admission.setPhotoLinks(getLinks(admission.getCode(), photos));
		}
	}

	private Set<Appendix> getLinks(String admissionCode, Set<Appendix> photos) {
		Set<Appendix> photoLinks = new HashSet<>();
		for (Appendix photo : photos) {
			photoLinks.add(getLink(admissionCode, photo));
		}
		return photoLinks;
	}

	private void addTermRegistrationLinks(Admission admission) {
		Set<TermRegistration> registrations = admission.getRegistrations();
		if (CollectionUtils.isNotEmpty(registrations)) {
			for (TermRegistration termRegistration : registrations) {
				addLinks(termRegistration);
			}
		}
	}

	@Override
	public void addLinks(TermRegistration termRegistration) {
		if (termRegistration.getApology() != null) {
			addTermRegistrationLinks(termRegistration);
		}
	}

	private void addTermRegistrationLinks(TermRegistration termRegistration) {
		addLinks(termRegistration.getAdmission(), termRegistration.getTerm(), termRegistration.getApology());
	}

	private void addLinks(Admission admission, Term term, Apology apology) {
		Set<Appendix> appendices = apology.getAppendices();
		if (CollectionUtils.isNotEmpty(appendices)) {
			apology.setMarshalledAppendices(getLinks(admission, term, appendices));
		}
	}

	private Set<Appendix> getLinks(Admission admission, Term term, Set<Appendix> appendices) {
		Set<Appendix> appendixLinks = new HashSet<>();
		for (Appendix appendix : appendices) {
			appendixLinks.add(getLink(admission.getCode(), term, appendix));
		}
		return appendixLinks;
	}

	private Appendix getLink(String admissionCode, Appendix appendix) {
		return getLink(linkService.getAppendixLink(admissionCode, appendix));
	}

	private Appendix getLink(String admissionCode, Term term, Appendix appendix) {
		return getLink(linkService.getAppendixLink(admissionCode, term, appendix));
	}

	private Appendix getLink(Link link) {
		Appendix appendix = new Appendix();
		appendix.setLink(link);
		return appendix;
	}

	@Override
	public void addContent(Appendix appendix) {
		AppendixContent appendixContent = new AppendixContent();
		appendixContent.setContent(appendix.getContent());

		appendix.setAppendixContent(appendixContent);
	}

	@Override
	public void addContents(Set<Appendix> appendices) {
		if (CollectionUtils.isNotEmpty(appendices)) {
			for (Appendix appendix : appendices) {
				addContent(appendix);
			}
		}
	}

	@Override
	public void addIdentifier(Appendix appendix) {
		appendix.setIdentifier(stringGenerator.generateRandom());
	}

	@Override
	public void addIdentifiers(Set<Appendix> appendices) {
		if (CollectionUtils.isNotEmpty(appendices)) {
			for (Appendix appendix : appendices) {
				addIdentifier(appendix);
			}
		}
	}

	@Override
	public void add(Appendix appendix) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Appendix get(UniqueConstraint<Appendix> uniqueConstraint) {
		return getAppendixOrThrowNotFound(uniqueConstraint);
	}

	private Appendix getAppendixOrThrowNotFound(UniqueConstraint<Appendix> uniqueConstraint) {
		Appendix appendix = getAppendix(uniqueConstraint);
		validateNotFound(new AppendixUniqueConstraint(appendix));
		appendix.setContent(appendix.getAppendixContent().getContent());

		return appendix;
	}

	private Appendix getAppendix(UniqueConstraint<Appendix> uniqueConstraint) {
		return appendixDao.getAppendix(getAppendixUniqueConstraint(uniqueConstraint));
	}

	private AppendixUniqueConstraint getAppendixUniqueConstraint(UniqueConstraint<Appendix> uniqueConstraint) {
		return (AppendixUniqueConstraint) uniqueConstraint;
	}

	@Override
	public void update(UniqueConstraint<Appendix> uniqueConstraint, Appendix appendix) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(UniqueConstraint<Appendix> uniqueConstraint) {
		Appendix appendix = getAppendixOrThrowNotFound(uniqueConstraint);
		appendix.remove();
	}

}
