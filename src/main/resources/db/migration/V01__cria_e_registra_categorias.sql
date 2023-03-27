CREATE TABLE categoria (
	codigo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

INSERT INTO categoria (nome) VALUES 
('Lazer'),
('Alimentação'),
('Supermercado'),
('Farmácia'),
('Outros');