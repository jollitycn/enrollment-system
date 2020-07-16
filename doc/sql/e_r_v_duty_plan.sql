DELIMITER $$

USE `educiotrecruit`$$

DROP VIEW IF EXISTS `e_r_v_duty_plan`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `e_r_v_duty_plan` AS 
SELECT `e_r_preach_plan`.`preach_plan_id` AS `id`,1 AS `type`,`e_r_preach_plan`.`preach_time` AS `start_time`,`e_r_preach_plan`.`end_time` AS `end_time`,`e_r_preach_plan`.`speaker_id` AS `user_id`,`e_r_preach_plan`.`speaker_name` AS `user_name` FROM `e_r_preach_plan` WHERE STATUS !=0 
UNION ALL SELECT `e_r_consultation`.`consultation_id` AS `id`,2 AS `type`,`e_r_consultation`.`start_time` AS `start_time`,`e_r_consultation`.`end_time` AS `end_time`,`e_r_consultation`.`daily_receptionist_id` AS `user_id`,`e_r_consultation`.`daily_receptionist_name` AS `user_name` FROM `e_r_consultation`$$

DELIMITER ;