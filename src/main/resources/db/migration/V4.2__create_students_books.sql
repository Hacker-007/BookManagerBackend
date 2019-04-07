CREATE TABLE `students_books` (
  `user_id` int(11),
  `b_id` int(11),
  FOREIGN KEY (user_id) REFERENCES `students`(s_id),
  FOREIGN KEY (b_id) REFERENCES `books`(b_id)
);