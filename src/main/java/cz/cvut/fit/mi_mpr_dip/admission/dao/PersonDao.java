package cz.cvut.fit.mi_mpr_dip.admission.dao;

import java.util.List;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

public interface PersonDao {

	public List<Person> findByEmail(String email);
}
