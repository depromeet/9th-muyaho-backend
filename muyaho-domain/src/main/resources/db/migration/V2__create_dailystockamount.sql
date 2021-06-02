CREATE TABLE `daily_stock_amount`
(
    `id`                        BIGINT   NOT NULL AUTO_INCREMENT,
    `member_id`                 BIGINT   NOT NULL,
    `local_date_time`           DATETIME NOT NULL,
    `final_asset`               DECIMAL(19, 2) DEFAULT NULL,
    `seed_amount`               DECIMAL(19, 2) DEFAULT NULL,
    `final_profit_or_lose_rate` DECIMAL(19, 2) DEFAULT NULL,
    `created_date_time`         DATETIME(6)    DEFAULT NULL,
    `last_modified_date_time`   DATETIME(6)    DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_daily_stock_amount_1` (`member_id`, `local_date_time`)
) ENGINE = InnoDB;
