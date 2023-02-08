CREATE TABLE user (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  cpf_cnpj varchar(18) NOT NULL,
  person_name varchar(255) NOT NULL,
  birth_date date DEFAULT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE (cpf_cnpj)
  )