CREATE TABLE `alunos`(
                         `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                         `nome` VARCHAR(150) NOT NULL,
                         `email` VARCHAR(255) UNIQUE NOT NULL,
                         `data_aula` DATETIME NOT NULL,
                         `professor_id` BIGINT NOT NULL,
                         `created_at` DATETIME NOT NULL,
                         `updated_at` DATETIME,
                         FOREIGN KEY (`professor_id`) REFERENCES `professores`(`id`)
);
