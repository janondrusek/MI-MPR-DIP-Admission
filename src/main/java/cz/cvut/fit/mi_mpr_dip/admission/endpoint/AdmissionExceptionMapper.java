package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import cz.cvut.fit.mi_mpr_dip.admission.domain.ErrorResponse;

public abstract class AdmissionExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

	@Override
	public Response toResponse(E exception) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(createMessage(exception));

		return Response.status(getResponseCode(exception)).entity(response).build();
	}

	abstract protected String createMessage(E exception);

	abstract protected Integer getResponseCode(E exception);
}