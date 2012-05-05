package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

public interface ProgrammeEndpointHelper extends EndpointHelper<Programme> {

	public Response getPrograms();

	public Response getProgramme(String name, String degree, String language, String studyMode);

	public Response deleteProgramme(String name, String degree, String language, String studyMode);

	public Programme validate(String name, String degree, String language, String studyMode, Programme programme);

	public void validate(Programme programme);
}
