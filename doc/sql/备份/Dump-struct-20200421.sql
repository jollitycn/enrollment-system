

/*Table structure for table `e_r_aim_major` */

DROP TABLE IF EXISTS `e_r_aim_major`;

CREATE TABLE `e_r_aim_major` (
  `aim_major_id` BIGINT(20) NOT NULL COMMENT '选择专业ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `major_id` BIGINT(20) DEFAULT NULL COMMENT '招生专业ID',
  `level` TINYINT(4) DEFAULT NULL COMMENT '志愿等级',
  `is_adjust` TINYINT(4) DEFAULT '0' COMMENT '是否接受调剂（0：不接受，1：接受）',
  PRIMARY KEY (`aim_major_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='选择专业表';

/*Table structure for table `e_r_aim_student` */

DROP TABLE IF EXISTS `e_r_aim_student`;

CREATE TABLE `e_r_aim_student` (
  `aim_student_id` BIGINT(20) NOT NULL COMMENT '目标生ID',
  `class_id` BIGINT(20) DEFAULT NULL COMMENT '班级ID',
  `student_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生姓名',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `student_telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生联系方式',
  `father_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父亲姓名',
  `father_telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父亲联系方式',
  `mother_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '母亲姓名',
  `mother_telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '母亲联系方式',
  `level` TINYINT(4) DEFAULT NULL COMMENT '公关等级（0：高，1：中，2：低）',
  `reason` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '意向原因',
  `direction` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公关方向',
  PRIMARY KEY (`aim_student_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='目标生表';

/*Table structure for table `e_r_charger` */

DROP TABLE IF EXISTS `e_r_charger`;

CREATE TABLE `e_r_charger` (
  `charger_id` BIGINT(20) NOT NULL COMMENT '负责人ID',
  `group_id` BIGINT(20) DEFAULT NULL COMMENT '招生组ID',
  `name` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `account` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `telephone` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态（0：删除，1：正常）',
  `is_leader` TINYINT(4) DEFAULT '0' COMMENT '是否是组长（0：否，1：是）',
  `api_id` BIGINT(20) DEFAULT NULL COMMENT '接口ID',
  PRIMARY KEY (`charger_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生负责人';

/*Table structure for table `e_r_class` */

DROP TABLE IF EXISTS `e_r_class`;

CREATE TABLE `e_r_class` (
  `class_id` BIGINT(20) NOT NULL COMMENT '班级ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校ID',
  `class_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '班级名称',
  `level` TINYINT(4) DEFAULT NULL COMMENT '公关等级（0：高，1：中，2：低）',
  `adviser` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '班主任',
  `telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `amount` INT(11) DEFAULT NULL COMMENT '学生人数',
  `willing_amount` INT(11) DEFAULT NULL COMMENT '意向生',
  `comment` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`class_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='班级表';

/*Table structure for table `e_r_college` */

DROP TABLE IF EXISTS `e_r_college`;

CREATE TABLE `e_r_college` (
  `college_id` BIGINT(20) NOT NULL COMMENT '院系ID',
  `api_id` BIGINT(20) DEFAULT NULL COMMENT '接口ID',
  `college_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '院系名称',
  `college_manager` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '院系负责人',
  `recruit_amount` INT(11) DEFAULT NULL COMMENT '招生人数',
  `recruit_target` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生对象',
  `cultivate_aim` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '培养目标',
  `main_course` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要课程',
  `career_forward` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业方向',
  `comment` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '院系简介',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `plan_id` BIGINT(20) DEFAULT NULL COMMENT '计划ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `api_college_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接口院系名称',
  PRIMARY KEY (`college_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生院系表';

/*Table structure for table `e_r_communication_record` */

DROP TABLE IF EXISTS `e_r_communication_record`;

CREATE TABLE `e_r_communication_record` (
  `communication_record_id` BIGINT(20) NOT NULL COMMENT '沟通记录ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `communication_date` DATE DEFAULT NULL COMMENT '沟通日期',
  `communication_way` TINYINT(4) DEFAULT NULL COMMENT '沟通方式（0：电话，1：微信，2：见面）',
  `communication_result` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '沟通结果',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`communication_record_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='沟通记录';

/*Table structure for table `e_r_consultation` */

DROP TABLE IF EXISTS `e_r_consultation`;

CREATE TABLE `e_r_consultation` (
  `consultation_id` BIGINT(20) NOT NULL COMMENT '到校咨询ID',
  `start_time` DATE DEFAULT NULL COMMENT '开始时间',
  `end_time` DATE DEFAULT NULL COMMENT '结束时间',
  `semester` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `daily_receptionist_id` BIGINT(20) DEFAULT NULL COMMENT '日常接待人ID',
  `daily_receptionist_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日常接待人名称',
  `telephone` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `schedule` LONGTEXT COLLATE utf8mb4_unicode_ci COMMENT '接待行程',
  `address` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接待地址',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  PRIMARY KEY (`consultation_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='到校咨询表';

/*Table structure for table `e_r_consultation_feedback` */

DROP TABLE IF EXISTS `e_r_consultation_feedback`;

CREATE TABLE `e_r_consultation_feedback` (
  `consultation_feedback_id` BIGINT(20) NOT NULL COMMENT '咨询反馈ID',
  `consultation_id` BIGINT(20) DEFAULT NULL COMMENT '到校咨询ID',
  `consultation_date` DATE DEFAULT NULL COMMENT '咨询日期',
  `consultation_content` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '反馈内容',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `summary` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '总结',
  PRIMARY KEY (`consultation_feedback_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='咨询反馈表';

/*Table structure for table `e_r_consultation_register` */

DROP TABLE IF EXISTS `e_r_consultation_register`;

CREATE TABLE `e_r_consultation_register` (
  `consultation_register_id` BIGINT(20) NOT NULL COMMENT '招生咨询登记ID',
  `consultation_id` BIGINT(20) DEFAULT NULL COMMENT '到校咨询ID',
  `consultation_type` TINYINT(4) DEFAULT NULL COMMENT '咨询类型（0：个人，1：学校）',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校ID',
  `is_openday` TINYINT(4) DEFAULT NULL COMMENT '是否接待日（0：是，1：否）',
  `class_id` BIGINT(20) DEFAULT NULL COMMENT '班级ID',
  `telephone` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `consultation_date` DATE DEFAULT NULL COMMENT '咨询日期',
  `attend_time` DATETIME DEFAULT NULL COMMENT '到校时间',
  `is_lunch` TINYINT(4) DEFAULT NULL COMMENT '是否用餐（0：是，1：否）',
  `comment` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `teacher` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '带队老师',
  `amount` INT(11) DEFAULT NULL COMMENT '人数',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人（负责人）',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称（负责人名称）',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `student_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生姓名',
  `is_attend` TINYINT(4) DEFAULT '0' COMMENT '是否签到（0：否，1：是）',
  PRIMARY KEY (`consultation_register_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生咨询登记表';

/*Table structure for table `e_r_family_member` */

DROP TABLE IF EXISTS `e_r_family_member`;

CREATE TABLE `e_r_family_member` (
  `family_member_id` BIGINT(20) NOT NULL COMMENT '家庭成员ID',
  `student_info` BIGINT(20) DEFAULT NULL COMMENT '学生基本信息ID',
  `relation` TINYINT(4) DEFAULT NULL COMMENT '关系（0：父亲，1：母亲）',
  `name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家长姓名',
  `company` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作单位',
  `position` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职务',
  `telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`family_member_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='家庭成员表';

/*Table structure for table `e_r_group` */

DROP TABLE IF EXISTS `e_r_group`;

CREATE TABLE `e_r_group` (
  `group_id` BIGINT(20) NOT NULL COMMENT '招生组ID',
  `group_info_id` BIGINT(20) NOT NULL COMMENT '招生组信息ID',
  `name` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组别名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `comment` VARCHAR(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `dept_id` BIGINT(20) DEFAULT NULL COMMENT '所属部门（来着api组织结构）',
  `dept_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `area_id` BIGINT(20) DEFAULT NULL COMMENT '所在区域（来着数据字典）',
  `status` TINYINT(4) DEFAULT '1' COMMENT '状态（0：删除，1：正常）',
  `target_amount` INT(11) DEFAULT NULL COMMENT '招生目标数量',
  PRIMARY KEY (`group_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生组表';

/*Table structure for table `e_r_group_info` */

DROP TABLE IF EXISTS `e_r_group_info`;

CREATE TABLE `e_r_group_info` (
  `group_info_id` BIGINT(20) NOT NULL COMMENT '招生组信息ID',
  `semester` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `title` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `comment` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  PRIMARY KEY (`group_info_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生组信息表';

/*Table structure for table `e_r_group_source_relation` */

DROP TABLE IF EXISTS `e_r_group_source_relation`;

CREATE TABLE `e_r_group_source_relation` (
  `group_source_relation_id` BIGINT(20) NOT NULL COMMENT '招生组生源学校关联ID',
  `group_id` BIGINT(20) DEFAULT NULL COMMENT '招生组ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校ID',
  PRIMARY KEY (`group_source_relation_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生组生源学校关联表';

/*Table structure for table `e_r_in_school` */

DROP TABLE IF EXISTS `e_r_in_school`;

CREATE TABLE `e_r_in_school` (
  `in_school_id` BIGINT(20) NOT NULL COMMENT '学生在校信息ID',
  `school_no` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原学籍号',
  `talent` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '特长',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `student_info` BIGINT(20) DEFAULT NULL COMMENT '学生基本信息ID',
  PRIMARY KEY (`in_school_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生在校信息表';

/*Table structure for table `e_r_major` */

DROP TABLE IF EXISTS `e_r_major`;

CREATE TABLE `e_r_major` (
  `major_id` BIGINT(20) NOT NULL COMMENT '招生专业ID',
  `college_id` BIGINT(20) DEFAULT NULL COMMENT '院系ID',
  `api_id` BIGINT(20) DEFAULT NULL COMMENT '接口ID',
  `major_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业名称',
  `api_major_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业名称',
  `recruit_amount` INT(11) DEFAULT NULL COMMENT '招生人数',
  `fee` DECIMAL(10,0) DEFAULT NULL COMMENT '学费（元/学期）',
  `major_type` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学制',
  `cultivate_aim` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '培养目标',
  `main_course` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主要课程',
  `career_forward` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '就业方向',
  `comment` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业简介',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`major_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生专业表';

/*Table structure for table `e_r_news` */

DROP TABLE IF EXISTS `e_r_news`;

CREATE TABLE `e_r_news` (
  `news_id` BIGINT(20) NOT NULL COMMENT '新闻ID',
  `title` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `message_type` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '信息类型',
  `recevier` TINYINT(4) DEFAULT NULL COMMENT '接收端（0：全部，1：学生，2：家长）',
  `belong_plan` TINYINT(4) DEFAULT NULL COMMENT '所属计划（0：不限，1：已发布计划）',
  `message_content` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生计划ID',
  `news_status` TINYINT(4) DEFAULT '0' COMMENT '新闻状态（0：草稿，1：已发布）',
  PRIMARY KEY (`news_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新闻表';

/*Table structure for table `e_r_openday` */

DROP TABLE IF EXISTS `e_r_openday`;

CREATE TABLE `e_r_openday` (
  `openday_id` BIGINT(20) NOT NULL COMMENT '接待日信息ID',
  `consultation_id` BIGINT(20) DEFAULT NULL COMMENT '到校咨询ID',
  `receptionist_id` BIGINT(20) DEFAULT NULL COMMENT '接待人ID',
  `receptionist_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接待人名称',
  `amount` INT(11) DEFAULT NULL COMMENT '接待人数',
  `openday_date` DATE DEFAULT NULL COMMENT '接待日期',
  `telephone` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `schedule` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '接待行程',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `is_openday` TINYINT(4) DEFAULT NULL COMMENT '是否是接待日（0：否，1：是）',
  PRIMARY KEY (`openday_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接待日信息表';

/*Table structure for table `e_r_plan` */

DROP TABLE IF EXISTS `e_r_plan`;

CREATE TABLE `e_r_plan` (
  `plan_id` BIGINT(20) NOT NULL COMMENT '招生计划ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `season` INT(11) DEFAULT NULL COMMENT '季度',
  `semester` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `title` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `start_time` DATETIME DEFAULT NULL COMMENT '招生开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '招生结束时间',
  `charger_id` BIGINT(20) DEFAULT NULL COMMENT '招生负责人ID',
  `charger_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生负责人名称',
  `publisher_id` BIGINT(20) DEFAULT NULL COMMENT '发布人',
  `publisher_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人名称',
  `publish_time` DATETIME DEFAULT NULL COMMENT '发布时间',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态（0：删除，1：未发布，2：已发布）',
  `is_defaulted` TINYINT(4) DEFAULT '0' COMMENT '是否默认（0：否，1：是）',
  `comment` TEXT COLLATE utf8mb4_unicode_ci COMMENT '说明',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `telephone_number` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `modifier_id` BIGINT(20) DEFAULT NULL COMMENT '修改人id',
  `modify_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `copy_id` BIGINT(20) DEFAULT NULL COMMENT '拷贝ID',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  PRIMARY KEY (`plan_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生计划表 ';

/*Table structure for table `e_r_plan_recruit_info` */

DROP TABLE IF EXISTS `e_r_plan_recruit_info`;

CREATE TABLE `e_r_plan_recruit_info` (
  `plan_recruit_info_id` BIGINT(20) NOT NULL COMMENT '招生简介ID',
  `plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生计划ID',
  `recruit_target` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生对象',
  `attention` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '注意事项',
  `register` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报到和录取',
  `plan_description` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生计划',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `is_deleted` TINYINT(4) DEFAULT '0' COMMENT '是否删除（0：删除，1：正常）',
  PRIMARY KEY (`plan_recruit_info_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生简介表';

/*Table structure for table `e_r_plan_school_introduction` */

DROP TABLE IF EXISTS `e_r_plan_school_introduction`;

CREATE TABLE `e_r_plan_school_introduction` (
  `plan_school_introduction_id` BIGINT(20) NOT NULL COMMENT '招生计划学校简介ID',
  `recruit_school_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生学校名称',
  `recruit_school_logo` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生学校logo',
  `is_deleted` TINYINT(4) DEFAULT '1' COMMENT '是否删除（0：删除，1：正常）',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `principal` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '校长名称',
  `telephone_number` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `address` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校地址',
  `description` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `api_id` BIGINT(20) DEFAULT NULL COMMENT '接口ID',
  `plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生计划ID',
  PRIMARY KEY (`plan_school_introduction_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生计划学校简介';

/*Table structure for table `e_r_preach_assistant_relation` */

DROP TABLE IF EXISTS `e_r_preach_assistant_relation`;

CREATE TABLE `e_r_preach_assistant_relation` (
  `preach_assistant_relationt_id` BIGINT(20) NOT NULL COMMENT '宣讲协助人关联ID',
  `preach_plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生宣讲计划ID',
  `charger_id` BIGINT(20) DEFAULT NULL COMMENT '负责人ID',
  PRIMARY KEY (`preach_assistant_relationt_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宣讲协助人关联表';

/*Table structure for table `e_r_preach_feedback` */

DROP TABLE IF EXISTS `e_r_preach_feedback`;

CREATE TABLE `e_r_preach_feedback` (
  `preach_feedback_id` BIGINT(20) NOT NULL COMMENT '宣讲反馈ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `preach_plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生宣讲计划ID',
  `performance` TINYINT(4) DEFAULT NULL COMMENT '宣讲效果（0：好，1：一般，2：差）',
  `feedback_content` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '反馈内容',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`preach_feedback_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宣讲反馈表';

/*Table structure for table `e_r_preach_plan` */

DROP TABLE IF EXISTS `e_r_preach_plan`;

CREATE TABLE `e_r_preach_plan` (
  `preach_plan_id` BIGINT(20) NOT NULL COMMENT '招生宣讲计划ID',
  `semester` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `preach_time` DATETIME DEFAULT NULL COMMENT '宣讲时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  `speaker_id` BIGINT(20) DEFAULT NULL COMMENT '主讲人ID',
  `speaker_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主讲人名称',
  `plan_amount` INT(11) DEFAULT NULL COMMENT '计划人数',
  `telephone` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `title` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '主题',
  `address` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宣讲地点',
  `comment` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态（0：删除，1：草稿，2：已发布）',
  PRIMARY KEY (`preach_plan_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生宣讲计划';

/*Table structure for table `e_r_preach_register` */

DROP TABLE IF EXISTS `e_r_preach_register`;

CREATE TABLE `e_r_preach_register` (
  `preach_register_id` VARCHAR(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '宣讲计划报名ID',
  `preach_plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生宣讲计划ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态（0：报名，1：签到）',
  PRIMARY KEY (`preach_register_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宣讲计划报名表';

/*Table structure for table `e_r_preach_school_relation` */

DROP TABLE IF EXISTS `e_r_preach_school_relation`;

CREATE TABLE `e_r_preach_school_relation` (
  `preach_school_relation_id` BIGINT(20) NOT NULL COMMENT '宣讲学校关联ID',
  `preach_plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生宣讲计划ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校',
  PRIMARY KEY (`preach_school_relation_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宣讲生源学校关联表';

/*Table structure for table `e_r_preach_summary` */

DROP TABLE IF EXISTS `e_r_preach_summary`;

CREATE TABLE `e_r_preach_summary` (
  `preach_summary_id` BIGINT(20) NOT NULL COMMENT '宣讲总结ID',
  `preach_plan_id` BIGINT(20) DEFAULT NULL COMMENT '招生宣讲计划ID',
  `performance` TINYINT(4) DEFAULT NULL COMMENT '宣讲效果（0：好，1：一般，2：差）',
  `feedback_content` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '反馈内容',
  `summery` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '宣讲总结',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`preach_summary_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宣讲总结表 ';

/*Table structure for table `e_r_progress` */

DROP TABLE IF EXISTS `e_r_progress`;

CREATE TABLE `e_r_progress` (
  `progress_id` BIGINT(20) NOT NULL COMMENT '学生报名进度ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `progress_type` TINYINT(4) DEFAULT NULL COMMENT '进度类型（0：报名登记，1：交费定位，2：接收通知，3：入学报名）',
  `progress_content` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '进度内容',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`progress_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生报名进度表';

/*Table structure for table `e_r_public_relation` */

DROP TABLE IF EXISTS `e_r_public_relation`;

CREATE TABLE `e_r_public_relation` (
  `public_relation` BIGINT(20) NOT NULL COMMENT '学校公关ID',
  `group_source_relation_id` BIGINT(20) DEFAULT NULL COMMENT '招生组生源学校关联ID',
  `group_id` BIGINT(20) DEFAULT NULL COMMENT '招生组ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校',
  `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
  `contacter` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '沟通人',
  `telephone` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `content` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校拜访内容',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人（负责人）',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称（负责人名称）',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`public_relation`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学校公关表';

/*Table structure for table `e_r_recruit_school` */

DROP TABLE IF EXISTS `e_r_recruit_school`;

CREATE TABLE `e_r_recruit_school` (
  `recruit_school_id` BIGINT(20) NOT NULL COMMENT '招生学校ID',
  `recruit_school_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生学校名称',
  `recruit_school_logo` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '招生学校logo',
  `is_deleted` TINYINT(4) DEFAULT '0' COMMENT '是否删除（0：删除，1：正常）',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `principal` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '校长名称',
  `telephone_number` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `address` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校地址',
  `description` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '简介',
  `api_id` BIGINT(20) DEFAULT NULL COMMENT '接口ID',
  PRIMARY KEY (`recruit_school_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生学校';

/*Table structure for table `e_r_recruit_way` */

DROP TABLE IF EXISTS `e_r_recruit_way`;

CREATE TABLE `e_r_recruit_way` (
  `recruit_way_id` BIGINT(20) NOT NULL COMMENT '学生招生渠道ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `recruit_type` TINYINT(4) DEFAULT NULL COMMENT '渠道类型（0：线上，1：线下）',
  `online_type` TINYINT(4) DEFAULT NULL COMMENT '线上渠道方式（0：网站招生，1：广告招生，2：朋友介绍）',
  `recommend_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推荐人名称',
  `telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`recruit_way_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生招生渠道表';

/*Table structure for table `e_r_request` */

DROP TABLE IF EXISTS `e_r_request`;

CREATE TABLE `e_r_request` (
  `request_id` BIGINT(20) NOT NULL COMMENT '诉求ID',
  `student_public_relation_id` BIGINT(20) DEFAULT NULL COMMENT '学生公关ID',
  `request_type` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '诉求类型',
  `request_content` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '诉求内容',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `reply_id` BIGINT(20) DEFAULT NULL COMMENT '回复人ID',
  `reply_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回复人名称',
  `reply_time` DATETIME DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`request_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='诉求表';

/*Table structure for table `e_r_score` */

DROP TABLE IF EXISTS `e_r_score`;

CREATE TABLE `e_r_score` (
  `score_id` BIGINT(20) NOT NULL COMMENT '成绩ID',
  `in_school_id` BIGINT(20) DEFAULT NULL COMMENT '学生在校信息ID',
  `course` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '科目',
  `score` DOUBLE DEFAULT NULL COMMENT '成绩',
  PRIMARY KEY (`score_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩表';

/*Table structure for table `e_r_semester` */

DROP TABLE IF EXISTS `e_r_semester`;

CREATE TABLE `e_r_semester` (
  `semester_id` BIGINT(20) NOT NULL COMMENT '学期ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `semester_api_id` BIGINT(20) DEFAULT NULL COMMENT '学期接口ID',
  `semester_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期名称',
  `sync_time` DATETIME DEFAULT NULL COMMENT '同步时间',
  PRIMARY KEY (`semester_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学期表';

/*Table structure for table `e_r_source_school` */

DROP TABLE IF EXISTS `e_r_source_school`;

CREATE TABLE `e_r_source_school` (
  `source_school_id` BIGINT(20) NOT NULL COMMENT '生源学校ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `source_school_name` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生源学校名称',
  `province` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在省份',
  `city` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在城市',
  `district` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所在区域',
  `is_relation` TINYINT(4) DEFAULT NULL COMMENT '是否公关（0：公关，1：不公关）',
  `relation_level` TINYINT(4) DEFAULT NULL COMMENT '公关等级（1：重点公关 2：普通公关 3：重新公关）',
  `relation_type` TINYINT(4) DEFAULT NULL COMMENT '学校性质（0 公立，1 民营）',
  `source_scale` INT(11) DEFAULT NULL COMMENT '生源规模',
  `qualification` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校资质',
  `address` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校地址',
  `superior_dept` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上级主管部门',
  `school_case_comment` VARCHAR(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学校情况说明',
  `comment` VARCHAR(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `recruit_amount` INT(11) DEFAULT NULL COMMENT '中招人数',
  `recruit_way` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '中招方式',
  `grade_director_name` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '年级主任',
  `grade_director_tel` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '年级主任电话',
  `class_amount` INT(11) DEFAULT NULL COMMENT '班级数量',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `status` TINYINT(4) DEFAULT '1' COMMENT '状态（0：删除，1：正常）',
  PRIMARY KEY (`source_school_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生源学校';

/*Table structure for table `e_r_source_school_type` */

DROP TABLE IF EXISTS `e_r_source_school_type`;

CREATE TABLE `e_r_source_school_type` (
  `source_school_type_id` BIGINT(20) NOT NULL COMMENT '生源学校类型ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校ID',
  `type_id` BIGINT(20) DEFAULT NULL COMMENT '数据字典ID',
  PRIMARY KEY (`source_school_type_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生源学校类型';

/*Table structure for table `e_r_student_info` */

DROP TABLE IF EXISTS `e_r_student_info`;

CREATE TABLE `e_r_student_info` (
  `student_info` BIGINT(20) NOT NULL COMMENT '学生基本信息ID',
  `student_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生姓名',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `gender` TINYINT(4) DEFAULT NULL COMMENT '性别（0：男，1：女）',
  `image_url` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '照片url',
  `nation` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '民族',
  `doa` DATE DEFAULT NULL COMMENT '出生年月',
  `id_no` VARCHAR(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `health_status` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身体状况',
  `province` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍省份',
  `city` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍城市',
  `district` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍区域',
  `wechat` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生微信',
  `parent_telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家长联系方式',
  `student_telephone` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学生联系方式',
  `family_province` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家庭省份',
  `family_city` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家庭城市',
  `family_district` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家庭区域',
  `address` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`student_info`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生基本信息表';

/*Table structure for table `e_r_student_public_relation` */

DROP TABLE IF EXISTS `e_r_student_public_relation`;

CREATE TABLE `e_r_student_public_relation` (
  `student_public_relation_id` BIGINT(20) NOT NULL COMMENT '学生公关ID',
  `student_info` BIGINT(20) DEFAULT NULL COMMENT '学生基本信息ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  `source_school_id` BIGINT(20) DEFAULT NULL COMMENT '生源学校ID',
  `class_id` BIGINT(20) DEFAULT NULL COMMENT '班级ID',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `status` TINYINT(4) DEFAULT NULL COMMENT '状态（0：删除，1：正常）',
  `level` TINYINT(4) DEFAULT NULL COMMENT '公关等级（0：高，1：中，2：低）',
  `reason` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '意向原因',
  `direction` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公关方向',
  `relation_type` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公关类型',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '负责人',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '负责人名称',
  `register_status` TINYINT(4) DEFAULT NULL COMMENT '报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生）',
  `semester` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `group_id` BIGINT(20) DEFAULT NULL COMMENT '招生组ID',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  PRIMARY KEY (`student_public_relation_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生公关表';

/*Table structure for table `e_r_sys_dictionary` */

DROP TABLE IF EXISTS `e_r_sys_dictionary`;

CREATE TABLE `e_r_sys_dictionary` (
  `dictionary_id` BIGINT(20) NOT NULL COMMENT '数字字典id',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父id',
  `data_code` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据编码',
  `data_type` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据类型（1：文本，2：布尔值，3：数值，4：日期）',
  `data_value` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据名称/数据值',
  `data_english_value` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据英文名称',
  `data_desc` VARCHAR(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据说明',
  `data_order` INT(11) DEFAULT NULL COMMENT '数据顺序',
  `is_read_only` TINYINT(4) DEFAULT '0' COMMENT '是否只读（0：否，1：只读）',
  `is_deleted` TINYINT(4) DEFAULT '0' COMMENT '状态（0：正常，1：删除）',
  `modify_id` BIGINT(20) DEFAULT NULL COMMENT '最后修改人',
  `modify_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  `modify_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  PRIMARY KEY (`dictionary_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统数字字典';

/*Table structure for table `e_r_sys_region` */

DROP TABLE IF EXISTS `e_r_sys_region`;

CREATE TABLE `e_r_sys_region` (
  `region_id` BIGINT(20) NOT NULL COMMENT '地区ID',
  `parent_id` BIGINT(20) DEFAULT NULL COMMENT '父ID',
  `short_name` VARCHAR(128) DEFAULT NULL COMMENT '简称',
  `name` VARCHAR(128) DEFAULT NULL COMMENT '名称',
  `merger_name` VARCHAR(256) DEFAULT NULL COMMENT '全称',
  `level` TINYINT(4) DEFAULT NULL COMMENT '层级（1：省，2：市，3：区县）',
  `pinyin` VARCHAR(128) DEFAULT NULL COMMENT '拼音',
  `code` VARCHAR(128) DEFAULT NULL COMMENT '长途区号',
  `zip_code` VARCHAR(16) DEFAULT NULL COMMENT '邮编',
  PRIMARY KEY (`region_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统地区表';

/*Table structure for table `e_r_target` */

DROP TABLE IF EXISTS `e_r_target`;

CREATE TABLE `e_r_target` (
  `target_id` BIGINT(20) NOT NULL COMMENT '目标ID',
  `semester` VARCHAR(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期',
  `title` VARCHAR(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `target_amount` INT(11) DEFAULT NULL COMMENT '目标数量',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `modify_id` BIGINT(20) DEFAULT NULL COMMENT '最后修改人',
  `modify_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  `modify_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `semester_id` BIGINT(20) DEFAULT NULL COMMENT '学期ID',
  `recruit_school_id` BIGINT(20) DEFAULT NULL COMMENT '招生学校ID',
  PRIMARY KEY (`target_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='目标';

/*Table structure for table `e_r_target_decomposition` */

DROP TABLE IF EXISTS `e_r_target_decomposition`;

CREATE TABLE `e_r_target_decomposition` (
  `target_decomposition_id` VARCHAR(40) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目标分解ID',
  `target_id` BIGINT(20) DEFAULT NULL COMMENT '目标ID',
  `parent_id` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父节点ID',
  `type` TINYINT(4) DEFAULT NULL COMMENT '类型（0：渠道，1：专业）',
  `node_id` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点ID（平台接口ID）',
  `node_name` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '节点名称',
  `target_amount` INT(11) DEFAULT NULL COMMENT '目标数量',
  `comment` VARCHAR(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`target_decomposition_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生目标分解(树形表) ';

/*Table structure for table `e_r_task` */

DROP TABLE IF EXISTS `e_r_task`;

CREATE TABLE `e_r_task` (
  `task_id` BIGINT(20) NOT NULL COMMENT '招生任务ID',
  `group_info_id` BIGINT(20) DEFAULT NULL COMMENT '招生组信息ID',
  `status` TINYINT(4) DEFAULT '0' COMMENT '状态（0：未完成，1：完成）',
  `title` VARCHAR(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` VARCHAR(400) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `creator_name` VARCHAR(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `creator_id` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `task_type` TINYINT(4) DEFAULT NULL COMMENT '类型（暂未确定）',
  PRIMARY KEY (`task_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='招生任务表';

/*Table structure for table `e_r_v_duty_plan` */

DROP TABLE IF EXISTS `e_r_v_duty_plan`;

/*!50001 DROP VIEW IF EXISTS `e_r_v_duty_plan` */;
/*!50001 DROP TABLE IF EXISTS `e_r_v_duty_plan` */;

/*!50001 CREATE TABLE  `e_r_v_duty_plan`(
 `id` bigint(20) ,
 `type` bigint(20) ,
 `start_time` datetime ,
 `end_time` datetime ,
 `user_id` bigint(20) ,
 `user_name` varchar(32) 
)*/;

/*Table structure for table `e_r_v_student_public_relation` */

DROP TABLE IF EXISTS `e_r_v_student_public_relation`;

/*!50001 DROP VIEW IF EXISTS `e_r_v_student_public_relation` */;
/*!50001 DROP TABLE IF EXISTS `e_r_v_student_public_relation` */;

/*!50001 CREATE TABLE  `e_r_v_student_public_relation`(
 `student_public_relation_id` bigint(20) ,
 `student_name` varchar(16) ,
 `student_info` bigint(20) ,
 `student_telephone` varchar(16) 
)*/;

/*View structure for view e_r_v_duty_plan */

/*!50001 DROP TABLE IF EXISTS `e_r_v_duty_plan` */;
/*!50001 DROP VIEW IF EXISTS `e_r_v_duty_plan` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `e_r_v_duty_plan` AS select `e_r_preach_plan`.`preach_plan_id` AS `id`,1 AS `type`,`e_r_preach_plan`.`preach_time` AS `start_time`,`e_r_preach_plan`.`end_time` AS `end_time`,`e_r_preach_plan`.`speaker_id` AS `user_id`,`e_r_preach_plan`.`speaker_name` AS `user_name` from `e_r_preach_plan` where (`e_r_preach_plan`.`status` <> 0) union all select `e_r_consultation`.`consultation_id` AS `id`,2 AS `type`,`e_r_consultation`.`start_time` AS `start_time`,`e_r_consultation`.`end_time` AS `end_time`,`e_r_consultation`.`daily_receptionist_id` AS `user_id`,`e_r_consultation`.`daily_receptionist_name` AS `user_name` from `e_r_consultation` */;

/*View structure for view e_r_v_student_public_relation */

/*!50001 DROP TABLE IF EXISTS `e_r_v_student_public_relation` */;
/*!50001 DROP VIEW IF EXISTS `e_r_v_student_public_relation` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `e_r_v_student_public_relation` AS (select `r`.`student_public_relation_id` AS `student_public_relation_id`,`i`.`student_name` AS `student_name`,`i`.`student_info` AS `student_info`,`i`.`student_telephone` AS `student_telephone` from (`e_r_student_public_relation` `r` left join `e_r_student_info` `i` on((`r`.`student_info` = `i`.`student_info`)))) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
