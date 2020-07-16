import { Toast } from "vant";
import { failTest } from "./failText";
import { errorText } from "./errorText";
import R from "@/router";

class Me {
  // 成功提示
  success(title) {
    Toast(title);
  }

  // 失败提示
  error(code, title) {
    if (errorText[code]) {
      Toast({
        message: title,
        onClose: () => {
          localStorage.clear();
          console.log(process.env.NODE_ENV);
          if(process.env.NODE_ENV === "development") {
            R.router.push({
              path: errorText[code].path
            })
          } else {
            window.wx.miniProgram.navigateTo({
              url: "/pages/index/index"
            });
          }

        }
      });
    } else {
      Toast(title);
    }
  }

  //系统一次提示
  fail(code) {
    if (failTest[code]) {
      Toast(failTest[code]);
    } else {
      Toast("网络异常");
    }
  }
}

const M = new Me();

export { M };
