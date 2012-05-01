package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

@Service
@RooJavaBean
public class ProgrammeEndpointHelperImpl extends CommonEndpointHelper<Programme> implements ProgrammeEndpointHelper {

	@Override
	public Response getPrograms() {
		return null;
	}

	@Override
	public Response getProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}

	@Override
	public Response deleteProgramme(String name, String degree, String language, String studyMode) {
		return null;
	}

	@Override
	public Programme validate(String name, String degree, String language, String studyMode, Programme programme) {
		return null;
	}

	@Override
	public void validate(Programme programme) {
		super.validate(programme);
	}

}
