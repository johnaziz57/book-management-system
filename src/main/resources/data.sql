insert into PERSON (id, name, user_name, password, role, borrowed_books_count) values (0, 'user', 'user', '$2a$10$q7iaHFTewlQ9Bs9mmNyrwOUa7g7vWNDvW98ILie/OFc5olt1A5k1S', 0 , 0);
insert into PERSON (id, name, user_name, password, role, borrowed_books_count) values (1, 'admin', 'admin', '$2a$10$q7iaHFTewlQ9Bs9mmNyrwOUa7g7vWNDvW98ILie/OFc5olt1A5k1S', 1, 0 );
insert into BOOK (id, title, isbn, available_copies) values
(0, 'The Catcher in the Rye', '978-5-3343-5918-5', 25),
(1, 'To Kill a Mockingbird', '978-7-0133-2963-2', 20),
(2, '1984', '978-5-5783-5603-2', 30),
(3, 'Pride and Prejudice', '978-4-5799-9285-0', 15),
(4, 'The Great Gatsby', '978-5-8444-9484-0', 18),
(5, 'One Hundred Years of Solitude', '978-6-5697-9195-5', 22),
(6, 'The Hobbit', '978-3-6264-7333-5', 27),
(7, 'Harry Potter and the Sorcerer''s Stone', '978-2-4759-8495-5', 35),
(8, 'The Lord of the Rings', '978-6-6312-9769-9', 25),
(9, 'The Da Vinci Code', '978-9-1829-9094-1', 40);