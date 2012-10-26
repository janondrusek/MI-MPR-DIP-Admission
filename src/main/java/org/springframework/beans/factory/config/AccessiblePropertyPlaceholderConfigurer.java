package org.springframework.beans.factory.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;

import com.google.common.collect.Maps;

public class AccessiblePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements
		AccessiblePropertyConfigurer<Map<String, String>> {

	private Map<String, String> propertiesMap;
	// Default as in PropertyPlaceholderConfigurer
	private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

	@Override
	public void setSystemPropertiesMode(int systemPropertiesMode) {
		super.setSystemPropertiesMode(systemPropertiesMode);
		springSystemPropertiesMode = systemPropertiesMode;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);

		propertiesMap = Maps.newHashMap();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props, springSystemPropertiesMode);
			propertiesMap.put(keyStr, valueStr);
		}
	}

	@Override
	public Map<String, String> getProperties() {
		return propertiesMap;
	}

}
