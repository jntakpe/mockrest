CREATE TABLE oauth_access_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication BYTEA,
    refresh_token VARCHAR(255)
);

CREATE TABLE oauth_approvals
(
    "userId" VARCHAR(255),
    "clientId" VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(255),
    "expiresAt" TIMESTAMP,
    "lastModifiedAt" TIMESTAMP
);

CREATE TABLE oauth_client_details
(
    client_id VARCHAR(255) PRIMARY KEY NOT NULL,
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INT,
    refresh_token_validity INT,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(4096)
);

CREATE TABLE oauth_client_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255)
);

CREATE TABLE oauth_code
(
    code VARCHAR(255)
);

CREATE TABLE oauth_refresh_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication BYTEA
);

CREATE TABLE t_user
(
    id SERIAL PRIMARY KEY NOT NULL,
    version INT,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(15),
    temporary BOOL NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    last_modified_by VARCHAR(50),
    last_modified_date TIMESTAMP
);

CREATE UNIQUE INDEX idx_user_email ON t_user (email);
CREATE UNIQUE INDEX idx_user_login ON t_user (login);
CREATE UNIQUE INDEX t_user_email_key ON t_user (email);
CREATE UNIQUE INDEX t_user_login_key ON t_user (login);

ALTER TABLE oauth_access_token ADD FOREIGN KEY (user_name) REFERENCES t_user (login);
ALTER TABLE oauth_client_token ADD FOREIGN KEY (user_name) REFERENCES t_user (login);