CREATE TABLE IF NOT EXISTS challenge_stats(
    id INTEGER auto_increment primary key,
    challenge_id INTEGER not null,
    created_at DATETIME,
	updated_at DATETIME
);



