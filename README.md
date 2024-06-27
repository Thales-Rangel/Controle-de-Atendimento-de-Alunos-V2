<div align="center">
	
<img src="Projeto/src/img/Project_Icon_Remove.png" width="144">
	
# Controle de Atendimento de Alunos V2

[![java](https://img.shields.io/badge/Java-21.x-orange)](https://www.java.com)
[![Javax Swing](https://img.shields.io/badge/Swing-GUI-white)](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F/javax/swing/package-summary.html)
[![MySQL Server](https://img.shields.io/badge/MySQl-Server-blue)](https://www.mysql.com)

</div>

<br>

## Introdução

Este projeto foi feito com o objetivo de por em prática os meus estudos de Banco de Dados com MySQL em Java, por isso refiz o projeto anterior de controle de atendimento de alunos usado na avaliação da disciplina de Programação Estruturada e Orientada a Objetos, só que dessa vez implementando interação com um Banco de Dados.

<br>

## Descrição

Se trata de um sistema para facilitar o controle de dúvidas dos alunos nas disciplinas cursadas. O sistema é capaz de armazenar as solicitações de atendimento dos alunos com os professores.

---

## Funcionalidades

1. O sistema guarda os seguintes dados de alunos e professores:
	- Professores: Matrícula, nome e disciplinas
 	- Alunos: Matrícula, nome, e turma.

<div align="center">
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/61e5f7e6-fa20-4620-ab19-0070189e338a" width="40%" >
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/83b8dfc9-673b-4507-83f1-263d7ba8b709" width="40%">
</div>

2. O sistema permite o cadastro, listagem e remoção de alunos e professores, disciplinas e turmas.

<div align="center">
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/bf1b3318-6bf0-4ba7-bb33-323ea7d2edcd" width="222">
</div>

3. É possível buscar por professores, alunos, disciplinas e turmas por nome, matricula ou ID, ou determinadas associações (turma associada a um aluno, ou disciplina associada a um professor...).

<div align="center">
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/0334b32e-6a54-412e-b90a-2854791588bb" width="444">
</div>

4. Também é possível buscar todos os professores de uma determinada disciplina.

5. O sistema permiter a listagem das solicitações de acompanhamento feitas.

<div align="center">
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/670cd57e-70c8-45dd-bce2-6fbaa267e78f" width="444">
</div>

6. Para cadastrar uma solicitação de acompanhamento o aluno deve informar o professor solicitado e uma descrição da dúvida que o aluno deseja tirar com o professor, juntamente com a disciplina da dúvida que ele está tendo.

<div align="center">
	
<img src="https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/257acb76-b74d-4f7b-965e-942ae601e97e" width="444">

</div>

---

## Observações

O banco de dados está afuncionando por meio de um localhost com usuário **"root"** e **senha vazia** na classe [**DAO**](https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/blob/main/Projeto/src/Utils/DAO.java) no package Utils, caso queira conectar em algum servidor com url, usuário ou senha diferente, se faz necessário alterar diretamente no código da classe **DAO**.

![Código para conectar no servidor](https://github.com/Thales-Rangel/Controle-de-Atendimento-de-Alunos-V2/assets/160191874/31a197f1-ba31-4dfe-9e5c-5c65b78c4208)

Além disso, o código funciona com uma Base de Dados de nome **project_pabd** (também pode ser visto na classe DAO), então garanta que o seu servidor tenha essa Base de Dados, juntamente com as tabelas, segue abaixo os comandos MySQL para deixar Base de Dados preparada:

```
create database if not exists project_pabd;

use project_pabd;

create table if not exists turmas(
	id int auto_increment primary key,
	nome varchar(20) unique
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

create table if not exists alunos(
	nome varchar(30),
    	matricula varchar(20) primary key,
    	senha varchar(100),
    	id_turma int,
    	foreign key (id_turma) references turmas(id)
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
    	id_disciplina int,
    	duvida text not null,
    	resposta text,
    	respondido enum('T', 'F') default 'F',
    	foreign key (matricula_a) references alunos(matricula),
    	foreign key (matricula_p) references professores(matricula),
    	foreign key (id_disciplina) references disciplinas(id)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

```
---

O projeto foi inteiramente feito com a linguagem de programação Java e com Banco de Dados MySQL.

<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a>
<a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> 
