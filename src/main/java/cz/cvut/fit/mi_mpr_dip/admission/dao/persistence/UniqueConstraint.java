package cz.cvut.fit.mi_mpr_dip.admission.dao.persistence;

public interface UniqueConstraint<T> {

	public Boolean isDuplicate(T duplicate);

	public Boolean isNotFound();
}
