CREATE TABLE IF NOT EXISTS users(
    id integer auto_increment primary key,
    username varchar(20) not null ,
    password varchar(20) not null,
    created_at DATETIME,
	updated_at DATETIME
);