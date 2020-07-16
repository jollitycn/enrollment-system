<template>
  <div v-loading="loading" element-loading-text="拼命加载中">
    <el-form class="form-card" ref="facultyForm" :rules='facultyRules' :model="facultyForm" label-width="120px"
      size="mini" label-position="right" v-loading="loading" element-loading-text="拼命加载中">
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="api院系名称">
            {{facultyForm.apiCollegeName}}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="招生人数">
            {{facultyForm.recruitAmount}}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="院系负责人">
            {{facultyForm.collegeManager}}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="院系名称" prop="collegeName">
            {{facultyForm.collegeName}}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="news-title">
      <leftBorder title="招生对象"></leftBorder>
      <div class="news-show" v-html="facultyForm.recruitTarget">
        {{facultyForm.recruitTarget}}
      </div>
    </div>
    <div class="news-title">
      <leftBorder title="培养目标"></leftBorder>
      <div class="news-show" v-html="facultyForm.cultivateAim">
        {{facultyForm.cultivateAim}}
      </div>
    </div>
    <div class="news-title">
      <leftBorder title="就业方向"></leftBorder>
      <div class="news-show" v-html="facultyForm.careerForward">
        {{facultyForm.careerForward}}
      </div>
    </div>
    <div class="news-title">
      <leftBorder title="院系简介"></leftBorder>
      <div class="news-show" v-html="facultyForm.comment">
        {{facultyForm.comment}}
      </div>
    </div>
    <el-button align="center" style="width: 100px;" type="primary" @click="returnFaculty">返回
    </el-button>
  </div>
</template>
<script>
  // import { queryCollegeInfoById } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  import { queryCollegeInfoById } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';

  export default {
    name: 'departmentShow',
    data() {
      return {
        loading: true,
        status: {
          showFaculty: false
        },
        facultyForm: {
          //查看院系的数据
          careerForward: '',
          collegeId: '',
          collegeManager: '',
          collegeName: '',
          comment: '',
          cultivateAim: '',
          mainCourse: '',
          recruitAmount: '',
          recruitTarget: '',
        }
      }
    },
    props: {
      planId: { type: String, default: '' },
      type: { type: String, default: '' }
    },
    created() {
      this.editShowFaculty()
    },
    methods: {
      /**
       * 查看招生院校
       * @author LiuGuoHao
       */
      editShowFaculty() {
        this.loading = true
        this.collegeId = this.$route.params.collegeId
        queryCollegeInfoById(this.collegeId).then(res => {
          this.facultyForm = res.data
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      },

      /**
       * 返回院系管理
       * @author LiuGuoHao
       */
      returnFaculty() {
        this.$router.go(-1)
      }
    },
  }
</script>
<style scoped>
  .news-show {
    margin-left: 20px;
    margin-top: 10px;
  }
  .news-title{
    margin-bottom: 20px;
    margin-left: 20px;
  }
</style>