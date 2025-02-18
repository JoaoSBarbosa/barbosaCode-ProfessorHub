ALTER TABLE dropbox
ADD COLUMN `data_hora_cadastro_acesso` VARCHAR(25),
ADD COLUMN `data_hora_vencimento_acesso` VARCHAR(25),
ADD COLUMN `expira_em` VARCHAR(10),
ADD COLUMN `codigo_autorizacao` VARCHAR(500);
