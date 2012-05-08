package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template;

public interface DeduplicationTemplate<T> {

	public void deduplicate(T deduplicant);
}
