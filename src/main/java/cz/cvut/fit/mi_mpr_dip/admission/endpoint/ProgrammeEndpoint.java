package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;

public interface ProgrammeEndpoint {

	public Response getPrograms();

	public Response getProgramme(String name, String degree, String language, String studyMode);

	public Response addProgramme(Programme programme);

	public Response updateProgramme(String name, String degree, String language, String studyMode, Programme programme);

	public Response deleteProgramme(String name, String degree, String language, String studyMode);
}
