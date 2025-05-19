--liquibase formatted sql
--changeset K.Pingol:PUPPET-1339.1 localFilePath:01.000.00/requisites.sql

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS requisites
(
    id                    uuid DEFAULT gen_random_uuid()
        CONSTRAINT pk_requisites_id PRIMARY KEY,
    account_number        VARCHAR(20) NOT NULL
        CONSTRAINT uq_requisites_account_number UNIQUE,
    bic                   VARCHAR(9)  NOT NULL,
    correspondent_account VARCHAR(20) NOT NULL,
    inn                   VARCHAR(12) NOT NULL
        CONSTRAINT uq_requisites_inn UNIQUE,
    kpp                   VARCHAR(9)  NOT NULL,
    kbk                   VARCHAR(20) NOT NULL
);
--rollback DROP TABLE requisites;