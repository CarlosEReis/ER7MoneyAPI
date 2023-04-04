CREATE TABLE pessoa (
	codigo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    logradouro VARCHAR(30),
    numero VARCHAR(30),
    complemento VARCHAR(30),
    bairro VARCHAR(30),
    cep VARCHAR(9),
    cidade VARCHAR(30),
    estado VARCHAR(30),
    ativo BOOLEAN NOT NULL
) ENGINE= InnoDB;

INSERT INTO pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values 
('João Silva', 'Rua do Abacaxi', '10', null, 'Brasil', '38400-121', 'Uberlândia', 'MG', true),
('Maria Rita', 'Rua do Sabiá', '110', 'Apto 101', 'Colina', '11400-121', 'Ribeirão Preto', 'SP', true),
('Pedro Santos', 'Rua da Bateria', '23', null, 'Morumbi', '54212-121', 'Goiânia', 'GO', true),
('Ricardo Pereira', 'Rua do Motorista', '123', 'Apto 302', 'Aparecida', '38400-121', 'Salvador', 'BA', true),
('Josué Mariano', 'Av Rio Branco', '321', null, 'Jardins', '56400-121', 'Natal', 'RN', true),
('Pedro Barbosa', 'Av Brasil', '100', null, 'Tubalina', '77400-121', 'Porto Alegre', 'RS', true),
('Henrique Medeiros', 'Rua do Sapo', '1120', 'Apto 201', 'Centro', '12400-121', 'Rio de Janeiro', 'RJ', true),
('Carlos Santana', 'Rua da Manga', '433', null, 'Centro', '31400-121', 'Belo Horizonte', 'MG', true),
('Leonardo Oliveira', 'Rua do Músico', '566', null, 'Segismundo Pereira', '38400-001', 'Uberlândia', 'MG', true),
('Isabela Martins', 'Rua da Terra', '1233', 'Apto 10', 'Vigilato', '99400-121', 'Manaus', 'AM', true);
