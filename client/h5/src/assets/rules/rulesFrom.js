/* eslint-disable */
const rules = {
  componetName: {
    title: "主题名称",
    rules: /^[0-9a-zA-Z\u4e00-\u9fa5]{2,50}$/,
    message: "请输入2到50个字符"
  },
  userName: {
    title: "企业人员姓名正则",
    rules: /^[a-zA-Z\u4e00-\u9fa5]{2,30}$/,
    message: "请输入2到30个中英文字符"
  },

  userCode: {
    title: "企业人员用户名",
    rules: /^[a-zA-Z0-9]([-_a-zA-Z0-9]{5,20})$/,
    message: "字母数字下划线，6-20位字符"
  },
  phone: {
    title: "手机号验证规则",
    rules: /^1[1234567890]\d{9}$/,
    message: "请输入合法手机号码"
  },
  password: {
    title: "密码验证规则",
    rules: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/,
    message: "请输入8至16位数字字母混合密码"
  },
  code: {
    title: "手机验证码验证规则",
    rules: /^\d{6}$/,
    message: "请输入正确验证码"
  },
  email: {
    title: "邮箱验证规则",
    rules: /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g,
    message: "请输入正确邮箱"
  },
  card: {
    title: "身份证验证规则",
    rules: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
    message: "请输入正确的身份证"
  }
};

export default rules;
/* eslint-disable */