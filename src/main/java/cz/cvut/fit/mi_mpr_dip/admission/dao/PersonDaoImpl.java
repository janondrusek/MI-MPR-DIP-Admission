package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Repository
public class PersonDaoImpl extends AbstractDao<Person> implements PersonDao {

	@Transactional(readOnly = true)
	@Override
	public List<Person> findByEmail(String email) {
		return findByEmailQuietly(email);
	}

	private List<Person> findByEmailQuietly(String email) {
		List<Person> people;
		try {
			people = Person.findPeopleByEmailEquals(email).getResultList();
		} catch (Exception e) {
			people = processListException(e);
		}
		return people;
	}

	@Override
	protected Person createEmptyResult() {
		return new Person();
	}

}
