CREATE TABLE IF NOT EXISTS passwords(
    id integer auto_increment primary key,
    salt varbinary(50) not null,
    username varchar(20) not null ,
    created_at DATETIME,
    updated_at DATETIME,
    unique(username)
);