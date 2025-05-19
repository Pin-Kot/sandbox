--liquibase formatted sql
--changeset K.Pingol:PUPPET-1328.1 localFilePath:01.000.00/users.sql

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS users
(
    id              uuid DEFAULT gen_random_uuid()
        CONSTRAINT pk_users_id PRIMARY KEY,
    first_name      VARCHAR(30) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    birth_date      DATE        NOT NULL,
    inn             VARCHAR(12) NOT NULL
        CONSTRAINT uq_users_inn UNIQUE,
    snils           VARCHAR(11) NOT NULL
        CONSTRAINT uq_users_snils UNIQUE,
    passport_number VARCHAR(11) NOT NULL
        CONSTRAINT uq_users_passport_number UNIQUE,
    login           VARCHAR(50) NOT NULL
        CONSTRAINT uq_users_login_unique UNIQUE,
    password        VARCHAR(50) NOT NULL
);
--rollback DROP TABLE users;

--liquibase formatted sql
--changeset K.Pingol:PUPPET-1328.2 localFilePath:01.000.00/users.sql

ALTER TABLE users
    ADD COLUMN requisites_id uuid NOT NULL DEFAULT gen_random_uuid();
--rollback ALTER TABLE users DROP COLUMN requisites_id;

--liquibase formatted sql
--changeset K.Pingol:PUPPET-1328.3 localFilePath:01.000.00/users.sql

ALTER TABLE users
    ADD CONSTRAINT fk_users_requisites FOREIGN KEY (requisites_id) REFERENCES requisites (id);
--rollback ALTER TABLE users DROP CONSTRAINT fk_users_requisites;