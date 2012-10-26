package org.springframework.beans.factory.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;

import com.google.common.collect.Maps;

public class JbpmAccessiblePropertyConfigurer extends PropertyResourceConfigurer implements
		AccessiblePropertyConfigurer<Map<String, String>> {

	private Map<String, String> jbpmProcessProperties = Maps.newHashMap();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		for (Object key : props.keySet()) {
			String pKey = key.toString();
			jbpmProcessProperties.put(pKey, props.getProperty(pKey));
		}
	}

	@Override
	public Map<String, String> getProperties() {
		return jbpmProcessProperties;
	}

}
