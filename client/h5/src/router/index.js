import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
    meta: {
      title: "首页"
    }
  },
  {
    path: "/networkError",
    name: "networkError",
    component: () => import("@/views/NetworkError"),
    meta: {
      title: "网络错误"
    }
  },
  {
    path: "/newList",
    name: "newList",
    component: () => import("@/views/list/list"),
    meta: {
      title: "任务列表"
    }
  },
  {
    path: "/list",
    name: "list",
    component: () => import("@/views/list/newList"),
    meta: {
      title: "任务列表"
    }
  },
  {
    path: "/admissions-process",
    component: () => import("@/views/admissions-process"),
    meta: {
      title: "招生过程管理"
    },
    children: [
      {
        path: "/",
        name: "admissions-list",
        component: () => import("@/views/admissions-process/admissions-process-navs"),
        meta: {
          title: "招生管理"
        }
      },
      {
        path: "preaching-plan/:status/:active",
        name: "preaching-plan",
        component: () => import("@/views/admissions-process/preaching-plan"),
        meta: {
          title: "宣讲计划管理"
        }
      },
      {
        path: "preaching-plan-add",
        name: "preaching-plan-add",
        component: () => import("@/views/admissions-process/preaching-plan/preaching-plan-add"),
        meta: {
          title: "新增宣讲计划"
        }
      },
      {
        path: "preaching-plan-detail/:id",
        name: "preaching-plan-detail",
        component: () => import("@/views/admissions-process/preaching-plan/preaching-plan-detail"),
        meta: {
          title: "宣讲查看"
        }
      },
      {
        path: "preaching-plan-summary/:id",
        name: "preaching-plan-summary",
        component: () => import("@/views/admissions-process/preaching-plan/preaching-plan-summary"),
        meta: {
          title: "宣讲总结"
        }
      },
      {
        path: "preaching-plan-summary/:id/:type",
        name: "preaching-plan-summary",
        component: () => import("@/views/admissions-process/preaching-plan/preaching-plan-summary"),
        meta: {
          title: "查看宣讲总结"
        }
      },
      {
        path: "preaching-plan-signed-students/:title/:id",
        name: "preaching-plan-signed-students",
        component: () => import("@/views/admissions-process/preaching-plan/preaching-plan-signed-students"),
        meta: {
          title: "宣讲总结"
        }
      },
      {
        path: "appeal-manage",
        name: "appeal-manage",
        component: () => import("@/views/admissions-process/appeal-manage"),
        meta: {
          title: "诉求管理"
        },
      },
      {
        path: "applets",
        name: "applets",
        component: () => import("@/views/admissions-process/applets"),
        meta: {
          title: "我的招生"
        },
      },
      {
        path: "to-school-counseling-manage",
        name: "to-school-counseling-manage",
        component: () => import("@/views/admissions-process/to-school-counseling-manage"),
        meta: {
          title: "到校咨询管理"
        },
      },
      {
        path: "counseling-add",
        name: "counseling-add",
        component: () => import("@/views/admissions-process/to-school-counseling-manage/counseling-add"),
        meta: {
          title: "增加咨询"
        },
      },
      {
        path: "visit-school-record",
        name: "visit-school-record",
        component: () => import("@/views/admissions-process/visit-school-record"),
        meta: {
          title: "学校拜访记录"
        },
      },
      {
        path: "visit-record-list/:sourceSchoolId",
        name: "visit-record-list",
        component: () => import("@/views/admissions-process/visit-school-record/visit-record-list"),
        meta: {
          title: "拜访记录查看"
        },
      },
      {
        path: "visit-record-add",
        name: "visit-record-add",
        component: () => import("@/views/admissions-process/visit-school-record/visit-record-add"),
        meta: {
          title: "新增拜访记录"
        },
      },
      {
        path: "visit-record-add/:publicRelation",
        name: "visit-record-add",
        component: () => import("@/views/admissions-process/visit-school-record/visit-record-add"),
        meta: {
          title: "修改拜访记录"
        },
      },
      {
        path: "responsibility-school-manage",
        name: "responsibility-school-manage",
        component: () => import("@/views/admissions-process/responsibility-school-manage"),
        meta: {
          title: "责任学校管理"
        },
        children: [
          {
            path: "school-list",
            name: "responsibility-school-list",
            component: () => import("@/views/admissions-process/responsibility-school-manage/responsibility-school-list"),
            meta: {
              title: "责任学校列表"
            },
          },
          {
            path: "responsibility-school-add",
            name: "responsibility-school-add",
            component: () => import("@/views/admissions-process/responsibility-school-manage/responsibility-school-add"),
            meta: {
              title: "新增责任学校"
            },
          },
          {
            path: "responsibility-school-edit/:id",
            name: "responsibility-school-edit",
            component: () => import("@/views/admissions-process/responsibility-school-manage/responsibility-school-edit"),
            meta: {
              title: "编辑责任学校"
            },
          },
          {
            path: "responsibility-school-detail/:id",
            name: "responsibility-school-detail",
            component: () => import("@/views/admissions-process/responsibility-school-manage/responsibility-school-detail"),
            meta: {
              title: "责任学校详情"
            },
          },
          {
            path: "school-class-add/:sid",
            name: "school-class-add",
            component: () => import("@/views/admissions-process/responsibility-school-manage/school-class-add"),
            meta: {
              title: "新增班级"
            },
          },
          {
            path: "school-class-detail/:sid/:id",
            name: "school-class-detail",
            component: () => import("@/views/admissions-process/responsibility-school-manage/school-class-detail"),
            meta: {
              title: "班级详情"
            },
          },
          {
            path: "school-class-edit/:sid/:id",
            name: "school-class-edit",
            component: () => import("@/views/admissions-process/responsibility-school-manage/school-class-edit"),
            meta: {
              title: "编辑班级"
            },
          },
        ]
      },
    ]
  },
  {
    path: "/student",
    name: "student",
    component: () => import("@/views/student"),
    meta: {
      title: "生源管理"
    },
    children: [
      {
        path: "",
        name: "student",
        component: () => import("@/views/student/student-navs"),
        meta: {
          title: "生源管理"
        },
      },
      {
        path: "/student-manage",
        name: "student-manage",
        component: () => import("@/views/student/student-manage"),
        meta: {
          title: "学生管理"
        },
        children: [
          {
            path: "student-list",
            name: "student-list",
            component: () => import("@/views/student/student-manage/student-list"),
            meta: {
              title: "学生列表"
            },
          },
          {
            path: "student-search",
            name: "student-search",
            component: () => import("@/views/student/student-manage/student-search"),
            meta: {
              title: "学生搜索"
            },
          },
          {
            path: "student-add",
            name: "student-add",
            component: () => import("@/views/student/student-manage/student-add"),
            meta: {
              title: "意向生报名"
            },
          },
          {
            path: "student-detail/:id",
            name: "student-detail",
            component: () => import("@/views/student/student-manage/student-detail"),
            meta: {
              title: "学生详情"
            },
          },
          {
            path: "student-more-info/:id",
            name: "student-more-info",
            component: () => import("@/views/student/student-manage/student-more-info"),
            meta: {
              title: "学生更多信息"
            },
          },
          {
            path: "student-more-info-edit/:id",
            name: "student-more-info-edit",
            component: () => import("@/views/student/student-manage/student-more-info-edit"),
            meta: {
              title: "学生更多信息编辑"
            },
          },
          {
            path: "student-school-info/:id",
            name: "student-school-info",
            component: () => import("@/views/student/student-manage/student-school-info"),
            meta: {
              title: "在校信息"
            },
          },
          {
            path: "student-select-major/:id/:relationId",
            name: "student-select-major",
            component: () => import("@/views/student/student-manage/student-select-major"),
            meta: {
              title: "选择专业"
            },
          },
          {
            path: "student-follow-info/:id/:relationId",
            name: "student-follow-info",
            component: () => import("@/views/student/student-manage/student-follow-info"),
            meta: {
              title: "跟进信息"
            },
          },
          {
            path: "student-communication-add/:id/:relationId",
            name: "student-communication-add",
            component: () => import("@/views/student/student-manage/student-communication-add"),
            meta: {
              title: "新增沟通"
            },
          },
          {
            path: "student-channels/:id/:relationId",
            name: "student-channels",
            component: () => import("@/views/student/student-manage/student-channels"),
            meta: {
              title: "招生渠道"
            },
          },
          {
            path: "student-progress-detail/:id/:relationId",
            name: "student-progress-detail",
            component: () => import("@/views/student/student-manage/student-progress-detail"),
            meta: {
              title: "进度查看"
            },
          },
          {
            path: "student-counseling/:id/:relationId",
            name: "student-counseling",
            component: () => import("@/views/student/student-manage/student-counseling"),
            meta: {
              title: "到校咨询"
            },
          },
          {
            path: "student-appeal/:id/:relationId",
            name: "student-appeal",
            component: () => import("@/views/student/student-manage/student-appeal"),
            meta: {
              title: "诉求管理"
            },
          },
        ]
      },
      {
        path: "/student-target-manage",
        name: "student-target-manage",
        component: () => import("@/views/student/student-target-manage"),
        meta: {
          title: "目标生管理"
        },
        children: [
          {
            path: "student-target-list",
            name: "student-target-list",
            component: () => import("@/views/student/student-target-manage/student-target-list"),
            meta: {
              title: "目标生列表"
            },
          },
          {
            path: "student-target-search",
            name: "student-target-search",
            component: () => import("@/views/student/student-target-manage/student-target-search"),
            meta: {
              title: "目标生搜索"
            },
          },
          {
            path: "student-target-add",
            name: "student-target-add",
            component: () => import("@/views/student/student-target-manage/student-target-add"),
            meta: {
              title: "目标生新增"
            },
          },
          {
            path: "student-target-add/:classId",
            name: "student-target-add",
            component: () => import("@/views/student/student-target-manage/student-target-add"),
            meta: {
              title: "目标生新增"
            },
          },
          {
            path: "student-target-edit/:id",
            name: "student-target-edit",
            component: () => import("@/views/student/student-target-manage/student-target-edit"),
            meta: {
              title: "目标生修改"
            },
          },
        ]
      },
    ]
  },
  {
    path: "/analysis",
    name: "analysis",
    component: () => import("@/views/analysis/analysis"),
    meta: {
      title: "进度分析"
    },
  }
];

const router = new VueRouter({
  routes
});

export default router;
