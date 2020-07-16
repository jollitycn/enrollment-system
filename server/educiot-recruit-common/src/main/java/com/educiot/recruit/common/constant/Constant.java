package com.educiot.recruit.common.constant;

/**
 * @author ：liuhao
 * @date ：Created in 2020/1/10
 */
public interface Constant {

    long SYS_LONG_ZERO = 0L;

    int SYS_ZERO = 0;
    int SYS_ONE = 1;
    int SYS_TWO = 2;
    int SYS_THREE = 3;
    int SYS_FOUR = 4;
    int SYS_FIVE = 5;
    int SYS_NEGATIVE_ONE = -1;

    String PERCENT = "%";
    String SLASH = "/";
    String COLON = ":";
    String SYS_ZERO_STR = "0";
    String ONLINE_TARGET = "线上招生目标";
    String UNDERLINE_TARGET = "线下招生目标";

    /**
     * 系统通用的常量
     *
     * @author Pan Juncai
     * @version 1.0
     * @date 2019/8/26 18:00
     */
    final class Sys {
        /**
         * session中图形验证码标识
         */
        public static final String RAND_IMAGE_VALIDATE_CODE = "randImageValidateCode";

        /**
         * session中用户标识
         */
        public static final String LOGIN_USER_CODE = "loginUser";

        /**
         * token
         */
        public static final String TOKEN_STR = "token";


        /**
         * token
         */
        public static final int TOKEN_EXPIRE_TIME = 60 * 60 * 24;

        /**
         * token
         */
        public static final int MSG_CODE_EXPIRE_TIME = 60;

        /**
         * token
         */
        public static final String DEFAULT_TOKEN = "Bearer";

//        /**
//         * 默认的验证码
//         */
//        public static final String DEFAULT_VERIFY_CODE = "888888";

        /**
         * 日期格式化
         */
        public static final String LOCALDATETIME_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 与Redis相关的常量
     *
     * @author Pan Juncai
     * @version 1.0
     * @date 2019/8/26 18:00
     */
    final class RedisKey {
        public static final String LOGIN_USER = Constant.Sys.LOGIN_USER_CODE;
        /**
         * 项目前缀
         */
        private static final String PREFIX = "er:";
        public static final String MSG_CODE = PREFIX + "msg_code";
        /**
         * redis存放token的路径
         */

        public static final String TOKEN_KEY = PREFIX + "pc:" + Sys.TOKEN_STR;
        public static final String GLOBAL_API = PREFIX + "api";
        public static final String API = PREFIX + "pc:api";
        public static final String APP_API = PREFIX + "app:api";
        public static final String WECHAT_API = PREFIX + "wechat:api";
        public static final String GLOBAL_CACHE = PREFIX + "global_cache";
        public static final String SYS_REGIONS = PREFIX + "sys_regions";
        public static final String PROVINCE = PREFIX + "province";
        public static final String SYS_NATIONS = PREFIX + "sys_nations";
        public static final String PLAN = PREFIX + "plan";
        public static final String PREACH_PLAN = PREFIX + "preach_plan";
        /**
         * redis存放token的路径
         */
        public static final String APP_TOKEN_KEY = PREFIX + "app:" + Sys.TOKEN_STR;


        /**
         * redis存放微信小程序token的路径
         */
        public static final String WECHAT_TOKEN_KEY = PREFIX + "wechat:" + Sys.TOKEN_STR;

        private RedisKey() {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * 数据表实际列名
     *
     * @author xuchao
     * @since 2020-04-14
     */
    final class Talble {
        /**
         * 招生目标表-主键id
         */
        public static final String TARGET_ID = "target_id";

        /**
         * 数据字典表-数据编码
         */
        public static final String DATA_CODE = "data_code";

        /**
         * 数据字典表-父id
         */
        public static final String PARENT_ID = "parent_id";

        /**
         * 招生学校表-主键id
         */
        public static final String RECRUIT_SCHOOL_ID = "recruit_school_id";

        /**
         * 招生目标表-学期id
         */
        public static final String SEMESTER_ID = "semester_id";

        /**
         * login表的openid字段
         */
        public static final String LOGIN_OPENID = "openid";

        /**
         * login_relation表的login字段
         */
        public static final String LOGIN_RELATION_LOGIN = "login";

        /**
         * login_relation表的student_info字段
         */
        public static final String LOGIN_RELATION_STUDENT_INFO = "student_info";

        /**
         * student_info表的student_name字段
         */
        public static final String STUDENT_INFO_STUDENT_NAME = "student_name";

        /**
         * student_info表的parent_telephone字段
         */
        public static final String STUDENT_INFO_PARENT_TELEPHONE = "parent_telephone";
        public static final String STUDENT_INFO_STUDENT_TELEPHONE = "student_telephone";
        public static final String PREACH_PLAN_ID = "preach_plan_id";

        public static final String CREATOR_ID = "creator_id";
        public static final String CREATE_TIME = "create_time";
        public static final String STATUS = "status";

        /**
         * student_public_relation表的source_school_id字段
         */
        public static final String SOURCE_SCHOOL_ID = "source_school_id";

        /**
         * student_public_relation表的class_id字段
         */
        public static final String CLASS_ID = "class_id";
    }

    /**
     * 数据字典查询code
     *
     * @author xuchao
     * @since 2020-04-14
     */
    final class Dictionary {

        /**
         * 线上招生目标渠道
         */
        public static final String ONLINE_TARGET = "online_target";

        /**
         * 学校标签Code
         */
        public static final String SCHOOL_TAG = "school_tag";

        /**
         * 招生区域Code
         */
        public static final String RECRUIT_ZONE = "recruit_zone";
    }

    /**
     * 平台请求URL信息
     *
     * @author xuchao
     * @since 2020-04-14
     */
    final class RequestURL {

        /**
         * 获取学校id获取院系列表信息
         */
        public static final String GET_COLLEGR = "http://192.168.1.31:8080/recruit/sys/depart/info";
    }

    /**
     * 微信端用到的常量
     *
     * @author Pan Juncai
     * @version 1.0
     * @date 2020/4/28 15:32
     */
    final class Wechat {

        /**
         * 获取openID时的标识
         */
        public static final String OPENID_FLAG = "openid";
    }
}
