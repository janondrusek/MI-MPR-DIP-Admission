package cz.cvut.fit.mi_mpr_dip.admission.builder;

public interface Builder<T> {
	
	public void createNew();

	public T get();
}
