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
public class CreatedClaimsReportService {

	private static final Logger logger = LoggerFactory.getLogger(CreatedClaimsReportService.class);

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private ReportUtils reportUtils;

	private String[] recipients;

	@PostConstruct
	public void init() {
		String recipientsStr = appConfig.getCreatedCountRecipients();
		recipients = reportUtils.getRecipientsArray(recipientsStr);
		logger.info("Initialized recipients for Created Claims Report Service");
	}

	public void generateAndSendReport() {
		logger.info("Generating Created Claims Report");
		long claimsCreatedCount = claimRepository.countClaimsCreatedInLast24Hours();
		String reportContent = "Number of claims created in the last 24 hours: " + claimsCreatedCount;

		String fileName = reportUtils.generateReportFileName("claims_created_report");
		reportUtils.writeReportToFile(fileName, reportContent);

		if (recipients.length > 0) {
			reportUtils.sendEmailWithAttachment(recipients, fileName, "Claims Created Report", reportContent,
					new File(fileName));
			logger.info("Created Claims Report sent to recipients");
		} else {
			logger.warn("No recipients configured for Created Claims Report");
		}
	}
}
