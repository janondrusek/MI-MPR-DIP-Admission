package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Term;
import cz.cvut.fit.mi_mpr_dip.admission.domain.TermRegistration;

@Repository
public class TermDaoImpl extends AbstractDao<Term> implements TermDao {

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
	private TermRegistrationDao termRegistrationDao;

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
	public void delete(Term term) {
		deleteRegistrations(term);
		deleteTerm(term);
	}

	@Transactional
	private void deleteTerm(Term term) {
		term.remove();
	}

	@Transactional
	private void deleteRegistrations(Term term) {
		if (CollectionUtils.isNotEmpty(term.getRegistrations())) {
			for (Iterator<TermRegistration> iterator = term.getRegistrations().iterator(); iterator.hasNext();) {
				TermRegistration termRegistration = iterator.next();
				iterator.remove();
				termRegistrationDao.delete(termRegistration);
			}
		}
	}

	@Override
	protected Term createEmptyResult() {
		return new Term();
	}
}
