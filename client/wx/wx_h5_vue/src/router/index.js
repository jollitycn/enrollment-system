import Vue from "vue";
import VueRouter from "vue-router";
import About from "../views/About.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/home/homeRouter.vue"),
    meta: {
      title: "首页",
      needLogin: true
    },
    children: [
      {
        path: "/",
        name: "home",
        component: () =>
          import(/* webpackChunkName: "login" */ "../views/home/home.vue"),
        meta: {
          title: "首页",
          needLogin: true
        }
      },
      {
        path: "homeDetail/:id",
        name: "homeDetail",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/home/child/homeList.vue"
          ),
        meta: {
          title: "学校详情",
          needLogin: true
        }
      },
      {
        path: "departmentDetail/:id",
        name: "departmentDetail",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/home/departmentDetail/departmentDetail.vue"
          ),
        meta: {
          title: "院系详情",
          needLogin: true
        }
      },
      {
        path: "objectDetail/:schoolID/:id",
        name: "objectDetail",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/home/objectDetail/objectDetail.vue"
          ),
        meta: {
          title: "专业详情",
          needLogin: true
        }
      }
    ]
  },

  {
    path: "/About",
    name: "About",
    component: About,
    meta: {
      title: "关于学校",
      needLogin: true
    }
  },
  {
    path: "/signList",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/signList/signList.vue"),
    meta: {
      title: "报名资料",
      needLogin: true
    },
    children: [
      {
        path: "/",
        name: "list",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/list/list.vue"
          ),
        meta: {
          title: "报名资料",
          needLogin: true
        }
      },
      {
        path: "backups",
        name: "backups",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/backups/backups.vue"
          ),
        meta: {
          title: "报名信息",
          needLogin: true
        }
      },
      {
        path: "information",
        name: "information",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/backups/child/information/information.vue"
          ),
        meta: {
          title: "基本信息",
          needLogin: true
        }
      },
      {
        path: "contact",
        name: "contact",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/backups/child/contact/contact"
          ),
        meta: {
          title: "联系方式",
          needLogin: true
        }
      },
      {
        path: "schoolInfo",
        name: "schoolInfo",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/backups/child/schoolInfo/schoolInfo"
          ),
        meta: {
          title: "在校信息",
          needLogin: true
        }
      },
      {
        path: "family",
        name: "family",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/backups/child/family/family"
          ),
        meta: {
          title: "家庭成员",
          needLogin: true
        }
      },
      {
        path: "info",
        name: "info",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/info/info.vue"
          ),
        meta: {
          title: "填写信息",
          needLogin: true
        }
      },
      {
        path: "volunteer",
        name: "volunteer",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/volunteer/volunteer.vue"
          ),
        meta: {
          title: "填写志愿",
          needLogin: true
        }
      },
      {
        path: "activeMajor",
        name: "activeMajor",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/volunteer/child/activeMajor"
          ),
        meta: {
          title: "选择专业",
          needLogin: true
        }
      },
      {
        path: "schoolList",
        name: "schoolList",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/schoolList/schoolList.vue"
          ),
        meta: {
          title: "学校宣传",
          needLogin: true
        }
      },
      {
        path: "schoolDetailInfo",
        name: "schoolDetailInfo",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/signList/child/schoolList/child/schoolDetailInfo"
          ),
        meta: {
          title: "宣传详情",
          needLogin: true
        }
      }
    ]
  },
  {
    path: "/user",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/user/user.vue"),
    meta: {
      title: "用户中心",
      needLogin: true
    },
    children: [
      {
        path: "/",
        name: "list",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/userList/userList.vue"
          ),
        meta: {
          title: "用户中心",
          needLogin: true
        }
      },
      {
        path: "evaluate",
        name: "evaluate",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/evaluate/evaluate.vue"
          ),
        meta: {
          title: "宣讲评价",
          needLogin: true
        }
      },
      {
        path: "myNeed",
        name: "myNeed",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/myNeed/myNeed.vue"
          ),
        meta: {
          title: "我的诉求",
          needLogin: true
        }
      },
      {
        path: "inSchool",
        name: "inSchool",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/inSchool/inSchool.vue"
          ),
        meta: {
          title: "到校咨询",
          needLogin: true
        }
      },
      {
        path: "addInSchool",
        name: "addInSchool",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/inSchool/child/addInSchool"
          ),
        meta: {
          title: "新增到校咨询",
          needLogin: true
        }
      },
      {
        path: "addMyNeed",
        name: "addMyNeed",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/myNeed/addMyNeed/addMyNeed.vue"
          ),
        meta: {
          title: "新增诉求",
          needLogin: true
        }
      },
      {
        path: "message",
        name: "message",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/message/message.vue"
          ),
        meta: {
          title: "消息中心",
          needLogin: true
        }
      },
      {
        path: "messageInfo",
        name: "messageInfo",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/message/child/messageInfo.vue"
          ),
        meta: {
          title: "报名信息",
          needLogin: true
        }
      },
      {
        path: "seeRate",
        name: "seeRate",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/seeRate/seeRate.vue"
          ),
        meta: {
          title: "报名进度",
          needLogin: true
        }
      },
      {
        path: "binding",
        name: "binding",
        component: () =>
          import(
            /* webpackChunkName: "login" */ "../views/user/child/binding/binding.vue"
          ),
        meta: {
          title: "我的绑定",
          needLogin: true
        }
      }
    ]
  },
  {
    path: "/Login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/login/login.vue"),
    meta: {
      title: "育联网",
      needLogin: false
    }
  },
  {
    path: "/LoginActive",
    name: "LoginActive",
    component: () =>
        import(/* webpackChunkName: "login" */ "../views/login/loginActive.vue"),
    meta: {
      title: "育联网",
      needLogin: false
    }
  }
];

const router = new VueRouter({
  routes
});

export default router;
