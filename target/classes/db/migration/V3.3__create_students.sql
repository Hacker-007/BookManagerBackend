CREATE TABLE `students` (
  `s_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `grade` int(11) NOT NULL,
  `teacher` varchar(255) NOT NULL,
  PRIMARY KEY (`s_id`),
  UNIQUE KEY `id_UNIQUE` (`s_id`)
);

INSERT INTO `students` (s_id, username, password, first_name, last_name, grade, teacher) VALUES
(1,  'admin', '$2a$12$ZhGS.zcWt1gnZ9xRNp7inOvo5hIT0ngN7N.pN939cShxKvaQYHnnu', 'Administrator', 'Adminstrator', 0, "NOTEACHER");