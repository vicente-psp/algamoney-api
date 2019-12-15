CREATE TABLE tb_categoria (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_categoria (nome) values ("Lazer");
INSERT INTO tb_categoria (nome) values ("Alimentação");
INSERT INTO tb_categoria (nome) values ("Supermercado");
INSERT INTO tb_categoria (nome) values ("Farmácia");
INSERT INTO tb_categoria (nome) values ("Outros");