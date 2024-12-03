CREATE TABLE professores (
id INT AUTO_INCREMENT PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL,
telefone VARCHAR(15),
especialidade VARCHAR(100)
);

CREATE TABLE cursos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        codigo VARCHAR(20) NOT NULL,
                        carga_horaria INT
);