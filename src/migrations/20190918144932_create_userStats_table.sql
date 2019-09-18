CREATE TABLE IF NOT EXISTS userStats(
    id INTEGER auto_increment primary key,
    user_id INTEGER not null,
    created_challenges INTEGER,
    solved_challenges INTEGER,
    current_points INTEGER,
    created_at DATETIME,
	updated_at DATETIME
)ENGINE=InnoDB;