<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/enrollmentPlanList' }">招生计划管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{title + '计划'}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tabs">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <!-- 基本信息 -->
        <el-tab-pane label="基本信息" name="first">
          <baseInfoAdd v-if="activeName == 'first'" @plan-created="returnPlanStatus" :planId="planId" :type="type">
          </baseInfoAdd>
        </el-tab-pane>
        <!-- 学校简介 -->
        <el-tab-pane label="学校简介" name="second" :disabled="isCreate">
          <schoolIntrAdd v-if="activeName == 'second'" @school-created="returnRecruit" :planId="planId" :type="type"></schoolIntrAdd>
          </schoolIntrAdd>
        </el-tab-pane>
        <!-- 招生简介 -->
        <el-tab-pane label="招生简介" name="third" :disabled="isCreate">
          <studentRecruit v-if="activeName == 'third'" @recruit-created="returnDepartment" :planId="planId"
            :type="type"></studentRecruit>
        </el-tab-pane>
        <!-- 院系管理 -->
        <!-- 院系管理列表 -->
        <el-tab-pane label="院系介绍" name="fourth" :disabled='isCreate'>
          <component v-if="activeName == 'fourth'" :is="status.departOrMajor" :planId="planId" :type="type"
            :addshow="status.addShow">
          </component>
          <!-- <component is="departmentList" :planId="planId"></component> -->
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
  import baseInfoAdd from './plan-add/base-info-add.vue';
  import schoolIntrAdd from './plan-add/school-introduce-add.vue';
  import studentRecruit from './plan-add/student-recruit.vue'
  import departmentShow from './department-manage/department-show.vue';
  import departmentList from './department-manage/department-list.vue';
  import departmentEdit from './department-manage/department-edit.vue'
  import majorList from './major-manage/major-list.vue'
  import majorEdit from './major-manage/major-edit.vue'
  import majorShow from './major-manage/major-show.vue'
  export default {
    components: {
      baseInfoAdd,
      departmentList,
      departmentShow,
      schoolIntrAdd,
      studentRecruit,
      departmentEdit,
      majorList,
      majorEdit,
      majorShow
    },
    data() {
      return {
        isCreate:false,
        type: '',
        planId: '',
        activeName: 'first',
        title: '',
        status: {
          departOrMajor: 'departmentList',
          addShow: true, //展示学校与招生简介，增加保存时使用
        }
      }
    },
    created() {
      this.schoolId = JSON.parse(localStorage.getItem("userInfo")).schoolid
      this.planId = this.$route.params.planId;
      this.type = this.$route.params.type;
      this.isEdit();
      this.majOrDepar();
    },
    methods: {
      /**
       * 添加基本信息成功后修改其余的状态
       * @param planStatus{Number} 传入的基本信息保存成功后的状态 --true
       * @param planId{number} 传入基本信息保存成功后的planId
       * @author LiuGuoHao
       */
      returnPlanStatus(planStatus, planId) {
        this.status.addShow = planStatus
        this.planId = planId
        this.type = "edit"
        this.$router.push({
          // path: `/enrollmentPlanList/addenrollmentPlan/${type}/${operation}/${planId}`
          path: `/enrollmentPlanList/school-introduce/${this.type}/${planId}`
        });
      },
      /**
       * 添加学生简历成功后跳转到招生简历
       * @author LiuGuoHao
       */
      returnRecruit(planId) {
        this.$router.push({
          // path: `/enrollmentPlanList/addenrollmentPlan/${type}/${operation}/${planId}`
          path: `/enrollmentPlanList/student-recruit/${this.type}/${planId}`
        });
      },
      /**
       * 添加招生简历成功后跳转到院系介绍
       * @author LiuGuoHao
       */
      returnDepartment() {
        // this.activeName = activeName
        this.$router.push({
          path: `/enrollmentPlanList/department/department-list/${this.type}/${this.planId}`
        })
      },
      /**
      * 各个tabs的默认值切换
      * @author LiuGuoHao
      */
      majOrDepar() {
        switch (this.$route.params.title) {
          //默认为基本信息tabs
          case "base-info":
            this.activeName = 'first'
            break;
          //默认为学校简介tabs
          case "school-introduce":
            this.activeName = 'second'
            break;
          //默认为招生简介tabs
          case "student-recruit":
            this.activeName = 'third'
            break;
          //默认为编辑院系tabs
          case "department-edit":
            this.activeName = 'fourth'
            this.status.departOrMajor = 'departmentEdit'
            break;
          //默认为查看院系tabs
          case "department-show":
            this.activeName = 'fourth'
            this.status.departOrMajor = 'departmentShow'
            break;
          //默认为专业管理tabs
          case "major-list":
            this.activeName = 'fourth'
            this.status.departOrMajor = 'majorList'
            break;
          //默认为创建或编辑专业tabs
          case "major-edit" || "major-create":
            this.activeName = 'fourth'
            this.status.departOrMajor = 'majorEdit'
            break;
          //默认为查看专业tabs
          case "major-show":
            this.activeName = 'fourth'
            this.status.departOrMajor = 'majorShow'
            break;
          //默认为院系管理列表tabs
          default:
            this.activeName = 'fourth'
            this.status.departOrMajor = 'departmentList'
            break;
        }
      },
      /**
       * 默认标题
       * @author LGH
       */
      isEdit() {
        if (this.type == "see") {
          this.title = '查看'
        } else if (this.type == "creat") {
          this.title = '创建'
          this.isCreate = true
        } else if (this.type == "edit") {
          this.title = '编辑'
        }
      },
      /**
      * tabs的点击事件
      * @author LiuGuoHao
      */
      handleClick(tab, event) {
        // if (tab.name == "fourth") {
        //   this.$router.push({
        //     path: `/enrollmentPlanList/addenrollmentPlan/departmentList/see/${this.operation}/${this.planId}`
        //   })
        //   this.status.departOrMajor = 'departmentList'
        // }
        switch (tab.name) {
          case "first":
            this.$router.push({
              path: `/enrollmentPlanList/base-info/${this.type}/${this.planId}`
            })
            break;
          case "second":
            this.$router.push({
              path: `/enrollmentPlanList/school-introduce/${this.type}/${this.planId}`
            })
            break;
          case "third":
            this.$router.push({
              path: `/enrollmentPlanList/student-recruit/${this.type}/${this.planId}`
            })
            break;
          case "fourth":
            this.$router.push({
              path: `/enrollmentPlanList/department/department-list/${this.type}/${this.planId}`
            })
            break;
        }
      }
    }
  };
</script>
<style scoped>

</style>