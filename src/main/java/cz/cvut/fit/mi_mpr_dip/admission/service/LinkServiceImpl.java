package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.net.URI;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Link;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.AdmissionEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.TermEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

@RooJavaBean
@Service
public class LinkServiceImpl implements LinkService {

	private static final Logger log = LoggerFactory.getLogger(LinkServiceImpl.class);

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Override
	public Link getAdmissionLink(Admission admission) {
		return getLink(WebKeys.ADMISSION, getUri(admission));
	}

	@Override
	public Link getTermLink(Term term) {
		return getLink(WebKeys.TERM, getUri(term));
	}

	@Override
	public Link getAppendixLink(String admissionCode, Appendix appendix) {
		return getLink(WebKeys.APPENDIX, getUri(admissionCode, appendix));
	}

	private URI getUri(String admissionCode, Appendix appendix) {
		return getUriEndpointHelper().getAppendixLocation(admissionCode, appendix);
	}

	@Override
	public Link getAppendixLink(String admissionCode, Term term, Appendix appendix) {
		return getLink(WebKeys.APPENDIX, getUri(admissionCode, term, appendix));
	}

	private URI getUri(String admissionCode, Term term, Appendix appendix) {
		return getUriEndpointHelper().getAppendixLocation(admissionCode, term, appendix);
	}

	private URI getUri(Admission admission) {
		return getUriEndpointHelper().getAdmissionLocation(AdmissionEndpointImpl.ENDPOINT_PATH, admission);
	}

	private URI getUri(Term term) {
		return getUriEndpointHelper().getTermLocation(TermEndpointImpl.ENDPOINT_PATH, term);
	}

	private Link getLink(String rel, URI uri) {
		Link link = new Link();
		try {
			String href = getHref(uri);
			link.setHref(href);
		} catch (Exception e) {
			log.error("Unable to build link [{}]", String.valueOf(e));
			throw new TechnicalException(e);
		}
		link.setMethod(HttpMethod.GET);
		link.setRel(rel);

		return link;
	}

	private String getHref(URI uri) {
		Response response = Response.created(uri).build();
		List<Object> headers = response.getMetadata().get(WebKeys.LOCATION);

		return String.valueOf(headers.get(0));
	}
}
