CREATE TABLE IF NOT EXISTS challenge_stats(
    id INTEGER auto_increment PRIMARY KEY,
    challenge_id INTEGER NOT NULL UNIQUE,
    solved_count INTEGER,
    average_score FLOAT,
    created_at DATETIME,
	updated_at DATETIME
);



