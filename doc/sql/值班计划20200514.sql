SELECT 
  * 
FROM
  (SELECT 
    *,
    COUNT(a.id) amount,
    CASE
      WHEN COUNT(a.id) > 1 
      THEN TRUE 
      ELSE FALSE 
    END conflict 
  FROM
    (SELECT 
      t.* 
    FROM
      (SELECT 
        consultation_id AS id,
        DATE duty_date,
        CONCAT(DATE, ' ', '00:00:00') AS start_time,
        CONCAT(DATE, ' ', '23:59:59') AS end_time,
        daily_receptionist_id user_id,
        daily_receptionist_name user_name,
        '1' AS TYPE 
      FROM
        (
          (SELECT 
            DATE 
          FROM
            (SELECT 
              DATE_FORMAT(
                DATE_ADD(
                  (SELECT 
                    DATE_SUB(start_time, INTERVAL 1 DAY) 
                  FROM
                    e_r_consultation 
                  WHERE semester_id = -7375514770120029148),
                  INTERVAL xc DAY
                ),
                '%Y-%m-%d'
              ) AS DATE 
            FROM
              (SELECT 
                @xi := @xi + 1 AS xc 
              FROM
                (SELECT 
                  1 
                UNION
                SELECT 
                  2 
                UNION
                SELECT 
                  3 
                UNION
                SELECT 
                  4 
                UNION
                SELECT 
                  5 
                UNION
                SELECT 
                  6 
                UNION
                SELECT 
                  7 
                UNION
                SELECT 
                  8 
                UNION
                SELECT 
                  9 
                UNION
                SELECT 
                  10) xc1,
                (SELECT 
                  1 
                UNION
                SELECT 
                  2 
                UNION
                SELECT 
                  3 
                UNION
                SELECT 
                  4 
                UNION
                SELECT 
                  5 
                UNION
                SELECT 
                  6 
                UNION
                SELECT 
                  7 
                UNION
                SELECT 
                  8 
                UNION
                SELECT 
                  9 
                UNION
                SELECT 
                  10) xc2,
                (SELECT 
                  1 
                UNION
                SELECT 
                  2 
                UNION
                SELECT 
                  3 
                UNION
                SELECT 
                  4 
                UNION
                SELECT 
                  5 
                UNION
                SELECT 
                  6 
                UNION
                SELECT 
                  7 
                UNION
                SELECT 
                  8 
                UNION
                SELECT 
                  9 
                UNION
                SELECT 
                  10) xc3,
                (SELECT 
                  @xi := 0) xc0) xcxc) f 
          WHERE f.date <= DATE_FORMAT(
              (SELECT 
                end_time 
              FROM
                e_r_consultation 
              WHERE semester_id = -7375514770120029148),
              '%Y-%m-%d'
            ) 
            AND f.date NOT IN 
            (SELECT 
              DATE_FORMAT(openday_date, '%Y-%m-%d') AS DATE 
            FROM
              e_r_openday 
            WHERE consultation_id IN 
              (SELECT 
                consultation_id 
              FROM
                e_r_consultation 
              WHERE semester_id = -7375514770120029148))) a,
          (SELECT 
            consultation_id,
            semester,
            daily_receptionist_id,
            daily_receptionist_name,
            telephone,
            SCHEDULE,
            address 
          FROM
            e_r_consultation 
          WHERE semester_id = -7375514770120029148) b
        ) 
        UNION
        SELECT 
          consultation_id,
          openday_date duty_date,
          CONCAT(openday_date, ' ', '00:00:00') AS start_time,
          CONCAT(openday_date, ' ', '23:59:59') AS end_time,
          receptionist_id,
          receptionist_name,
          '0' AS TYPE 
        FROM
          e_r_openday 
        WHERE consultation_id IN 
          (SELECT 
            consultation_id 
          FROM
            e_r_consultation 
          WHERE semester_id = -7375514770120029148) 
          UNION
          SELECT 
            `e_r_preach_plan`.`preach_plan_id` AS `id`,
            e_r_preach_plan.`preach_time` AS `duty_date`,
            `e_r_preach_plan`.`preach_time` AS `start_time`,
            `e_r_preach_plan`.`end_time` AS `end_time`,
            `e_r_preach_plan`.`speaker_id` AS `user_id`,
            `e_r_preach_plan`.`speaker_name` AS `user_name`,
            2 AS `type` 
          FROM
            `e_r_preach_plan` 
          WHERE (`e_r_preach_plan`.`status` = 2) 
            UNION
            SELECT 
              p.`preach_plan_id` AS `id`,
              p.`preach_time` AS `duty_date`,
              p.`preach_time` AS `start_time`,
              p.`end_time` AS `end_time`,
              ar.`charger_id` AS `user_id`,
              ar.`charger_name` AS `user_name`,
              3 AS `type` 
            FROM
              `e_r_preach_plan` p 
              LEFT JOIN e_r_preach_assistant_relation ar 
                ON p.preach_plan_id = ar.preach_plan_id 
            WHERE (p.`status` = 2)) t 
            LEFT JOIN 
              (SELECT 
                consultation_id AS id,
                DATE duty_date,
                CONCAT(DATE, ' ', '00:00:00') AS start_time,
                CONCAT(DATE, ' ', '23:59:59') AS end_time,
                daily_receptionist_id user_id,
                daily_receptionist_name user_name,
                '1' AS TYPE 
              FROM
                (
                  (SELECT 
                    DATE 
                  FROM
                    (SELECT 
                      DATE_FORMAT(
                        DATE_ADD(
                          (SELECT 
                            DATE_SUB(start_time, INTERVAL 1 DAY) 
                          FROM
                            e_r_consultation 
                          WHERE semester_id = -7375514770120029148),
                          INTERVAL xc DAY
                        ),
                        '%Y-%m-%d'
                      ) AS DATE 
                    FROM
                      (SELECT 
                        @xi := @xi + 1 AS xc 
                      FROM
                        (SELECT 
                          1 
                        UNION
                        SELECT 
                          2 
                        UNION
                        SELECT 
                          3 
                        UNION
                        SELECT 
                          4 
                        UNION
                        SELECT 
                          5 
                        UNION
                        SELECT 
                          6 
                        UNION
                        SELECT 
                          7 
                        UNION
                        SELECT 
                          8 
                        UNION
                        SELECT 
                          9 
                        UNION
                        SELECT 
                          10) xc1,
                        (SELECT 
                          1 
                        UNION
                        SELECT 
                          2 
                        UNION
                        SELECT 
                          3 
                        UNION
                        SELECT 
                          4 
                        UNION
                        SELECT 
                          5 
                        UNION
                        SELECT 
                          6 
                        UNION
                        SELECT 
                          7 
                        UNION
                        SELECT 
                          8 
                        UNION
                        SELECT 
                          9 
                        UNION
                        SELECT 
                          10) xc2,
                        (SELECT 
                          1 
                        UNION
                        SELECT 
                          2 
                        UNION
                        SELECT 
                          3 
                        UNION
                        SELECT 
                          4 
                        UNION
                        SELECT 
                          5 
                        UNION
                        SELECT 
                          6 
                        UNION
                        SELECT 
                          7 
                        UNION
                        SELECT 
                          8 
                        UNION
                        SELECT 
                          9 
                        UNION
                        SELECT 
                          10) xc3,
                        (SELECT 
                          @xi := 0) xc0) xcxc) f 
                  WHERE f.date <= DATE_FORMAT(
                      (SELECT 
                        end_time 
                      FROM
                        e_r_consultation 
                      WHERE semester_id = -7375514770120029148),
                      '%Y-%m-%d'
                    ) 
                    AND f.date NOT IN 
                    (SELECT 
                      DATE_FORMAT(openday_date, '%Y-%m-%d') AS DATE 
                    FROM
                      e_r_openday 
                    WHERE consultation_id IN 
                      (SELECT 
                        consultation_id 
                      FROM
                        e_r_consultation 
                      WHERE semester_id = -7375514770120029148))) a,
                  (SELECT 
                    consultation_id,
                    semester,
                    daily_receptionist_id,
                    daily_receptionist_name,
                    telephone,
                    SCHEDULE,
                    address 
                  FROM
                    e_r_consultation 
                  WHERE semester_id = -7375514770120029148) b
                ) 
                UNION
                SELECT 
                  consultation_id,
                  openday_date duty_date,
                  CONCAT(openday_date, ' ', '00:00:00') AS start_time,
                  CONCAT(openday_date, ' ', '23:59:59') AS end_time,
                  receptionist_id,
                  receptionist_name,
                  '0' AS TYPE 
                FROM
                  e_r_openday 
                WHERE consultation_id IN 
                  (SELECT 
                    consultation_id 
                  FROM
                    e_r_consultation 
                  WHERE semester_id = -7375514770120029148) 
                  UNION
                  SELECT 
                    `e_r_preach_plan`.`preach_plan_id` AS `id`,
                    e_r_preach_plan.`preach_time` AS `duty_date`,
                    `e_r_preach_plan`.`preach_time` AS `start_time`,
                    `e_r_preach_plan`.`end_time` AS `end_time`,
                    `e_r_preach_plan`.`speaker_id` AS `user_id`,
                    `e_r_preach_plan`.`speaker_name` AS `user_name`,
                    2 AS `type` 
                  FROM
                    `e_r_preach_plan` 
                  WHERE (`e_r_preach_plan`.`status` = 2) 
                    UNION
                    SELECT 
                      p.`preach_plan_id` AS `id`,
                      p.`preach_time` AS `duty_date`,
                      p.`preach_time` AS `start_time`,
                      p.`end_time` AS `end_time`,
                      ar.`charger_id` AS `user_id`,
                      ar.`charger_name` AS `user_name`,
                      3 AS `type` 
                    FROM
                      `e_r_preach_plan` p 
                      LEFT JOIN e_r_preach_assistant_relation ar 
                        ON p.preach_plan_id = ar.preach_plan_id 
                    WHERE (p.`status` = 2)) b 
                    ON (t.user_id = b.user_id) 
                  WHERE (
                      t.start_time < b.end_time 
                      OR t.start_time = b.start_time
                    ) 
                    AND (
                      t.end_time > b.start_time 
                      OR t.end_time = b.end_time
                    )) a 
                GROUP BY a.id,
                  a.type,a.duty_date,a.start_time,a.end_time,a.user_id,a.user_name) a 
              WHERE conflict = 1 
              ORDER BY a.start_time DESC  