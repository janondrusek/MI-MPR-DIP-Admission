package cz.cvut.fit.mi_mpr_dip.admission.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RandomStringGenerator {

	public String generateRandom() {
		return UUID.randomUUID().toString();
	}

	public String generateRandomAlphanumeric() {
		return generateRandom().replaceAll(StringPool.DASH, StringPool.BLANK);
	}
}
