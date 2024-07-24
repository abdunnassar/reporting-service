package com.nasserusmantv.reporting.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.nasserusmantv.reporting.dbconfig.AppConfig;
import com.nasserusmantv.reporting.service.UpdatedClaimsReportService;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableScheduling
public class UpdatedClaimsCountReportSchedulingConfigurer implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(UpdatedClaimsCountReportSchedulingConfigurer.class);

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private UpdatedClaimsReportService updatedClaimsReportService;

	private ThreadPoolTaskScheduler taskScheduler;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler);

		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				updatedClaimsReportService.generateAndSendReport();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cronExpression = appConfig.getUpdatedCountCron();
				if (cronExpression != null && !cronExpression.isEmpty()) {
					try {
						CronTrigger cronTrigger = new CronTrigger(cronExpression);
						return cronTrigger.nextExecutionTime(triggerContext);
					} catch (IllegalArgumentException e) {
						logger.error("Invalid cron expression for Updated Claims Report: {}", cronExpression, e);
					}
				} else {
					logger.warn("Cron expression for Updated Claims Report is null or empty");
				}
				return null;
			}
		});
	}
}
