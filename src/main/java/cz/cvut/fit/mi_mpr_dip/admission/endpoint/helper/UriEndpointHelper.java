package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.AdmissionEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.ProgrammeEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.TermEndpointImpl;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;
import cz.cvut.fit.mi_mpr_dip.admission.util.URIKeys;

@Service
@RooJavaBean
public class UriEndpointHelper {

	private static final Logger log = LoggerFactory.getLogger(UriEndpointHelper.class);

	@Autowired
	private TermDateUtils termDateUtils;

	public URI getAdmissionLocation(String baseLocation, Admission admission) {
		return getAdmissionLocation(baseLocation, admission.getCode());
	}

	public URI getAdmissionLocation(String baseLocation, String admissionCode) {
		String path = replace(AdmissionEndpointImpl.ADMISSION_PATH, URIKeys.ADMISSION_CODE, admissionCode);

		return buildURI(baseLocation, path);
	}

	public URI getTermLocation(String baseLocation, Term term) {
		return getTermLocation(baseLocation, term.getDateOfTerm(), term.getRoom());
	}

	public URI getTermLocation(String baseLocation, Date dateOfTerm, String room) {
		String path = replace(TermEndpointImpl.TERM_PATH, URIKeys.DATE_OF_TERM, formatDateAndReplaceSpaces(dateOfTerm));
		path = replace(path, URIKeys.ROOM, room);

		return buildURI(baseLocation, path);
	}

	public URI getProgrammeLocation(String baseLocation, Programme programme) {
		String path = replace(ProgrammeEndpointImpl.PROGRAMME_PATH, URIKeys.DEGREE, programme.getDegree().getName());
		path = replace(path, URIKeys.LANGUAGE, programme.getLanguage().getName());
		path = replace(path, URIKeys.NAME, programme.getName());
		path = replace(path, URIKeys.STUDY_MODE, programme.getStudyMode().getName());

		return buildURI(baseLocation, path);
	}

	public URI getAppendixLocation(String admissionCode, Appendix appendix) {
		String path = replace(AdmissionEndpointImpl.ENDPOINT_PATH + AdmissionEndpointImpl.PHOTO_PATH,
				URIKeys.ADMISSION_CODE, admissionCode);
		path = replace(path, URIKeys.IDENTIFIER, appendix.getIdentifier());

		return buildURI(StringPool.BLANK, path);
	}

	private String formatDateAndReplaceSpaces(Date dateOfTerm) {
		return getTermDateUtils().toUnderscoredIso(dateOfTerm);
	}

	private String replace(String path, String pattern, String replacement) {
		return StringUtils.replaceOnce(path, pattern, replacement);
	}

	private URI buildURI(String baseLocation, String path) {
		try {
			baseLocation = StringUtils.stripEnd(baseLocation, StringPool.SLASH);
			path = StringUtils.stripStart(path, StringPool.SLASH);
			return UriBuilder.fromPath(baseLocation + StringPool.SLASH + path).build();
		} catch (Exception e) {
			log.debug("Could not create URI [{}], [{}]", baseLocation, path);
			throw new TechnicalException(e);
		}
	}

}
