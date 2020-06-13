
CREATE TABLE `cars` (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name varchar(100) NULL,
	manufacture_name varchar(100) NULL,
	model varchar(100) NULL,
	manufacturing_year varchar(100) NULL,
	color varchar(100) NULL,
	CONSTRAINT cars_pk PRIMARY KEY (id)
);


CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(100) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `created_by` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);


CREATE TABLE `user_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(1000) NOT NULL,
  `token_type` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);