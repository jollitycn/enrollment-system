package com.educiot.common.base;

/**
 * @program: hd
 * @description: 错误CODE和MSG
 * @author: XuChao
 * @create: 2019-12-24 14:57
 **/
public class CodeMsg {

    public static CodeMsg PREACH_PLAN_SPEAKERID_ASSISTANTID_DUPLICATE = new CodeMsg(500110, "主讲人和协助人重复");
    // 按照模块定义CodeMsg
    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg API_NOT_CONFIG = new CodeMsg(10000, "平台接口未配置");
    public static CodeMsg API_SUCCESS = new CodeMsg(200, "平台接口调用成功");
    public static CodeMsg API_FAILED = new CodeMsg(10004, "平台接口调用失败");
    public static CodeMsg API_NOT_LOGIN = new CodeMsg(10001, "平台接口登录失败");
    public static CodeMsg API_MAJOR_LIST_FAIL = new CodeMsg(110001, "专业接口失败");
    public static CodeMsg API_DEPT_INFO_FAIL = new CodeMsg(110002, "院系接口失败");
    public static CodeMsg API_REQUEST_LOGIN_NOT_FOUND = new CodeMsg(110003, "API登录信息获取失败");
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(500100, "服务端异常");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(500101, "输入参数为空");
    public static CodeMsg PERMISSION_ERROR = new CodeMsg(500102, "你没有权限访问");
    //    public static CodeMsg SYS_USER_NOT_LOGIN = new CodeMsg(500003, "后台账号未登录");
    public static CodeMsg ID_NOT_EXIST = new CodeMsg(500104, "ID不存在");
    public static CodeMsg NO_DATA = new CodeMsg(500105, "暂无数据");
    public static CodeMsg TOKEN_NOT_EXIST = new CodeMsg(500106, "登录失效，请重新登录！");
    public static CodeMsg LOGIN_CHARGER_NOT_EXIST = new CodeMsg(500107, "责任人不存在！");
    public static CodeMsg LOGIN_DEFAULT_PLAN_NOT_EXIST = new CodeMsg(500108, "默认计划不存在！");
    public static CodeMsg DIC_NOT_FOUND = new CodeMsg(500109, "招生学校数据字典不存在!");
     public static CodeMsg SEMESTER_NOT_FOUND = new CodeMsg(500110, "学期不存在!");



    // web端业务异常
    public static CodeMsg GROUP_NOT_EXIST = new CodeMsg(500010, "分组不存在");
    public static CodeMsg SOURCE_SCHOOL_NOT_EXIST = new CodeMsg(500011, "生源学校不存在");
    public static CodeMsg DATA_INSERT_ERROR = new CodeMsg(500012, "数据添加失败");
    public static CodeMsg DATA_DELETE_ERROR = new CodeMsg(500013, "数据删除失败");
    public static CodeMsg DATA_UPDATE_ERROR = new CodeMsg(500014, "数据更新失败");
    public static CodeMsg DIC_CODE_REPEAT = new CodeMsg(500015, "数据字典编码不能重复");
    public static CodeMsg DIC_DELETE_ERROR = new CodeMsg(500016, "该数据字典有子数据，无法删除");
    public static CodeMsg RECRUIT_SCHOOL_NOT_EXIST = new CodeMsg(500017, "招生学校不存在");
    public static CodeMsg PLAN_NOT_EXIST = new CodeMsg(500018, "招生计划不存在");
    public static CodeMsg YEAR_OR_SEMESTER_EXIST = new CodeMsg(500019, "该学期已创建计划");
    public static CodeMsg PLAN_RECRUIT_INFO_EXIST = new CodeMsg(500020, "招生简介不存在");
    public static CodeMsg COLLEGE_INFO_NOT_EXIST = new CodeMsg(500021, "招生院系不存在");
    public static CodeMsg MAJOR_INFO_NOT_EXIST = new CodeMsg(500022, "招生专业不存在");
    public static CodeMsg PLAN_NOT_DELETE = new CodeMsg(500023, "已发布状态不允许删除");
    public static CodeMsg GROUP_INFO_NOT_EXIST = new CodeMsg(500024, "招生区域不存在");
    public static CodeMsg REPEAT_ADD = new CodeMsg(500025, "请勿重复添加");
    public static CodeMsg REPEAT_DAY = new CodeMsg(500026, "一天只能有一个咨询反馈");
    public static CodeMsg CONSULTATION_EXIST = new CodeMsg(500027, "已存在咨询计划");
    public static CodeMsg GROUP_HAS_LEADER = new CodeMsg(500028, "分组下已有组长");
    public static CodeMsg REPEAT__OPENDAY = new CodeMsg(500029, "一天只能有一个到校咨询");
    public static CodeMsg FILE_CANNOT_EMPTY = new CodeMsg(500026, "文件不能为空");
    public static CodeMsg FILE_FORMATTER_ERROR = new CodeMsg(500027, "文件表头不匹配");
    public static CodeMsg SOURCE_SCHOOL_BATCH_IMPORT_ERROR = new CodeMsg(500028, "批量导入失败");
    public static CodeMsg SCHOOL_HAS_RELATION_GROUP = new CodeMsg(500029, "学校关联了分组，不能删除");
    public static CodeMsg CHARGER_HAS_RELATION_GROUP = new CodeMsg(500030, "分组下还有负责人关联，不能删除");
    public static CodeMsg CRECRUIT_SCHOOL_EXIST = new CodeMsg(500031, "招生学校已存在");
    public static CodeMsg DATA_COPY_ERROR = new CodeMsg(500032, "数据复制失败");
    public static CodeMsg TARGET_NOT_EXIST = new CodeMsg(500033, "该学期招生目标不存在，不能创建招生计划");
    public static CodeMsg PLAN_DEFAULT_NOT_FOUND = new CodeMsg(500034, "当前还没有建立默认计划,请先创建!");
    public static CodeMsg CHARGE_HAS_BIND_GROUP = new CodeMsg(500035, "该负责人已经绑定分组");
    public static CodeMsg TARGET_GROUP_NOT_EXIST = new CodeMsg(500036, "该学期招生目标区域不存在，是否先创建招生区域");
    public static CodeMsg SOURCE_SCHOOL_REPEAT = new CodeMsg(500037, "生源学校只能绑定一个组");
    public static CodeMsg ALREADY_ATTEND = new CodeMsg(500038, "选中的咨询人中有已经签到的");
    public static CodeMsg PUBLISH_STATUS = new CodeMsg(500050, "学校简介和招生简介未填写");
    public static CodeMsg YEAR_OR_SEMESTER_EXIST_GROUP = new CodeMsg(500051, "该学期已创建区域");
    public static CodeMsg YEAR_OR_SEMESTER_EXIST_TARGET = new CodeMsg(500052, "该学期已创建目标");
    // 小程序端业务异常
    public static CodeMsg APPLET_SERVER_ERROR = new CodeMsg(600000, "小程序端服务异常");
    public static CodeMsg CODE_INVALIDE = new CodeMsg(600001, "code有误");
    public static CodeMsg USER_UN_REGISTER = new CodeMsg(600002, "用户未注册，请先注册");
    public static CodeMsg UN_BIND_STUDENT = new CodeMsg(600003, "未绑定学生");
    public static CodeMsg STUDENT_UN_EXIST = new CodeMsg(600004, "该学生与家长手机号不存在，请重新输入");
    public static CodeMsg VERIFY_CODE_ERROR = new CodeMsg(600005, "验证码失效，请重新获取");
    public static CodeMsg STUDENT_AND_PHONE_EXIST = new CodeMsg(600006, "该学校班级下的学生已经存在，是否绑定");
    public static CodeMsg WILL_CREATE_NEW_STUDENT = new CodeMsg(600007, "将会新建一个学生账号，是否继续");
    public static CodeMsg STUDENT_INFO_UN_EXIST = new CodeMsg(600008, "学生不存在");
    public static CodeMsg REGISTER_PHONE_CANNOT_MATCH = new CodeMsg(600009, "注册手机号不匹配");
    public static CodeMsg CONSULTATION_REGISTER_EXIST = new CodeMsg(600010, "当天已经申请过到校咨询");
    public static CodeMsg BIND_EXIST_STUDENT = new CodeMsg(600011, "将会绑定已注册的账号，是否绑定");
    public static CodeMsg STUDENT_HAS_BIND = new CodeMsg(600012, "该学生已被绑定");
    public static CodeMsg GROUP_NO_LEADER_CHARGE = new CodeMsg(600013, "分组下的负责人没有组长");
    public static CodeMsg LOGIN_INFO_NOT_FOUND = new CodeMsg(600014, "获取不到登录信息");
    public static CodeMsg GROUP_IS_EXIST = new CodeMsg(600015, "分组名称已存在");
    public static CodeMsg GROUP_NO_LEADER_CHARGE_FOR_SOURCE_SCHOOL = new CodeMsg(600016, "该学校下没有区域负责人，不能创建新的学生");
    public static CodeMsg PUBLISH_STATUS_CHARGER_ID = new CodeMsg(600017, "该计划没有负责人,不能发布！");
    public static CodeMsg VERIFY_CODE_SEND_ERROR = new CodeMsg(600018, "验证码发送失败");
    public static CodeMsg OPPENID_UN_BIND_RECRUIT_SCHOOL = new CodeMsg(600019, "未绑定该招生学校，请选择绑定的学生");
    public static CodeMsg DIC_DELETE_ERROR_SOURCE_SCHOOL = new CodeMsg(600020, "该数据有子数据，无法删除");

    // app端业务异常
  public static CodeMsg PREACH_REGISTER_SIGNIN = new CodeMsg(600020, "您已经签过到啦");
   public static CodeMsg PREACH_REGISTER_SIGNUP = new CodeMsg(600021, "您已经报过名啦");
    public static CodeMsg APP_SERVER_ERROR = new CodeMsg(700000, "APP端服务异常");
    public static CodeMsg CLASS_UN_EXIST = new CodeMsg(700001, "该学生不存在");
    private final int retCode;
    private String message;


    public CodeMsg(int retCode, String message) {
        this.retCode = retCode;
        this.message = message;
    }

    public int getRetCode() {
        return retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
