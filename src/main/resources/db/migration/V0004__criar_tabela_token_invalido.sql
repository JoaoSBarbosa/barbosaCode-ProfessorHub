CREATE TABLE `token_invalido`(
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `token` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME
);