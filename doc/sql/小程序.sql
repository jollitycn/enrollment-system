SELECT 
  c.charger_id,
  c.`name`,
  g.group_id 
FROM
  e_r_group_source_relation gsr 
  LEFT JOIN e_r_group g 
    ON gsr.group_id = g.group_id 
  LEFT JOIN e_r_group_info gi 
    ON g.group_info_id = gi.group_info_id 
  LEFT JOIN e_r_charger c 
    ON c.group_id = g.group_id 
WHERE gsr.source_school_id = 1260047342774538242
  AND gi.semester_id = 1130110208874661580
  AND c.is_leader = 1 
  AND c.status = 1 
  
  SELECT * FROM e_r_charger c   LEFT JOIN e_r_group g 
    ON c.group_id = g.group_id 
    LEFT JOIN e_r_group_info gi 
    ON g.group_info_id = gi.group_info_id 
    LEFT JOIN  e_r_group_source_relation gsr 
     ON gsr.group_id = g.group_id 
      WHERE c.is_leader = 1 
  AND c.status = 1 AND gsr.source_school_id = 1260047342774538242
  
  //同一个手机多个openid
  
  SELECT DISTINCT * FROM (
  SELECT lr.* FROM e_r_login_relation lr WHERE lr.login = 1262224759124217857
  UNION ALL 
  SELECT lr.* FROM e_r_login_relation lr LEFT JOIN e_r_login l ON  lr.login = l.login  WHERE l.mobile IN (SELECT mobile FROM e_r_login WHERE login= 1262224759124217857)
  )a
   #查询学生公关信息
  SELECT * FROM e_r_student_public_relation  WHERE student_info = 1260845809352069121
  ##查询生源学校
  SELECT * FROM e_r_source_school WHERE source_school_id = 1260047342774538242
  ##查询班级
  SELECT * FROM e_r_class WHERE class_id = 1260130705426452481
  