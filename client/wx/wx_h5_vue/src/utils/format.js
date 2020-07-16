/**
 *
 * 日期通用格式化方法
 * @param {Date} date 需要格式化的时间
 * @param {String} fmt 格式化模板，如 yyyy-MM-dd HH:mm
 */
export const dateFormat = (date, fmt = "yyyy-MM-dd HH:mm") => {
  if (date instanceof Date) {
  } else if (typeof date === "string") {
    date = new Date(date);
  } else {
    console.warn("参数错误,date ==", date);
    return "";
  }
  let ret;
  const opt = {
    "Y+": date.getFullYear().toString(), //年
    "m+": (date.getMonth() + 1).toString(), // 月
    "d+": date.getDate().toString(), // 日
    "H+": date.getHours().toString(), // 时
    "M+": date.getMinutes().toString(), // 分
    "S+": date.getSeconds().toString() // 秒
    // 有其他格式化字符需求可以继续添加，必须转化成字符串
  };
  for (let k in opt) {
    ret = new RegExp("(" + k + ")").exec(fmt);
    if (ret) {
      fmt = fmt.replace(
        ret[1],
        ret[1].length == 1 ? opt[k] : opt[k].padStart(ret[1].length, "0")
      );
    }
  }
  return fmt;
};
