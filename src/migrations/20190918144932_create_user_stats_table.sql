CREATE TABLE IF NOT EXISTS user_stats(
	id INTEGER auto_increment primary key,
	user_id INTEGER FOREIGN KEY REFERENCES users (id),
	created_challenges INTEGER NOT NULL,
	solved_challenges INTEGER NOT NULL,
	current_points INTEGER NOT NULL,
	created_at DATETIME,
	updated_at DATETIME
);
