<template>
  <div style="margin-left: 10px;" v-loading="loading" element-loading-text="拼命加载中">
    <h4>院系名称：{{majorForm.collegeName}}</h4>
    <h4 style="margin-bottom: 20px;">专业名称：{{majorForm.majorName}}</h4>
    <div style="margin-left: 30px;">
      <div>
        <p>计划招生人数：{{majorForm.recruitAmount}}</p>
        <p>学费：{{majorForm.fee}}</p>
      </div>
      <p>学制：{{majorForm.majorType}}</p>
    </div>
    <div style="margin-top: 20px;">
      <leftBorder title="培养目标"></leftBorder>
      <div class="news-show" v-html="majorForm.cultivateAim">
        {{majorForm.cultivateAim}}
      </div>
      <leftBorder title="主要课程"></leftBorder>
      <div class="news-show" v-html="majorForm.mainCourse">
        {{majorForm.mainCourse}}
      </div>
      <leftBorder title="就业方向"></leftBorder>
      <div class="news-show" v-html="majorForm.careerForward">
        {{majorForm.careerForward}}
      </div>
      <leftBorder title="专业简介"></leftBorder>
      <div class="news-show" v-html="majorForm.comment">
        {{majorForm.comment}}
      </div>
      <el-button align="center" style="width: 110px;margin-top: 20px;" type="primary" @click="returnMajor">返回专业管理
      </el-button>
    </div>
  </div>
</template>

<script>
  import { queryMajorInfoByMajorId } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  export default {
    name: 'majorShow',
    data() {
      return {
        loading: true,
        planId: '',
        majorId: '',
        apiId: '',
        collegeId: '',
        majorForm: {}
      }
    },
    created() {
      this.planId = this.$route.params.planId;
      this.majorId = this.$route.params.majorId;
      this.apiId = this.$route.params.apiId;
      this.collegeId = this.$route.params.collegeId;
      this.getData()
    },
    methods: {
      getData() {
        this.loading = true
        let query = {majorId:this.majorId,planId:this.planId}
        queryMajorInfoByMajorId(query).then(res => {
          this.majorForm = res.data
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      },
      /**
       * 返回到专业管理列表
       * @author LGH
       */
      returnMajor() {
        this.$router.go(-1)
      }
    },
  }

</script>
<style scoped>
  .news-show {
    margin-left: 20px;
  }
</style>