-- INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, CREATION_DATE, TEXT, USERNAME) VALUES (0, 1, '2020-06-20 00:00:00', 'Sve u redu','pera@gmail.com');
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME) VALUES (0, 2, 'Nista nije u redu', 'pera@gmail.com');
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME) VALUES (0, 3, 'Alo bre', 'pera@gmail.com');

INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('enduser@gmail.com', 1, 1, 'pera@gmail.com');
INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('enduser@gmail.com', 5, 1, 'pera@gmail.com');
INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('pera@gmail.com', 1, 1, 'pera123@gmail.com');
INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('pera@gmail.com', 5, 1, 'pera123@gmail.com');
INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('pera@gmail.com', 4, 1, 'pera123@gmail.com');
INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME) VALUES ('pera123@gmail.com', 4, 1, 'pera@gmail.com');