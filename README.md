# The problem
At one of our clients, a Spring application that supports the company's day-to-day business is currently hosted in WebSphere 9 (Production environment has 6 server boxes running the same application). The DB used is IBM DB2. When the application gets moved to PCF soon, in Production environment there will be 6 instances running there, of the corresponding Spring Boot application.

We want to generate a report daily 3 times (say 10am, 1pm, 6pm) that shows how many claims were processed during the past 24 hours. There is a CLAIM table that we can query to get the info; assume for now that the number of claims processed is the number of claims created; so we can use the created_date timestamp column in the CLAIM table to calculate the count of claims created during the past 24 hours. Soon after generating the report, it should be emailed to a list of email addresses.

We don't want all 6 instances generating and emailing the report. We want just 1 instance generating and emailing the report.

# My solution
I proposed just running one new Spring Boot application called **reporting-service**. It will run on one designated server (the same server always) among the 6 servers running WebSphere 9. When the application gets moved to PCF, this **reporting-service** will be a single instance running there separate from the 6 instances supporting the insurance company's day-to-day business. The code here demonstrates how to bring up the **reporting-service** in a [Java 8, Spring 5, Spring Boot 2] environment, as a standalone Spring Boot application.

**Screenshot 1 of 2:** Local validations: what the DB tables look like
![image](https://github.com/user-attachments/assets/47069236-67f4-4169-aa65-1ecef0d7b97f)

**Screenshot 2 of 2:** Local validations: emails being received using MailHog
![image](https://github.com/user-attachments/assets/49ab6b4f-019c-4c72-a1ac-082c6605f454)

**NOTE:**
1. In this demonstration, there is *DB-configurability* for email-recipients and trigger-times.
2. Any report that is generated gets saved to disk on the server with a meaningful name, and that file is what gets attached when sending the email out.
3. In this demonstration, 2 separate reports are being generated at separate trigger times.

# Thoughts regarding making the reporting-service a part of the main application that support's the company's day-to-day business
1. For the alternative of running 6 instances of the service on WAS and having just one instance generate the report and send the email, it makes sense to identify the server we want generating the report, by its IP address (I am saying IP address because I am not able to think of any other identifier). But in PCF if we have 6 instances of the ReportingService running, how will we identify which one we want generating the report? In the PCF case because the IP address can change, I am not able to think of an identifier.

   Is there currently an **application_instance** table where each instance registers after it successfully starts up? And each instance knows what its unique ordinal is in the **application_instance** table. Is there something like that we can rely on?

   The **application_instance** table that I mentioned above is the solution that I can think of. Each app instance will register itself to this table at startup and each app-instance keeps a record of what its number is in the **application_instance** table.
 
   If we don't have something like that already, it makes another case for just having a single standalone instance of a new **reporting-service** application (Spring Boot 2 standalone application). Future reports too can be added to this **reporting-service** application.
 
2. The other option that comes to my mind is to have a production-grade task scheduler (built inhouse, provided by WAS or PCF, or a 3rd party tool, that also includes add, delete, view, edit, and audit capabilities) that invokes a REST endpoint to generate the report, through the application's load-balancer URL. Going through the load-balancer will ensure that only one instance is hit. 
 

