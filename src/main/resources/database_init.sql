--   用户关系表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(64)      NOT NULL DEFAULT '',
    `password` varchar(128)     NOT NULL DEFAULT '',
    `salt`     varchar(32)      NOT NULL DEFAULT '',
    `head_url` varchar(128)      NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
);

-- 登录凭证
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket`
(
    `id`      INT PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT         NOT NULL,
    `ticket`  VARCHAR(45) NOT NULL,
    `expired` DATETIME    NOT NULL,
    `status`  INT         NULL DEFAULT 0,
    UNIQUE INDEX `ticket_UNIQUE` (`ticket`)
)

-- 问题表
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`            INT PRIMARY KEY AUTO_INCREMENT,
    `title`         VARCHAR(255) NOT NULL,
    `content`       TEXT         NULL,
    `user_id`       INT          NOT NULL,
    `created_date`  DATETIME     NOT NULL,
    `comment_count` INT          NOT NULL,
    INDEX `date_index` (`created_date`)
);

-- 评论表
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`           INT PRIMARY KEY AUTO_INCREMENT,
    `content`      TEXT     NOT NULL,
    `user_id`      INT      NOT NULL,
    `entity_id`    INT      NOT NULL,
    `entity_type`  INT      NOT NULL,
    `created_date` DATETIME NOT NULL,
    `status`       INT      NOT NULL DEFAULT 0,
    INDEX `entity_index` (`entity_id`, `entity_type`)
);

--  私信表
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`
(
    `id`              INT PRIMARY KEY AUTO_INCREMENT,
    `from_id`         INT         NULL,
    `to_id`           INT         NULL,
    `content`         TEXT        NULL,
    `created_date`    DATETIME    NULL,
    `has_read`        INT         NULL,
    `conversation_id` VARCHAR(45) NOT NULL,
    INDEX `conversation_index` (`conversation_id`),
    INDEX `created_date` (`created_date`)
);

--  赞同表
DROP TABLE IF EXISTS `agreement`;
CREATE TABLE `agreement`
(
    `id`           INT PRIMARY KEY AUTO_INCREMENT,
    `user_id`      INT      NOT NULL,
    `entity_id`    INT      NOT NULL,
    `entity_type`  INT      NOT NULL,
    `status`       INT      NOT NULL DEFAULT 0,
    `created_date` DATETIME NOT NULL,
    INDEX `entity_index` (`entity_id`, `entity_type`)
);

--  关注表
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows`
(
    `id`           INT PRIMARY KEY AUTO_INCREMENT,
    `user_id`      INT      NOT NULL,
    `entity_id`    INT      NOT NULL,
    `entity_type`  INT      NOT NULL,
    `status`       INT      NOT NULL DEFAULT 0,
    `created_date` DATETIME NOT NULL,
    INDEX `entity_index` (`entity_id`, `entity_type`)
);

-- feed 表
DROP TABLE IF EXISTS `feed`;
CREATE TABLE `feed`
(
    `id`           INT PRIMARY KEY AUTO_INCREMENT,
    `created_date` DATETIME NULL,
    `user_id`      INT      NULL,
    `data`         TINYTEXT NULL,
    `type`         INT      NULL,
    INDEX `user_index` (`user_id`)
);