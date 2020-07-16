import Vue from 'vue';
import App from './App.vue';
import router from './router/routerBefor';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import axios from 'axios'

import { messages } from './components/common/i18n';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import 'vue-orgchart/dist/style.min.css' //树状图插件样式
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';
import BaiduMap from 'vue-baidu-map'
import custom from './components/custom/index.js'
import './mixin'
import './fiters'


import Vue2OrgTree from 'vue2-org-tree';


Vue.prototype.$http = axios;
Vue.use(Vue2OrgTree)
Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(custom)
Vue.use(ElementUI, {
  size: 'small'
});
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'dx0zcopASuISA4WNZ9XEWIVqwVPdL9vV'
})
const i18n = new VueI18n({
  locale: 'zh',
  messages
});



new Vue({
  router,
  i18n,
  render: h => h(App)
}).$mount('#app');