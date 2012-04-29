package cz.cvut.fit.mi_mpr_dip.admission.util;

import org.springframework.beans.factory.annotation.Required;

public class DummyStringGenerator implements StringGenerator {

	private String random;
	private String randomAlphanumeric;

	@Override
	public String generateRandom() {
		return random;
	}

	@Override
	public String generateRandomAlphanumeric() {
		return randomAlphanumeric;
	}

	@Required
	public void setRandom(String random) {
		this.random = random;
	}

	@Required
	public void setRandomAlphanumeric(String randomAlphanumeric) {
		this.randomAlphanumeric = randomAlphanumeric;
	}

}
