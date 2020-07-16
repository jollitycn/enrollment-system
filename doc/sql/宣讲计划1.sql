SELECT 
  COUNT(1) 
FROM
  (SELECT 
    p.*,
    COUNT(pr.`preach_plan_id`) sign_amount 
  FROM
    e_r_preach_plan p 
    LEFT JOIN e_r_preach_register pr 
      ON p.preach_plan_id = pr.preach_plan_id 
  WHERE p.status != 0 
    AND (
      p.preach_time >= "2021-01-01 00:00:00"
      OR p.preach_time IS NULL
    ) 
    AND (
      p.end_time <= "2021-01-01 00:00:00"
      OR p.end_time IS NULL
    )  
  GROUP BY p.`preach_plan_id`)a