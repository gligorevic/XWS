-- INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, CREATION_DATE, TEXT, USERNAME) VALUES (0, 1, '2020-06-20 00:00:00', 'Sve u redu','pera@gmail.com');
-- INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME) VALUES (0, 2, 'Nista nije u redu', 'pera@gmail.com');
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME, AGENT_USERNAME, IN_BUNDLE) VALUES (0, 3, 'Alo bre', 'pera@gmail.com', 'enduser@gmail.com', false );
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME, AGENT_USERNAME, IN_BUNDLE) VALUES (1, 1, 'Fina kolica! Sve naj!', 'pera@gmail.com', 'enduser@gmail.com', false );
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME, AGENT_USERNAME, IN_BUNDLE) VALUES (1, 2, 'Krs, nema ni neonke, pff', 'pera@gmail.com', 'enduser@gmail.com', false );
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME, AGENT_USERNAME, IN_BUNDLE) VALUES (1, 4, 'Ide kao avion', 'pera@gmail.com', 'enduser@gmail.com', false );
INSERT INTO Comment(COMMENT_STATUS, REQUEST_ID, TEXT, USERNAME, AGENT_USERNAME, IN_BUNDLE) VALUES (1, 5, 'Strava java', 'pera@gmail.com', 'enduser@gmail.com', false );


--
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('enduser@gmail.com', 1, 1, 'pera@gmail.com', false);
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('enduser@gmail.com', 5, 1, 'pera@gmail.com');
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('pera@gmail.com', 1, 1, 'pera123@gmail.com');
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('pera@gmail.com', 5, 1, 'pera123@gmail.com');
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('pera@gmail.com', 4, 1, 'pera123@gmail.com');
-- INSERT INTO Grade(AGENT_USERNAME, GRADE, REQUEST_ID, USERNAME, IN_BUNDLE) VALUES ('pera123@gmail.com', 4, 1, 'pera@gmail.com');