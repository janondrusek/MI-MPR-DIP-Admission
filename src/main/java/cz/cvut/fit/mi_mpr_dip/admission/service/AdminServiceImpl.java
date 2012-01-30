package cz.cvut.fit.mi_mpr_dip.admission.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AccessiblePropertyPlaceholderConfigurer;

public class AdminServiceImpl implements AdminService {

	private AccessiblePropertyPlaceholderConfigurer exposablePropertyHolder;

	private String buildNumber;

	@Override
	public Map<String, String> getApplicationProperties() {
		return getExposablePropertyHolder().getProperties();
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
	public void setExposablePropertyHolder(AccessiblePropertyPlaceholderConfigurer exposablePropertyHolder) {
		this.exposablePropertyHolder = exposablePropertyHolder;
	}

	public AccessiblePropertyPlaceholderConfigurer getExposablePropertyHolder() {
		return exposablePropertyHolder;
	}

}
