CREATE TABLE IF NOT EXISTS user_stats(
	id INTEGER auto_increment primary key,
	user_id INTEGER UNIQUE,
	created_challenges INTEGER NOT NULL,
	solved_challenges INTEGER NOT NULL,
	current_points INTEGER NOT NULL,
	created_at DATETIME,
	updated_at DATETIME,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
