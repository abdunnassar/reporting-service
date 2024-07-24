package com.nasserusmantv.reporting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReportUtils {

	private static final Logger logger = LoggerFactory.getLogger(ReportUtils.class);

	@Autowired
	private JavaMailSender emailSender;

	@Value("${report.directory}")
	private String reportDirectory;

	@PostConstruct
	public void init() {
		// Create the report directory if it doesn't exist
		try {
			Files.createDirectories(Paths.get(reportDirectory));
			logger.info("Report directory created at: {}", reportDirectory);
		} catch (IOException e) {
			logger.error("Failed to create report directory: {}", reportDirectory, e);
		}
	}

	public void sendEmailWithAttachment(String[] recipients, String fileName, String subject, String text, File file) {
		MimeMessage message = emailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setText(text);

			for (String recipient : recipients) {
				helper.setTo(recipient);
			}

			helper.addAttachment(fileName, file);
			emailSender.send(message);
			logger.info("Email sent to recipients with subject: {}", subject);
		} catch (MessagingException e) {
			logger.error("Failed to send email with subject: {}", subject, e);
		}
	}

	public String generateReportFileName(String prefix) {
		return reportDirectory + prefix + "_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
	}

	public void writeReportToFile(String fileName, String reportContent) {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(reportContent);
			fileWriter.close();
			logger.info("Report written to file: {}", fileName);
		} catch (IOException e) {
			logger.error("Failed to write report to file: {}", fileName, e);
		}
	}

	public String[] getRecipientsArray(String recipientsStr) {
		if (recipientsStr != null && !recipientsStr.isEmpty()) {
			return recipientsStr.split(",");
		} else {
			return new String[0];
		}
	}
}
