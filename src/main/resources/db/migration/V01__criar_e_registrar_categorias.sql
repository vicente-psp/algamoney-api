CREATE TABLE categoria (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (name) values ("Lazer");
INSERT INTO categoria (name) values ("Alimentação");
INSERT INTO categoria (name) values ("Supermercado");
INSERT INTO categoria (name) values ("Farmácia");
INSERT INTO categoria (name) values ("Outros");