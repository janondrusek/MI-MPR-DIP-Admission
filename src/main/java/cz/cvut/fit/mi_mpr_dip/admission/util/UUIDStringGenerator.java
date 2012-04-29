package cz.cvut.fit.mi_mpr_dip.admission.util;

import java.util.UUID;

public class UUIDStringGenerator implements StringGenerator {

	public String generateRandom() {
		return UUID.randomUUID().toString();
	}

	public String generateRandomAlphanumeric() {
		return generateRandom().replaceAll(StringPool.DASH, StringPool.BLANK);
	}
}
