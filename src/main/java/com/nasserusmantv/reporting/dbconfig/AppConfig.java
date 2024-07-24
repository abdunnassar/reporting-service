package com.nasserusmantv.reporting.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nasserusmantv.reporting.repository.AppConfigRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppConfig {
	@Autowired
	private AppConfigRepository appConfigRepository;

	private Map<String, String> configCache = new HashMap<>();

	@PostConstruct
	public void init() {
		refreshConfig();
	}

	private void refreshConfig() {
		configCache.put("reporting_service_claims_created_count_recipients",
				appConfigRepository.findConfigValueByConfigName("reporting_service_claims_created_count_recipients"));
		configCache.put("reporting_service_claims_updated_count_recipients",
				appConfigRepository.findConfigValueByConfigName("reporting_service_claims_updated_count_recipients"));
		configCache.put("reporting_service_claims_created_count_cron",
				appConfigRepository.findConfigValueByConfigName("reporting_service_claims_created_count_cron"));
		configCache.put("reporting_service_claims_updated_count_cron",
				appConfigRepository.findConfigValueByConfigName("reporting_service_claims_updated_count_cron"));
	}

	public String getCreatedCountRecipients() {
		return configCache.get("reporting_service_claims_created_count_recipients");
	}

	public String getUpdatedCountRecipients() {
		return configCache.get("reporting_service_claims_updated_count_recipients");
	}

	public String getCreatedCountCron() {
		return configCache.get("reporting_service_claims_created_count_cron");
	}

	public String getUpdatedCountCron() {
		return configCache.get("reporting_service_claims_updated_count_cron");
	}
}
