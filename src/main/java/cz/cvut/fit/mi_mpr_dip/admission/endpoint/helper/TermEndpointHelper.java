package cz.cvut.fit.mi_mpr_dip.admission.endpoint.helper;

import java.util.Date;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface TermEndpointHelper {

	public Response getTerms();

	public Response getTerm(String dateOfTerm, String room);

	public Response getTerm(Date dateOfTerm, String room);

	public Response deleteTerm(String dateOfTerm, String room);

	public Term validate(String dateOfTerm, String room, Term term);

	public void validate(Term term);
}
