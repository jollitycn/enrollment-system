<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生报表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 50px;">
      <i class="el-icon-question icon-class" title="筛选对应期间的任务指标"></i>
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="时间筛选">
          <el-date-picker :clearable="false" @change="change" v-model="time" type="daterange" range-separator="至"
            start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="所属招生计划">
          <el-select v-model="formInline.semesterId" class="wid100" placeholder="请选择招生计划">
            <el-option v-for="item in semesterQuery" :key="item.semesterId" :label="item.title"
              :value="item.semesterId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-button @click="admissionsReport" type="primary">查询</el-button>
      </el-form>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%" :border="true" :span-method="objectSpanMethod" v-loading="loading"
        element-loading-text="拼命加载中">
        <el-table-column prop="zname" label="组别" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" align="center"></el-table-column>
        <el-table-column label="拜访学校情况" align="center">
          <el-table-column prop="visitedSchoolNumber" label="拜访学校数" align="center"></el-table-column>
          <el-table-column prop="schoolNumber" label="来校参观数" align="center"></el-table-column>
          <el-table-column prop="peopleNumber" label="来校参观人数" align="center"></el-table-column>
        </el-table-column>
        <el-table-column label="报名缴费情况" align="center">
          <el-table-column prop="timeAmount" label="任务指标" align="center"></el-table-column>
          <el-table-column prop="totalAmount" label="报名人数" align="center"></el-table-column>
          <el-table-column prop="totalAmount" label="报名总人数" align="center"></el-table-column>
          <el-table-column prop="refundAmount" label="退款人数" align="center"></el-table-column>
          <el-table-column prop="payAmount" label="报名人数小计" align="center"></el-table-column>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import { admissionsReport } from "@/api/admin/Dashboard/Dashboard"
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    data() {
      return {
        semesterQuery: [],
        loading: false,
        time: [this.setTime(7), this.setTime()],
        formInline: {
          startTime: this.setTime(7),
          endTime: this.setTime()
        },
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
          this.$set(this.formInline, 'semesterId', this.semesterQuery[0].semesterId)
          this.admissionsReport()
        })
      },
      setTime(n = 0) {
        let data = (new Date()).getTime();
        let time = data - (1000 * 60 * 60 * 24 * Number(n));
        let eveData = new Date(time)
        let year = eveData.getFullYear();
        let month = eveData.getMonth() + 1 > 9 ? eveData.getMonth() + 1 : '0' + (eveData.getMonth() + 1);
        let day = eveData.getDate() > 9 ? eveData.getDate() : '0' + eveData.getDate();
        console.log(year + '-' + month + '-' + day)
        return year + '-' + month + '-' + day
      },
      // 选择时间
      change(time) {
        this.time = time;
        this.formInline.startTime = time[0];
        this.formInline.endTime = time[1];
      },
      //获取招生表报
      admissionsReport() {
        this.loading = true
        admissionsReport(this.formInline).then(res => {
          console.log(res);
          let data = res.data.regionVOS;
          let arr = [];
          if (data) {
            for (let i = 0; i < data.length; i++) {
              let data1 = data[i].regionChargerVOS;
              if (data1) {
                for (let j = 0; j < data1.length; j++) {
                  let rows = data1.length;
                  if (j === 0) {
                    arr.push({
                      zname: data[i].name,
                      rows,
                      ...data1[j]
                    })
                  } else {
                    arr.push({
                      zname: data[i].name,
                      ...data1[j]
                    })
                  }

                }
              }
            }
            // delete res.data.name
            arr.push({
              zname: "总计",
              ...res.data
            })
          }
          this.tableData = arr
          console.log(arr, 999);
          this.loading = false
        }).catch(err => {
          console.log(err);
          this.loading = false
        })
      },
      objectSpanMethod({ row, column, rowIndex, columnIndex }) {
        if (columnIndex === 0) {
          console.log(row, column, rowIndex, columnIndex, this.tableData.length);
          if (row.rows) {
            return {
              rowspan: row.rows,
              colspan: 1
            };
          } else if (rowIndex === this.tableData.length - 1) {
            return {
              rowspan: 1,
              colspan: 1
            };
          } else {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }
      }
    },
    created() {
      this.queryAllFinishedPlan()
    }
  }
</script>
<style>
  .icon-class {
    float: left;
    margin-top: 8px;
  }

  /* .el-table--border td,
  .el-table--border th {
    border-right: 1px solid;
  }

  .el-table--border th,
  .el-table--border th.gutter:last-of-type {
    border-bottom: 1px solid;
  }

  .el-table td,
  .el-table th.is-leaf {
    border-bottom: 1px solid;
  } */
</style>