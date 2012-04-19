package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Admission;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.action.AdmissionAction;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.AdmissionEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.EndpointHelper;

@RooJavaBean
@Path(AndroidMobileEndpoint.ENDPOINT_PATH)
public class AndroidMobileEndpoint implements MobileEndpoint {

	public static final String ADMISSION_PATH = "/admission";
	public static final String SAVE_PHOTO_PATH = "/photo";
	public static final String SAVE_RESULT_PATH = "/result";
	public static final String ENDPOINT_PATH = "/mobile";

	@Autowired
	private EndpointHelper endpointHelper;

	@Path(AdmissionEndpointHelper.IDENTITY_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getUserIdentity() {
		return getEndpointHelper().getUserIdentity();
	}

	@Secured("PERM_READ_PERSON")
	@Path(ADMISSION_PATH + "/{admissionCode}")
	@GET
	@Override
	public Response getAdmission(@PathParam("admissionCode") String admissionCode) {
		return getEndpointHelper().getAdmission(admissionCode);
	}

	@Secured("PERM_WRITE_RESULT")
	@Path(ADMISSION_PATH + "/{admissionCode}" + SAVE_RESULT_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response saveResult(@PathParam("admissionCode") String admissionCode, @Valid final AdmissionResult result)
			throws URISyntaxException {
		return getEndpointHelper().mergeAdmission(admissionCode, getAdmissionBasePath(), result,
				new AdmissionAction<AdmissionResult>() {

					@Override
					public void performAction(Admission admission, AdmissionResult actor) {
						admission.setResult(result);
					}
				});
	}

	@Secured("PERM_WRITE_PHOTO")
	@Path(ADMISSION_PATH + "/{admissionCode}" + SAVE_PHOTO_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response savePhoto(@PathParam("admissionCode") String admissionCode, @Valid final Appendix photo)
			throws URISyntaxException {
		return getEndpointHelper().mergeAdmission(admissionCode, getAdmissionBasePath(), photo,

		new AdmissionAction<Appendix>() {

			@Override
			public void performAction(Admission admission, Appendix actor) {
				admission.getPhotos().add(photo);
			}
		});
	}

	private String getAdmissionBasePath() {
		return ENDPOINT_PATH + ADMISSION_PATH;
	}

}