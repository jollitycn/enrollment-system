import Vue from "vue";
import App from "./App.vue";
import router from "./router/routerBefor";
import store from "./store";
import "amfe-flexible/index.js";
import "./assets/css/resrt.css";
Vue.config.productionTip = false;
import WXConfig from "./assets/js/wx"; //微信分享
Vue.prototype.WXConfig = WXConfig;
import { Toast } from "vant";

Vue.use(Toast);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
