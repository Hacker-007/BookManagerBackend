CREATE TABLE `classes` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher` varchar(255) NOT NULL,
  primary key (`c_id`),
  unique key `id_UNIQUE` (`c_id`)
);