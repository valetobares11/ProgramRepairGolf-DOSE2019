CREATE TABLE IF NOT EXISTS test_challenges(
    id integer not null auto_increment primary key,
    challenge_id integer not null,
    test varchar(1000) not null,
    created_at DATETIME,
	updated_at DATETIME
);
