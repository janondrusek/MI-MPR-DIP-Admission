package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.exception.TechnicalException;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@Service
public class UriEndpointHelper implements EndpointHelper<URI> {

	private static final Logger log = LoggerFactory.getLogger(UriEndpointHelper.class);

	public URI getAdmissionLocation(String baseLocation, Admission admission) {
		return getAdmissionLocation(baseLocation, admission.getCode());
	}

	public URI getAdmissionLocation(String baseLocation, String admissionCode) {
		try {
			return new URI(baseLocation + StringPool.SLASH + admissionCode);
		} catch (Exception e) {
			log.debug("Could not create URI for admissionCode [{}]", admissionCode);
			throw new TechnicalException(e);
		}

	}
}
