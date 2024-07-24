At one of our clients, a Spring application that supports the company's day-to-day business is currently hosted in WebSphere 9 (Production environment has 6 server boxes running the same application). The DB used is IBM DB2. When the application gets moved to PCF soon, in Production environment there will be 6 instances running there, of the corresponding Spring Boot application.

We want to generate a report daily 3 times (say 10am, 1pm, 6pm) that shows how many claims were processed during the past 24 hours. There is a CLAIM table that we can query to get the info; assume for now that the number of claims processed is the number of claims created; so we can use the created_date timestamp column in the CLAIM table to calculate the count of claims created during the past 24 hours. Soon after generating the report, it should be emailed to a list of email addresses configured in DB at app_config.config_name = reporting_service_claims_processed_count_recipients; app_config.config_value holds a string value that can be null; the value in DB is a comma-separated string of email addresses; the string could be set to null to prevent sending emails completely. The exact times during the day for generating the report should also be DB-configurable as a string of comma-separated HHMM values at app_config.config_name = reporting_service_claims_processed_count_trigger_times; app_config.config_value holds a string value that can be null; the string could be set to null to prevent report generation completely. Any report that is generated should be saved to disk on the server with a meaningful name, and that file is what gets attached when sending the email out.

We don't want all 6 instances generating and emailing the report. We want just one instance generating the report and it should be the same instance always.

I proposed just running one new Spring Boot application called reporting_service. It will run on one designated server (the same server always) among the 6 servers running WebSphere 9. When the application gets moved to PCF, this reporting_service will be a single instance running there separate from the 6 instances supporting the insurance company's day-to-day business. The code here demonstrates how to bring up the reporting_service in a [Java 8, Spring 5, Spring Boot 2] environment, as a standalone Spring Boot application.

Screenshot 1 of 2: What the DB tables used for local validations look like:
![image](https://github.com/user-attachments/assets/47069236-67f4-4169-aa65-1ecef0d7b97f)

Screenshot 2 of 2: Emails being received using MailHog.
![image](https://github.com/user-attachments/assets/49ab6b4f-019c-4c72-a1ac-082c6605f454)

