package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface MobileService {

	public Response authenticate(String username, String password);

	public Response getPerson(String sessionId, String admissionCode);

	public void saveResult(String sessionId, String admissionCode, Double result);

	public void savePhoto(String sessionId, String admissionCode, String photo);

}
