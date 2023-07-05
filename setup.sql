-- create the databases
CREATE DATABASE IF NOT EXISTS pccw;

-- create the users for each database
CREATE USER 'tom'@'%' IDENTIFIED BY 'password';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `pccw`.* TO 'tom'@'%';
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `pccw`.* TO 'root'@'%';
FLUSH PRIVILEGES;

-- create table
CREATE TABLE IF NOT EXISTS `pccw`.`users` (
    `id` bigint (20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `email` varchar(128) NOT NULL COMMENT 'email',
    `deleted` tinyint (1) NOT NULL DEFAULT '0' COMMENT 'whether it is deleted',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'last update time',
    PRIMARY KEY (`id`),
    KEY `idx_email` (`email`, `deleted`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'user table';


CREATE TABLE IF NOT EXISTS `pccw`.`user_passwords` (
    `user_id` bigint (20) unsigned NOT NULL COMMENT 'user id',
    `hash_code` varchar(128) NOT NULL COMMENT 'hash code of password',
    `deleted` tinyint (1) NOT NULL DEFAULT '0' COMMENT 'is deleted',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'last update time',
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'passwords';


CREATE TABLE IF NOT EXISTS `pccw`.`user_model` (
    `user_id` bigint (20) unsigned NOT NULL COMMENT 'user id',
    `first_name` varchar(128) NOT NULL COMMENT 'The user first name',
    `last_name` varchar(128) NOT NULL COMMENT 'The user last name',
    `birthdate` varchar(64) COMMENT 'birthdate 20230111',
    `gender` varchar(8)  COMMENT 'male, female, other',
    `zipcode` int(11)  COMMENT 'zipcode',
    `deleted` tinyint (1) NOT NULL DEFAULT '0' COMMENT 'is deleted',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'last update time',
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'to store user information';


CREATE TABLE IF NOT EXISTS `pccw`.`user_welcome_mail_sent` (
    `user_id` bigint (20) unsigned NOT NULL COMMENT 'user id',
    `mail_path` varchar(128) COMMENT 'mail storage path',
    `mail_content` TEXT NOT NULL COMMENT 'mail content',
    `c_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    PRIMARY KEY (`user_id`)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'to store user welcome email sent';


