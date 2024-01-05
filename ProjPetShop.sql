CREATE TABLE IF NOT EXISTS Clientes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    endereco VARCHAR(100),
    telefone VARCHAR (11),
    email VARCHAR(100) UNIQUE,
	senha VARCHAR(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS Gatos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    idade INT,
    cor VARCHAR(50),              
    peso DECIMAL(10, 2),
	tipo_pelo VARCHAR(50),
    historico_saude TEXT,  
    cliente_cpf VARCHAR(11) REFERENCES Clientes(cpf) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Cachorros (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    idade INT,
    cor VARCHAR(50),
	peso DECIMAL(10, 2),
	raca VARCHAR(50),              
    porte VARCHAR(50),
    historico_saude TEXT,     
    cliente_cpf VARCHAR(11) REFERENCES Clientes(cpf) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Servicos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);


CREATE TABLE IF NOT EXISTS Agendamentos (
    id SERIAL PRIMARY KEY,
    data VARCHAR(10) NOT NULL,
	hora VARCHAR(5) NOT NULL,
    cliente_cpf VARCHAR(11) REFERENCES Clientes(cpf) ON UPDATE CASCADE ON DELETE CASCADE,
    gato_id INT REFERENCES Gatos(id) ON UPDATE CASCADE ON DELETE CASCADE,
    cachorro_id INT REFERENCES Cachorros(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Servicos_agendados (
    agendamento_id INT REFERENCES Agendamentos(id) ON UPDATE CASCADE ON DELETE CASCADE,
    servico_id INT REFERENCES Servicos(id) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (agendamento_id, servico_id)
);

CREATE TABLE IF NOT EXISTS Pagamentos (
    id SERIAL PRIMARY KEY,
    valor_total DECIMAL(10, 2) NOT NULL,
    metodo_pagamento VARCHAR(50) NOT NULL,
    agendamento_id INT REFERENCES Agendamentos(id) ON UPDATE CASCADE ON DELETE CASCADE
);



INSERT INTO Clientes (cpf, nome, endereco, telefone, email, senha)
VALUES 
    ('12345678901', 'João Silva', 'Rua A, 123', '1122334455', 'joao.silva@email.com', '123654'),
    ('98765432101', 'Maria Oliveira', 'Avenida B, 456', '5544332211', 'maria.oliveira@email.com', '000');


INSERT INTO Gatos (nome, idade, cor, peso, tipo_pelo, historico_saude, cliente_cpf)
VALUES
    ('Garfield', 3, 'Laranja', 5.0, 'Curto', 'Sem problemas de saúde conhecidos', '12345678901'),
    ('Whiskers', 2, 'Branco e Preto', 4.5, 'Longo', 'Vacinas em dia', '98765432101');


INSERT INTO Cachorros (nome, idade, cor, peso, raca, porte, historico_saude, cliente_cpf)
VALUES
    ('Buddy', 4, 'Marrom', 12.0, 'Golden Retriever', 'Grande', 'Sem problemas de saúde conhecidos', '12345678901'),
    ('Luna', 2, 'Preto', 8.5, 'Labrador', 'Médio', 'Vacinas em dia', '98765432101');


INSERT INTO Servicos (nome, descricao, preco)
VALUES
    ('Banho', 'Banho e secagem do animal', 30.00),
    ('Tosa', 'Tosa higiênica e estilizada', 40.00),
	('Corte de Unhas', 'Corte das unhas do animal', 20.00),
    ('Hidratação de Pelagem', 'Tratamento para hidratar a pelagem do animal', 35.00),
    ('Escovação de Dentes', 'Escovação dos dentes do animal', 15.00),
    ('Limpeza de Ouvidos', 'Limpeza dos ouvidos do animal', 18.00);


INSERT INTO Agendamentos (data, hora, cliente_cpf, gato_id, cachorro_id)
VALUES
    ('2023-01-15', '14:00', '12345678901', 1, NULL),
    ('2023-02-10', '10:30', '98765432101', NULL, 2),
    ('2023-03-05', '15:30', '12345678901', 1, NULL),
    ('2023-03-10', '11:00', '98765432101', NULL, 2),
    ('2023-03-12', '14:30', '12345678901', 1, NULL);


INSERT INTO Servicos_agendados (agendamento_id, servico_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 3),
    (3, 4),
    (2, 4);

INSERT INTO Pagamentos (valor_total, metodo_pagamento, agendamento_id)
VALUES
    (70.00, 'Cartão', 1),
    (45.00, 'Pix', 2),
    (55.00, 'Pix', 3),
    (40.00, 'Dinheiro', 4),
    (30.00, 'Cartão', 5);
	

SELECT * FROM Clientes

SELECT * FROM Cachorros 
