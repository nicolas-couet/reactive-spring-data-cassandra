CREATE TABLE user (
  email text,
  password text,
  state int,
  creation_date timestamp,
  last_modification_date timestamp,
  PRIMARY KEY (email)
);