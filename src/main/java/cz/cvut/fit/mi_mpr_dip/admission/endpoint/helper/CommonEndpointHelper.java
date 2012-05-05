package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.validation.AnnotatedBeanValidator;

@RooJavaBean
public abstract class CommonEndpointHelper<T> implements EndpointHelper<T> {

	@Autowired
	private AnnotatedBeanValidator beanValidator;

	@Override
	public Response getOkResponse() {
		return build(Response.ok());
	}

	@Override
	public Response getOkResponse(Object o) {
		return build(Response.ok(o));
	}

	@Override
	public Response getCreatedResponse(URI uri) {
		return build(Response.created(uri));
	}

	@Override
	public Response build(ResponseBuilder builder) {
		return builder.build();
	}

	protected void validate(T o) {
		getBeanValidator().validate(o);
	}
}
