DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user
(
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    credit INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`username`)
);

DROP TABLE IF EXISTS app_user_book_rental_current;
CREATE TABLE app_user_book_rental_current
(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    book_id INT NOT NULL,
    rented_at DATETIME NOT NULL,
    rent_until DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS app_user_book_rental_history;
CREATE TABLE app_user_book_rental_history
(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    book_id INT NOT NULL,
    rented_at DATETIME NOT NULL,
    rent_until DATETIME NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    book_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
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
