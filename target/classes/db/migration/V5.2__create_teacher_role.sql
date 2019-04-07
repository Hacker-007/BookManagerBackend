CREATE TABLE `teacher_role` (
  `teacher_id` int,
  `role_id` int,
  FOREIGN KEY (teacher_id) REFERENCES `classes`(c_id),
  FOREIGN KEY (role_id) REFERENCES `security_role`(r_id)
);