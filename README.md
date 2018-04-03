# Phone-Book Application

## You can specify either file-storage or rdb-storage in the application.properties.

There are two created accounts for rdb-storage. 
First one with the login test123 and password corresponding.
And second with the login ivanov and password corresponding.

Tables created for mysql.


CREATE DATABASE IF NOT EXISTS phonebook CHARACTER SET utf8 COLLATE utf8_general_ci;
USE phonebook;
DROP TABLE IF EXISTS phone_book;
DROP TABLE IF EXISTS account;

CREATE TABLE account (
	acc_id VARCHAR(255) NOT NULL,
	user_name VARCHAR(255) NOT NULL, 
	full_name VARCHAR(255) NOT NULL, 
    password VARCHAR(255) NOT NULL,
	PRIMARY KEY (acc_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE phone_book_entry(
	id INT NOT NULL AUTO_INCREMENT,
	last_name VARCHAR(255) NOT NULL, 
	first_name VARCHAR(255) NOT NULL, 
	patronymic_name VARCHAR(255) NOT NULL,
	mobile_number VARCHAR(255),  
	home_number VARCHAR(255), 
	address VARCHAR(255), 
	email VARCHAR(255), 
	acc_id VARCHAR(255), 
	INDEX acc_ind (acc_id),
	primary key (id),
	FOREIGN KEY (acc_id)
        REFERENCES account(acc_id)
        ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO account(acc_id, user_name, full_name, password) VALUES
('c194ec11-81c8-4dd5-91f0-3ffc6c0fe097', 'test123', 'TestTestovi4', '$2a$10$357P2X5YK2Ib3QVl5oxJ1eJ7yxy5by74UtbnbgvFaqM2T1OskXUpi'),
('ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf', 'ivanov', 'Иванов Иван Иванович', '$2a$10$G0V0jaanQSiIaY2j9o4yCennH3Y0NXHyMQ0zXhVvjFuFakwyqXEHW');


INSERT INTO phone_book_entry(id, last_name, first_name, patronymic_name, mobile_number, home_number, address, email, acc_id) VALUES
(1, 'Медведева', 'Светлана', 'Андреевна', '0951458112','', '', 'medved@gmail.com', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(2, 'Орловская', 'Галина', 'Викторовна', '+380995781199', '', 'г. Москва, ул. Горького', 'OrelVictory@mail.ru', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(3, 'Соколов', 'Александр', 'Вячеславович', '063458-58-55', '0445442121', 'Киев, ул. Садовая 1', 'sokol@ukr.net', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(4, 'Червяковский', 'Сергей', 'Сергеевич', '0(63)411-55-55', '', '', '4erv@yahoo.com', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(5, 'Яковлева', 'Татьяна', 'Андреевна', '0(63)4114567', '', 'Костромская обл. Кострома', 'yakovleva@yandex.ru', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(6, 'Медведева', 'Светлана', 'Андреевна', '0951458112','', '', 'medved@gmail.com', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(7, 'Орловская', 'Галина', 'Викторовна', '+380995781199', '', 'г. Москва, ул. Горького', 'OrelVictory@mail.ru', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(8, 'Соколов', 'Александр', 'Вячеславович', '063458-58-55', '0445442121', 'Киев, ул. Садовая 1', 'sokol@ukr.net', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(9, 'Червяковский', 'Сергей', 'Сергеевич', '0(63)411-55-55', '', '', '4erv@yahoo.com', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(10, 'Яковлева', 'Татьяна', 'Андреевна', '0(63)4114567', '', 'Костромская обл. Кострома', 'yakovleva@yandex.ru', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(11, 'Панченко', 'Евгений', 'Валерьевич', '+380(63)4421111', '', 'Свердловская обл. г. Екатеринбург', '', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(12, 'Исаков', 'Владимир', 'Вячеславович', '+380(73)758-11-23', '', '', '', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(13, 'Попова', 'Ольга', 'Константиновна', '0971458999', '044-583-11-23', 'Киев, Гоголевска 25', 'popova@bigmir.net', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(14, 'Ходаковский', 'Вадим', 'Валерьевич', '0501114499', '', '', '', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(15, 'Kolesnikov', 'Dmitrii', 'Olegovic', '0(63)4124566', '0525832223', 'харьков ул. сумская 35', 'koleso@live.ru', 'c194ec11-81c8-4dd5-91f0-3ffc6c0fe097'),
(16, 'Панченко', 'Евгений', 'Валерьевич', '+380(63)4421111', '', 'Свердловская обл. г. Екатеринбург', '', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(17, 'Исаков', 'Владимир', 'Вячеславович', '+380(73)758-11-23', '', '', '', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(18, 'Попова', 'Ольга', 'Константиновна', '0971458999', '044-583-11-23', 'Киев, Гоголевска 25', 'popova@bigmir.net', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(19, 'Ходаковский', 'Вадим', 'Валерьевич', '0501114499', '', '', '', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf'),
(20, 'Kolesnikov', 'Dmitrii', 'Olegovic', '0(63)4124566', '0525832223', 'харьков ул. сумская 35', 'koleso@live.ru', 'ed582f8f-9e70-4fb3-a1b6-d596dd6b4dcf');
