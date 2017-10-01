-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
SET GLOBAL max_allowed_packet = 1024*1024*15;

-- -----------------------------------------------------
-- Schema findlunchandswa
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `findlunchandswa` ;

-- -----------------------------------------------------
-- Schema findlunchandswa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `findlunchandswa` DEFAULT CHARACTER SET utf8;
USE `findlunchandswa`;

-- Create User & grant privileges
DROP USER IF EXISTS 'SalesWebAppUser'@'localhost';
CREATE USER 'SalesWebAppUser'@'localhost' IDENTIFIED BY 'SWADBPassword';
GRANT SELECT ON findlunchandswa.* TO 'SalesWebAppUser'@'localhost';
GRANT INSERT ON findlunchandswa.* TO 'SalesWebAppUser'@'localhost';
GRANT UPDATE ON findlunchandswa.* TO 'SalesWebAppUser'@'localhost';
GRANT DELETE ON findlunchandswa.* TO 'SalesWebAppUser'@'localhost';

-- -----------------------------------------------------
-- Table `findlunchandswa`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `country` (
  `country_code` varchar(2) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ——————————————————————————
-- Table `findlunchandswa`.`day_of_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `day_of_week` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `day_number` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `day_number_UNIQUE` (`day_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`restaurant_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurant_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `FindLunchAndSWA`.`restaurant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `street` varchar(60) NOT NULL,
  `street_number` varchar(11) NOT NULL,
  `zip` varchar(5) NOT NULL,
  `city` varchar(60) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `location_latitude` float DEFAULT NULL,
  `location_longitude` float DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `phone` varchar(60) NOT NULL,
  `url` varchar(60) DEFAULT NULL,
  `restaurant_type_id` int(11) DEFAULT NULL,
  `restaurant_uuid` varchar(40) NOT NULL,
  `qr_uuid` blob NOT NULL,
  `swa_offer_modify_permission` tinyint(1) NOT NULL DEFAULT '0',
  `swa_blocked` tinyint(1) NOT NULL DEFAULT '0',
  `swa_sales_person_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_restaurant_countries1_idx` (`country_code`),
  KEY `fk_restaurant_restaurant_type1_idx` (`restaurant_type_id`),
  KEY `fk_restaurant_swa_sales_person1_idx` (`swa_sales_person_id`),
  CONSTRAINT `fk_restaurant_countries1` FOREIGN KEY (`country_code`) REFERENCES `country` (`country_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_restaurant_type1` FOREIGN KEY (`restaurant_type_id`) REFERENCES `restaurant_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_swa_sales_person1` FOREIGN KEY (`swa_sales_person_id`) REFERENCES `swa_sales_person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`user_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`account_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `account_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_number` int(11) NOT NULL,
  `account_type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_account_account_type1_idx` (`account_type_id`),
  CONSTRAINT `fk_account_account_type1` FOREIGN KEY (`account_type_id`) REFERENCES `account_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`course_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `course_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `sort_by` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_course_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_course_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password` varchar(255) NOT NULL,
  `restaurant_id` int(11) DEFAULT NULL,
  `user_type_id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_user_restaurant1_idx` (`restaurant_id`),
  KEY `fk_user_user_type1_idx` (`user_type_id`),
  KEY `fk_user_account1_idx` (`account_id`),
  CONSTRAINT `fk_user_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_type1` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`favorites`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `favorites` (
  `user_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`restaurant_id`),
  KEY `fk_user_has_restaurant_restaurant1_idx` (`restaurant_id`),
  KEY `fk_user_has_restaurant_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_restaurant_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_restaurant_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`kitchen_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kitchen_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `FindLunchAndSWA`.`offer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `offer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `swa_change_request_id` int(11) DEFAULT '0',
  `restaurant_id` int(11) NOT NULL,
  `title` varchar(60) NOT NULL,
  `description` tinytext NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `preparation_time` int(11) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `needed_points` int(11) NOT NULL,
  `sold_out` tinyint(1) NOT NULL,
  `course_type` int(11) DEFAULT NULL,
  `order` int(11) NOT NULL DEFAULT '1',
  `swa_comment_of_last_change` text,
  `swa_last_changed_by` int(11) DEFAULT NULL,
  `swa_change_request` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_product_restaurant1_idx` (`restaurant_id`),
  KEY `fk_product_course_idx` (`course_type`),
  KEY `fk_offer_swa_sales_person1_idx` (`swa_last_changed_by`),
  CONSTRAINT `fk_offer_swa_sales_person1` FOREIGN KEY (`swa_last_changed_by`) REFERENCES `swa_sales_person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productcourse1` FOREIGN KEY (`course_type`) REFERENCES `course_types` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`user_pushtoken`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_pushtoken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `fcm_token` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_pushtoken_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_pushtoken_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`offer_has_day_of_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `offer_has_day_of_week` (
  `offer_id` int(11) NOT NULL,
  `day_of_week_id` int(11) NOT NULL,
  PRIMARY KEY (`offer_id`,`day_of_week_id`),
  KEY `fk_offer_has_day_of_week_day_of_week1_idx` (`day_of_week_id`),
  KEY `fk_offer_has_day_of_week_offer1_idx` (`offer_id`),
  CONSTRAINT `fk_offer_has_day_of_week_day_of_week1` FOREIGN KEY (`day_of_week_id`) REFERENCES `day_of_week` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_offer_has_day_of_week_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`offer_photo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `offer_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offer_id` int(11) NOT NULL,
  `photo` mediumblob NOT NULL,
  `thumbnail` mediumblob NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_offer_photo_offer1_idx` (`offer_id`),
  CONSTRAINT `fk_offer_photo_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`time_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) NOT NULL,
  `offer_start_time` datetime DEFAULT NULL,
  `offer_end_time` datetime DEFAULT NULL,
  `day_of_week_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_time_schedule_restaurant1_idx` (`restaurant_id`),
  KEY `fk_time_schedule_day_of_week1_idx` (`day_of_week_id`),
  CONSTRAINT `fk_time_schedule_day_of_week1` FOREIGN KEY (`day_of_week_id`) REFERENCES `day_of_week` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_time_schedule_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`opening_time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `opening_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opening_time` datetime NOT NULL,
  `closing_time` datetime NOT NULL,
  `time_schedule_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_opening_time_time_schedule1_idx` (`time_schedule_id`),
  CONSTRAINT `fk_opening_time_time_schedule1` FOREIGN KEY (`time_schedule_id`) REFERENCES `time_schedule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`push_notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `push_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(60) DEFAULT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `radius` int(11) NOT NULL,
  `fcm_token` text NOT NULL,
  `sns_token` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_push_notification_user1_idx` (`user_id`),
  CONSTRAINT `fk_push_notification_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`push_notification_has_day_of_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `push_notification_has_day_of_week` (
  `push_notification_id` int(11) NOT NULL,
  `day_of_week_id` int(11) NOT NULL,
  PRIMARY KEY (`push_notification_id`,`day_of_week_id`),
  KEY `fk_push_notification_has_day_of_week_day_of_week1_idx` (`day_of_week_id`),
  KEY `fk_push_notification_has_day_of_week_push_notification1_idx` (`push_notification_id`),
  CONSTRAINT `fk_push_notification_has_day_of_week_day_of_week1` FOREIGN KEY (`day_of_week_id`) REFERENCES `day_of_week` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_push_notification_has_day_of_week_push_notification1` FOREIGN KEY (`push_notification_id`) REFERENCES `push_notification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`push_notification_has_kitchen_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `push_notification_has_kitchen_type` (
  `push_notification_id` int(11) NOT NULL,
  `kitchen_type_id` int(11) NOT NULL,
  PRIMARY KEY (`push_notification_id`,`kitchen_type_id`),
  KEY `fk_push_notification_has_kitchen_type_kitchen_type1_idx` (`kitchen_type_id`),
  KEY `fk_push_notification_has_kitchen_type_push_notification1_idx` (`push_notification_id`),
  CONSTRAINT `fk_push_notification_has_kitchen_type_kitchen_type1` FOREIGN KEY (`kitchen_type_id`) REFERENCES `kitchen_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_push_notification_has_kitchen_type_push_notification1` FOREIGN KEY (`push_notification_id`) REFERENCES `push_notification` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`restaurant_has_kitchen_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurant_has_kitchen_type` (
  `restaurant_id` int(11) NOT NULL,
  `kitchen_type_id` int(11) NOT NULL,
  PRIMARY KEY (`restaurant_id`,`kitchen_type_id`),
  KEY `fk_restaurant_has_kitchen_type_kitchen_type1_idx` (`kitchen_type_id`),
  KEY `fk_restaurant_has_kitchen_type_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_restaurant_has_kitchen_type_kitchen_type1` FOREIGN KEY (`kitchen_type_id`) REFERENCES `kitchen_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_restaurant_has_kitchen_type_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`points`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `points` (
  `user_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`restaurant_id`),
  KEY `fk_points_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_points_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_points_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`euro_per_point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `euro_per_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `euro` decimal(3,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`minimum_profit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `minimum_profit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profit` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_number` varchar(12) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `minimum_profit_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `bill_pdf` mediumblob NOT NULL,
  `total_price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bill_minimum_profit1_idx` (`minimum_profit_id`),
  KEY `fk_bill_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_bill_minimum_profit1` FOREIGN KEY (`minimum_profit_id`) REFERENCES `minimum_profit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_bill_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_number` int(11) NOT NULL,
  `amount` int(4) NOT NULL,
  `reservation_time` datetime NOT NULL,
  `confirmed` tinyint(1) NOT NULL,
  `rejected` tinyint(1) NOT NULL,
  `total_price` decimal(5,2) NOT NULL,
  `donation` decimal(5,2) NOT NULL,
  `used_points` tinyint(1) NOT NULL,
  `user_id` int(11) NOT NULL,
  `offer_id` int(11) DEFAULT NULL,
  `euro_per_point_id` int(11) NOT NULL,
  `bill_id` int(11) DEFAULT NULL,
  `restaurant_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `reservation_number_UNIQUE` (`reservation_number`),
  KEY `fk_reservation_user1_idx` (`user_id`),
  KEY `fk_reservation_offer1_idx` (`offer_id`),
  KEY `fk_reservation_euro_per_point1_idx` (`euro_per_point_id`),
  KEY `fk_reservation_bill1_idx` (`bill_id`),
  KEY `fk_reservation_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_reservation_bill1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_reservation_euro_per_point1` FOREIGN KEY (`euro_per_point_id`) REFERENCES `euro_per_point` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle findlunchandswa.booking
CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `booking_time` datetime NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `booking_reason_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_booking_booking_reason1_idx` (`booking_reason_id`),
  KEY `fk_booking_account1_idx` (`account_id`),
  KEY `fk_booking_bill1_idx` (`bill_id`),
  CONSTRAINT `fk_booking_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_bill1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_booking_reason1` FOREIGN KEY (`booking_reason_id`) REFERENCES `booking_reason` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`booking_reason`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `booking_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) NOT NULL,
  `booking_time` datetime NOT NULL,
  `amount` decimal(6,2) NOT NULL,
  `booking_reason_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `bill_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_booking_booking_reason1_idx` (`booking_reason_id`),
  KEY `fk_booking_account1_idx` (`account_id`),
  KEY `fk_booking_bill1_idx` (`bill_id`),
  CONSTRAINT `fk_booking_account1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_bill1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_booking_reason1` FOREIGN KEY (`booking_reason_id`) REFERENCES `booking_reason` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`donation_per_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `donation_per_month` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `amount` decimal(5,2) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `datetime_of_update` datetime NOT NULL,
  `bill_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_donation_per_month_restaurant1_idx` (`restaurant_id`),
  KEY `fk_donation_per_month_bill1_idx` (`bill_id`),
  CONSTRAINT `fk_donation_per_month_bill1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_donation_per_month_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`bill_counter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bill_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `counter` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`allergenic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `allergenic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `short` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`offer_has_allergenic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `offer_has_allergenic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offer_id` int(11) NOT NULL,
  `allergenic_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_offer_has_allergenic_offer1_idx` (`offer_id`),
  KEY `fk_offer_has_allergenic_allergenic1_idx` (`allergenic_id`),
  CONSTRAINT `fk_offer_has_allergenic_allergenic1` FOREIGN KEY (`allergenic_id`) REFERENCES `allergenic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_offer_has_allergenic_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`additives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `additives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `short` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `findlunchandswa`.`offer_has_additives`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `offer_has_additives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `additives_id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_offer_has_additives_additives1_idx` (`additives_id`),
  KEY `fk_offer_has_additives_offer1_idx` (`offer_id`),
  CONSTRAINT `fk_offer_has_additives_additives1` FOREIGN KEY (`additives_id`) REFERENCES `additives` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_offer_has_additives_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `FindLunchAndSWA`.`swa_sales_person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `swa_sales_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(60) NOT NULL,
  `second_name` varchar(60) NOT NULL,
  `street` varchar(60) NOT NULL,
  `street_number` varchar(11) NOT NULL,
  `zip` varchar(5) NOT NULL,
  `city` varchar(60) NOT NULL,
  `phone` varchar(60) NOT NULL,
  `country_code` varchar(2) NOT NULL,
  `email` varchar(60) NOT NULL,
  `iban` varchar(60) NOT NULL,
  `bic` varchar(60) NOT NULL,
  `salary_percentage` decimal(2,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_swa_sales_person_country1_idx` (`country_code`),
  CONSTRAINT `fk_swa_sales_person_country1` FOREIGN KEY (`country_code`) REFERENCES `country` (`country_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `FindLunchAndSWA`.`swa_todo_request_typ`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `swa_todo_request_typ` (
  `id` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `FindLunchAndSWA`.`swa_todo_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `swa_todo_list` (
  `id` int(11) NOT NULL,
  `todo_request_typ_id` int(11) NOT NULL,
  `sales_person_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `offer_id` int(11) DEFAULT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_swa_todo_list_swa_sales_person1_idx` (`sales_person_id`),
  KEY `fk_swa_todo_list_restaurant1_idx` (`restaurant_id`),
  KEY `fk_swa_todo_list_offer1_idx` (`offer_id`),
  KEY `fk_swa_todo_list_swa_todo_request_typ1_idx` (`todo_request_typ_id`),
  CONSTRAINT `fk_swa_todo_list_offer1` FOREIGN KEY (`offer_id`) REFERENCES `offer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_swa_todo_list_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_swa_todo_list_swa_sales_person1` FOREIGN KEY (`sales_person_id`) REFERENCES `swa_sales_person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_swa_todo_list_swa_todo_request_typ1` FOREIGN KEY (`todo_request_typ_id`) REFERENCES `swa_todo_request_typ` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle findlunchandswa.bill
CREATE TABLE IF NOT EXISTS `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_number` varchar(12) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `minimum_profit_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `bill_pdf` mediumblob NOT NULL,
  `total_price` decimal(6,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bill_minimum_profit1_idx` (`minimum_profit_id`),
  KEY `fk_bill_restaurant1_idx` (`restaurant_id`),
  CONSTRAINT `fk_bill_minimum_profit1` FOREIGN KEY (`minimum_profit_id`) REFERENCES `minimum_profit` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_bill_restaurant1` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle findlunchandswa.bill_counter
CREATE TABLE IF NOT EXISTS `bill_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `counter` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;