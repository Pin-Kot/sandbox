--liquibase formatted sql
--changeset K.Pingol:PUPPET-1339.1 localFilePath:01.000.00/data.sql

INSERT INTO requisites(id, account_number, bic, correspondent_account, inn, kpp, kbk)
VALUES ('b2d83dcb-6472-4df0-9188-af916dec7f35', '40567890012345678123', '044525225', '40702810000000000001',
        '123456789012',
        '123456789', '18210101010010000101'),
       ('c2c3574e-fa5b-4336-a175-ada64db5279f', '40567890012345678124', '044525226', '40702810000000000002',
        '234567890123',
        '234567890', '18210101010010000102'),
       ('6687596d-7a74-4ca6-80b7-72613ee4709e', '40567890012345678125', '044525227', '40702810000000000003',
        '345678901234',
        '345678901', '18210101010010000103'),
       ('fb20eddf-6048-4d70-982d-a9e943c46ef0', '40567890012345678126', '044525228', '40702810000000000004',
        '456789012345',
        '456789012', '18210101010010000104'),
       ('4e99e77d-b7ee-4b93-bb64-2f0cdb77c215', '40567890012345678127', '044525229', '40702810000000000005',
        '567890123456',
        '567890123', '18210101010010000105');

--rollback DELETE FROM requisites WHERE id IN (
--    'b2d83dcb-6472-4df0-9188-af916dec7f35',
--    'c2c3574e-fa5b-4336-a175-ada64db5279f',
--    '6687596d-7a74-4ca6-80b7-72613ee4709e',
--    'fb20eddf-6048-4d70-982d-a9e943c46ef0',
--    '4e99e77d-b7ee-4b93-bb64-2f0cdb77c215'
--);

--changeset K.Pingol:PUPPET-1328.1 localFilePath:01.000.00/data.sql

INSERT INTO users(id, requisites_id, last_name, first_name, birth_date, inn, snils, passport_number, login, password)
VALUES ('fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8', 'b2d83dcb-6472-4df0-9188-af916dec7f35', 'Антонов', 'Андрей',
        '2000-01-01', '468175102208', '12345678901',
        '1010111222', 'andrew11', 'andrew11'),
       ('9729d5b5-04c4-473e-8a35-70d9464c6acd', 'c2c3574e-fa5b-4336-a175-ada64db5279f', 'Бубнов', 'Борис', '1999-02-02',
        '468175102209', '12345678902',
        '1030111223', 'boris22', 'boris22'),
       ('e30612af-d93a-4b6c-b607-43d8651cd81e', '6687596d-7a74-4ca6-80b7-72613ee4709e', 'Васильева', 'Вера',
        '1998-03-03', '468175102207', '12345678903',
        '1030111224', 'vera33', 'vera33'),
       ('9648761f-9e83-44d3-95b2-3cc9229c68c7', 'fb20eddf-6048-4d70-982d-a9e943c46ef0', 'Галкина', 'Галина',
        '2001-04-04', '468175102206', '12345678904',
        '1030111225', 'gala44', 'gala33'),
       ('b8f86318-a8ef-48f2-a2f0-bdfaaa726dd0', '4e99e77d-b7ee-4b93-bb64-2f0cdb77c215', 'Дудук', 'Денис', '1985-05-05',
        '468175102205', '12345678905',
        '1030111226', 'denis44', 'denis33');

--rollback DELETE FROM users WHERE id IN (
--    'fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8',
--    '9729d5b5-04c4-473e-8a35-70d9464c6acd',
--    'e30612af-d93a-4b6c-b607-43d8651cd81e',
--    '9648761f-9e83-44d3-95b2-3cc9229c68c7',
--    'b8f86318-a8ef-48f2-a2f0-bdfaaa726dd0'
--);

--changeset K.Pingol:PUPPET-1328.2 localFilePath:01.000.00/data.sql

INSERT INTO users_roles(user_id, role)
VALUES ('fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8', 'ADMIN'),
       ('fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8', 'USER'),
       ('9729d5b5-04c4-473e-8a35-70d9464c6acd', 'USER'),
       ('e30612af-d93a-4b6c-b607-43d8651cd81e', 'USER'),
       ('9648761f-9e83-44d3-95b2-3cc9229c68c7', 'USER'),
       ('b8f86318-a8ef-48f2-a2f0-bdfaaa726dd0', 'USER');

--rollback DELETE FROM users_roles WHERE user_id IN (
--    'fa83d03a-2b10-40e8-a5cf-5b99fc81c4b8',
--    '9729d5b5-04c4-473e-8a35-70d9464c6acd',
--    'e30612af-d93a-4b6c-b607-43d8651cd81e',
--    '9648761f-9e83-44d3-95b2-3cc9229c68c7',
--    'b8f86318-a8ef-48f2-a2f0-bdfaaa726dd0'
--);