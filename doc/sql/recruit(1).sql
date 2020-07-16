/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/4/10 14:01:50                           */
/*==============================================================*/


drop table if exists e_r_aim_student;

drop table if exists e_r_charger;

drop table if exists e_r_class;

drop table if exists e_r_college;

drop table if exists e_r_consultation;

drop table if exists e_r_consultation_feedback;

drop table if exists e_r_consultation_register;

drop table if exists e_r_group;

drop table if exists e_r_group_info;

drop table if exists e_r_group_source_relation;

drop table if exists e_r_major;

drop table if exists e_r_message;

drop table if exists e_r_openday;

drop table if exists e_r_plan;

drop table if exists e_r_plan_recruit_info;

drop table if exists e_r_plan_school_introduction;

drop table if exists e_r_preach_assistant_relation;

drop table if exists e_r_preach_feedback;

drop table if exists e_r_preach_plan;

drop table if exists e_r_preach_register;

drop table if exists e_r_preach_school_relation;

drop table if exists e_r_preach_summary;

drop table if exists e_r_public_relation;

drop table if exists e_r_recruit_school;

drop table if exists e_r_source_school;

drop table if exists e_r_source_school_type;

drop table if exists e_r_sys_dictionary;

drop table if exists e_r_sys_region;

drop table if exists e_r_target;

drop table if exists e_r_target_decomposition;

drop table if exists e_r_willing_student;


/*==============================================================*/
/* Table: e_r_charger                                           */
/*==============================================================*/
create table e_r_charger
(
   charger_id           bigint not null comment '负责人ID',
   group_id             bigint comment '招生组ID',
   name                 varchar(40) comment '姓名',
   account              varchar(40) comment '账号',
   telephone            varchar(40) comment '手机号',
   status               tinyint comment '状态（0：删除，1：正常）',
   is_leader            tinyint default 0 comment '是否是组长（0：否，1：是）',
   primary key (charger_id)
);

alter table e_r_charger comment '招生负责人';

/*==============================================================*/
/* Table: e_r_class                                             */
/*==============================================================*/
create table e_r_class
(
   class_id             bigint not null comment '班级ID',
   source_school_id     bigint comment '生源学校ID',
   class_name           varchar(32) comment '班级名称',
   level                tinyint comment '公关等级（0：高，1：中，2：低）',
   adviser              varchar(16) comment '班主任',
   telephone            varchar(16) comment '联系方式',
   amount               int comment '学生人数',
   willing_amount       int comment '意向生',
   comment              varchar(32) comment '说明',
   create_time          datetime comment '创建时间',
   creator_name         varchar(16) comment '创建人名称',
   creator_id           bigint comment '创建人',
   primary key (class_id)
);

alter table e_r_class comment '班级表';

/*==============================================================*/
/* Table: e_r_college                                           */
/*==============================================================*/
create table e_r_college
(
   college_id           bigint not null comment '院系ID',
   api_id               bigint comment '接口ID',
   college_name         varchar(32) comment '院系名称',
   college_manager      varchar(32) comment '院系负责人',
   recruit_amount       int comment '招生人数',
   recruit_target       varchar(40) comment '招生对象',
   cultivate_aim        varchar(128) comment '培养目标',
   main_course          varchar(128) comment '主要课程',
   career_forward       varchar(128) comment '就业方向',
   comment              varchar(1024) comment '院系简介',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   plan_id              bigint comment '计划ID',
   primary key (college_id)
);

alter table e_r_college comment '招生院系表';

/*==============================================================*/
/* Table: e_r_consultation                                      */
/*==============================================================*/
create table e_r_consultation
(
   consultation_id      bigint not null comment '到校咨询ID',
   start_time           date comment '开始时间',
   end_time             date comment '结束时间',
   year                 int comment '年份',
   semester             varchar(32) comment '学期',
   daily_receptionist_id bigint comment '日常接待人ID',
   daily_receptionist_name varchar(16) comment '日常接待人名称',
   telephone            varchar(32) comment '联系方式',
   schedule             varchar(1024) comment '接待行程',
   address              varchar(1024) comment '接待地址',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (consultation_id)
);

alter table e_r_consultation comment '到校咨询表';

/*==============================================================*/
/* Table: e_r_consultation_feedback                             */
/*==============================================================*/
create table e_r_consultation_feedback
(
   consultation_feedback_id bigint not null comment '咨询反馈ID',
   consultation_id      bigint not null comment '到校咨询ID',
   consultation_date    date comment '咨询日期',
   consultation_content varchar(512) comment '反馈内容',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (consultation_feedback_id)
);

alter table e_r_consultation_feedback comment '咨询反馈表';

/*==============================================================*/
/* Table: e_r_consultation_register                             */
/*==============================================================*/
create table e_r_consultation_register
(
   consultation_register bigint not null comment '招生咨询登记ID',
   consultation_id      bigint comment '到校咨询ID',
   consultation_type    tinyint comment '咨询类型（0：个人，1：学校）',
   source_school_id     bigint comment '生源学校ID',
   is_openday           tinyint comment '是否接待日（0：是，1：否）',
   class_id             bigint comment '班级ID',
   telephone            varchar(32) comment '联系方式',
   consultation_date    date comment '咨询日期',
   attend_time          datetime comment '到校时间',
   is_lunch             tinyint comment '是否用餐（0：是，1：否）',
   comment              varchar(512) comment '说明',
   willing_student_id   bigint not null comment '意向生ID',
   teacher              varchar(16) comment '带队老师',
   amount               int comment '人数',
   primary key (consultation_register)
);

alter table e_r_consultation_register comment '招生咨询登记表';

/*==============================================================*/
/* Table: e_r_group                                             */
/*==============================================================*/
create table e_r_group
(
   group_id             bigint not null comment '招生组ID',
   group_info_id        bigint not null comment '招生组信息ID',
   name                 varchar(40) comment '组别名称',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   comment              varchar(4000) comment '说明',
   dept_id              bigint comment '所属部门（来着api组织结构）',
   dept_name            varchar(32) comment '部门名称',
   area_id              bigint comment '所在区域（来着数据字典）',
   status               tinyint comment '状态（0：删除，1：正常）',
   primary key (group_id)
);

alter table e_r_group comment '招生组表';

/*==============================================================*/
/* Table: e_r_group_info                                        */
/*==============================================================*/
create table e_r_group_info
(
   group_info_id        bigint not null comment '招生组信息ID',
   year                 int comment '年份',
   semester             varchar(32) comment '学期',
   title                varchar(32) comment '标题',
   comment              varchar(1024) comment '说明',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (group_info_id)
);

alter table e_r_group_info comment '招生组信息表';

/*==============================================================*/
/* Table: e_r_group_source_relation                             */
/*==============================================================*/
create table e_r_group_source_relation
(
   group_source_relation_id bigint not null comment '招生组生源学校关联ID',
   group_id             bigint comment '招生组ID',
   source_school_id     bigint comment '生源学校',
   primary key (group_source_relation_id)
);

alter table e_r_group_source_relation comment '招生组生源学校关联表';

/*==============================================================*/
/* Table: e_r_major                                             */
/*==============================================================*/
create table e_r_major
(
   major_id             bigint not null comment '招生专业ID',
   college_id           bigint comment '院系ID',
   api_id               bigint comment '接口ID',
   major_name           varchar(32) comment '专业名称',
   api_major_name       varchar(32) comment '专业名称',
   recruit_amount       int comment '招生人数',
   fee                  decimal comment '学费（元/学期）',
   major_type           varchar(32) comment '学制',
   cultivate_aim        varchar(128) comment '培养目标',
   main_course          varchar(128) comment '主要课程',
   career_forward       varchar(128) comment '就业方向',
   comment              varchar(1024) comment '专业简介',
   creator_id           bigint comment '创建人',
   create_time          datetime comment '创建时间',
   primary key (major_id)
);

alter table e_r_major comment '招生专业表';


/*==============================================================*/
/* Table: e_r_openday                                           */
/*==============================================================*/
create table e_r_openday
(
   openday_id           bigint not null comment '接待日信息ID',
   consultation_id      bigint not null comment '到校咨询ID',
   receptionist_id      bigint comment '接待人ID',
   receptionist_name    varchar(16) comment '接待人名称',
   amount               int comment '接待人数',
   openday_date         date comment '接待日期',
   telephone            varchar(32) comment '联系方式',
   schedule             varchar(1024) comment '接待行程',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (openday_id)
);

alter table e_r_openday comment '接待日信息表';

/*==============================================================*/
/* Table: e_r_plan                                              */
/*==============================================================*/
create table e_r_plan
(
   plan_id              bigint not null comment '招生计划ID',
   recruit_school_id    bigint comment '招生学校ID',
   year                 int comment '年份',
   season               int comment '季度',
   semester             varchar(32) comment '学期',
   title                varchar(40) comment '标题',
   start_time           datetime comment '招生开始时间',
   end_time             datetime comment '招生结束时间',
   charger_id           bigint comment '招生负责人ID',
   charger_name         varchar(16) comment '招生负责人名称',
   publisher_id         bigint comment '发布人',
   publisher_name       bigint comment '发布人名称',
   publish_time         datetime comment '发布时间',
   status               tinyint comment '状态（0：删除，1：未发布，2：已发布）',
   is_defaulted         tinyint default 0 comment '是否默认（0：否，1：是）',
   comment              varchar(1024) comment '说明',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   telephone_number     varchar(16) comment '联系电话',
   modifier_id          bigint comment '修改人id',
   modify_time          datetime comment '修改时间',
   copy_id              bigint comment '拷贝ID',
   primary key (plan_id)
);

alter table e_r_plan comment '招生计划表 ';

/*==============================================================*/
/* Table: e_r_plan_recruit_info                                 */
/*==============================================================*/
create table e_r_plan_recruit_info
(
   plan_recruit_info_id bigint not null comment '招生简介ID',
   plan_id              bigint comment '招生计划ID',
   recruit_target       varchar(40) comment '招生对象',
   attention            varchar(1024) comment '注意事项',
   register             varchar(40) comment '报到和录取',
   plan_description     varchar(1024) comment '招生计划',
   create_time          datetime comment '创建时间',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   primary key (plan_recruit_info_id)
);

alter table e_r_plan_recruit_info comment '招生简介表';

/*==============================================================*/
/* Table: e_r_plan_school_introduction                          */
/*==============================================================*/
create table e_r_plan_school_introduction
(
   plan_school_introduction_id bigint not null comment '招生计划学校简介ID',
   recruit_school_name  varchar(32) comment '招生学校名称',
   recruit_school_logo  varchar(1024) comment '招生学校logo',
   is_deleted           tinyint default 1 comment '是否删除（0：删除，1：正常）',
   create_time          datetime comment '创建时间',
   principal            varchar(32) comment '校长名称',
   telephone_number     varchar(16) comment '联系电话',
   address              varchar(128) comment '学校地址',
   description          varchar(1024) comment '简介',
   api_id               bigint comment '接口ID',
   plan_id              bigint comment '招生计划ID',
   primary key (plan_school_introduction_id)
);

alter table e_r_plan_school_introduction comment '招生计划学校简介';

/*==============================================================*/
/* Table: e_r_preach_assistant_relation                         */
/*==============================================================*/
create table e_r_preach_assistant_relation
(
   preach_assistant_relationt_id bigint not null comment '宣讲协助人关联ID',
   preach_plan_id       bigint comment '招生宣讲计划ID',
   charger_id           bigint comment '负责人ID',
   primary key (preach_assistant_relationt_id)
);

alter table e_r_preach_assistant_relation comment '宣讲协助人关联表';

/*==============================================================*/
/* Table: e_r_preach_feedback                                   */
/*==============================================================*/
create table e_r_preach_feedback
(
   preach_feedback_id   bigint not null comment '宣讲反馈ID',
   preach_plan_id       bigint comment '招生宣讲计划ID',
   willing_student_id   bigint comment '意向生ID',
   perfomance           tinyint comment '宣讲效果（0：好，1：一般，2：差）',
   feedback_content     varchar(512) comment '反馈内容',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (preach_feedback_id)
);

alter table e_r_preach_feedback comment '宣讲反馈表';

/*==============================================================*/
/* Table: e_r_preach_plan                                       */
/*==============================================================*/
create table e_r_preach_plan
(
   preach_plan_id       bigint not null comment '招生宣讲计划ID',
   year                 int comment '年份',
   semester             varchar(32) comment '学期',
   preach_time          datetime comment '宣讲时间',
   speaker_id           bigint comment '主讲人ID',
   speaker_name         varchar(32) comment '主讲人名称',
   plan_amount          int comment '计划人数',
   telephone            varchar(32) comment '联系方式',
   title                varchar(64) comment '主题',
   address              varchar(128) comment '宣讲地点',
   comment              varchar(1024) comment '备注',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (preach_plan_id)
);

alter table e_r_preach_plan comment '招生宣讲计划';

/*==============================================================*/
/* Table: e_r_preach_register                                   */
/*==============================================================*/
create table e_r_preach_register
(
   preach_register_id   varchar(32) not null comment '宣讲计划报名ID',
   preach_plan_id       bigint comment '招生宣讲计划ID',
   willing_student_id   bigint comment '意向生ID',
   status               tinyint comment '状态（0：报名，1：签到）',
   primary key (preach_register_id)
);

alter table e_r_preach_register comment '宣讲计划报名表';

/*==============================================================*/
/* Table: e_r_preach_school_relation                            */
/*==============================================================*/
create table e_r_preach_school_relation
(
   preach_school_relation_id bigint not null comment '宣讲学校关联ID',
   preach_plan_id       bigint comment '招生宣讲计划ID',
   source_school_id     bigint comment '生源学校',
   primary key (preach_school_relation_id)
);

alter table e_r_preach_school_relation comment '宣讲生源学校关联表';

/*==============================================================*/
/* Table: e_r_preach_summary                                    */
/*==============================================================*/
create table e_r_preach_summary
(
   preach_summary_id    bigint not null comment '宣讲总结ID',
   preach_plan_id       bigint comment '招生宣讲计划ID',
   perfomance           tinyint comment '宣讲效果（0：好，1：一般，2：差）',
   feedback_content     varchar(512) comment '反馈内容',
   summery              varchar(512) comment '宣讲总结',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (preach_summary_id)
);

alter table e_r_preach_summary comment '宣讲总结表 ';

/*==============================================================*/
/* Table: e_r_public_relation                                   */
/*==============================================================*/
create table e_r_public_relation
(
   public_relation      bigint not null comment '学校公关ID',
   group_source_relation_id bigint comment '招生组生源学校关联ID',
   group_id             bigint comment '招生组ID',
   source_school_id     bigint comment '生源学校',
   start_time           datetime comment '开始时间',
   end_time             datetime comment '结束时间',
   contacter            varchar(32) comment '沟通人',
   telephone            varchar(32) comment '联系方式',
   content              varchar(1024) comment '学校拜访内容',
   creator_id           bigint comment '创建人',
   creator_name         varchar(16) comment '创建人名称',
   create_time          datetime comment '创建时间',
   primary key (public_relation)
);

alter table e_r_public_relation comment '学校公关表';

/*==============================================================*/
/* Table: e_r_recruit_school                                    */
/*==============================================================*/
create table e_r_recruit_school
(
   recruit_school_id    bigint not null comment '招生学校ID',
   recruit_school_name  varchar(32) comment '招生学校名称',
   recruit_school_logo  varchar(1024) comment '招生学校logo',
   is_deleted           tinyint default 0 comment '是否删除（0：删除，1：正常）',
   create_time          datetime comment '创建时间',
   principal            varchar(32) comment '校长名称',
   telephone_number     varchar(16) comment '联系电话',
   address              varchar(128) comment '学校地址',
   description          varchar(1024) comment '简介',
   api_id               bigint comment '接口ID',
   primary key (recruit_school_id)
);

alter table e_r_recruit_school comment '招生学校';

/*==============================================================*/
/* Table: e_r_source_school                                     */
/*==============================================================*/
create table e_r_source_school
(
   source_school_id     bigint not null comment '生源学校ID',
   recruit_school_id    bigint comment '招生学校ID',
   source_school_name   varchar(32) comment '生源学校名称',
   province             varchar(40) comment '所在省份',
   city                 varchar(40) comment '所在城市',
   district             varchar(40) comment '所在区域',
   is_relation          tinyint comment '是否公关（0：公关，1：不公关）',
   relation_level       tinyint comment '公关等级',
   relation_type        tinyint comment '学校性质（0 公立，1 民营）',
   source_scale         int comment '生源规模',
   qualification        varchar(32) comment '学校资质',
   address              varchar(128) comment '学校地址',
   superior_dept        varchar(32) comment '上级主管部门',
   comment              varchar(500) comment '说明',
   recruit_amount       int comment '中招人数',
   recruit_way          varchar(40) comment '中招方式',
   grade_director_name  varchar(40) comment '年级主任',
   grade_director_tel   varchar(40) comment '年级主任电话',
   class_amount         int comment '班级数量',
   create_time          datetime comment '创建时间',
   creator_name         varchar(16) comment '创建人名称',
   creator_id           bigint comment '创建人',
   status               tinyint default 1 comment '状态（0：删除，1：正常）',
   primary key (source_school_id)
);

alter table e_r_source_school comment '生源学校';

/*==============================================================*/
/* Table: e_r_source_school_type                                */
/*==============================================================*/
create table e_r_source_school_type
(
   source_school_type_id bigint not null comment '生源学校类型ID',
   source_school_id     bigint comment '生源学校ID',
   type_id              bigint comment '数据字典ID',
   primary key (source_school_type_id)
);

alter table e_r_source_school_type comment '生源学校类型';

/*==============================================================*/
/* Table: e_r_sys_dictionary                                    */
/*==============================================================*/
create table e_r_sys_dictionary
(
   dictionary_id        bigint not null comment '数字字典id',
   parent_id            bigint comment '父id',
   data_code            varchar(32) comment '数据编码',
   data_type            varchar(16) comment '数据类型（1：文本，2：布尔值，3：数值，4：日期）',
   data_value           varchar(64) comment '数据名称/数据值',
   data_english_value   varchar(32) comment '数据英文名称',
   data_desc            varchar(128) comment '数据说明',
   data_order           int comment '数据顺序',
   is_read_only         tinyint default 0 comment '是否只读（0：否，1：只读）',
   is_deleted           tinyint default 0 comment '状态（0：正常，1：删除）',
   modify_id            bigint comment '最后修改人',
   modify_time          datetime comment '修改时间',
   recruit_school_id    bigint comment '招生学校ID'
);

alter table e_r_sys_dictionary comment '系统数字字典';

/*==============================================================*/
/* Table: e_r_sys_region                                        */
/*==============================================================*/
create table e_r_sys_region
(
   region_id            bigint not null comment '地区ID',
   parent_id            bigint comment '父ID',
   short_name           varchar(128) comment '简称',
   name                 varchar(128) comment '名称',
   merger_name          varchar(256) comment '全称',
   level                tinyint comment '层级（1：省，2：市，3：区县）',
   pinyin               varchar(128) comment '拼音',
   code                 varchar(128) comment '长途区号',
   zip_code             varchar(16) comment '邮编',
   primary key (region_id)
);

alter table e_r_sys_region comment '系统地区表';

/*==============================================================*/
/* Table: e_r_target                                            */
/*==============================================================*/
create table e_r_target
(
   target_id            bigint not null comment '目标ID',
   year                 int comment '年份',
   semester             varchar(20) comment '学期',
   title                varchar(32) comment '标题',
   target_amount        int comment '目标数量',
   primary key (target_id)
);

alter table e_r_target comment '目标';

/*==============================================================*/
/* Table: e_r_target_decomposition                              */
/*==============================================================*/
create table e_r_target_decomposition
(
   target_decomposition_id bigint not null comment '目标分解ID',
   target_id            bigint comment '目标ID',
   parent_id            bigint comment '父节点ID',
   type                 tinyint comment '类型（0：渠道，1：专业）',
   node_id              varchar(40) comment '节点ID',
   node_name            varchar(40) comment '节点名称',
   target_amount        int comment '目标数量',
   primary key (target_decomposition_id)
);

alter table e_r_target_decomposition comment '招生目标分解(树形表) ';
