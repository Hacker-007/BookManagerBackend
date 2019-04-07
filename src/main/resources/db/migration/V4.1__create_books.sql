CREATE TABLE `books` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `isbn` varchar(255) NOT NULL,
  `redemption_code` varchar(255) NOT NULL,
  `checked_out` boolean,
  `students_s_id` int(11),
  primary key (`b_id`),
  unique key `id_UNIQUE` (`b_id`)
);
