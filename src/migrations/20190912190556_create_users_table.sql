CREATE TABLE IF NOT EXISTS users(
    id integer auto_increment primary key,
    username varchar(20) not null ,
    password varchar(20) not null,
    email_address varchar(50) not null,
    admin BOOLEAN not null default 0,
    active_account  BOOLEAN not null default 1,
    created_at DATETIME,
    updated_at DATETIME,
    unique(username),
    unique(email_address)
);
