package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;
import cz.cvut.fit.mi_mpr_dip.admission.util.TermDateUtils;

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
		return buildURI(baseLocation, admissionCode);

	}

	public URI getTermLocation(String baseLocation, Term term) {
		return getTermLocation(baseLocation, term.getDateOfTerm(), term.getRoom());
	}

	public URI getTermLocation(String baseLocation, Date dateOfTerm, String room) {
		String path = "dateOfTerm:" + formatDateAndReplaceSpaces(dateOfTerm) + "/room:" + room;
		return buildURI(baseLocation, path);
	}

	private String formatDateAndReplaceSpaces(Date dateOfTerm) {
		return getTermDateUtils().toUnderscoredIso(dateOfTerm);
	}

	private URI buildURI(String baseLocation, String path) {
		try {
			return new URI(baseLocation + StringPool.SLASH + path);
		} catch (Exception e) {
			log.debug("Could not create URI [{}], [{}]", baseLocation, path);
			throw new TechnicalException(e);
		}
	}
}
