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
    book_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_id`)
);

DROP TABLE IF EXISTS book_price;
CREATE TABLE book_price
(
    book_id INT NOT NULL AUTO_INCREMENT,
    rent_price VARCHAR(255) NOT NULL,
    buy_price VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_id`)
);

DROP TABLE IF EXISTS book_inventory;
CREATE TABLE book_inventory
(
    book_inventory_id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    book_inventory_state VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_id`)
);

DROP TABLE IF EXISTS book_inventory_rented;
CREATE TABLE book_inventory_rented
(
    book_inventory_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    return_due_at DATETIME NOT NULL,
    rented_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_id`)
);

DROP TABLE IF EXISTS book_inventory_rent_history;
CREATE TABLE book_inventory_rent_history
(
    book_inventory_rent_history_id INT NOT NULL AUTO_INCREMENT,
    book_inventory_id INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    return_due_at DATETIME NOT NULL,
    rented_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_rent_history_id`)
);

DROP TABLE IF EXISTS book_inventory_return_history;
CREATE TABLE book_inventory_return_history
(
    book_inventory_return_history_id INT NOT NULL AUTO_INCREMENT,
    book_inventory_id INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    returned_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_inventory_return_history_id`)
);

