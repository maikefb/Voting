CREATE TABLE voting_agenda(
	id	BIGINT unsigned NOT NULL AUTO_INCREMENT,
	title VARCHAR(50) NOT NULL,
	description	VARCHAR(255) NOT NULL,
	was_voted	BOOLEAN NOT NULL DEFAULT FALSE,
	was_approved	BOOLEAN NOT NULL DEFAULT FALSE,
	user_id BIGINT UNSIGNED NOT NULL,
	created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	foreign key (user_id) references user(id)
)