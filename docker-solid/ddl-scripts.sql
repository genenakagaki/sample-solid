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

DROP TABLE IF EXISTS book_copy;
CREATE TABLE book_copy
(
    book_copy_id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    book_copy_state VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`book_copy_id`)
);

DROP TABLE IF EXISTS book_copy_rental_history;
CREATE TABLE book_copy_rental_history
(
    book_copy_id INT NOT NULL,
    rented_at DATETIME NOT NULL,
    username VARCHAR(255) NOT NULL,
    return_due_at DATETIME NOT NULL,
    returned_at DATETIME NOT NULL,
    PRIMARY KEY (`book_copy_id`, `rented_at`)
);

