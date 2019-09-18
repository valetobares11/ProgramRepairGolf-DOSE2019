CREATE TABLE IF NOT EXISTS comments(
    id integer auto_increment primary key,
    challenge_id integer not null,
    user_id integer not null,
    title varchar(50) not null ,
    description varchar(300) not null,
    constraint ci foreign key (challenge_id) references challenges(id),
    constraint ui foreign key (user_id) references users(id),
    created_at DATETIME,
    updated_at DATETIME
);
