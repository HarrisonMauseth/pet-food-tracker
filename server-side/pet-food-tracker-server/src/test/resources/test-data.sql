BEGIN TRANSACTION;

-- Users
INSERT INTO users (username,password_hash,role) VALUES ('user1','password1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','password2','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('user3','password3','ROLE_USER');

INSERT INTO pet (user_id, pet_name, pet_nickname, pet_type, pet_birthday, notes)
    VALUES (1, 'name1', 'short1', 'type1', '2000-01-01', 'notes1');
INSERT INTO pet (user_id, pet_name, pet_nickname, pet_type, pet_birthday, notes)
    VALUES (2, 'name2', 'short2', 'type2', '2000-02-02', 'notes2');
INSERT INTO pet (user_id, pet_name, pet_nickname, pet_type, pet_birthday, notes)
    VALUES (3, 'name3', 'short3', 'type3', '2000-03-03', 'notes3');

INSERT INTO tracker (user_id, pet_id, time_fed, food_type, portion_amount, portion_units, notes)
    VALUES (1, 1, '2000-01-01 01:00:00', 'food1', 1, 'unit1', 'notes1');
INSERT INTO tracker (user_id, pet_id, time_fed, food_type, portion_amount, portion_units, notes)
    VALUES (2, 2, '2000-02-02 02:00:00', 'food2', 2, 'unit2', 'notes2');
INSERT INTO tracker (user_id, pet_id, time_fed, food_type, portion_amount, portion_units, notes)
    VALUES (3, 3, '2000-03-03 03:00:00', 'food3', 3, 'unit3', 'notes3');

COMMIT TRANSACTION;