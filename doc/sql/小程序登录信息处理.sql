SELECT * FROM e_r_login l LEFT JOIN e_r_login_relation lr ON l.`login` = lr.`login` WHERE mobile = 15992221884

1268464874925670402


SELECT * FROM `e_r_student_info` where student_info in (1272435577999978497,1272809619596636161)
select * from e_r_login_relation lr where lr.student_info in(1272435577999978497,1272809619596636161)

delete from e_r_login  where login in (
select login from e_r_login_relation lr where lr.student_info in(1272435577999978497,1272809619596636161))

DELETE FROM e_r_login WHERE mobile =17503045588;
DELETE FROM e_r_login_relation WHERE login NOT IN (SELECT login FROM e_r_login);
DELETE FROM e_r_student_info WHERE student_info	IN (SELECT  student_info FROM e_r_login_relation WHERE login NOT IN (SELECT login FROM e_r_login));


SELECT * FROM e_r_student_info si LEFT JOIN e_r_login_relation lr ON si.`student_info` = lr.`student_info` WHERE lr.`login`='1272526527594098690'

SELECT b.*,si.`student_name` FROM (SELECT DISTINCT
  *
FROM
  (SELECT
    lr.*
  FROM
    e_r_login_relation lr
  WHERE lr.login = 1272435579463790594
  UNION
  ALL
  SELECT
    lr.*
  FROM
    e_r_login_relation lr
    LEFT JOIN e_r_login l
      ON lr.login = l.login
  WHERE l.mobile IN
    (SELECT
      mobile
    FROM
      e_r_login
    WHERE login = 1272435579463790594)) a  GROUP BY student_info)b LEFT JOIN e_r_student_info si ON b.student_info = si.`student_info`
    
    
    
    SELECT * FROM e_r_login_relation WHERE student_info  IN (1272809619596636161,1272435577999978497)
    