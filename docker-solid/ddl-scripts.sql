DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user
(
    username VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`username`)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    book_id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_id`)
);

DROP TABLE IF EXISTS book_price;
CREATE TABLE book_price
(
    book_id BIGINT NOT NULL AUTO_INCREMENT,
    rent_price VARCHAR(255) NOT NULL,
    buy_price VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_id`)
);

DROP TABLE IF EXISTS book_inventory;
CREATE TABLE book_inventory
(
    book_inventory_id BIGINT NOT NULL AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    book_inventory_state VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_id`)
);

DROP TABLE IF EXISTS book_inventory_state_history;
CREATE TABLE book_inventory_state_history
(
    book_inventory_state_history_id BIGINT NOT NULL AUTO_INCREMENT,
    book_inventory_state VARCHAR(255) NOT NULL,
    action_type VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_state_history_id`)
);

