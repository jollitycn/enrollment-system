 
SELECT 
  ss.source_school_id sourceSchoolId,
  ss.source_school_name sourceSchoolName,
  COUNT(pr.source_school_id) amount 
FROM
  e_r_group_source_relation gsr 
  LEFT JOIN e_r_source_school ss 
    ON gsr.`source_school_id` = ss.`source_school_id` 
    LEFT JOIN e_r_public_relation pr 
    ON ss.`source_school_id` = pr.`source_school_id` 
WHERE ss.`status` = 1 
#1262225500789571586
#1262225458418712578
SELECT   ss.source_school_id sourceSchoolId,
  ss.source_school_name sourceSchoolName,
  COUNT(pr.source_school_id) amount  FROM  e_r_group_source_relation gsr  LEFT JOIN e_r_source_school ss 
    ON gsr.`source_school_id` = ss.`source_school_id` 
    LEFT JOIN e_r_public_relation pr 
    ON ss.`source_school_id` = pr.`source_school_id` 
    WHERE ss.`status` = 1 AND gsr.`group_id`=1262225500789571586
    GROUP BY ss.source_school_id ,
  ss.source_school_name 
  