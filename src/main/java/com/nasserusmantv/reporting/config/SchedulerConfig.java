package com.nasserusmantv.reporting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

	@Bean
	ThreadPoolTaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		scheduler.setThreadNamePrefix("scheduler-");
		scheduler.setAwaitTerminationSeconds(60); // Wait up to 60 seconds for running tasks to complete on shutdown
		scheduler.setWaitForTasksToCompleteOnShutdown(true); // Wait for running tasks to complete on shutdown
		return scheduler;
	}
}
