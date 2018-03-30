--DROP TABLE IF EXISTS phone_book;
--DROP TABLE IF EXISTS user_entity;
--
--
--CREATE TABLE user_entity (
--	user_id VARCHAR(255) NOT NULL,
--	full_name VARCHAR(255) NOT NULL, 
--	user_name VARCHAR(255) NOT NULL, 
--	PRIMARY KEY (user_id)
--)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--CREATE TABLE phone_book(
--	id INT NOT NULL AUTO_INCREMENT,
--	last_name VARCHAR(255) NOT NULL, 
--	first_name VARCHAR(255) NOT NULL, 
--	patronymic_name VARCHAR(255) NOT NULL,
--	address VARCHAR(255), 
--	email VARCHAR(255), 
--	home_number VARCHAR(255), 
--	mobile_number VARCHAR(255),  
--	user_id VARCHAR(255), 
----	INDEX usr_ind (user_id),
--	PRIMARY KEY (id)
--)ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--
--ALTER TABLE phone_book ADD FOREIGN KEY(user_id) REFERENCES user_entity(user_id) ON DELETE CASCADE ON UPDATE CASCADE;
--
--








