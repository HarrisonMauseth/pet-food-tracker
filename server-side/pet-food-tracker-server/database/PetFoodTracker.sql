START TRANSACTION;

DROP TABLE IF EXISTS tracker, pet, users CASCADE;

CREATE TABLE users (
    user_id SERIAL,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE pet (
    pet_id SERIAL,
    user_id int NOT NULL,
    pet_name varchar(50) NOT NULL,
    pet_nickname varchar(8) NULL,
    pet_type varchar(20) NOT NULL,
    pet_birthday date NULL,
    notes text NULL,
    CONSTRAINT PK_pet PRIMARY KEY (pet_id),
    CONSTRAINT FK_pet_users FOREIGN KEY (user_id) REFERENCES users
);

CREATE TABLE tracker (
    tracker_id SERIAL, 
    user_id int NOT NULL,
    pet_id int NOT NULL,
    time_fed timestamp NOT NULL DEFAULT NOW(),
    food_type varchar(255) NULL,
    portion_amount numeric(4, 3) NULL,
    portion_units varchar(20) NULL,
    notes text NULL,
    CONSTRAINT PK_tracker PRIMARY KEY (tracker_id),
    CONSTRAINT FK_tracker_users FOREIGN KEY (user_id) REFERENCES users,
    CONSTRAINT FK_tracker_pet FOREIGN KEY (pet_id) REFERENCES pet
);

COMMIT;