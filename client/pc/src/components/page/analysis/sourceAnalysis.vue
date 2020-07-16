<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>区域招生进度表</el-breadcrumb-item>
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
        <el-button @click="regionalEnrollmentProgressReport" type="primary">查询</el-button>
      </el-form>
      <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column prop="groupName" label="组名" align="center"></el-table-column>
        <el-table-column prop="targetAmount" label="计划人数" align="center"></el-table-column>
        <el-table-column prop="applyAmount" label="报名人数" align="center"></el-table-column>
        <el-table-column prop="payAmount" label="交费人数" align="center"></el-table-column>
        <el-table-column prop="refundAmount" label="退费人数" align="center"></el-table-column>
        <el-table-column prop="degreeCompletionAmount" label="完成度" align="center"></el-table-column>
      </el-table>
    </div>
    <div class="form">
      <el-form ref="form" :model="form" label-width="150px">
        <el-form-item class="form-content" label="目标人数">
          {{form.planAllNumber}}
        </el-form-item>
        <el-form-item class="form-content" label="实际招生人数">
          {{form.numberCompletions}}
        </el-form-item>
        <el-form-item class="form-content" label="完成度">
          {{form.allDegreeCompletionAmount}}
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { regionalEnrollmentProgressReport } from "@/api/admin/Dashboard/Dashboard"
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    data() {
      return {
        formInline: {
          semesterId:''
        },
        semesterQuery: [],
        loading: false,
        tableData: [],
        form: {}
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
          this.regionalEnrollmentProgressReport()
        })
      },
      //区域招生进度表
      regionalEnrollmentProgressReport() {
        this.loading = true
        regionalEnrollmentProgressReport(this.formInline).then(res => {
          let data = res.data.regionalEnrollmentProgressReportVOS;
          // for (let i = 0; i < data.length; i++) {
          //   if (data[i].targetAmount != 0) {
          //     data[i].refundAmountLv = ((data[i].applyAmount / data[i].targetAmount).toFixed(2)) * 100 + '%'
          //   }else{
          //     //对除数为0时的处理
          //     data[i].refundAmountLv = "未知"
          //   }
          // }
          // console.log(data, 1111);
          this.form = res.data
          this.tableData = data;
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
<style scoped>
  .form {
    margin-top: 20px;
    margin-left: 20px;
  }

  .form-content {
    height: 14px;
  }

  >>>.el-form-item__label {
    color: #909399;
    font-weight: 540;
  }
</style>