<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>生源报表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 50px;">
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="所属招生计划">
          <el-select v-model="formInline.semesterId" class="wid100" placeholder="请选择招生计划">
            <el-option v-for="item in semesterQuery" :key="item.semesterId" :label="item.title"
              :value="item.semesterId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-button @click="studentSourceReport" type="primary">查询</el-button>
      </el-form>
      <el-table :data="tableData" style="width: 100%" show-summary v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column prop="schoolName" label="学校名称" align="center"></el-table-column>
        <el-table-column prop="applyAmount" label="报名人数" align="center"></el-table-column>
        <el-table-column prop="payAmount" label="交费人数" align="center"></el-table-column>
        <el-table-column prop="refundAmount" label="退费人数" align="center"></el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import { studentSourceReport } from "@/api/admin/Dashboard/Dashboard"
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    data() {
      return {
        formInline: {
          semesterId: ''
        },
        semesterQuery: [],
        loading: false,
        tableData: []
      }
    },
    methods: {
      /**
       * 获取所有已发布计划信息
       * @author lgh
       */
      queryAllFinishedPlan() {
        queryAllFinishedPlan().then(res => {
          this.semesterQuery = res.data
          this.$set( this.formInline,'semesterId',this.semesterQuery[0].semesterId)
          this.studentSourceReport()
        })
      },
      //生源报表
      studentSourceReport() {
        this.loading = true
        studentSourceReport(this.formInline).then(res => {
          this.tableData = res.data.sourceSchoolReportVOS;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      }
    },
    created() {
      this.queryAllFinishedPlan()
    }
  }
</script>