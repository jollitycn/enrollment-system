
SELECT 
  * 
FROM
  SELECT 
    source_school_id 
  FROM
    e_r_preach_school_relation 
  WHERE preach_plan_id = #{id} 
  
  
  SELECT * FROM e_r_plan_recruit_info pri LEFT JOIN e_r_plan p ON pri.plan_id = p.plan_id WHERE p.recruit_school_id = 1261234366813380610  AND p.semester_id = 5022515296258378413
  