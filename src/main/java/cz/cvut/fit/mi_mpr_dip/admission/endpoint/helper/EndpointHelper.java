package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public interface EndpointHelper<T> {

	public Response getOkResponse();

	public Response getOkResponse(Object o);

	public Response build(ResponseBuilder builder);
}
