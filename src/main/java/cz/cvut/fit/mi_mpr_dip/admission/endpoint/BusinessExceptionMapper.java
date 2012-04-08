package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.stereotype.Component;

import cz.cvut.fit.mi_mpr_dip.admission.domain.ErrorResponse;
import cz.cvut.fit.mi_mpr_dip.admission.exception.BusinessException;

@Component
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException exception) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(createMessage(exception));

		return Response.status(exception.getResponseCode()).entity(response).build();
	}

	private String createMessage(BusinessException exception) {
		return exception.getConstraintViolations().toString();
	}

}
