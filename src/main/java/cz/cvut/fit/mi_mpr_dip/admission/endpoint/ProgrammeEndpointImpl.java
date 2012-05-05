package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.ProgrammeEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper.UriEndpointHelper;
import cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.ProgrammeDeduplicationService;
import cz.cvut.fit.mi_mpr_dip.admission.util.StringPool;

@RooJavaBean
@Path(ProgrammeEndpointImpl.ENDPOINT_PATH)
public class ProgrammeEndpointImpl implements ProgrammeEndpoint {

	public static final String ENDPOINT_PATH = "/programme";
	public static final String PROGRAMME_PATH = "/name:{name}/degree:{degree}/language:{language}/studyMode:{studyMode}";

	@Autowired
	private ProgrammeDeduplicationService programmeDeduplicationService;

	@Autowired
	private ProgrammeEndpointHelper programmeEndpointHelper;

	@Autowired
	private UriEndpointHelper uriEndpointHelper;

	@Secured("PERM_READ_PROGRAMS")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getPrograms() {
		return getProgrammeEndpointHelper().getPrograms();
	}

	@Secured("PERM_READ_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@GET
	@Override
	public Response getProgramme(@PathParam("name") String name, @PathParam("degree") String degree,
			@PathParam("language") String language, @PathParam("studyMode") String studyMode) {
		return getProgrammeEndpointHelper().getProgramme(name, degree, language, studyMode);
	}

	@Secured("PERM_WRITE_PROGRAMME")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@POST
	@Override
	public Response addProgramme(Programme programme) {
		validateAndDeduplicateAndStore(programme);
		URI uri = getUriEndpointHelper().getProgrammeLocation(ENDPOINT_PATH + StringPool.SLASH, programme);
		return getProgrammeEndpointHelper().getCreatedResponse(uri);
	}

	@Transactional
	private void validateAndDeduplicateAndStore(Programme programme) {
		getProgrammeEndpointHelper().validate(programme);
		deduplicateAndStore(programme);
	}

	private void deduplicateAndStore(Programme programme) {
		getProgrammeDeduplicationService().deduplicateAndStore(programme);
	}

	@Secured("PERM_WRITE_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@PUT
	@Override
	public Response updateProgramme(String name, String degree, String language, String studyMode, Programme programme) {
		validateAndUpdate(name, degree, language, studyMode, programme);
		return getProgrammeEndpointHelper().getOkResponse();
	}

	private void validateAndUpdate(String name, String degree, String language, String studyMode, Programme programme) {
		Programme dbProgramme = getProgrammeEndpointHelper().validate(name, degree, language, studyMode, programme);

		dbProgramme.setDegree(programme.getDegree());
		dbProgramme.setLanguage(programme.getLanguage());
		dbProgramme.setName(programme.getName());
		dbProgramme.setStudyMode(programme.getStudyMode());

		deduplicateAndMerge(dbProgramme);
	}

	private void deduplicateAndMerge(Programme programme) {
		getProgrammeDeduplicationService().deduplicateAndMerge(programme);
	}

	@Secured("PERM_DELETE_PROGRAMME")
	@Path(PROGRAMME_PATH)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@DELETE
	@Override
	public Response deleteProgramme(@PathParam("name") String name, @PathParam("degree") String degree,
			@PathParam("language") String language, @PathParam("studyMode") String studyMode) {
		return getProgrammeEndpointHelper().deleteProgramme(name, degree, language, studyMode);
	}
}
