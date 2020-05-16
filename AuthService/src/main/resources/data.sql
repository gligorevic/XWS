INSERT INTO USER_TABLE (first_name, last_name, email, username, password) VALUES ('Kristina', 'Neshkic', 'kristi@hotmail.com', 'kristi', '$2a$10$feiotRqclqYZ7aJnytCDSedhWmzztzhuwyujbVfVucV4nbmMRNnwa');

INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES ('1', '1');

INSERT INTO PRIVILEGE (name) VALUES ('mnogo mocan');

INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 1);