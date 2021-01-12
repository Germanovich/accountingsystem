-- -----------------------------------------------------
-- Schema accounting_system
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `accounting_system`;
CREATE SCHEMA IF NOT EXISTS `accounting_system` DEFAULT CHARACTER SET utf8;
USE `accounting_system`;

-- -----------------------------------------------------
-- Table `profiles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `profiles`
(
    `id`                   INT         NOT NULL AUTO_INCREMENT,
    `name`                 VARCHAR(50) NOT NULL,
    `surname`              VARCHAR(50) NOT NULL,
    `sex`                  TINYINT     NULL,
    `date_of_birth`        DATE        NULL,
    `date_of_registration` DATE        NOT NULL,
    CONSTRAINT pk_profiles_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `login`      VARCHAR(45)  NOT NULL,
    `password`   VARCHAR(256) NOT NULL,
    `role`       VARCHAR(10)  NOT NULL,
    `profile_id` INT          NOT NULL UNIQUE,
    CONSTRAINT pk_users_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `catalogs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `catalogs`
(
    `id`                INT         NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(45) NOT NULL,
    `parent_catalog_id` INT         NULL,
    CONSTRAINT pk_catalogs_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `firms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `firms`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    CONSTRAINT pk_firms_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `cars`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cars`
(
    `id`          INT           NOT NULL AUTO_INCREMENT,
    `catalog_id`  INT           NOT NULL,
    `firm_id`     INT           NOT NULL,
    `model`       VARCHAR(45)   NOT NULL,
    `status`      VARCHAR(45)   NOT NULL,
    `price`       DECIMAL(8, 2) NOT NULL,
    `description` VARCHAR(256)  NULL,
    CONSTRAINT pk_cars_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `orders`
(
    `id`              INT           NOT NULL AUTO_INCREMENT,
    `user_id`         INT           NOT NULL,
    `car_id`          INT           NOT NULL,
    `start_date`      TIMESTAMP     NOT NULL,
    `end_date`        TIMESTAMP     NOT NULL,
    `price`           DECIMAL(8, 2) NOT NULL,
    `actual_end_date` TIMESTAMP     NULL,
    CONSTRAINT pk_orders_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `requests`
(
    `id`      INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `car_id`  INT NOT NULL,
    CONSTRAINT pk_requests_id PRIMARY KEY (`id`)
) ENGINE = INNODB;


-- -----------------------------------------------------
-- Table `contact_persons`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `contact_persons`
(
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `firm_id`   INT         NOT NULL,
    `telephone` VARCHAR(20) NOT NULL,
    `name`      VARCHAR(50) NOT NULL,
    CONSTRAINT pk_contact_persons_id PRIMARY KEY (`id`)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS `settings`
(
    `id`     INT         NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(45) NOT NULL,
    `access` TINYINT     NOT NULL,
    CONSTRAINT pk_settings_id PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

ALTER TABLE `users`
    ADD INDEX `fk_user_profile_id` (`profile_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_user_profile`
        FOREIGN KEY (`profile_id`)
            REFERENCES `profiles` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE `catalogs`
    ADD INDEX `fk_catalog_catalog_id` (`parent_catalog_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_catalog_catalog`
        FOREIGN KEY (`parent_catalog_id`)
            REFERENCES `catalogs` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE `cars`
    ADD INDEX `fk_car_catalog_id` (`catalog_id` ASC) VISIBLE,
    ADD INDEX `fk_car_firm_id` (`firm_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_car_catalog`
        FOREIGN KEY (`catalog_id`)
            REFERENCES `catalogs` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_car_firm`
        FOREIGN KEY (`firm_id`)
            REFERENCES `firms` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE `orders`
    ADD INDEX `fk_order_user_id` (`user_id` ASC) VISIBLE,
    ADD INDEX `fk_order_car_id` (`car_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_order_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_order_car`
        FOREIGN KEY (`car_id`)
            REFERENCES `cars` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE `requests`
    ADD INDEX `fk_request_user_id` (`user_id` ASC) VISIBLE,
    ADD INDEX `fk_request_car_id` (`car_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_request_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `fk_request_car`
        FOREIGN KEY (`car_id`)
            REFERENCES `cars` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;

ALTER TABLE `contact_persons`
    ADD INDEX `fk_phone_number_firm_id` (`firm_id` ASC) VISIBLE,
    ADD CONSTRAINT `fk_phone_number_firm`
        FOREIGN KEY (`firm_id`)
            REFERENCES `firms` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
;
COMMIT;
