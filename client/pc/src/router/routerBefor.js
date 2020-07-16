import router from "./index";
import { reLogin } from "@/api/admin/Dashboard/Dashboard";
const MaxTime = 1000 * 60 * 59;
const MinTime = 1000 * 60 * 31;
//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    if (to.params.type == "edit") {
        document.title = `编辑${to.meta.title} | ER育联网招生系统`;
    } else if (to.params.type == "creat") {
        document.title = `新增${to.meta.title} | ER育联网招生系统`;
    } else if (to.params.type == "see") {
        document.title = `查看${to.meta.title} | ER育联网招生系统`;
    } else {
        document.title = `${to.meta.title} | ER育联网招生系统`;
    }
    const role = localStorage.getItem('ACCESS_TOKEN');
    if (!role && to.path !== '/login') { localStorage.removeItem("tokenTime");next('/login'); }
    else {
        let tokenTime = localStorage.getItem("tokenTime");
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
                next("/login");
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
            localStorage.setItem("ACCESS_TOKEN",res.data.token);
            localStorage.setItem('menus',JSON.stringify(res.data.menus))
            delete res.data.userName
            localStorage.setItem('user', JSON.stringify(res.data));
            //特殊处理，兼容获取userInfo方式获取schoolid
            const userInfo = {...res.data.apiInfo};
            userInfo.schoolid = userInfo.schoolId;
            localStorage.setItem("userInfo",JSON.stringify(userInfo))
            localStorage.setItem('ms_username', userInfo.userName);
            localStorage.setItem("tokenTime", (new Date().getTime()));
        })
        .catch(err => {
            console.log(err);
        });
}
export default router;
