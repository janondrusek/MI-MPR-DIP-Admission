package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.ProgrammeDao;
import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Programs;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.validation.unique.ProgrammeUniqueConstraintValidator;

@Service
@RooJavaBean
public class ProgrammeEndpointHelperImpl extends CommonEndpointHelper<Programme> implements ProgrammeEndpointHelper {

	@Autowired
	private ProgrammeDao programmeDao;

	@Autowired
	private ProgrammeUniqueConstraintValidator uniqueConstraintValidator;

	@Override
	public Response getPrograms() {
		Programs programs = createPrograms();
		return getOkResponse(programs);
	}

	private Programs createPrograms() {
		Programs programs = new Programs();
		populate(programs);

		return programs;
	}

	private void populate(Programs programs) {
		List<Programme> dbPrograms = Programme.findAllProgrammes();
		programs.setPrograms(new HashSet<>(dbPrograms));

		updateCollectionDomainCounters(new Long(dbPrograms.size()), programs);
	}

	@Override
	public Response getProgramme(String name, String degree, String language, String studyMode) {
		Programme programme = getProgrammeOrThrowNotFound(name, degree, language, studyMode);
		return getOkResponse(programme);
	}

	@Override
	public Response deleteProgramme(String name, String degree, String language, String studyMode) {
		Programme programme = getProgrammeOrThrowNotFound(name, degree, language, studyMode);

		programme.remove();
		return getOkResponse();
	}

	private Programme getProgrammeOrThrowNotFound(String name, String degree, String language, String studyMode) {
		Programme programme = findProgramme(name, degree, language, studyMode);
		validateNotFound(programme);
		return programme;
	}

	private Programme findProgramme(String name, String degreeName, String languageName, String studyModeName) {
		return getProgrammeDao().getProgramme(name, degreeName, languageName, studyModeName);
	}

	@Override
	public Programme validate(String name, String degree, String language, String studyMode, Programme programme) {
		super.validate(programme);
		Programme dbProgramme = getProgrammeOrThrowNotFound(name, degree, language, studyMode);
		if (!programme.equals(dbProgramme)) {
			getBusinessExceptionUtil().throwException(HttpServletResponse.SC_BAD_REQUEST,
					"Unique constraint change requested");
		}
		return dbProgramme;
	}

	@Override
	protected boolean isNotFound(Programme programme) {
		return programme.getName() == null || programme.getDegree() == null || programme.getLanguage() == null
				|| programme.getStudyMode() == null;
	}

	@Override
	public void validate(Programme programme) {
		super.validate(programme);
		getUniqueConstraintValidator().validate(programme);
	}

}
