create table `security_role` (
    `r_id` int(11) NOT NULL AUTO_INCREMENT,
    `role_name` varchar(12),
    `description` varchar(45),
    primary key (`r_id`),
    unique key `id_UNIQUE` (`r_id`)
);

INSERT INTO `security_role` (r_id, role_name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO `security_role` (r_id, role_name, description) VALUES (2, 'ROLE_TEACHER', 'Teacher');
INSERT INTO `security_role` (r_id, role_name, description) VALUES (3, 'ROLE_USER', 'Student');