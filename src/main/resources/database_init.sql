--   用户关系表
  DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '',
    `password` varchar(128) NOT NULL DEFAULT '',
    `salt` varchar(32) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
  );

-- 登录凭证
  DROP TABLE IF EXISTS `login_ticket`;
  CREATE TABLE `login_ticket` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `ticket` VARCHAR(45) NOT NULL,
    `expired` DATETIME NOT NULL,
    `status` INT NULL DEFAULT 0,
    UNIQUE INDEX `ticket_UNIQUE` (`ticket`)
  )

-- 问题表
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` TEXT NULL,
  `user_id` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  `comment_count` INT NOT NULL,
  INDEX `date_index` (`created_date`));

-- 评论表
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
`id` INT  PRIMARY KEY AUTO_INCREMENT,
`content` TEXT NOT NULL,
`user_id` INT NOT NULL,
`entity_id` INT NOT NULL,
`entity_type` INT NOT NULL,
`created_date` DATETIME NOT NULL,
`status` INT NOT NULL DEFAULT 0,
INDEX `entity_index` (`entity_id`, `entity_type`)
) ;