CREATE TABLE `professores`(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `nome` VARCHAR(125) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `idade` INTEGER NOT NULL,
    `descricao` TEXT NOT NULL,
    `valor_hora` DECIMAL(5,0) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `foto_perfil` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME
);
