package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public interface EndpointHelper<T> {

	public Response getOkResponse();

	public Response getOkResponse(Object o);

	public Response getCreatedResponse(URI uri);

	public Response getSeeOtherResponse(URI uri);

	public Response build(ResponseBuilder builder);
}
