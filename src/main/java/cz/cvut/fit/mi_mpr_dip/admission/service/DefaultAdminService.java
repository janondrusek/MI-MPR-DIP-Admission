package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AccessiblePropertyConfigurer;

public class DefaultAdminService implements AdminService {

	private AccessiblePropertyConfigurer<Map<String, String>> accessiblePropertyConfigurer;

	private String buildNumber;

	@Override
	public Map<String, String> getApplicationProperties() {
		return getAccessiblePropertyConfigurer().getProperties();
	}

	@Required
	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	@Override
	public String getBuildNumber() {
		return buildNumber;
	}

	@Required
	public void setAccessiblePropertyConfigurer(AccessiblePropertyConfigurer<Map<String, String>> exposablePropertyHolder) {
		this.accessiblePropertyConfigurer = exposablePropertyHolder;
	}

	public AccessiblePropertyConfigurer<Map<String, String>> getAccessiblePropertyConfigurer() {
		return accessiblePropertyConfigurer;
	}

}
