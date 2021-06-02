CREATE TABLE IF NOT EXISTS SPRING_SESSION
(
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
);

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);

CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES
(
    SESSION_ID      CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME  VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES MEDIUMBLOB   NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION (SESSION_ID) ON DELETE CASCADE
);

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);



CREATE TABLE IF NOT EXISTS `member`
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


CREATE TABLE IF NOT EXISTS `delete_member`
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


CREATE TABLE IF NOT EXISTS `stock`
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


CREATE TABLE IF NOT EXISTS `member_stock`
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


CREATE TABLE IF NOT EXISTS `deleted_member_stock`
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


CREATE TABLE IF NOT EXISTS `stock_history`
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
