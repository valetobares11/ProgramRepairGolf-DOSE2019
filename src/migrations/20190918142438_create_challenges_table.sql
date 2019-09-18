CREATE TABLE IF NOT EXISTS challenges(
    id integer auto_increment primary key,
    title varchar (50),
    description varchar(50),
    source varchar(10000),
    point integer,
    owner_id integer,
    owner_solution_id integer,
    created_at DATETIME,
	updated_at DATETIME
);