 SELECT feedback_content,COUNT(1) amount  FROM e_r_preach_summary p
     WHERE p.preach_plan_id =  #{query.preachPlanId} 
     GROUP BY p.feedback_content
