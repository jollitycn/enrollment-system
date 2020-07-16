
  SELECT * FROM (
        SELECT *, COUNT(a.id) amount, CASE WHEN COUNT(a.id) >1   THEN TRUE ELSE FALSE END conflict FROM (
SELECT  t.*
        FROM e_r_v_duty_plan t
        LEFT JOIN
        e_r_v_duty_plan b
        ON (
        t.user_id = b.user_id
        AND t.start_time < b.end_time
        AND t.end_time > b.start_time
        AND b.id <> t.id
        )
        ) a GROUP BY a.id,a.type
)a

`e_r_recruit_way``e_r_public_relation`