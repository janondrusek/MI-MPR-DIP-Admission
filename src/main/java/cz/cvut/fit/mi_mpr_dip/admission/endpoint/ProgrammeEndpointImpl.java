package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.roo.addon.javabean.RooJavaBean;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@RooJavaBean
@Path(AdmissionEndpointImpl.ENDPOINT_PATH)
public class ProgrammeEndpointImpl implements ProgrammeEndpoint {

	protected static final String ENDPOINT_PATH = "/programme";

	@Override
	public Response getPrograms() {
		return null;
	}

	@Override
	public Response getProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}

	@Override
	public Response addProgramme(Programme programme) {
		return null;
	}

	@Override
	public Response updateProgramme(String name, String degree, String language, String studyMode, Programme programme) {
		return null;
	}

	@Override
	public Response deleteProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}
}
