INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_ENDUSER');
INSERT INTO ROLE (name) VALUES ('ROLE_AGENT');

--ENDUSER specific privileges
INSERT INTO PRIVILEGE (name) VALUES ('REQUEST_CREATING');
INSERT INTO PRIVILEGE (name) VALUES ('RENT_PAYING');
INSERT INTO PRIVILEGE (name) VALUES ('ENDUSER_REQUEST_CANCELING');
INSERT INTO PRIVILEGE (name) VALUES ('CREATED_REQUEST_VIEWING');
INSERT INTO PRIVILEGE (name) VALUES ('RENT_RATING');

--Common privileges for AGENT and ENDUSER roles
INSERT INTO PRIVILEGE (name) VALUES ('REQUEST_ACCEPTING');
INSERT INTO PRIVILEGE (name) VALUES ('PRICELIST_CREATION');
INSERT INTO PRIVILEGE (name) VALUES ('PRICELIST_ITEM_CREATION');
INSERT INTO PRIVILEGE (name) VALUES ('MESSAGE_CREATING');
INSERT INTO PRIVILEGE (name) VALUES ('MESSAGE_VIEWING');
INSERT INTO PRIVILEGE (name) VALUES ('REQUEST_VIEWING');
INSERT INTO PRIVILEGE (name) VALUES ('RENT_COMMENTING');
INSERT INTO PRIVILEGE (name) VALUES ('CAR_LOCATION_TOKEN');
INSERT INTO PRIVILEGE (name) VALUES ('RENT_REPORT_CREATING');

--AGENT specific privileges
INSERT INTO PRIVILEGE (name) VALUES ('CAR_USAGE_STATISTIC_VIEWING');
INSERT INTO PRIVILEGE (name) VALUES ('AGENT_PAID_REQUEST_CREATING');
INSERT INTO PRIVILEGE (name) VALUES ('PRICELIST_ITEM_DISCOUNT_CREATION');
INSERT INTO PRIVILEGE (name) VALUES ('DATA_SYNCHRONIZATION');

--ADMIN specific privileges
INSERT INTO PRIVILEGE (name) VALUES ('ENDUSER_PERMISION_CHANGING');
INSERT INTO PRIVILEGE (name) VALUES ('CAR_CODEBOOK_CRUD');
INSERT INTO PRIVILEGE (name) VALUES ('RENT_COMMENT_APPROVING');

--Privileges bound with ENDUSER role
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 1);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 2);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 3);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 4);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 5);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 6);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 7);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 8);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 9);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 10);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 11);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 12);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 13);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 14);

--Privileges bound with AGENT role
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 6);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 7);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 8);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 9);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 10);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 11);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 12);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 13);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 14);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 15);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 16);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 17);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 18);

--Privileges bound with ADMIN role
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 19);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 20);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 21);

----DATA----
INSERT INTO USER_TABLE (first_name, last_name, email, username, password) VALUES ('ENDUSER', 'ENDUSERIC', 'enduser@gmail.com', 'aske', '$2a$10$feiotRqclqYZ7aJnytCDSedhWmzztzhuwyujbVfVucV4nbmMRNnwa');

INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES ('1', '1');


