package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;

@Repository
public class TermDaoImpl extends Dao<Term> implements TermDao {

	@Transactional(readOnly = true)
	@Override
	public Term getTerm(Date dateOfTerm, String room) {
		return getTermQuietly(dateOfTerm, room);
	}

	private Term getTermQuietly(Date dateOfTerm, String room) {
		Term term;
		try {
			term = uniqueResult(Term.findTermsByDateOfTermEqualsAndRoomEquals(dateOfTerm, room));
		} catch (Exception e) {
			term = processException(e);
		}
		return term;
	}

	@Override
	protected Term createEmptyResult() {
		return new Term();
	}
}
