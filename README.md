<h1 align="center">Controle de Atendimento de Alunos V2</h1>

---
<h2 align="center">Descrição</h2>

- Esta segunda versão do projeto de Controle de Atendimento de Alunos foi feita com o objetivo de por em prática os meus estudos recentes de Banco de Dados com MySQL em Java, por isso refiz o projeto anterior usado na avaliação da disciplina de Programação Estruturada e Orientada a Objetos, só que dessa vez implementando interação com um Banco de Dados.

---
<h2 align="center">Objetivos do projeto</h2>

- Se trata de um sistema para facilitar o controle de dúvidas dos alunos nas disciplinas cursadas. O sistema é capaz de armazenar as solicitações de atendimento dos alunos com os professores.

- O sistema deve guardar os seguintes dados de alunos e professores:
    - Professores: Matrícula, nome e disciplinas
    - Alunos: Matrícula, nome, e turma.

- O sistema deve permitir o cadastro, listagem e remoção de alunos e professores. 

- Deve ser possível buscar um professor pelo seu nome.

- Também deverá ser possível buscar todos os professores de uma determinada disciplina.

- O sistema também deve permitir a listagem das solicitações de acompanhamento feitas.

- Para cadastrar uma solicitação de acompanhamento deve ser informado o aluno que solicitou, o professor solicitado e uma descrição da dúvida que o aluno deseja tirar com o professor.

---
<h2 align="center">Observações</h2>

- O banco de dados está afuncionando por meio de um localhost com usuário root e senha vazia na classe DAO no Package Models, caso queira conectar em algum servidor com url, usuário ou senha diferente, se faz necessário alterar diretamente no código da classe DAO.
- Além disso, o código funciona com uma Base de Dados de nome project_pabd (também pode ser visto na classe DAO), então garanta que o seu servidor tenha essa Base de Dados, juntamente com as tabelas, segue abaixo os comandos MySQL para deixar Base de Dados preparada:

```
create database if not exists Project_PABD;

use project_pabd;

create table if not exists alunos(
	nome varchar(30),
    matricula varchar(20) primary key,
    senha varchar(100),
    id_turma int,
    foreign key (id_turma) references turmas(id)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists turmas(
	id int auto_increment primary key,
    nome varchar(20) unique
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists disciplinas(
	id int primary key auto_increment,
    nome varchar(80) unique
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists professores(
	nome varchar(30),
    matricula varchar(20) primary key,
    senha varchar(100)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


create table if not exists ensina(
	id int auto_increment primary key,
    matricula_professor varchar(20),
    id_disciplina int,
    foreign key (matricula_professor) references professores(matricula),
    foreign key (id_disciplina) references disciplinas(id)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists estuda(
	id int auto_increment primary key,
    id_disciplina int,
    id_turma int,
    foreign key (id_disciplina) references disciplinas(id),
    foreign key (id_turma) references turmas(id)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists solicitacoes(
	id int auto_increment primary key,
    matricula_a varchar(20),
    matricula_p varchar(20),
    duvida text not null,
    resposta text,
    respondido enum('T', 'F') default 'F',
    foreign key (matricula_a) references alunos(matricula),
    foreign key (matricula_p) references professores(matricula)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

```
---
- O projeto foi inteiramente feito com a linguagem de programação Java e com Banco de Dados MySQL.

<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>

<a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> 

---
* Veja mais projetos meus no meu [perfil](https://github.com/Thales-Rangel).