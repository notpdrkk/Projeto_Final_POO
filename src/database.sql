
CREATE TYPE parentesco_tipo AS ENUM ('FILHO', 'SOBRINHO', 'OUTROS');


CREATE TABLE IF NOT EXISTS funcionario (
    id SERIAL PRIMARY KEY, 
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    salario_bruto DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS dependente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    parentesco parentesco_tipo NOT NULL, 
    funcionario_id INT,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);

CREATE TABLE IF NOT EXISTS folha_pagamento (
    id SERIAL PRIMARY KEY,
    funcionario_id INT,
    data_pagamento DATE NOT NULL,
    desconto_inss DECIMAL(10,2) NOT NULL,
    desconto_ir DECIMAL(10,2) NOT NULL,
    salario_liquido DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
);