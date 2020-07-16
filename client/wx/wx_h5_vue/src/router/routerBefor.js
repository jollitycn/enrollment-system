import router from "./index";
import store from "../store";
import { reLogin } from "@/api/login/login";
import {errorText} from "../utils/errorText";
const MaxTime = 1000 * 60 * 59;
const MinTime = 1000 * 60 * 31;
//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title}`;
  let isLogin = localStorage.getItem("token");
  let tokenTime = localStorage.getItem("tokenTime");
  if (
    to.matched.some(function(item) {
      return item.meta.needLogin;
    })
  ) {
    //如果当前未登录，开启拦截。
    if (!isLogin) {
      localStorage.clear();
      if(process.env.NODE_ENV === "development") {
        next("/login?code=123");
      } else {
        window.wx.miniProgram.navigateTo({
          url: "/pages/index/index"
        });
      }
    } else {
      if (tokenTime) {
        //获取现在的时间戳
        let nowTime = new Date().getTime();
        let tokenMi = Number(tokenTime) + Number(MinTime);
        let tokenMa = Number(tokenTime) + Number(MaxTime);
        //判断现在的时间戳在某个区间之间
        if (nowTime > tokenMi && nowTime < tokenMa) {
          getToken();
          next();
        } else if (nowTime > tokenMa) {
          //如果太长时间未登录
          localStorage.clear();
          if(process.env.NODE_ENV === "development") {
            next("/login?code=123");
          } else {
            window.wx.miniProgram.navigateTo({
              url: "/pages/index/index"
            });
          }
        } else {
          next();
        }
      } else {
        localStorage.clear();
        if(process.env.NODE_ENV === "development") {
          next("/login?code=123");
        } else {
          window.wx.miniProgram.navigateTo({
            url: "/pages/index/index"
          });
        }
      }
    }
  } else {
    next();
  }
});
function getToken() {
  reLogin()
    .then(res => {
      console.log(res, "我在刷新token");
      store.commit("setUser", res.data);
    })
    .catch(err => {
      console.log(err);
    });
}
export default router;
