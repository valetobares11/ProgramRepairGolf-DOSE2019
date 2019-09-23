CREATE TABLE IF NOT EXISTS propositions(
    id integer auto_increment primary key,
    user_id integer not null,
    challenge_id integer not null,
    source text,
    foreign key (user_id) references users(id)
    /*foreign key (challenge_id) references challenges(id)*/    
);

