package cz.cvut.fit.mi_mpr_dip.admission.endpoint;

import javax.ws.rs.core.Response;

public interface TermEndpoint {

	public Response getTerms();

	public Response getTerm(String dateOfTerm, String room);

	public Response addTerm();

	public Response updateTerm(String dateOfTerm, String room);

	public Response deleteTerm(String dateOfTerm, String room);
}
