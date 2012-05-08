package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

public interface DeduplicationService<T> {

	public void deduplicateAndStore(T deduplicant);
	
	public void deduplicateAndMerge(T deduplicant);
}
