CREATE TABLE IF NOT EXISTS compilation_challenges(
    id integer not null auto_increment primary key,
    challenge_id integer not null,
    created_at DATETIME,
	updated_at DATETIME
);