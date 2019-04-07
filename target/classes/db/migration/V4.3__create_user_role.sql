CREATE TABLE `user_role` (
  `user_id` int,
  `role_id` int,
  FOREIGN KEY (user_id) REFERENCES `students`(s_id),
  FOREIGN KEY (role_id) REFERENCES `security_role`(r_id)
);

INSERT INTO `user_role`(user_id, role_id) VALUES
 (1, 1),
 (1, 2),
 (1, 3);