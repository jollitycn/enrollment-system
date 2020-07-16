import Vue from "vue";
// import VConsole from "vconsole";
import App from "./App.vue";
// import router from "./router/index";
import router from "./router/routerBefor";
import store from "./store";
import "amfe-flexible/index.js";
import "./assets/css/resrt.css";
import { Message } from './utils/message';
import { Toast } from "vant";
import './fiters'
Vue.prototype.$showToast = Toast;
Vue.prototype.$message = Message;

Vue.config.productionTip = false;

// let vconsole = new VConsole();
// console.log('vconsole==',vconsole)

//使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//   document.title = `${to.meta.title} | ER育联网APP招生系统`;
//   const role = localStorage.getItem('ACCESS_TOKEN');
//   if (!role && to.path !== '/') {
//     // Message.error("进入APP失败，请重新进入！")
//     next('/');
//   }
//   else {
//     next();
//   }
// });

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");