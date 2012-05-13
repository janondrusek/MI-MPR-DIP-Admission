# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.5.16-log
# Server OS:                    Win64
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2012-05-13 15:30:32
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for admission
DROP DATABASE IF EXISTS `admission`;
CREATE DATABASE IF NOT EXISTS `admission` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `admission`;


# Dumping structure for table admission.accomplishment
DROP TABLE IF EXISTS `accomplishment`;
CREATE TABLE IF NOT EXISTS `accomplishment` (
  `accomplishment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accomplishment_type` bigint(20) NOT NULL,
  PRIMARY KEY (`accomplishment_id`),
  KEY `FK8B5E4621C04C2B4A` (`accomplishment_type`),
  CONSTRAINT `FK8B5E4621C04C2B4A` FOREIGN KEY (`accomplishment_type`) REFERENCES `accomplishment_type` (`accomplishment_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.accomplishment_accomplishment_value
DROP TABLE IF EXISTS `accomplishment_accomplishment_value`;
CREATE TABLE IF NOT EXISTS `accomplishment_accomplishment_value` (
  `accomplishment_id` bigint(20) NOT NULL,
  `accomplishment_value_id` bigint(20) NOT NULL,
  PRIMARY KEY (`accomplishment_id`,`accomplishment_value_id`),
  UNIQUE KEY `accomplishment_value_id` (`accomplishment_value_id`),
  KEY `FKEBF20131B2C5151` (`accomplishment_id`),
  KEY `FKEBF201316FF33200` (`accomplishment_value_id`),
  CONSTRAINT `FKEBF201316FF33200` FOREIGN KEY (`accomplishment_value_id`) REFERENCES `accomplishment_value` (`accomplishment_value_id`),
  CONSTRAINT `FKEBF20131B2C5151` FOREIGN KEY (`accomplishment_id`) REFERENCES `accomplishment` (`accomplishment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.accomplishment_type
DROP TABLE IF EXISTS `accomplishment_type`;
CREATE TABLE IF NOT EXISTS `accomplishment_type` (
  `accomplishment_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`accomplishment_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.accomplishment_value
DROP TABLE IF EXISTS `accomplishment_value`;
CREATE TABLE IF NOT EXISTS `accomplishment_value` (
  `accomplishment_value_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`accomplishment_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.address
DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `house_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `post_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `postal_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `street` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `address_type` bigint(20) NOT NULL,
  `city` bigint(20) NOT NULL,
  `country` bigint(20) NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FKBB979BF4BE5FD641` (`city`),
  KEY `FKBB979BF464174828` (`address_type`),
  KEY `FKBB979BF42735CFE1` (`country`),
  CONSTRAINT `FKBB979BF42735CFE1` FOREIGN KEY (`country`) REFERENCES `country` (`country_id`),
  CONSTRAINT `FKBB979BF464174828` FOREIGN KEY (`address_type`) REFERENCES `address_type` (`address_type_id`),
  CONSTRAINT `FKBB979BF4BE5FD641` FOREIGN KEY (`city`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.address_print_line
DROP TABLE IF EXISTS `address_print_line`;
CREATE TABLE IF NOT EXISTS `address_print_line` (
  `address_id` bigint(20) NOT NULL,
  `print_line_id` bigint(20) NOT NULL,
  PRIMARY KEY (`address_id`,`print_line_id`),
  UNIQUE KEY `print_line_id` (`print_line_id`),
  KEY `FK1D9CD371BA8A296A` (`print_line_id`),
  KEY `FK1D9CD371C08D9DEF` (`address_id`),
  CONSTRAINT `FK1D9CD371C08D9DEF` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `FK1D9CD371BA8A296A` FOREIGN KEY (`print_line_id`) REFERENCES `print_line` (`print_line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.address_type
DROP TABLE IF EXISTS `address_type`;
CREATE TABLE IF NOT EXISTS `address_type` (
  `address_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`address_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission
DROP TABLE IF EXISTS `admission`;
CREATE TABLE IF NOT EXISTS `admission` (
  `admission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accepted` tinyint(1) NOT NULL,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `dormitory_request` tinyint(1) DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `admission_state` bigint(20) DEFAULT NULL,
  `faculty` bigint(20) DEFAULT NULL,
  `person` bigint(20) NOT NULL,
  `programme` bigint(20) DEFAULT NULL,
  `result` bigint(20) DEFAULT NULL,
  `user_identity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`admission_id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK1A2180926272B07` (`person`),
  KEY `FK1A21809305D0A12` (`faculty`),
  KEY `FK1A218092AD7252` (`result`),
  KEY `FK1A21809B4E8E102` (`programme`),
  KEY `FK1A218094AF3FECD` (`user_identity`),
  KEY `FK1A21809A2979454` (`admission_state`),
  CONSTRAINT `FK1A21809A2979454` FOREIGN KEY (`admission_state`) REFERENCES `admission_state` (`admission_state_id`),
  CONSTRAINT `FK1A2180926272B07` FOREIGN KEY (`person`) REFERENCES `person` (`person_id`),
  CONSTRAINT `FK1A218092AD7252` FOREIGN KEY (`result`) REFERENCES `admission_result` (`admission_result_id`),
  CONSTRAINT `FK1A21809305D0A12` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`faculty_id`),
  CONSTRAINT `FK1A218094AF3FECD` FOREIGN KEY (`user_identity`) REFERENCES `user_identity` (`user_identity_id`),
  CONSTRAINT `FK1A21809B4E8E102` FOREIGN KEY (`programme`) REFERENCES `programme` (`programme_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_accomplishment
DROP TABLE IF EXISTS `admission_accomplishment`;
CREATE TABLE IF NOT EXISTS `admission_accomplishment` (
  `admission_id` bigint(20) NOT NULL,
  `accomplishment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admission_id`,`accomplishment_id`),
  UNIQUE KEY `accomplishment_id` (`accomplishment_id`),
  KEY `FKC4D66917B2C5151` (`accomplishment_id`),
  KEY `FKC4D6691721474109` (`admission_id`),
  CONSTRAINT `FKC4D6691721474109` FOREIGN KEY (`admission_id`) REFERENCES `admission` (`admission_id`),
  CONSTRAINT `FKC4D66917B2C5151` FOREIGN KEY (`accomplishment_id`) REFERENCES `accomplishment` (`accomplishment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_appeal
DROP TABLE IF EXISTS `admission_appeal`;
CREATE TABLE IF NOT EXISTS `admission_appeal` (
  `admission_id` bigint(20) NOT NULL,
  `appeal_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admission_id`,`appeal_id`),
  UNIQUE KEY `appeal_id` (`appeal_id`),
  KEY `FK54924685E91C39CB` (`appeal_id`),
  KEY `FK5492468521474109` (`admission_id`),
  CONSTRAINT `FK5492468521474109` FOREIGN KEY (`admission_id`) REFERENCES `admission` (`admission_id`),
  CONSTRAINT `FK54924685E91C39CB` FOREIGN KEY (`appeal_id`) REFERENCES `appeal` (`appeal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_appendix
DROP TABLE IF EXISTS `admission_appendix`;
CREATE TABLE IF NOT EXISTS `admission_appendix` (
  `admission_id` bigint(20) NOT NULL,
  `appendix_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admission_id`,`appendix_id`),
  UNIQUE KEY `appendix_id` (`appendix_id`),
  KEY `FK7920913F21474109` (`admission_id`),
  KEY `FK7920913FB598BF4B` (`appendix_id`),
  CONSTRAINT `FK7920913FB598BF4B` FOREIGN KEY (`appendix_id`) REFERENCES `appendix` (`appendix_id`),
  CONSTRAINT `FK7920913F21474109` FOREIGN KEY (`admission_id`) REFERENCES `admission` (`admission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_evaluation
DROP TABLE IF EXISTS `admission_evaluation`;
CREATE TABLE IF NOT EXISTS `admission_evaluation` (
  `admission_id` bigint(20) NOT NULL,
  `evaluation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admission_id`,`evaluation_id`),
  UNIQUE KEY `evaluation_id` (`evaluation_id`),
  KEY `FK65E8153245499E2B` (`evaluation_id`),
  KEY `FK65E8153221474109` (`admission_id`),
  CONSTRAINT `FK65E8153221474109` FOREIGN KEY (`admission_id`) REFERENCES `admission` (`admission_id`),
  CONSTRAINT `FK65E8153245499E2B` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`evaluation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_reference_number
DROP TABLE IF EXISTS `admission_reference_number`;
CREATE TABLE IF NOT EXISTS `admission_reference_number` (
  `admission_id` bigint(20) NOT NULL,
  `reference_number_id` bigint(20) NOT NULL,
  PRIMARY KEY (`admission_id`,`reference_number_id`),
  UNIQUE KEY `reference_number_id` (`reference_number_id`),
  KEY `FK938C1C3321474109` (`admission_id`),
  KEY `FK938C1C3346F7A900` (`reference_number_id`),
  CONSTRAINT `FK938C1C3346F7A900` FOREIGN KEY (`reference_number_id`) REFERENCES `reference_number` (`reference_number_id`),
  CONSTRAINT `FK938C1C3321474109` FOREIGN KEY (`admission_id`) REFERENCES `admission` (`admission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_result
DROP TABLE IF EXISTS `admission_result`;
CREATE TABLE IF NOT EXISTS `admission_result` (
  `admission_result_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  PRIMARY KEY (`admission_result_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.admission_state
DROP TABLE IF EXISTS `admission_state`;
CREATE TABLE IF NOT EXISTS `admission_state` (
  `admission_state_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `desciption` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`admission_state_id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.apology
DROP TABLE IF EXISTS `apology`;
CREATE TABLE IF NOT EXISTS `apology` (
  `apology_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `approved` tinyint(1) NOT NULL,
  `text` longtext COLLATE utf8_bin NOT NULL,
  `registration` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`apology_id`),
  KEY `FKD0AA2FD59ABE752F` (`registration`),
  CONSTRAINT `FKD0AA2FD59ABE752F` FOREIGN KEY (`registration`) REFERENCES `term_registration` (`term_registration_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.apology_appendix
DROP TABLE IF EXISTS `apology_appendix`;
CREATE TABLE IF NOT EXISTS `apology_appendix` (
  `apology_id` bigint(20) NOT NULL,
  `appendix_id` bigint(20) NOT NULL,
  PRIMARY KEY (`apology_id`,`appendix_id`),
  UNIQUE KEY `appendix_id` (`appendix_id`),
  KEY `FK56C87EF35C73CD49` (`apology_id`),
  KEY `FK56C87EF3B598BF4B` (`appendix_id`),
  CONSTRAINT `FK56C87EF3B598BF4B` FOREIGN KEY (`appendix_id`) REFERENCES `appendix` (`appendix_id`),
  CONSTRAINT `FK56C87EF35C73CD49` FOREIGN KEY (`apology_id`) REFERENCES `apology` (`apology_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.appeal
DROP TABLE IF EXISTS `appeal`;
CREATE TABLE IF NOT EXISTS `appeal` (
  `appeal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accepted` tinyint(1) NOT NULL,
  `appeal_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`appeal_id`),
  KEY `FKABE4CD8F79C55344` (`appeal_type`),
  CONSTRAINT `FKABE4CD8F79C55344` FOREIGN KEY (`appeal_type`) REFERENCES `appeal_type` (`appeal_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.appeal_type
DROP TABLE IF EXISTS `appeal_type`;
CREATE TABLE IF NOT EXISTS `appeal_type` (
  `appeal_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`appeal_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.appendix
DROP TABLE IF EXISTS `appendix`;
CREATE TABLE IF NOT EXISTS `appendix` (
  `appendix_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext COLLATE utf8_bin NOT NULL,
  `filename` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mime_type` varchar(255) COLLATE utf8_bin NOT NULL,
  `appendix_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`appendix_id`),
  KEY `FK45ED7DC93F8C0244` (`appendix_type`),
  CONSTRAINT `FK45ED7DC93F8C0244` FOREIGN KEY (`appendix_type`) REFERENCES `appendix_type` (`appendix_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.appendix_type
DROP TABLE IF EXISTS `appendix_type`;
CREATE TABLE IF NOT EXISTS `appendix_type` (
  `appendix_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`appendix_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.attachment
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE IF NOT EXISTS `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `access_type` int(11) DEFAULT NULL,
  `attached_at` datetime DEFAULT NULL,
  `attachment_content_id` bigint(20) NOT NULL,
  `content_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `attachment_size` int(11) DEFAULT NULL,
  `attached_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `task_data_attachments_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8AF75923D627EAC4` (`task_data_attachments_id`),
  KEY `FK8AF7592365D67657` (`attached_by`),
  CONSTRAINT `FK8AF7592365D67657` FOREIGN KEY (`attached_by`) REFERENCES `organizational_entity` (`id`),
  CONSTRAINT `FK8AF75923D627EAC4` FOREIGN KEY (`task_data_attachments_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.boolean_expression
DROP TABLE IF EXISTS `boolean_expression`;
CREATE TABLE IF NOT EXISTS `boolean_expression` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expression` longtext COLLATE utf8_bin,
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `escalation_constraints_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK90A4D30FAFB75F7D` (`escalation_constraints_id`),
  CONSTRAINT `FK90A4D30FAFB75F7D` FOREIGN KEY (`escalation_constraints_id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.city
DROP TABLE IF EXISTS `city`;
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `part` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`city_id`),
  UNIQUE KEY `name` (`name`,`part`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.content
DROP TABLE IF EXISTS `content`;
CREATE TABLE IF NOT EXISTS `content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.country
DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `country_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.deadline
DROP TABLE IF EXISTS `deadline`;
CREATE TABLE IF NOT EXISTS `deadline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadline_date` datetime DEFAULT NULL,
  `escalated` tinyint(1) NOT NULL,
  `deadlines_start_dead_line_id` bigint(20) DEFAULT NULL,
  `deadlines_end_dead_line_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1E04DA587ECCD20B` (`deadlines_start_dead_line_id`),
  KEY `FK1E04DA58E718F332` (`deadlines_end_dead_line_id`),
  CONSTRAINT `FK1E04DA58E718F332` FOREIGN KEY (`deadlines_end_dead_line_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK1E04DA587ECCD20B` FOREIGN KEY (`deadlines_start_dead_line_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.degree
DROP TABLE IF EXISTS `degree`;
CREATE TABLE IF NOT EXISTS `degree` (
  `degree_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`degree_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.delegation_delegates
DROP TABLE IF EXISTS `delegation_delegates`;
CREATE TABLE IF NOT EXISTS `delegation_delegates` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK3B8329372C122ED2` (`entity_id`),
  KEY `FK3B83293736B2F154` (`task_id`),
  CONSTRAINT `FK3B83293736B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK3B8329372C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.disability_type
DROP TABLE IF EXISTS `disability_type`;
CREATE TABLE IF NOT EXISTS `disability_type` (
  `disability_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`disability_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.document
DROP TABLE IF EXISTS `document`;
CREATE TABLE IF NOT EXISTS `document` (
  `document_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` varchar(255) COLLATE utf8_bin NOT NULL,
  `document_type` bigint(20) NOT NULL,
  PRIMARY KEY (`document_id`),
  KEY `FK335CD11B47EF3970` (`document_type`),
  CONSTRAINT `FK335CD11B47EF3970` FOREIGN KEY (`document_type`) REFERENCES `document_type` (`document_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.document_type
DROP TABLE IF EXISTS `document_type`;
CREATE TABLE IF NOT EXISTS `document_type` (
  `document_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`document_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.email_header
DROP TABLE IF EXISTS `email_header`;
CREATE TABLE IF NOT EXISTS `email_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` longtext COLLATE utf8_bin,
  `from_address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `language` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `reply_to_address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.escalation
DROP TABLE IF EXISTS `escalation`;
CREATE TABLE IF NOT EXISTS `escalation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `deadline_escalation_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF0E0EA95C7A04C70` (`deadline_escalation_id`),
  CONSTRAINT `FKF0E0EA95C7A04C70` FOREIGN KEY (`deadline_escalation_id`) REFERENCES `deadline` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.evaluation
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE IF NOT EXISTS `evaluation` (
  `evaluation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  `evaluation_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`evaluation_id`),
  KEY `FK332C073C75725864` (`evaluation_type`),
  CONSTRAINT `FK332C073C75725864` FOREIGN KEY (`evaluation_type`) REFERENCES `evaluation_type` (`evaluation_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.evaluation_type
DROP TABLE IF EXISTS `evaluation_type`;
CREATE TABLE IF NOT EXISTS `evaluation_type` (
  `evaluation_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`evaluation_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.faculty
DROP TABLE IF EXISTS `faculty`;
CREATE TABLE IF NOT EXISTS `faculty` (
  `faculty_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`faculty_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.gender
DROP TABLE IF EXISTS `gender`;
CREATE TABLE IF NOT EXISTS `gender` (
  `gender_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`gender_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.i18ntext
DROP TABLE IF EXISTS `i18ntext`;
CREATE TABLE IF NOT EXISTS `i18ntext` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `text` longtext COLLATE utf8_bin,
  `task_subjects_id` bigint(20) DEFAULT NULL,
  `task_names_id` bigint(20) DEFAULT NULL,
  `task_descriptions_id` bigint(20) DEFAULT NULL,
  `reassignment_documentation_id` bigint(20) DEFAULT NULL,
  `notification_subjects_id` bigint(20) DEFAULT NULL,
  `notification_names_id` bigint(20) DEFAULT NULL,
  `notification_documentation_id` bigint(20) DEFAULT NULL,
  `notification_descriptions_id` bigint(20) DEFAULT NULL,
  `deadline_documentation_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2140804B2162DFB4` (`notification_descriptions_id`),
  KEY `FK2140804BD488CEEB` (`notification_names_id`),
  KEY `FK2140804B5EEBB6D9` (`reassignment_documentation_id`),
  KEY `FK2140804B3330F6D9` (`deadline_documentation_id`),
  KEY `FK2140804B8046A239` (`notification_documentation_id`),
  KEY `FK2140804B69B21EE8` (`task_descriptions_id`),
  KEY `FK2140804BB2FA6B18` (`task_subjects_id`),
  KEY `FK2140804B98B62B` (`task_names_id`),
  KEY `FK2140804BF952CEE4` (`notification_subjects_id`),
  CONSTRAINT `FK2140804BF952CEE4` FOREIGN KEY (`notification_subjects_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK2140804B2162DFB4` FOREIGN KEY (`notification_descriptions_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK2140804B3330F6D9` FOREIGN KEY (`deadline_documentation_id`) REFERENCES `deadline` (`id`),
  CONSTRAINT `FK2140804B5EEBB6D9` FOREIGN KEY (`reassignment_documentation_id`) REFERENCES `reassignment` (`id`),
  CONSTRAINT `FK2140804B69B21EE8` FOREIGN KEY (`task_descriptions_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK2140804B8046A239` FOREIGN KEY (`notification_documentation_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK2140804B98B62B` FOREIGN KEY (`task_names_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK2140804BB2FA6B18` FOREIGN KEY (`task_subjects_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK2140804BD488CEEB` FOREIGN KEY (`notification_names_id`) REFERENCES `notification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.language
DROP TABLE IF EXISTS `language`;
CREATE TABLE IF NOT EXISTS `language` (
  `language_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`language_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.marital_status
DROP TABLE IF EXISTS `marital_status`;
CREATE TABLE IF NOT EXISTS `marital_status` (
  `marital_status_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`marital_status_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.notification
DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `priority` int(11) NOT NULL,
  `escalation_notifications_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK237A88EB3E0890B` (`escalation_notifications_id`),
  CONSTRAINT `FK237A88EB3E0890B` FOREIGN KEY (`escalation_notifications_id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.notification_bas
DROP TABLE IF EXISTS `notification_bas`;
CREATE TABLE IF NOT EXISTS `notification_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK6012C6C02C122ED2` (`entity_id`),
  KEY `FK6012C6C09C76EABA` (`task_id`),
  CONSTRAINT `FK6012C6C09C76EABA` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK6012C6C02C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.notification_email_headers
DROP TABLE IF EXISTS `notification_email_headers`;
CREATE TABLE IF NOT EXISTS `notification_email_headers` (
  `notification` bigint(20) NOT NULL,
  `email_headers` bigint(20) NOT NULL,
  `email_headers_key` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`notification`,`email_headers_key`),
  UNIQUE KEY `email_headers` (`email_headers`),
  KEY `FKAA16E98FF88391FD` (`email_headers`),
  KEY `FKAA16E98FA3B41C18` (`notification`),
  CONSTRAINT `FKAA16E98FA3B41C18` FOREIGN KEY (`notification`) REFERENCES `notification` (`id`),
  CONSTRAINT `FKAA16E98FF88391FD` FOREIGN KEY (`email_headers`) REFERENCES `email_header` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.notification_recipients
DROP TABLE IF EXISTS `notification_recipients`;
CREATE TABLE IF NOT EXISTS `notification_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK685E6D4E2C122ED2` (`entity_id`),
  KEY `FK685E6D4E9C76EABA` (`task_id`),
  CONSTRAINT `FK685E6D4E9C76EABA` FOREIGN KEY (`task_id`) REFERENCES `notification` (`id`),
  CONSTRAINT `FK685E6D4E2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.organizational_entity
DROP TABLE IF EXISTS `organizational_entity`;
CREATE TABLE IF NOT EXISTS `organizational_entity` (
  `dtype` varchar(31) COLLATE utf8_bin NOT NULL,
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.people_assignments_bas
DROP TABLE IF EXISTS `people_assignments_bas`;
CREATE TABLE IF NOT EXISTS `people_assignments_bas` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK5AD75EB2C122ED2` (`entity_id`),
  KEY `FK5AD75EB36B2F154` (`task_id`),
  CONSTRAINT `FK5AD75EB36B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK5AD75EB2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.people_assignments_excl_owners
DROP TABLE IF EXISTS `people_assignments_excl_owners`;
CREATE TABLE IF NOT EXISTS `people_assignments_excl_owners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FKFD3FC3A2C122ED2` (`entity_id`),
  KEY `FKFD3FC3A36B2F154` (`task_id`),
  CONSTRAINT `FKFD3FC3A36B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKFD3FC3A2C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.people_assignments_pot_owners
DROP TABLE IF EXISTS `people_assignments_pot_owners`;
CREATE TABLE IF NOT EXISTS `people_assignments_pot_owners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK3826EB932C122ED2` (`entity_id`),
  KEY `FK3826EB9336B2F154` (`task_id`),
  CONSTRAINT `FK3826EB9336B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK3826EB932C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.people_assignments_recipients
DROP TABLE IF EXISTS `people_assignments_recipients`;
CREATE TABLE IF NOT EXISTS `people_assignments_recipients` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK820367C32C122ED2` (`entity_id`),
  KEY `FK820367C336B2F154` (`task_id`),
  CONSTRAINT `FK820367C336B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK820367C32C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.people_assignments_stakeholders
DROP TABLE IF EXISTS `people_assignments_stakeholders`;
CREATE TABLE IF NOT EXISTS `people_assignments_stakeholders` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FK75304F962C122ED2` (`entity_id`),
  KEY `FK75304F9636B2F154` (`task_id`),
  CONSTRAINT `FK75304F9636B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `FK75304F962C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.person
DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `person_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `birth_identification_number` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthdate` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `firstname` varchar(255) COLLATE utf8_bin NOT NULL,
  `lastname` varchar(255) COLLATE utf8_bin NOT NULL,
  `maidenname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `middlename` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `permanent_residence_granted` tinyint(1) DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `prefix` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `suffix` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `citizenship` bigint(20) DEFAULT NULL,
  `city_of_birth` bigint(20) DEFAULT NULL,
  `country_of_birth` bigint(20) DEFAULT NULL,
  `gender` bigint(20) DEFAULT NULL,
  `marital_status` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  KEY `FKC4E39B55E2D7632B` (`country_of_birth`),
  KEY `FKC4E39B55777AA0A6` (`marital_status`),
  KEY `FKC4E39B5576BD9DF` (`gender`),
  KEY `FKC4E39B55676259EF` (`citizenship`),
  KEY `FKC4E39B55DC872581` (`city_of_birth`),
  KEY `person_email` (`email`),
  CONSTRAINT `FKC4E39B55DC872581` FOREIGN KEY (`city_of_birth`) REFERENCES `city` (`city_id`),
  CONSTRAINT `FKC4E39B55676259EF` FOREIGN KEY (`citizenship`) REFERENCES `country` (`country_id`),
  CONSTRAINT `FKC4E39B5576BD9DF` FOREIGN KEY (`gender`) REFERENCES `gender` (`gender_id`),
  CONSTRAINT `FKC4E39B55777AA0A6` FOREIGN KEY (`marital_status`) REFERENCES `marital_status` (`marital_status_id`),
  CONSTRAINT `FKC4E39B55E2D7632B` FOREIGN KEY (`country_of_birth`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.person_address
DROP TABLE IF EXISTS `person_address`;
CREATE TABLE IF NOT EXISTS `person_address` (
  `person_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`person_id`,`address_id`),
  UNIQUE KEY `address_id` (`address_id`),
  KEY `FK23F8B90A941E29F7` (`person_id`),
  KEY `FK23F8B90AC08D9DEF` (`address_id`),
  CONSTRAINT `FK23F8B90AC08D9DEF` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `FK23F8B90A941E29F7` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.person_disability_type
DROP TABLE IF EXISTS `person_disability_type`;
CREATE TABLE IF NOT EXISTS `person_disability_type` (
  `person_id` bigint(20) NOT NULL,
  `disability_type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`person_id`,`disability_type_id`),
  KEY `FKE8D303D372499170` (`disability_type_id`),
  KEY `FKE8D303D3941E29F7` (`person_id`),
  CONSTRAINT `FKE8D303D3941E29F7` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `FKE8D303D372499170` FOREIGN KEY (`disability_type_id`) REFERENCES `disability_type` (`disability_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.person_document
DROP TABLE IF EXISTS `person_document`;
CREATE TABLE IF NOT EXISTS `person_document` (
  `person_id` bigint(20) NOT NULL,
  `document_id` bigint(20) NOT NULL,
  PRIMARY KEY (`person_id`,`document_id`),
  UNIQUE KEY `document_id` (`document_id`),
  KEY `FKD71F56C555C468F7` (`document_id`),
  KEY `FKD71F56C5941E29F7` (`person_id`),
  CONSTRAINT `FKD71F56C5941E29F7` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`),
  CONSTRAINT `FKD71F56C555C468F7` FOREIGN KEY (`document_id`) REFERENCES `document` (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.print_line
DROP TABLE IF EXISTS `print_line`;
CREATE TABLE IF NOT EXISTS `print_line` (
  `print_line_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  `print_line_type` bigint(20) NOT NULL,
  PRIMARY KEY (`print_line_id`),
  KEY `FK8435A46C62121A3` (`print_line_type`),
  CONSTRAINT `FK8435A46C62121A3` FOREIGN KEY (`print_line_type`) REFERENCES `print_line_type` (`print_line_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.print_line_type
DROP TABLE IF EXISTS `print_line_type`;
CREATE TABLE IF NOT EXISTS `print_line_type` (
  `print_line_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`print_line_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.programme
DROP TABLE IF EXISTS `programme`;
CREATE TABLE IF NOT EXISTS `programme` (
  `programme_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `degree` bigint(20) NOT NULL,
  `language` bigint(20) NOT NULL,
  `study_mode` bigint(20) NOT NULL,
  PRIMARY KEY (`programme_id`),
  UNIQUE KEY `name` (`name`,`study_mode`,`degree`,`language`),
  KEY `FKC6419B1C9798742F` (`study_mode`),
  KEY `FKC6419B1CF2EE370E` (`degree`),
  KEY `FKC6419B1CDDD9FAA6` (`language`),
  CONSTRAINT `FKC6419B1CDDD9FAA6` FOREIGN KEY (`language`) REFERENCES `language` (`language_id`),
  CONSTRAINT `FKC6419B1C9798742F` FOREIGN KEY (`study_mode`) REFERENCES `study_mode` (`study_mode_id`),
  CONSTRAINT `FKC6419B1CF2EE370E` FOREIGN KEY (`degree`) REFERENCES `degree` (`degree_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.reassignment
DROP TABLE IF EXISTS `reassignment`;
CREATE TABLE IF NOT EXISTS `reassignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `escalation_reassignments_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6881B140A5C17EE0` (`escalation_reassignments_id`),
  CONSTRAINT `FK6881B140A5C17EE0` FOREIGN KEY (`escalation_reassignments_id`) REFERENCES `escalation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.reassignment_potential_owners
DROP TABLE IF EXISTS `reassignment_potential_owners`;
CREATE TABLE IF NOT EXISTS `reassignment_potential_owners` (
  `task_id` bigint(20) NOT NULL,
  `entity_id` varchar(255) COLLATE utf8_bin NOT NULL,
  KEY `FKF13A3C802C122ED2` (`entity_id`),
  KEY `FKF13A3C80E17E130F` (`task_id`),
  CONSTRAINT `FKF13A3C80E17E130F` FOREIGN KEY (`task_id`) REFERENCES `reassignment` (`id`),
  CONSTRAINT `FKF13A3C802C122ED2` FOREIGN KEY (`entity_id`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.reference_number
DROP TABLE IF EXISTS `reference_number`;
CREATE TABLE IF NOT EXISTS `reference_number` (
  `reference_number_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_bin NOT NULL,
  `executed` datetime NOT NULL,
  `reference_number_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`reference_number_id`),
  KEY `FKD790DEBDE8ACBC79` (`reference_number_type`),
  CONSTRAINT `FKD790DEBDE8ACBC79` FOREIGN KEY (`reference_number_type`) REFERENCES `reference_number_type` (`reference_number_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.reference_number_type
DROP TABLE IF EXISTS `reference_number_type`;
CREATE TABLE IF NOT EXISTS `reference_number_type` (
  `reference_number_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`reference_number_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.study_mode
DROP TABLE IF EXISTS `study_mode`;
CREATE TABLE IF NOT EXISTS `study_mode` (
  `study_mode_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`study_mode_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.sub_tasks_strategy
DROP TABLE IF EXISTS `sub_tasks_strategy`;
CREATE TABLE IF NOT EXISTS `sub_tasks_strategy` (
  `dtype` varchar(100) COLLATE utf8_bin NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72E9E0E336B2F154` (`task_id`),
  CONSTRAINT `FK72E9E0E336B2F154` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.task
DROP TABLE IF EXISTS `task`;
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allowed_to_delegate` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `priority` int(11) NOT NULL,
  `activation_time` datetime DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `document_access_type` int(11) DEFAULT NULL,
  `document_content_id` bigint(20) NOT NULL,
  `document_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `expiration_time` datetime DEFAULT NULL,
  `fault_access_type` int(11) DEFAULT NULL,
  `fault_content_id` bigint(20) NOT NULL,
  `fault_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `fault_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `output_access_type` int(11) DEFAULT NULL,
  `output_content_id` bigint(20) NOT NULL,
  `output_type` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `previous_status` int(11) DEFAULT NULL,
  `process_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `process_instance_id` bigint(20) NOT NULL,
  `process_session_id` int(11) NOT NULL,
  `skipable` tinyint(1) NOT NULL,
  `status` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `work_item_id` bigint(20) NOT NULL,
  `task_initiator` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `actual_owner` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK363585E3F7BE93` (`created_by`),
  KEY `FK363585DB863247` (`actual_owner`),
  KEY `FK3635859346A57A` (`task_initiator`),
  CONSTRAINT `FK3635859346A57A` FOREIGN KEY (`task_initiator`) REFERENCES `organizational_entity` (`id`),
  CONSTRAINT `FK363585DB863247` FOREIGN KEY (`actual_owner`) REFERENCES `organizational_entity` (`id`),
  CONSTRAINT `FK363585E3F7BE93` FOREIGN KEY (`created_by`) REFERENCES `organizational_entity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.task_comment
DROP TABLE IF EXISTS `task_comment`;
CREATE TABLE IF NOT EXISTS `task_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `added_at` datetime DEFAULT NULL,
  `text` longtext COLLATE utf8_bin,
  `added_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `task_data_comments_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK61F475A5C12024EA` (`task_data_comments_id`),
  KEY `FK61F475A548F8057B` (`added_by`),
  CONSTRAINT `FK61F475A548F8057B` FOREIGN KEY (`added_by`) REFERENCES `organizational_entity` (`id`),
  CONSTRAINT `FK61F475A5C12024EA` FOREIGN KEY (`task_data_comments_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.term
DROP TABLE IF EXISTS `term`;
CREATE TABLE IF NOT EXISTS `term` (
  `term_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apology_to` datetime NOT NULL,
  `capacity` int(11) NOT NULL,
  `date_of_term` datetime NOT NULL,
  `register_from` datetime NOT NULL,
  `register_to` datetime NOT NULL,
  `room` varchar(255) COLLATE utf8_bin NOT NULL,
  `term_type` bigint(20) NOT NULL,
  PRIMARY KEY (`term_id`),
  UNIQUE KEY `date_of_term` (`date_of_term`,`room`),
  KEY `FK36446C11E82724` (`term_type`),
  CONSTRAINT `FK36446C11E82724` FOREIGN KEY (`term_type`) REFERENCES `term_type` (`term_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.term_programme
DROP TABLE IF EXISTS `term_programme`;
CREATE TABLE IF NOT EXISTS `term_programme` (
  `term_id` bigint(20) NOT NULL,
  `programme_id` bigint(20) NOT NULL,
  PRIMARY KEY (`term_id`,`programme_id`),
  KEY `FKB22F4309C2BDD8EB` (`term_id`),
  KEY `FKB22F43093B49F704` (`programme_id`),
  CONSTRAINT `FKB22F43093B49F704` FOREIGN KEY (`programme_id`) REFERENCES `programme` (`programme_id`),
  CONSTRAINT `FKB22F4309C2BDD8EB` FOREIGN KEY (`term_id`) REFERENCES `term` (`term_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.term_registration
DROP TABLE IF EXISTS `term_registration`;
CREATE TABLE IF NOT EXISTS `term_registration` (
  `term_registration_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attended` tinyint(1) DEFAULT NULL,
  `admission` bigint(20) NOT NULL,
  `term` bigint(20) NOT NULL,
  PRIMARY KEY (`term_registration_id`),
  UNIQUE KEY `admission` (`admission`,`term`),
  KEY `FKECCC9DAC14DCE801` (`admission`),
  KEY `FKECCC9DAC17CE5789` (`term`),
  CONSTRAINT `FKECCC9DAC17CE5789` FOREIGN KEY (`term`) REFERENCES `term` (`term_id`),
  CONSTRAINT `FKECCC9DAC14DCE801` FOREIGN KEY (`admission`) REFERENCES `admission` (`admission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.term_type
DROP TABLE IF EXISTS `term_type`;
CREATE TABLE IF NOT EXISTS `term_type` (
  `term_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`term_type_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_identity
DROP TABLE IF EXISTS `user_identity`;
CREATE TABLE IF NOT EXISTS `user_identity` (
  `user_identity_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authentication` varchar(255) COLLATE utf8_bin NOT NULL,
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_password` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_identity_id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKFAEBCAB2EC369287` (`user_password`),
  CONSTRAINT `FKFAEBCAB2EC369287` FOREIGN KEY (`user_password`) REFERENCES `user_password` (`user_password_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_identity_role
DROP TABLE IF EXISTS `user_identity_role`;
CREATE TABLE IF NOT EXISTS `user_identity_role` (
  `user_identity_id` bigint(20) NOT NULL,
  `user_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_identity_id`,`user_role_id`),
  KEY `FK8114DE3B8D0A763` (`user_role_id`),
  KEY `FK8114DE3456285E3` (`user_identity_id`),
  CONSTRAINT `FK8114DE3456285E3` FOREIGN KEY (`user_identity_id`) REFERENCES `user_identity` (`user_identity_id`),
  CONSTRAINT `FK8114DE3B8D0A763` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_password
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE IF NOT EXISTS `user_password` (
  `user_password_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hash` varchar(255) COLLATE utf8_bin NOT NULL,
  `salt` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_password_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_permission
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE IF NOT EXISTS `user_permission` (
  `user_permission_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_permission_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_role
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_role_permission
DROP TABLE IF EXISTS `user_role_permission`;
CREATE TABLE IF NOT EXISTS `user_role_permission` (
  `user_role_id` bigint(20) NOT NULL,
  `user_permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_role_id`,`user_permission_id`),
  KEY `FK2C1620C4B8D0A763` (`user_role_id`),
  KEY `FK2C1620C441948A03` (`user_permission_id`),
  CONSTRAINT `FK2C1620C441948A03` FOREIGN KEY (`user_permission_id`) REFERENCES `user_permission` (`user_permission_id`),
  CONSTRAINT `FK2C1620C4B8D0A763` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.


# Dumping structure for table admission.user_session
DROP TABLE IF EXISTS `user_session`;
CREATE TABLE IF NOT EXISTS `user_session` (
  `user_session_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `grant_valid_to` datetime NOT NULL,
  `identifier` varchar(255) COLLATE utf8_bin NOT NULL,
  `user_identity_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_session_id`),
  UNIQUE KEY `identifier` (`identifier`),
  KEY `FKD1401A22456285E3` (`user_identity_id`),
  CONSTRAINT `FKD1401A22456285E3` FOREIGN KEY (`user_identity_id`) REFERENCES `user_identity` (`user_identity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
