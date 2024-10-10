INSERT INTO `tbl_role` (`name`) VALUES ('ROLE_ADMIN'),('ROLE_EMPLOYER'),('ROLE_APPLIER');
INSERT INTO `tbl_applie_status` (`name`) VALUES ('WAITING'), ('REJECT'), ('CANCEL'), ('ACCEPTED');

INSERT INTO `tbl_profile` (`created_date`, `updated_date`, `fullname`) VALUES ('2024-10-09 22:09:50.532898', '2024-10-09 22:09:50.532898', 'KHANG');
INSERT INTO `tbl_account` (`email`, `password`, `role_id`, id) VALUES ('nguyenmanhkhang2002@gmail.com', '$2a$10$D0Q4rcpiaBPLTrjM9q5rYudKngku619jUQbW9sJUoT2Z34sE4EidS', 1, 1);