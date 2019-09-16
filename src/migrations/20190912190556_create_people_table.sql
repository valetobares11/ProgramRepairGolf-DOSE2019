CREATE TABLE people (
  id           INT(11) NOT NULL AUTO_INCREMENT,
  name         VARCHAR(128),
  surname      VARCHAR(128),
  created_at   DATETIME DEFAULT NULL,
  updated_at   DATETIME DEFAULT NULL,
  PRIMARY KEY (id)
);