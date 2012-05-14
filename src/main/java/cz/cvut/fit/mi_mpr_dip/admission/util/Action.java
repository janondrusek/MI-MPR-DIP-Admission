package cz.cvut.fit.mi_mpr_dip.admission.util;

public interface Action<T> {

	public void perform(T o);
}
