CREATE TABLE app_config (
    id BIGINT NOT NULL PRIMARY KEY,
    config_name VARCHAR(255) NOT NULL,
    config_value VARCHAR(255)
);

CREATE TABLE claim (
    id BIGINT NOT NULL PRIMARY KEY,
    created_date TIMESTAMP,
    last_update_date TIMESTAMP
);
