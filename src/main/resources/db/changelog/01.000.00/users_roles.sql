--liquibase formatted sql
--changeset K.Pingol:PUPPET-1328.1 localFilePath:01.000.00/users_roles.sql

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id uuid        NOT NULL,
    role    VARCHAR(20) NOT NULL,
    CONSTRAINT uq_users_id_role UNIQUE (user_id, role),
    CONSTRAINT fk_users_roles_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback DROP TABLE users_roles;