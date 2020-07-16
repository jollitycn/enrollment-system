import router from "./index";
import { reLogin } from "@/api/login/login";
const MaxTime = 1000 * 60 * 59;
const MinTime = 1000 * 60 * 31;
//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title}`;
  let role = localStorage.getItem("ACCESS_TOKEN");
  let tokenTime = localStorage.getItem("tokenTime");
  if (!role && to.path !== '/') { localStorage.removeItem("tokenTime");next('/'); }
    else {
        if (tokenTime) {
            // //获取现在的时间戳
            let nowTime = new Date().getTime();
            let tokenMi = Number(tokenTime) + Number(MinTime)
            let tokenMa = Number(tokenTime) + Number(MaxTime)
            // //判断现在的时间戳在某个区间之间
            console.log(nowTime ,tokenMi , tokenMa)
            if ( nowTime > tokenMi && nowTime < tokenMa) {
                getToken();
                next();
            } else if (nowTime > tokenMa) {
                localStorage.removeItem("tokenTime")
                //如果太长时间未登录
                next("/");
            } else {
                next();
            }
        }else {
            next();
        }
    }
});
function getToken() {
  reLogin()
    .then(res => {
      console.log(res, "我在刷新token");
      localStorage.setItem("ACCESS_TOKEN", res.data.token);
      localStorage.setItem("tokenTime", (new Date().getTime()));
    })
    .catch(err => {
      console.log(err);
    });
}
export default router;
