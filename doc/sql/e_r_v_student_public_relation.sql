
CREATE
    /*[ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]
    [DEFINER = { user | CURRENT_USER }]
    [SQL SECURITY { DEFINER | INVOKER }]*/
    VIEW `educiotrecruit`.`e_r_v_student_public_relation` 
    AS
(SELECT r.student_public_relation_id,i.student_name,i.student_info,i.student_telephone FROM e_r_student_public_relation r LEFT JOIN e_r_student_info i ON r.student_info = i.student_info);


  SELECT * FROM e_r_preach_feedback p LEFT  JOIN  e_r_v_student_public_relation vspr ON p.student_public_relation_id = vspr.student_public_relation_id
            WHERE p.preach_plan_id = 1251399501680836610
        ORDER BY p.create_time DESC
        
         SELECT * FROM e_r_preach_feedback p LEFT  JOIN  e_r_v_student_public_relation vspr ON p.student_public_relation_id = vspr.student_public_relation_id
            WHERE p.preach_plan_id = 1251399501680836610
        ORDER BY p.create_time DESC
        
        
        SELECT * FROM ( SELECT COUNT(1) amount,1 performance FROM e_r_preach_feedback WHERE performance  =1 AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),2 FROM e_r_preach_feedback WHERE performance  =2  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),3 FROM e_r_preach_feedback WHERE performance  =3  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),4 FROM e_r_preach_feedback WHERE performance  =4  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),5 FROM e_r_preach_feedback WHERE performance  =5  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1) ,1  FROM e_r_preach_summary WHERE performance  =1  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),2 FROM e_r_preach_summary WHERE performance  =2  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),3 FROM e_r_preach_summary WHERE performance  =3  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),4 FROM e_r_preach_summary WHERE performance  =4  AND preach_plan_id =  ? UNION ALL SELECT COUNT(1),5 FROM e_r_preach_summary WHERE performance  =5  AND preach_plan_id =  ? )a GROUP BY a.performance