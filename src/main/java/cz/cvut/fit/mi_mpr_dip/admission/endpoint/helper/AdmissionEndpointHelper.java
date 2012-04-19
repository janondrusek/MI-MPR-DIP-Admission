package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import cz.cvut.fit.mi_mpr_dip.admission.dao.AdmissionDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.service.UserIdentityService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
public class AdmissionEndpointHelper implements EndpointHelper {

	public static final String IDENTITY_PATH = "/identity";

	@Autowired
	private AdmissionDao admissionDao;

	@Autowired
	private UserIdentityService userIdentityService;

	private SecurityContextHolderStrategy securityContextHolderStrategy;

	@PreAuthorize("!hasRole('ROLE_ANONYMOUS')")
	@Override
	public Response getUserIdentity() {
		Authentication authentication = getAuthentication();
		return Response.ok(getUserIdentityService().getUserIdentity(authentication.getPrincipal().toString())).build();
	}

	private Authentication getAuthentication() {
		return getSecurityContextHolderStrategy().getContext().getAuthentication();
	}

	@Override
	public Response getAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			builder = Response.ok(admission);
		}
		return builder.build();
	}

	@Override
	public Response deleteAdmission(String admissionCode) {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			admission.remove();
			builder = Response.ok();
		}
		return builder.build();
	}

	@Override
	public <T> Response mergeAdmission(String admissionCode, String baseLocation, T actor, AdmissionAction<T> action)
			throws URISyntaxException {
		Admission admission = getAdmissionDao().getAdmission(admissionCode);
		ResponseBuilder builder;
		if (admission.getCode() == null) {
			builder = Response.status(Status.NOT_FOUND);
		} else {
			action.performAction(admission, actor);
			admission.merge();
			builder = Response.seeOther(new URI(baseLocation + StringPool.SLASH + admission.getCode()));
		}
		return builder.build();
	}

	@Required
	public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
		this.securityContextHolderStrategy = securityContextHolderStrategy;
	}
}
