package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication.template.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.address.Address;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.PrintLine;
import cz.cvut.fit.mi_mpr_dip.admission.domain.address.PrintLineType;

@Service
public class PrintLineDeduplicationTemplate extends AddressDeduplicationTemplate<PrintLineType> {

	@Override
	protected Set<PrintLineType> collect(Address address) {
		Set<PrintLineType> printLineTypes = new HashSet<PrintLineType>();
		if (CollectionUtils.isNotEmpty(address.getPrintLines())) {
			for (PrintLine printLine : address.getPrintLines()) {
				printLineTypes.add(printLine.getPrintLineType());
			}
		}
		return printLineTypes;
	}

	@Override
	protected List<PrintLineType> findByNameEquals(PrintLineType printLineType) {
		return PrintLineType.findPrintLineTypesByNameEquals(printLineType.getName()).getResultList();
	}

	@Override
	protected void deduplicateItem(PrintLineType printLineType, Address address) {
		for (PrintLine printLine : address.getPrintLines()) {
			if (printLine.getPrintLineType().equals(printLineType)) {
				printLine.setPrintLineType(printLineType);
			}
		}
	}
}