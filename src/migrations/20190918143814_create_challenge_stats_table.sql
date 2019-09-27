CREATE TABLE IF NOT EXISTS challenge_stats(
    id INTEGER auto_increment primary key,
    challenge_id INTEGER not null,
    solved_count INTEGER,
    average_score INTEGER,
    created_at DATETIME,
	updated_at DATETIME
);



