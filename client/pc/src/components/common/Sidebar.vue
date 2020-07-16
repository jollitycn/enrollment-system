<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="white"
      text-color="#999999" active-text-color="#20a0ff" :unique-opened="true" @select="clickMenu" router>
      <template v-for="item in items">
        <template v-if="item.subs && item.subs.length">
          <el-submenu :index="item.index" :key="item.title">
            <template slot="title">
              <i :class="[item.icon,{'isClick':isFaMenu==item.index}]"></i>
              <span slot="title">{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu v-if="subItem.subs && subItem.subs.length" :index="subItem.index" :key="subItem.title">
                <template slot="title">{{ subItem.title }}</template>
                <el-menu-item v-for="threeItem in subItem.subs" :key="threeItem.menuId" :index="threeItem.route">
                  {{ threeItem.title }}</el-menu-item>
              </el-submenu>
              <el-menu-item v-else :index="subItem.index" :key="subItem.menuId">{{ subItem.title }}</el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index" :key="item.title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
  import bus from '../common/bus';
  import { schoolinfo } from '../../api/admin/API-Controller/APIController.js'
  export default {
    data() {
      return {
        isFaMenu: "",
        userInfo: {
          schoolid: '',
          name: '',
          userid: '',
          schoolname: ''
        },
        collapse: false,

        items: [{
          icon: 'el-icon-s-home',
          index: '/dashboard',
          title: '系统首页'
        }, {
          icon: 'el-icon-lx-shopfill',
          index: '1',
          title: '年度招生计划管理',
          subs: [
            {
              index: '/enrollmentTargetList',
              title: '招生目标列表'
            }, {
              index: '/enrollmentPlanList',
              title: '招生计划管理'
            }, {
              index: '/enrollmentManagement',
              title: '招生区域管理'
            },
            // {
            //     index: '/enrollmentPlanRelease',
            //     title: '招生计划发布'
            // },
          ]
        }, {
          icon: 'el-icon-s-management',
          index: '2',
          title: '招生过程管理',
          subs: [{
            index: '/consultingSchoolManagement',
            title: '到校咨询管理'
          }, {
            index: '/publicityPlanManagement',
            title: '宣讲计划管理'
          }, {
            index: '/dutySchedule',
            title: '值班计划管理'
          },]
        }, {
          icon: 'el-icon-s-data',
          index: '3',
          title: '招生数据分析',
          subs: [{
            index: '/enrollmentProgressAnalysis',
            title: '招生报表'
          }, {
            index: '/sourceAnalysis',
            title: '区域招生进度表'
          }, {
            index: '/propagandaConsultationAnalysis',
            title: '生源报表'
          }, {
            index: '/channelAnalysis',
            title: '渠道表'
          }]
        }, {
          icon: 'el-icon-tickets',
          index: '4',
          title: '信息推送管理',
          subs: [{
            index: '/informationPush',
            title: '信息推送'
          }]
        }, {
          icon: 'el-icon-s-operation',
          index: '5',
          title: '基础设置',
          subs: [{
            index: '/schoolResume',
            title: '学校信息管理'
          }, {
            index: '/feederSchoolManagement',
            title: '生源学校管理'
          }, {
            index: '/number-dictionaries',
            title: '数据字典'
          }]
        },

          // {
          //     icon: 'el-icon-rank',
          //     index: '6',
          //     title: '系统自带组件',
          //     subs: [
          //         {
          //             icon: 'el-icon-lx-cascades',
          //             index: 'table',
          //             title: '基础表格'
          //         },
          //         {
          //             icon: 'el-icon-lx-copy',
          //             index: 'tabs',
          //             title: 'tab选项卡'
          //         },
          //         {
          //             icon: 'el-icon-lx-calendar',
          //             index: '6-3',
          //             title: '表单相关',
          //             subs: [
          //                 {
          //                     index: 'form',
          //                     title: '基本表单'
          //                 },
          //                 {
          //                     index: 'editor',
          //                     title: '富文本编辑器'
          //                 },
          //                 {
          //                     index: 'markdown',
          //                     title: 'markdown编辑器'
          //                 },
          //                 {
          //                     index: 'upload',
          //                     title: '文件上传'
          //                 }
          //             ]
          //         },
          //         {
          //             icon: 'el-icon-lx-emoji',
          //             index: 'icon',
          //             title: '自定义图标'
          //         },
          //         {
          //             icon: 'el-icon-pie-chart',
          //             index: 'charts',
          //             title: 'schart图表'
          //         },
          //         {
          //             icon: 'el-icon-rank',
          //             index: '6-6',
          //             title: '拖拽组件',
          //             subs: [
          //                 {
          //                     index: 'drag',
          //                     title: '拖拽列表'
          //                 },
          //                 {
          //                     index: 'dialog',
          //                     title: '拖拽弹框'
          //                 }
          //             ]
          //         },
          //         {
          //             icon: 'el-icon-lx-global',
          //             index: 'i18n',
          //             title: '国际化功能'
          //         },
          //         {
          //             icon: 'el-icon-lx-warn',
          //             index: '6-8',
          //             title: '错误处理',
          //             subs: [
          //                 {
          //                     index: 'permission',
          //                     title: '权限测试'
          //                 },
          //                 {
          //                     index: '404',
          //                     title: '404页面'
          //                 }
          //             ]
          //         },
          //         {
          //             icon: 'el-icon-lx-locationfill',
          //             index: '/map',
          //             title: '百度地图'
          //         },
          //         {
          //             icon: 'el-icon-lx-punch',
          //             index: '/print',
          //             title: '打印'
          //         }
          //     ]
          // }
        ]
      };
    },
    watch: {
      "$route.meta.name"() {
        this.clickMenu(this.$route.meta.name)
      },
    },
    computed: {
      onRoutes() {

        return this.$route.meta.name;
      },
    },
    methods: {
      /**
       * 点击子菜单栏后更改对应的父菜单栏的颜色
       * @param {String}index 目前点击的index名称
       */
      clickMenu(index) {
        if (index == "/dashboard") {
          this.isFaMenu = "/dashboard"
        } else {
          this.items.forEach(item => {
            if (item.subs) {
              item.subs.forEach(ite => {
                if (ite.index == index) {
                  this.isFaMenu = item.index
                }
              })
            }
          })
        }
      },
      schoolinfo() {
        schoolinfo().then(res => {
          this.userInfo = res.data.data
          console.log('userInfo:', this.userInfo)
          localStorage.setItem("userInfo", JSON.stringify(this.userInfo))
          // console.log(JSON.parse(localStorage.getItem("userInfo")))
        })
      }
    },
    created() {
      this.clickMenu(this.$route.meta.name)
      // 通过 Event Bus 进行组件间通信，来折叠侧边栏
      bus.$on('collapse', msg => {
        this.collapse = msg;
        bus.$emit('collapse-content', msg);
      });
      // this.items = JSON.parse(localStorage.getItem('menus'))
      console.log('menus:', this.items);
      // this.schoolinfo();
    }
  };
</script>

<style scoped>
  >>>.el-submenu.is-active .el-submenu__title {
    color: #409EFF !important;
  }

  .isClick {
    color: #409EFF !important;
  }

  .sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
    color: #999999;
    background-color: white;
  }

  .sidebar::-webkit-scrollbar {
    width: 0;
  }

  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
  }

  .sidebar>ul {
    height: 100%;
  }
</style>