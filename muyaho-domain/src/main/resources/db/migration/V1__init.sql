CREATE TABLE `spring_session`
(
    `session_id`            CHAR(36) NOT NULL,
    `creation_time`         BIGINT   NOT NULL,
    `last_access_time`      BIGINT   NOT NULL,
    `max_inactive_interval` INT      NOT NULL,
    `principal_name`        VARCHAR(100) DEFAULT NULL,
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (`session_id`)
) ENGINE = InnoDB;

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (`last_access_time`);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (`principal_name`);


CREATE TABLE `spring_session_attributes`
(
    `session_id`      CHAR(36)     NOT NULL,
    `attribute_name`  VARCHAR(200) NOT NULL,
    `attribute_bytes` BLOB         NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (`session_id`, `attribute_name`),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (`session_id`) REFERENCES SPRING_SESSION (`session_id`) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (`session_id`);


CREATE TABLE `member`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `uid`                     VARCHAR(50) NOT NULL,
    `email`                   VARCHAR(50)   DEFAULT NULL,
    `name`                    VARCHAR(30) NOT NULL,
    `profile_url`             VARCHAR(2048) DEFAULT NULL,
    `provider`                VARCHAR(30) NOT NULL,
    `created_date_time`       DATETIME(6)   DEFAULT NULL,
    `last_modified_date_time` DATETIME(6)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_member_1` (`uid`, `provider`),
    UNIQUE KEY `uni_member_2` (`name`)
) ENGINE = InnoDB;


CREATE TABLE `delete_member`
(
    `id`                      BIGINT      NOT NULL AUTO_INCREMENT,
    `previous_id`             BIGINT      NOT NULL,
    `uid`                     VARCHAR(50) NOT NULL,
    `email`                   VARCHAR(50)   DEFAULT NULL,
    `name`                    VARCHAR(30) NOT NULL,
    `profile_url`             VARCHAR(2048) DEFAULT NULL,
    `provider`                VARCHAR(30) NOT NULL,
    `created_date_time`       DATETIME(6)   DEFAULT NULL,
    `last_modified_date_time` DATETIME(6)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;


CREATE TABLE `stock`
(
    `id`                      BIGINT       NOT NULL AUTO_INCREMENT,
    `type`                    VARCHAR(30)  NOT NULL,
    `code`                    VARCHAR(30)  NOT NULL,
    `name`                    VARCHAR(150) NOT NULL,
    `status`                  VARCHAR(30) DEFAULT NULL,
    `created_date_time`       DATETIME(6) DEFAULT NULL,
    `last_modified_date_time` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_stock_1` (`type`, `status`, `name`)
) ENGINE = InnoDB;


CREATE TABLE `member_stock`
(
    `id`                          BIGINT         NOT NULL AUTO_INCREMENT,
    `member_id`                   BIGINT         NOT NULL,
    `stock_id`                    BIGINT         NOT NULL,
    `purchase_unit_price`         DECIMAL(19, 2) NOT NULL,
    `currency_type`               VARCHAR(30)    NOT NULL,
    `purchase_quantity`           DECIMAL(19, 2) NOT NULL,
    `total_purchase_price_in_won` DECIMAL(19, 2) DEFAULT NULL,
    `created_date_time`           DATETIME(6)    DEFAULT NULL,
    `last_modified_date_time`     DATETIME(6)    DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uni_member_stock_1` (`member_id`, `stock_id`)
) ENGINE = InnoDB;


CREATE TABLE `deleted_member_stock`
(
    `id`                          BIGINT         NOT NULL AUTO_INCREMENT,
    `backup_id`                   BIGINT         NOT NULL,
    `member_id`                   BIGINT         NOT NULL,
    `stock_id`                    BIGINT         NOT NULL,
    `purchase_unit_price`         DECIMAL(19, 2) NOT NULL,
    `currency_type`               VARCHAR(30)    NOT NULL,
    `purchase_quantity`           DECIMAL(19, 2) NOT NULL,
    `total_purchase_price_in_won` DECIMAL(19, 2) DEFAULT NULL,
    `created_date_time`           DATETIME(6)    DEFAULT NULL,
    `last_modified_date_time`     DATETIME(6)    DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;


CREATE TABLE `stock_history`
(
    `id`                      BIGINT         NOT NULL AUTO_INCREMENT,
    `member_stock_id`         BIGINT         NOT NULL,
    `current_price_in_won`    DECIMAL(19, 2) NOT NULL,
    `current_price_in_dollar` DECIMAL(19, 2) NOT NULL,
    `profit_or_lose_rate`     DECIMAL(19, 2) NOT NULL,
    `created_date_time`       DATETIME(6) DEFAULT NULL,
    `last_modified_date_time` DATETIME(6) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
