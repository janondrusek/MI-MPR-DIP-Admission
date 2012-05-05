package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.HashSet;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.collection.Programs;
import cz.cvut.fit.mi_mpr_dip.admission.domain.study.Programme;
import cz.cvut.fit.mi_mpr_dip.admission.validation.ProgrammeUniqueConstraintValidator;

@Service
@RooJavaBean
public class ProgrammeEndpointHelperImpl extends CommonEndpointHelper<Programme> implements ProgrammeEndpointHelper {

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
		programs.setPrograms(new HashSet<Programme>(dbPrograms));

		updateCollectionDomainCounters(new Long(dbPrograms.size()), programs);
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
