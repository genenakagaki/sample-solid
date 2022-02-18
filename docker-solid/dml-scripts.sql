INSERT INTO sample.app_user (username, role, created_at) VALUES ('g-nakagaki', 'ADMIN', '2022-02-18 10:30:14');
INSERT INTO sample.app_user (username, role, created_at) VALUES ('k-nishizawa', 'USER', '2022-02-18 10:30:21');

INSERT INTO sample.book (book_id, title, created_at) VALUES (1, 'A Book', '2022-02-18 10:55:30');
INSERT INTO sample.book (book_id, title, created_at) VALUES (2, 'A Book: 2nd Edition', '2022-02-18 10:55:38');

INSERT INTO sample.book_inventory (book_inventory_id, book_id, book_inventory_state, created_at) VALUES (1, 1, 'AVAILABLE', '2022-02-18 10:56:03');
INSERT INTO sample.book_inventory (book_inventory_id, book_id, book_inventory_state, created_at) VALUES (2, 1, 'AVAILABLE', '2022-02-18 10:56:15');
INSERT INTO sample.book_inventory (book_inventory_id, book_id, book_inventory_state, created_at) VALUES (3, 2, 'AVAILABLE', '2022-02-18 10:56:15');

