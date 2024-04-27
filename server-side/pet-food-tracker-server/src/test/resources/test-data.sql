BEGIN TRANSACTION;

-- Users
INSERT INTO users (username,password_hash,role) VALUES ('user1','password1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','password2','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('user3','password3','ROLE_USER');


COMMIT TRANSACTION;