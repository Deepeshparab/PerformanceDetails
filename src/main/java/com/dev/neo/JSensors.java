package com.dev.neo;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dev.neo.model.components.Components;
//import com.dev.neo.SendMail;

public enum JSensors {
	get;
	
	//static SendMail sendmail=new SendMail();

	private static final Logger LOGGER = LoggerFactory.getLogger(JSensors.class);

	final Map<String, String> baseConfig;

	private Map<String, String> usedConfig = null;

	private JSensors() {
		baseConfig = SensorsConfig.getConfigMap();
	}

	public JSensors config(Map<String, String> config) {
		if (this.usedConfig == null) {
			this.usedConfig = this.baseConfig;
		}

		for (final Map.Entry<String, String> entry : config.entrySet()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Overriding config entry %s, %s by %s", entry.getKey(),
						this.usedConfig.get(entry.getKey()), entry.getValue()));
			}
			this.usedConfig.put(entry.getKey(), entry.getValue());
		}

		return this;
	}

	public Components components() {
		if (this.usedConfig == null) {
			this.usedConfig = new HashMap<String, String>();
		}

		Components components = SensorsLocator.get.getComponents(this.usedConfig);

		this.usedConfig = this.baseConfig;

		return components;
	}
}
