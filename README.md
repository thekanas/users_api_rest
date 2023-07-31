# Используемый стек
1. Java 17
2. Spring 6
3. Spring Data JPA 3
4. Hibernate 6
5. MySQL 8
6. TomCat 10.1.9
7. log4j 2
8. lombok
___

# Инструкция по запуску
При написании проекта использовалась IntelliJ IDEA, для проверки RESTFUL cервиса необходимо:    
* настроить конфигурацию Tomcat
* указать пароль от базы данных в файле users_api_rest/database/src/main/resources/application.properties
* создать и заполнить базу данных users_api_db с помощью файла users_api_rest/database/src/main/resources/createDB.sql
* запустить Tomcat
___

# Endpoints 
* Добавление пользователя осуществляется POST запросом на адрес ../api/users   
	Основные поля:
    * Фамилия (40 символов, только латинские буквы)   
    * Имя (20 символов, только латинские буквы)   
    * Отчество (40 символов, только латинские буквы)   
    * Email (50 символов, стандартный шаблон)   
    * Role (Administrator, Sale User, Customer User, Secure API User)   
    Например:    
~~~
{
    "lastName" : "lastName",
    "name" : "name",
    "patronymic" : "patronymic",
    "email" : "email@email.com",
    "role" : "ADMINISTRATOR"
}
~~~

* Получение всех пользователей осуществляется GET запросом на адрес ../api/users   
	Выводимые поля:
    * ФИО    
    * Email    
    * Role   
Записи выводятся по email в алфавитном порядке.   
По умолчанию выводиться не более 10 записей.

* Получение всех пользователей с указанием страницы и количества записей на странице осуществляется GET запросом на адрес ../api/users с ключами page и limit,
например ../api/users?page2&limit=1
___
