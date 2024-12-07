CREATE DATABASE IF NOT EXISTS dbLoja_online;

USE dbLoja_online;

CREATE TABLE IF NOT EXISTS funcionario(
	`idFuncionario` INT(11) NOT NULL AUTO_INCREMENT,
    `nomeFuncionario` VARCHAR(100) NOT NULL,
    `senha` VARCHAR(8) NOT NULL,
    `cpfFuncionario` VARCHAR(14) NOT NULL UNIQUE,
    `dtNascimento` DATE NOT NULL,
    `emailFuncionario` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`idFuncionario`)
    );
    
CREATE TABLE IF NOT EXISTS cliente(
	`idCliente` INT(11) NOT NULL AUTO_INCREMENT,
    `nomeCliente` VARCHAR(100) NOT NULL,
    `genero` VARCHAR(20),
    `cpfCliente` VARCHAR(14) NOT NULL UNIQUE,
    `telefoneCliente` VARCHAR(12) NOT NULL,
    `enderecoCliente` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`idCliente`)
    );
    
CREATE TABLE IF NOT EXISTS produtos(
	`idProduto` INT(11) NOT NULL AUTO_INCREMENT,
    `nomeProduto` VARCHAR(30) NOT NULL,
    `dtValidade` DATE,
    `fornecedor` VARCHAR(30) NOT NULL,
    `descricao` VARCHAR(30) NOT NULL,
    `preco` DECIMAL (8,2) NOT NULL,
    PRIMARY KEY(`idProduto`)
    );
    
CREATE TABLE IF NOT EXISTS pagamento(
	`idFormaPagmnt` INT(11) NOT NULL AUTO_INCREMENT,
    `meioPagmnt` VARCHAR(30) NOT NULL,
    `taxaJuros` DECIMAL(8,2) NOT NULL,
    `quantParcelas` INT(2),
    `data` DATE NOT NULL,
    `descricao` VARCHAR(20) NOT NULL,
    PRIMARY KEY(`idFormaPagmnt`)
    );
    
CREATE TABLE IF NOT EXISTS pedidos(
	`idItensPedidos` INT(11) NOT NULL AUTO_INCREMENT,
    `quantdVendida` INT(8) NOT NULL,
    `precoVenda` DECIMAL(8,2) NOT NULL,
    `idCliente` INT(11) NOT NULL,
    `idProduto` INT(11) NOT NULL,
    `idFormaPagmnt` INT(11) NOT NULL,
    PRIMARY KEY(`idItensPedidos`),
    CONSTRAINT `fkIdCliente`
		FOREIGN KEY(`idCliente`)
        REFERENCES `dbloja_online`.`cliente`(`idCliente`), 
	CONSTRAINT `fkIdProduto`
		FOREIGN KEY(`idProduto`)
        REFERENCES `dbloja_online`.`produtos`(`idProduto`),
	CONSTRAINT `fkIdFormaPagmnt`
		FOREIGN KEY (`idFormaPagmnt`)
        REFERENCES `dbloja_online`.`pagamento`(`idFormaPagmnt`)
    );
    