DROP DATABASE IF EXISTS users_api_db;

CREATE DATABASE users_api_db;

CREATE TABLE users
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    lastname   VARCHAR(40),
    name       VARCHAR(20),
    patronymic VARCHAR(40),
    email      VARCHAR(50) UNIQUE NOT NULL,
    role       VARCHAR(20) NOT NULL
);


INSERT INTO users (lastname, name, patronymic, email, role)
VALUES ('Kalashnikov', 'Appolon', 'Romanovich', 'romanovich@gmail.com', 'CUSTOMER_USER'),
       ('Sharova', 'Taisiya', 'Maksimovna', 'tai@yandex.ru', 'SALE_USER'),
       ('Kusmin', 'Yan', 'Stepanovich', 'yan@mail.ru', 'ADMINISTRATOR');
