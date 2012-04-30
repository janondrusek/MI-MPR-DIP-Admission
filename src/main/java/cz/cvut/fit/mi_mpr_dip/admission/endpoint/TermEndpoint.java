package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

public interface TermEndpoint {

	public Response getTerms();

	public Response getTerm(String dateOfTerm, String room);

	public Response addTerm(Term term);

	public Response updateTerm(String dateOfTerm, String room, Term term);

	public Response deleteTerm(String dateOfTerm, String room);
}
