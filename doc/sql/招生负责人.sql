SELECT 
  gi.semester_id,
  c.charger_id,
  c.group_id,
  c.name,
  c.account,
  c.telephone,
  c.status,
  c.is_leader,
  c.api_id 
FROM
  e_r_charger c 
  LEFT JOIN e_r_group g 
    ON c.group_id = g.group_id 
  LEFT JOIN e_r_group_info gi 
    ON g.group_info_id = gi.group_info_id 
  WHERE gi.semester_id = 2956256533177323487
  AND c.api_id = 1366830273319946995 
  SELECT * FROM e_r_charger c WHERE c.api_id = 1366830273319946995 
    AND c.status = 1
    
   SELECT * FROM e_r_group WHERE  group_id IN (1263351779815337986,1263728062445400066)