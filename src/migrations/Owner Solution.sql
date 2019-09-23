create table if not exists Owner Solution (
	id integer auto_increment primary key,
	id_challenge integer,
	user_id integer,
	created_at DATETIME,
	updated_at DATETIME
	);
	
