SELECT 
  * 
FROM e_r_v_duty_plan o 
  LEFT JOIN 
    (SELECT DISTINCT 
      t.* 
    FROM e_r_v_duty_plan t 
      LEFT JOIN 
        e_r_v_duty_plan b 
        ON (
          t.start_time <= b.end_time 
          AND t.end_time >= b.start_time 
          AND b.id <> t.id
        )) c 
    ON o.id = c.id 
    AND o.type = c.type 