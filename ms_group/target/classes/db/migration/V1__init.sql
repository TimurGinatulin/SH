CREATE TABLE `group_table` (
  `ip` VARCHAR(16) NOT NULL ,
  `title` VARCHAR(128) NOT NULL,
  `description` VARCHAR(512) NULL,
  `time_stamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`ip`)
  );
CREATE TABLE `sensor_table` (
  `id` BINARY(36) NOT NULL DEFAULT (uuid()),
  `title` VARCHAR(128) NOT NULL,
  `variable` VARCHAR(128) NOT NULL,
  `time_stamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
  );
CREATE TABLE `adjuster_table` (
  `id` BINARY(36) NOT NULL DEFAULT (uuid()),
  `title` VARCHAR(128) NOT NULL,
  `condition_ad` VARCHAR(128) NOT NULL,
  `time_stamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
  );
CREATE TABLE group_adjuster (
  ip_group VARCHAR(16),
  id_adjuster BINARY(36),
  PRIMARY KEY (`ip_group`,`id_adjuster`),
  KEY `fk_group_adjuster_idx` (`ip_group`),
  CONSTRAINT `fk_group_adjuster` FOREIGN KEY (`ip_group`) REFERENCES `group_table` (`ip`),
  CONSTRAINT `fk_adjuster_group` FOREIGN KEY (`id_adjuster`) REFERENCES `adjuster_table` (`id`)
  );
CREATE TABLE group_sensor (
  ip_group VARCHAR(16),
  id_sensor BINARY(36),
  PRIMARY KEY (`ip_group`,`id_sensor`),
  KEY `fk_group_sensor_idx` (`ip_group`),
  CONSTRAINT `fk_group_sensor` FOREIGN KEY (`ip_group`) REFERENCES `group_table` (`ip`),
  CONSTRAINT `fk_sensor_group` FOREIGN KEY (`id_sensor`) REFERENCES `sensor_table` (`id`)
  );