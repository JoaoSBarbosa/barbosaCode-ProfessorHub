CREATE TABLE `dropbox`(
    `dropbox_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `api_key` VARCHAR(500) NOT NULL,
    `api_secret` VARCHAR(500) NOT NULL,
    `access_token` VARCHAR(500) NOT NULL,
    `refresh_token` VARCHAR(500),
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME
);