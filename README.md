![springify](https://raw.githubusercontent.com/drumaddict/springify/master/src/main/resources/public/springify.png)

[![Build Status](https://travis-ci.org/kabasakalis/springify.svg?branch=master)](https://travis-ci.org/kabasakalis/springify)    [![codecov](https://codecov.io/gh/kabasakalis/springify/branch/master/graph/badge.svg)](https://codecov.io/gh/kabasakalis/springify)

# Overview
**Springify** is a  custom Spring Boot implementation of `spring-data-rest` that exposes a discoverable, navigable REST API of a simple music streaming service mockup domain model using JSON [HAL](http://stateless.co/hal_specification.html) as media type.
The [Spring Data REST](http://projects.spring.io/spring-data-rest/) framework analyzes an application’s domain model and exposes hypermedia-driven HTTP resources for aggregates contained in the model using reasonable defaults for endpoints and marshalling, leaving only  minimal implementation code for the developer. However, sometimes there's a need for a more custom approach which is showcased in this demo.
#### [Online interactive Swagger documentation on Heroku](https://springify.herokuapp.com/api/docs/v1/index.html#!/Artists/getArtistById)

|   UML diagram| UML Relations  |
|---|---|
|![uml](https://raw.githubusercontent.com/drumaddict/springify/master/src/main/resources/public/uml.png)|![uml](https://raw.githubusercontent.com/drumaddict/springify/master/src/main/resources/public/uml2.png)	|


# Features
 * _Controller Generics and Inheritance for reducing boilerplate code for common CRUD or association related endpoints._ Controllers inherit from `AbstractBaseRestController` which provides some core endpoints - they only have to implement endpoints specific to the domain model they expose.
* _Extensions of `ResourceSupport`_ for separation of concerns between the domain model and the JSON marshalled response to   avoid littering the model with JSON related annotations like `@JsonIgnore`  or methods that exist solely for the purpose of returning extra data in addition to model properties.
* _Custom Resource Assemblers_  for the purpose of customizing/extending the response links without littering the controllers.
* _Domain level exceptions_ (`EntityNotFoundException`, `AssociationNotFoundException`) to enrich the exception responses with additional information using domain-specific semantics.
* _Custom Exception to `HttpStatus` code mappings_ . Reasonable defaults are used for all generic exceptions with the ability to override them or provide additional mappings for domain-specific exceptions.
* _Global exception handling with [Aspect Oriented Programming](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop)_ using `@ControllerAdvice` annotation.
* Authentication with [JWT token.](https://jwt.io/)
* Simple Role base authorization.
* Java 8 Optional API.
* Custom modular Gradle setup for profile support.
* _[PostgreSQL 9.4 database.](https://www.postgresql.org/)_
* [Liquibase](http://www.liquibase.org/) for database version control.
* [Postman]( https://www.getpostman.com/) endpoints collection for development convenience.
*  _[Online interactive Swagger documentation on Heroku.](https://springify.herokuapp.com/api/docs/v1/index.html#!/Artists/getArtistById)_
* _[Karate, BDD style testing framework.](https://github.com/intuit/karate)_
* _[Gradle Build Tool.](https://gradle.org/)_ Property driven profiles.
* _[Travis CI](https://travis-ci.com/)_
* _Code Coverage with [JaCoCo](http://www.jacoco.org/jacoco/) and [CodeCov.](https://codecov.io)_

# Gradle build setup
Gradle does not come with built in profile support, so a custom profile set up was essential to handle different environments.
* **_dev_**, **Development Profile**.     Will load development postgreSQL and Liquibase settings from `src/main/resources/application-dev.properties` file.  Use `gradle -Pdev bootRun` command to start the application in development mode. `bootRun` is a task provided by [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html) and runs a project in place without building a jar.

* **_deb_**, **Debug Profile**.  Use `gradle -Pdeb bootRun` for debugging. This will suspend the application listening at port 5005. Console should log `Listening for transport dt_socket at address: 5005`. A client process such as an IDE dubugger can attach to this address and start a debug session. The debug configuration for an IntellijIDEA session is shown in the screenshot below.
![debug](https://raw.githubusercontent.com/drumaddict/springify/master/src/main/resources/public/deb.png)

* **_prod_**, **Development Profile**.  Will load production (heroku) postgreSQL and Liquibase settings, (as environmental variables) from `src/main/resources/application-prod.properties` file.  `prod` property is used in `gradle -Pprod migrate update` to update the production database with new migrations. In production `gradle -Pprod bootRun` is not used, since `bootRun` is for development only, the build jar is executed instead. See `Procfile` for details.

* **_tests_**, **Test Profile**.  Will load test postgreSQL and Liquibase settings, from `src/main/resources/application-tests.properties` file.  Run tests with `gradle -Ptests -Padminpwd=[PLAIN_TEXT_PWD] test`. `adminpwd` property is explained in test setup section later.

* **_travis_**, **Travis CI Profile**.  Will load test postgreSQL and Liquibase settings, from `src/main/resources/application-travis.properties` file. Used in `gradle -Ptravis -Padminpwd=$ADMINPWD check` to build the project and run the tests on travis CI server. See `.travis.yml` file for details. `adminpwd` property is explained in test setup section later.

# Local development and tests setup
`springify` and `spingify-test` databases are used for development and test respectively. Create these local databases
, update the related `.properties` files with database information and then run `gradle -Pdev migrate update` and `gradle -Ptests migrate update`  to create the tables and populate the databases with static data from `src/main/resources/db/migrations/060_populate_tables.sql`. `migrate` is a convenient custom gradle task that encapsulates a rather verbose liquibase command that incrementally updates the database schema. You can drop all the tables anytime with `gradle -Pdev migrate dropall` and `gradle -Ptests migrate dropall`.
### Defining the admin password.
 There are by default 4 users: 1 admin, 2 moderators and a user. The admin password in plain text is needed to run the tests. It is passed as an external gradle property and is not hardcoded anywhere in plain text. Follow these simple steps to define the admin password.
 * Comment out the admin specific data migrations in `060_populate_tables.sql` file:
  ```
 insert into users (id, username, email, password) values (1, 'admin', 'admin@springify.com', '$2a$10$efzxz2vlIbHOA4bmae41aOKlS97sOhtzagxLLxemVyYagM9vTIv5m');
```
```
insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (1, 2);
insert into users_roles (user_id, role_id) values (1, 3);
```
 * Drop all tables if any with `gradle -Pdev migrate dropAll`, and  set up the development and test databases ( running `gradle -Pdev migrate update` and `gradle -Ptests migrate update`). Start the application in dev mode with `gradle -Pdev bootRun`.
 * Signup (see Postman endpoint) with these credentials, where you provide your own admin password.
```
{"username": "admin",
"password": "[PLAIN_TEXT_ADMIN_PWD]",
"passwordConfirm": "[PLAIN_TEXT_ADMIN_PWD]",
"email" : "admin@springify.com"
}
```
* Retrieve the encoded password for the newly signed up admin from the database, `[ENCODED_ADMIN_PWD]`
* Comment in the admin specific lines you commented out in `060_populate_tables.sql`, replacing the encoded password:
`insert into users (id, username, email, password) values (1, 'admin', 'admin@springify.com', '[ENCODED_ADMIN_PWD]');`
* Drop the tables and create them again (using the new edited version of `060_populate_tables.sql`).
* Test logging in as an admin using the `login` endpoint, again you can use the Postman template for your request.
The request body should be
```
{ "username" : "admin",
"password" : "[PLAIN_TEXT_ADMIN_PWD]"
}
```
You should get the admin user and his token as a response. Edit the Postman Springify collection to use `Bearer Token` Authorization and paste the token you got from the response in the Token field. Use `Inherit from parent` for all your endpoint configurations in Postman to use this token in your requests.

* At this point you can run the tests with `gradle -Ptests -Padminpwd=[PLAIN_TEXT_ADMIN_PWD] test`
* You can run individual `*Runner.java` (example `/src/test/java/springifyapi/album/AlbumRunner.java`) test files from your IDE.
* You can now make requests from Postman to all endpoints, using the token you obtained from login.

# [Swagger online interactive documentation](https://springify.herokuapp.com/api/docs/v1/index.html#!/Artists/getArtistById)
Follow the online instructions to try the API.
