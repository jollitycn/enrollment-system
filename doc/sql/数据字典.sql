INSERT INTO e_r_sys_dictionary( dictionary_id,
parent_id ,
data_code ,
data_type ,
data_value ,
data_english_value ,
data_desc ,
data_order ,
is_read_only ,
is_deleted ,
modify_id ,
modify_name ,
modify_time ,
recruit_school_id) SELECT dictionary_id +1252559728180355074,
IF(parent_id==0,0,parent_id+1252559728180355074),
data_code ,
data_type ,
data_value ,
data_english_value ,
data_desc ,
data_order ,
is_read_only ,
is_deleted ,
modify_id ,
modify_name ,
modify_time ,
'1252559728180355074' FROM e_r_sys_dictionary 

UPDATE  e_r_sys_dictionary SET parent_id = 0 WHERE parent_id = 100


SELECT * FROM e_r_plan_recruit_info pri LEFT JOIN e_r_plan p ON pri.plan_id=  p.plan_id AND recruite_school_id = 