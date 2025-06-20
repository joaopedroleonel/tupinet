CREATE TABLE jogo (
  jogo_id INT NOT NULL AUTO_INCREMENT,
  imagem VARCHAR(255),
  nome VARCHAR(100) NOT NULL,
  rota VARCHAR(100) NOT NULL,
  tipo_jogo_id INT NOT NULL,
  PRIMARY KEY (jogo_id),
  FOREIGN KEY (tipo_jogo_id) REFERENCES tipo_jogo(tipo_jogo_id)
);

CREATE TABLE jogo_palavra (
  jogo_id INT NOT NULL,
  palavra_id INT NOT NULL,
  posicao VARCHAR(50),
  PRIMARY KEY (jogo_id, palavra_id),
  FOREIGN KEY (jogo_id) REFERENCES jogo(jogo_id),
  FOREIGN KEY (palavra_id) REFERENCES palavra(palavra_id)
);

CREATE TABLE palavra (
  palavra_id INT NOT NULL AUTO_INCREMENT,
  texto VARCHAR(100) NOT NULL,
  PRIMARY KEY (palavra_id)
);

CREATE TABLE pontuacao (
  pontuacao_id INT NOT NULL AUTO_INCREMENT,
  acertos INT NOT NULL,
  aluno VARCHAR(100) NOT NULL,
  data DATE NOT NULL,
  pontos INT NOT NULL,
  sala_jogo_id BIGINT NOT NULL,
  PRIMARY KEY (pontuacao_id),
  FOREIGN KEY (sala_jogo_id) REFERENCES sala_jogo(id)
);

CREATE TABLE professor (
  professor_id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(100) NOT NULL,
  nome VARCHAR(100) NOT NULL,
  senha VARCHAR(100) NOT NULL,
  PRIMARY KEY (professor_id),
  UNIQUE (email)
);

CREATE TABLE sala (
  sala_id INT NOT NULL AUTO_INCREMENT,
  ativo BIT(1) NOT NULL,
  codigo VARCHAR(20) NOT NULL,
  nome VARCHAR(100) NOT NULL,
  PRIMARY KEY (sala_id),
  UNIQUE (codigo)
);

CREATE TABLE sala_jogo (
  id BIGINT NOT NULL AUTO_INCREMENT,
  jogo_id INT NOT NULL,
  sala_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (jogo_id) REFERENCES jogo(jogo_id),
  FOREIGN KEY (sala_id) REFERENCES sala(sala_id)
);

CREATE TABLE sala_professor (
  professor_id INT NOT NULL,
  sala_id INT NOT NULL,
  PRIMARY KEY (professor_id, sala_id),
  FOREIGN KEY (professor_id) REFERENCES professor(professor_id),
  FOREIGN KEY (sala_id) REFERENCES sala(sala_id)
);

CREATE TABLE tipo_jogo (
  tipo_jogo_id INT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(255),
  nome VARCHAR(50) NOT NULL,
  PRIMARY KEY (tipo_jogo_id),
  UNIQUE (nome)
);

CREATE TABLE traducao_palavra (
  palavra_id INT NOT NULL,
  traducao VARCHAR(100) NOT NULL,
  PRIMARY KEY (palavra_id, traducao),
  FOREIGN KEY (palavra_id) REFERENCES palavra(palavra_id)
);
