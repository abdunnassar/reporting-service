-- Populate the app_config table
INSERT INTO app_config (id, config_name, config_value) VALUES (1, 'reporting_service_claims_created_count_recipients', 'created_report_recipient@example.com');
INSERT INTO app_config (id, config_name, config_value) VALUES (2, 'reporting_service_claims_updated_count_recipients', 'updated_report_recipient@example.com');
INSERT INTO app_config (id, config_name, config_value) VALUES (3, 'reporting_service_claims_created_count_cron', '0 5,15,25,35,45,55 * * * ?'); -- Every minute that ends with 5
INSERT INTO app_config (id, config_name, config_value) VALUES (4, 'reporting_service_claims_updated_count_cron', '0 10,20,30,40,50,0 * * * ?'); -- Every minute that ends with 10

-- Populate the claim table with test data
INSERT INTO claim (id, created_date, last_update_date) VALUES (1, CURRENT_TIMESTAMP - INTERVAL '1' HOUR, CURRENT_TIMESTAMP - INTERVAL '30' MINUTE);
INSERT INTO claim (id, created_date, last_update_date) VALUES (2, CURRENT_TIMESTAMP - INTERVAL '2' HOUR, CURRENT_TIMESTAMP - INTERVAL '1' HOUR);
INSERT INTO claim (id, created_date, last_update_date) VALUES (3, CURRENT_TIMESTAMP - INTERVAL '1' DAY, CURRENT_TIMESTAMP - INTERVAL '12' HOUR);
INSERT INTO claim (id, created_date, last_update_date) VALUES (4, CURRENT_TIMESTAMP - INTERVAL '1' DAY + INTERVAL '1' HOUR, CURRENT_TIMESTAMP - INTERVAL '6' HOUR);
INSERT INTO claim (id, created_date, last_update_date) VALUES (5, CURRENT_TIMESTAMP - INTERVAL '2' DAY, CURRENT_TIMESTAMP - INTERVAL '1' DAY);
