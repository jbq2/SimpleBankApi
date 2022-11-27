USE simplebankdbapi;

CREATE TABLE `sessions` (
	`session_id` VARCHAR(60) NOT NULL UNIQUE,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `last_activity` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`session_id`, `email`)
);

CREATE EVENT `expire_session`
ON SCHEDULE EVERY 1 MINUTE 
DO DELETE FROM `sessions` WHERE UNIX_TIMESTAMP(CURRENT_TIMESTAMP) - UNIX_TIMESTAMP(`last_activity`) > 300;