package cz.cvut.fit.mi_mpr_dip.admission.service;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.UniqueConstraint;

public interface CrudService<T> {

	public void add(T o);

	public T get(UniqueConstraint<T> uniqueConstraint);

	public void update(UniqueConstraint<T> uniqueConstraint, T o);

	public void delete(UniqueConstraint<T> uniqueConstraint);
}
