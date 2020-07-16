SELECT 
  plan_id,
  recruit_school_id,
  season,
  semester,
  title,
  start_time,
  end_time,
  charger_id,
  charger_name,
  publisher_id,
  publisher_name,
  publish_time,
  STATUS,
  is_defaulted,
  COMMENT,
  creator_id,
  creator_name,
  create_time,
  telephone_number,
  modifier_id,
  modify_time,
  copy_id,
  semester_id 
FROM
  e_r_plan 
WHERE (
    is_defaulted = 1
    AND recruit_school_id = 1252559728180355074
  )