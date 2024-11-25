CREATE DATABASE IF NOT EXISTS dbLoja_online;

USE dbLoja_online;

CREATE TABLE IF NOT EXISTS `dbLoja_online`.`funcionario` (
	`id_funcionario` INT(11) NOT NULL AUTO_INCREMENT,
    `nome_funcionario` VARCHAR(30) NOT NULL,
    `senha` INT(8) NOT NULL,
    `CPF_funcionario` INT (11) NOT NULL UNIQUE,
    `dt_nascimento` DATE NOT NULL,
    PRIMARY KEY(`id_funcionario`)
    );