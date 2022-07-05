CREATE TABLE users (
 email varchar(100) NOT NULL,
 pword varchar(100) DEFAULT NULL,
 firstName varchar(100) DEFAULT NULL,
 lastName varchar(100) DEFAULT NULL,
 PRIMARY KEY (email)
);