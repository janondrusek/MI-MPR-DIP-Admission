package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

public interface DeduplicationTemplate<T> {

	public void deduplicate(T deduplicant);
}
