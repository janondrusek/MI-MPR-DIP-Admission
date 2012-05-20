package cz.cvut.fit.mi_mpr_dip.admission.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fit.mi_mpr_dip.admission.dao.persistence.AppendixUniqueConstraint;
import cz.cvut.fit.mi_mpr_dip.admission.domain.Appendix;

@Repository
public class AppendixDaoImpl extends AbstractDao<Appendix> implements AppendixDao {

	@Transactional(readOnly = true)
	@Override
	public Appendix getAppendix(String identifier) {
		return getAppendixQuietly(identifier);
	}

	@Transactional(readOnly = true)
	@Override
	public Appendix getAppendix(AppendixUniqueConstraint uniqueConstraint) {
		return getAppendix(uniqueConstraint.getIdentifier());
	}

	private Appendix getAppendixQuietly(String identifier) {
		Appendix appendix;
		try {
			appendix = uniqueResult(Appendix.findAppendixesByIdentifierEquals(identifier));
		} catch (Exception e) {
			appendix = processException(e);
		}
		return appendix;
	}

	@Override
	protected Appendix createEmptyResult() {
		return new Appendix();
	}

}
