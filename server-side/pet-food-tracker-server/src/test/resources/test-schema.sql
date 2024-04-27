START TRANSACTION;

DROP TABLE IF EXISTS item_charge_condition, item_rarity, charge_condition, item, rarity, category, users CASCADE;

CREATE TABLE users (
    user_id SERIAL,
    username varchar(50) NOT NULL UNIQUE,
    password_hash varchar(200) NOT NULL,
    role varchar(50) NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

COMMIT TRANSACTION;