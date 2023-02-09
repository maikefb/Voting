CREATE TABLE voting(
  	id bigint unsigned NOT NULL AUTO_INCREMENT,
  	user_id BIGINT UNSIGNED NOT NULL,
  	voting_agenda_id BIGINT UNSIGNED NOT NULL,
  	was_approved	BOOLEAN NOT NULL DEFAULT FALSE,
  	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	PRIMARY KEY (id),
  	foreign key (user_id) references user(id),
  	foreign key (voting_agenda_id) references voting_agenda(id)
  )