CREATE TABLE tb_usuario (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_permissao (
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_usuario_permissao (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES tb_permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO tb_usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO tb_usuario (id, nome, email, senha) values (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO tb_permissao (id, descricao) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO tb_permissao (id, descricao) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO tb_permissao (id, descricao) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO tb_permissao (id, descricao) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO tb_permissao (id, descricao) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO tb_permissao (id, descricao) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO tb_permissao (id, descricao) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 3);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 6);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (1, 8);

-- maria
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (2, 2);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (2, 5);
INSERT INTO tb_usuario_permissao (id_usuario, id_permissao) values (2, 8);