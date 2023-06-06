CREATE TABLE estado (
	codigo INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

INSERT INTO estado (codigo, nome) VALUES(1, 'AC');
INSERT INTO estado (codigo, nome) VALUES(2, 'AL');
INSERT INTO estado (codigo, nome) VALUES(3, 'AM');
INSERT INTO estado (codigo, nome) VALUES(4, 'AP');
INSERT INTO estado (codigo, nome) VALUES(5, 'BA');
INSERT INTO estado (codigo, nome) VALUES(6, 'CE');
INSERT INTO estado (codigo, nome) VALUES(7, 'DF');
INSERT INTO estado (codigo, nome) VALUES(8, 'ES');
INSERT INTO estado (codigo, nome) VALUES(9, 'GO');
INSERT INTO estado (codigo, nome) VALUES(10, 'MA');
INSERT INTO estado (codigo, nome) VALUES(11, 'MG');
INSERT INTO estado (codigo, nome) VALUES(12, 'MS');
INSERT INTO estado (codigo, nome) VALUES(13, 'MT');
INSERT INTO estado (codigo, nome) VALUES(14, 'PA');
INSERT INTO estado (codigo, nome) VALUES(15, 'PB');
INSERT INTO estado (codigo, nome) VALUES(16, 'PE');
INSERT INTO estado (codigo, nome) VALUES(17, 'PI');
INSERT INTO estado (codigo, nome) VALUES(18, 'RJ');
INSERT INTO estado (codigo, nome) VALUES(19, 'RJ');
INSERT INTO estado (codigo, nome) VALUES(20, 'RN');
INSERT INTO estado (codigo, nome) VALUES(21, 'RO');
INSERT INTO estado (codigo, nome) VALUES(22, 'RR');
INSERT INTO estado (codigo, nome) VALUES(23, 'RS');
INSERT INTO estado (codigo, nome) VALUES(24, 'SC');
INSERT INTO estado (codigo, nome) VALUES(25, 'SE');
INSERT INTO estado (codigo, nome) VALUES(26, 'SP');
INSERT INTO estado (codigo, nome) VALUES(27, 'TO');



CREATE TABLE cidade (
	codigo INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
  codigo_estado INT NOT NULL,
  FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
) ENGINE=InnoDB;

INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (1, 'Belo Horizonte', 11);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (2, 'Uberlândia', 11);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (3, 'Uberaba', 11);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (4, 'São Paulo', 26);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (5, 'Campinas', 26);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (6, 'Rio de Janeiro', 19);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (7, 'Angra dos Reis', 19);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (8, 'Goiânia', 9);
INSERT INTO cidade (codigo, nome, codigo_estado) VALUES (9, 'Caldas Novas', 9);



ALTER TABLE pessoa DROP COLUMN cidade;
ALTER TABLE pessoa DROP COLUMN estado;
ALTER TABLE pessoa ADD COLUMN codigo_cidade INT;
ALTER TABLE pessoa ADD CONSTRAINT fk_pessoa_cidade FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo);

UPDATE pessoa SET codigo_cidade = 2;