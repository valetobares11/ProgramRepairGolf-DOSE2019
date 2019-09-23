CREATE TABLE IF NOT EXISTS challenge_stats(
    id integer auto_increment primary key,
    challenge_id integer not null,
    created_at DATETIME,
	  updated_at DATETIME
);
