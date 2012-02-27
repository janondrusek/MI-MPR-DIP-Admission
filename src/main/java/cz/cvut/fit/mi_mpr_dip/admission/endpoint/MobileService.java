package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface MobileService {

	public Response getUserIdentity();

	public Response getPerson(String admissionCode);

	public void saveResult(String admissionCode, Double result);

	public void savePhoto(String admissionCode, String photo);

}
