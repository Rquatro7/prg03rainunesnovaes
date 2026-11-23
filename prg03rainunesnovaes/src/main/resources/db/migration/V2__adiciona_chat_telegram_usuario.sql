-- o bot publica as ofertas no Telegram, entao cada usuario pode ter o chat onde recebe os avisos
ALTER TABLE usuario ADD COLUMN chat_id_telegram VARCHAR(30) NULL;
