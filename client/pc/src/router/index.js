import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  routes: [{
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/',
    component: () =>
      import( /* webpackChunkName: "home" */ '../components/common/Home.vue'),
    meta: { title: '自述文件' },
    children: [{
      path: '/dashboard',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
      meta: { title: '系统首页', name: '/dashboard' }
    },
    //年度招生计划管理
    {
      path: '/enrollmentPlanList',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-list.vue'),
      meta: { title: '招生计划管理',name: '/enrollmentPlanList' }
    },

    //创建年度招生计划流程
    //基本信息 -- type(creat,see,edit)
    //基本信息-学校简介-招生简介 -- title(base-info,school-introduce,student-recruit)
    {
      path: '/enrollmentPlanList/:title/:type/:planId',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-add.vue'),
      meta: { title: '计划',name: '/enrollmentPlanList' }
    },
    {
      path: '/enrollmentPlanList/department/:title/:type/:planId',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-add.vue'),
      meta: { title: '院系介绍',name: '/enrollmentPlanList' }
    },
    {
      path: '/enrollmentPlanList/department/:title/:type/:planId/:collegeId',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-add.vue'),
      meta: { title: '院系',name: '/enrollmentPlanList' }
    },
    //专业管理
    {
      path: '/enrollmentPlanList/major/:title/:type/:planId/:collegeId/:apiId',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-add.vue'),
      meta: { title: '专业管理',name: '/enrollmentPlanList' }
    },
    {
      path: '/enrollmentPlanList/:title/:type/:planId/:collegeId/:majorId/:apiId',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlan/enrollment-plan-add.vue'),
      meta: { title: '专业',name: '/enrollmentPlanList' }
    },



    {
      path: '/enrollmentManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentManagement.vue'),
      meta: { title: '招生区域管理',name: '/enrollmentManagement' }
    },
    {
      path: '/addEnrollmentManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/addEnrollmentManagement.vue'),
      meta: { title: '招生区域创建',name: '/enrollmentManagement' }
    },
    {
      path: '/copyEnrollmentManagement/:id',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/copyEnrollmentManagement.vue'),
      meta: { title: '复制招生区域',name: '/enrollmentManagement' }
    },
    {
      path: '/editEnrollmentManagement/:id',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/editEnrollmentManagement.vue'),
      meta: { title: '招生区域编辑',name: '/enrollmentManagement' }
    },
    {
      path: '/enrollmentTargetList',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentTargetList.vue'),
      meta: { title: '招生目标列表',name: '/enrollmentTargetList' }
    },
    {
      path: '/addEnrollmentTargetList/:id',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/addEnrollmentTargetList.vue'),
      meta: { title: '新增招生目标',name: '/enrollmentTargetList' }
    },
    {
      path: '/feederSchoolManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/feederSchoolManagement.vue'),
      meta: { title: '生源学校管理',name: '/feederSchoolManagement' }
    },
    {
      path: '/enrollmentPlanRelease',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/annualEnrollmentPlanManagement/enrollmentPlanRelease.vue'),
      meta: { title: '招生计划发布',name: '/enrollmentPlanRelease' }
    },
    //招生过程管理
    {
      path: '/consultingSchoolManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagement.vue'),
      meta: { title: '到校咨询管理',name: '/consultingSchoolManagement' }
    },
    {
      path: '/consultingSchoolManagement/:type',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagementComponent/addConsultingSchoolManagement.vue'),
      meta: { title: '到校咨询管理',name: '/consultingSchoolManagement' }
    },
    {
      path: '/addConsultingSchoolManagementDay',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagementComponent/addConsultingSchoolManagementDay.vue'),
      meta: { title: '咨询日程',name: '/consultingSchoolManagement' }
    },
    {
      path: '/addConsultingSchoolManagemenNow',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagementComponent/addConsultingSchoolManagementNow.vue'),
      meta: { title: '咨询管理',name: '/consultingSchoolManagement' }
    },
    {
      path: '/seeConsultingSchoolManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagementComponent/seeConsultingSchoolManagement.vue'),
      meta: { title: '查看到校咨询',name: '/consultingSchoolManagement' }
    },
    {
      path: '/submitConsultingSchoolManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/consultingSchoolManagementComponent/submitConsultingSchoolManagement.vue'),
      meta: { title: '咨询总结',name: '/consultingSchoolManagement' }
    },
    {
      path: '/publicityPlanManagement',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/publicityPlanManagement.vue'),
      meta: { title: '宣讲计划列表',name: '/publicityPlanManagement' }
    },
    {
      path: '/publicityPlanManagement/:type/:id',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/publicityPlanManagementComenet/doPublicityPlanManagement.vue'),
      meta: { title: '宣讲计划管理',name: '/publicityPlanManagement' }
    },
    {
      path: '/dutySchedule',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/enrollmentProcessManagement/dutySchedule.vue'),
      meta: { title: '信息发布管理',name: '/dutySchedule' }
    },
    //招生数据分析
    {
      path: '/enrollmentProgressAnalysis',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/analysis/enrollmentProgressAnalysis.vue'),
      meta: { title: '招生报表',name: '/enrollmentProgressAnalysis' }
    },
    {
      path: '/sourceAnalysis',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/analysis/sourceAnalysis.vue'),
      meta: { title: '区域招生进度表',name: '/sourceAnalysis' }
    },
    {
      path: '/propagandaConsultationAnalysis',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/analysis/propagandaConsultationAnalysis.vue'),
      meta: { title: '生源报表',name: '/propagandaConsultationAnalysis' }
    },
    {
      path: '/channelAnalysis',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/analysis/channelAnalysis.vue'),
      meta: { title: '渠道表',name: '/channelAnalysis' }
    },
    //信息推送管理
    {
      path: '/informationPush',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/informationManagement/informationPush.vue'),
      meta: { title: '信息推送',name: '/informationPush' }
    },
    {
      path: '/informationPush/addInformationPush',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/informationManagement/addInformationPush.vue'),
      meta: { title: '新建信息',name: '/informationPush' }
    },
    {
      path: '/informationPush/editInformationPush/:type/:id',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/management/informationManagement/editInformationPush.vue'),
      meta: { title: '编辑信息',name: '/informationPush' }
    },
    //基础设置
    {
      path: '/schoolResume',
      component: () =>
        import( /* webpackChunkName: "dashboard" */ '../components/page/foundationSetup/schoolResume.vue'),
      meta: { title: '学校信息管理',name: '/schoolResume' }
    },

    {
      path: '/number-dictionaries',
      component: () =>
        import( /* webpackChunkName: "icon" */ '../components/page/management/systems-management/number-dictionaries/number-dictionaries.vue'),
      meta: { title: '数据字典',name: '/number-dictionaries' }
    },

    {
      path: '/icon',
      component: () =>
        import( /* webpackChunkName: "icon" */ '../components/page/Icon.vue'),
      meta: { title: '自定义图标' }
    },
    {
      path: '/table',
      component: () =>
        import( /* webpackChunkName: "table" */ '../components/page/BaseTable.vue'),
      meta: { title: '基础表格' }
    },
    {
      path: '/tabs',
      component: () =>
        import( /* webpackChunkName: "tabs" */ '../components/page/Tabs.vue'),
      meta: { title: 'tab选项卡' }
    },
    {
      path: '/form',
      component: () =>
        import( /* webpackChunkName: "form" */ '../components/page/BaseForm.vue'),
      meta: { title: '基本表单' }
    },
    {
      // 富文本编辑器组件
      path: '/editor',
      component: () =>
        import( /* webpackChunkName: "editor" */ '../components/page/VueEditor.vue'),
      meta: { title: '富文本编辑器' }
    },
    {
      // markdown组件
      path: '/markdown',
      component: () =>
        import( /* webpackChunkName: "markdown" */ '../components/page/Markdown.vue'),
      meta: { title: 'markdown编辑器' }
    },
    {
      // 图片上传组件
      path: '/upload',
      component: () =>
        import( /* webpackChunkName: "upload" */ '../components/page/Upload.vue'),
      meta: { title: '文件上传' }
    },
    {
      // vue-schart组件
      path: '/charts',
      component: () =>
        import( /* webpackChunkName: "chart" */ '../components/page/BaseCharts.vue'),
      meta: { title: 'schart图表' }
    },
    {
      // 拖拽列表组件
      path: '/drag',
      component: () =>
        import( /* webpackChunkName: "drag" */ '../components/page/DragList.vue'),
      meta: { title: '拖拽列表' }
    },
    {
      // 拖拽Dialog组件
      path: '/dialog',
      component: () =>
        import( /* webpackChunkName: "dragdialog" */ '../components/page/DragDialog.vue'),
      meta: { title: '拖拽弹框' }
    },
    {
      // 国际化组件
      path: '/i18n',
      component: () =>
        import( /* webpackChunkName: "i18n" */ '../components/page/I18n.vue'),
      meta: { title: '国际化' }
    },
    {
      // 权限页面
      path: '/permission',
      component: () =>
        import( /* webpackChunkName: "permission" */ '../components/page/Permission.vue'),
      meta: { title: '权限测试', permission: true }
    },
    {
      path: '/404',
      component: () =>
        import( /* webpackChunkName: "404" */ '../components/page/404.vue'),
      meta: { title: '404' }
    },
    {
      path: '/403',
      component: () =>
        import( /* webpackChunkName: "403" */ '../components/page/403.vue'),
      meta: { title: '403' }
    },
    {
      path: '/map',
      component: () =>
        import( /* webpackChunkName: "403" */ '../components/page/Map.vue'),
      meta: { title: '百度地圖' }
    },
    {
      path: '/print',
      component: () =>
        import( /* webpackChunkName: "403" */ '../components/page/Print.vue'),
      meta: { title: '打印功能' }
    },
    {
      path: '/donate',
      component: () =>
        import( /* webpackChunkName: "donate" */ '../components/page/Donate.vue'),
      meta: { title: '支持作者' }
    }
    ]
  },
  {
    path: '/login',
    component: () =>
      import( /* webpackChunkName: "login" */ '../components/page/Login.vue'),
    meta: { title: '登录' }
  },

  {
    path: '/retrieve-password',
    component: () =>
      import( /* webpackChunkName: "login" */ '../components/page/RetrievePassword.vue'),
    meta: { title: '找回密码' }
  },
  {
    path: '*',
    redirect: '/404'
  }
  ]
});