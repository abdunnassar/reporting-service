package com.nasserusmantv.reporting.service;

import com.nasserusmantv.reporting.dbconfig.AppConfig;
import com.nasserusmantv.reporting.repository.ClaimRepository;
import com.nasserusmantv.reporting.util.ReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UpdatedClaimsReportService {

	private static final Logger logger = LoggerFactory.getLogger(UpdatedClaimsReportService.class);

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ReportUtils reportUtils;

	private String[] recipients;

	@PostConstruct
	public void init() {
		String recipientsStr = appConfig.getUpdatedCountRecipients();
		recipients = reportUtils.getRecipientsArray(recipientsStr);
		logger.info("Initialized recipients for Updated Claims Report Service");
	}

	public void generateAndSendReport() {
		logger.info("Generating Updated Claims Report");
		long claimsUpdatedCount = claimRepository.countClaimsUpdatedInLast24Hours();
		String reportContent = "Number of claims updated in the last 24 hours: " + claimsUpdatedCount;

		String fileName = reportUtils.generateReportFileName("claims_updated_report");
		reportUtils.writeReportToFile(fileName, reportContent);

		if (recipients.length > 0) {
			reportUtils.sendEmailWithAttachment(recipients, fileName, "Claims Updated Report", reportContent,
					new File(fileName));
			logger.info("Updated Claims Report sent to recipients");
		} else {
			logger.warn("No recipients configured for Updated Claims Report");
		}
	}
}
