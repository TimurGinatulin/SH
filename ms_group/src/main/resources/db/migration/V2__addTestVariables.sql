INSERT INTO `group_table` (`ip`,`title`,`description`) VALUES ("0.0.0.0","test group","test entity");
INSERT INTO `sensor_table` (`title`,`variable`) VALUES ("test sensor","0.0");
INSERT INTO `adjuster_table` (`title`,`condition_ad`) VALUES ("test adjuster","ON");
INSERT INTO `group_adjuster` VALUES ("0.0.0.0",(SELECT `id` FROM adjuster_table WHERE `title` = "test adjuster"));
INSERT INTO `group_sensor` VALUES ("0.0.0.0",(SELECT `id` FROM sensor_table WHERE `title` = "test sensor"));