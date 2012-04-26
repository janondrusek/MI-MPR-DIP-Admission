package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.AdmissionResult;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.UserRoles;

public interface MobileEndpoint {

	public Response getUserIdentity();

	public Response deleteUserSession(String username, String sessionIdentifier);

	public Response updateUserRoles(String username, UserRoles userRoles);

	public Response getAdmission(String admissionCode);

	public Response saveResult(String admissionCode, AdmissionResult result);

	public Response savePhoto(String admissionCode, Appendix photo);

}
