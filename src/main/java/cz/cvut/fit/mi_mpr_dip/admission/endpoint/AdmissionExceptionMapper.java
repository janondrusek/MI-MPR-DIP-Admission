package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.MDC;

import cz.cvut.fit.mi_mpr_dip.admission.domain.ErrorResponse;
import cz.cvut.fit.mi_mpr_dip.admission.util.WebKeys;

public abstract class AdmissionExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

	@Override
	public Response toResponse(E exception) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(createMessage(exception));
		response.setInternalRequestId(MDC.get(WebKeys.MDC_KEY_INTERNAL_REQUEST_ID));

		return Response.status(getResponseCode(exception)).entity(response).build();
	}

	abstract protected String createMessage(E exception);

	abstract protected Integer getResponseCode(E exception);
}