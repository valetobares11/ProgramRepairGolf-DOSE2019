CREATE TABLE users (
  id           INT(11) NOT NULL AUTO_INCREMENT,
  username     VARCHAR(128),
  password     VARCHAR(128),
  created_at   DATETIME DEFAULT NULL,
  updated_at   DATETIME DEFAULT NULL,
  PRIMARY KEY (id)
);