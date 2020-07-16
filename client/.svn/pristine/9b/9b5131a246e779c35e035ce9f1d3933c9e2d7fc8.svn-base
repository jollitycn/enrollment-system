<template>
  <div class="page-container">
    <div class="duty-plan-page">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>值班计划管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tabs" style="margin-top: 30px;">
      <el-tabs v-model="activeName" type="card">
        <!-- 值班列表 -->
        <el-tab-pane label="值班列表" name="first">
          <div>
            <el-form>
              <el-form-item class="floMar">
                <!-- <el-date-picker
                      v-model="value1"
                      type="daterange"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                    ></el-date-picker> -->
                <el-date-picker v-model="value1" type="date" value-format="yyyy-MM-dd" placeholder="选择值班日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item class="floMar">
                <el-input @keyup.enter.native="handleSearch" v-model.trim="query.userName" placeholder="请输入负责人名称"
                  class="handle-input"></el-input>
              </el-form-item>
              <el-form-item class="floMar">
                <el-select v-model="query.planId" class="wid100" placeholder="请选择招生计划">
                  <el-option v-for="item in semesterQuery" :key="item.planId" :label="item.title" :value="item.planId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-button style="float: right;margin-right: 20px;" type="primary" icon="el-icon-search"
                @click="handleSearch">查询</el-button>
            </el-form>
          </div>
          <el-table :data="tableData" style="width: 100%" :row-class-name="dutyUnusual" v-loading="loading"
            element-loading-text="拼命加载中">
            <el-table-column fixed width="55" label="序号" align="center">
              <template slot-scope="scope">{{scope.$index + 1}}</template>
            </el-table-column>
            <el-table-column prop="dutyDate" label="值班日期" align="center"></el-table-column>
            <el-table-column prop="typeName" label="值班类型" align="center"></el-table-column>
            <el-table-column prop="startTime" label="开始日期" align="center"></el-table-column>
            <el-table-column prop="endTime" label="结束日期" align="center"></el-table-column>
            <el-table-column prop="userName" label="负责人" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-s-operation"
                  @click="jump(scope.row.type,scope.row.id,scope.row.dutyDate)">调整</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
              :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
              @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
          </div>
        </el-tab-pane>
        <!-- 异常列表 -->
        <el-tab-pane label="异常列表" name="second">
          <el-table :data="tableData1" style="width: 100%" :row-class-name="tableRowClassName" v-loading="loading"
            element-loading-text="拼命加载中">
            <el-table-column fixed width="55" label="序号" align="center">
              <template slot-scope="scope">{{scope.$index + 1}}</template>
            </el-table-column>
            <el-table-column prop="dutyDate" label="值班日期" align="center"></el-table-column>
            <el-table-column prop="typeName" label="值班类型" align="center"></el-table-column>
            <el-table-column prop="startTime" label="开始日期" align="center"></el-table-column>
            <el-table-column prop="endTime" label="结束日期" align="center"></el-table-column>
            <el-table-column prop="userName" label="负责人" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" icon="el-icon-s-operation"
                  @click="jump(scope.row.type,scope.row.id,scope.row.dutyDate)">调整</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination background layout="sizes, prev, pager, next,total" :current-page="query.pageNum"
              :page-size="query.pageSize" :total="pageTotal1" @current-change="handlePageChange"
              :page-sizes="[10, 20, 50, 100]" @size-change="handleSizeChange"></el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
  import {
    dutyPage,
    dutypageComplite,
    getPlanList
  } from "../../../../api/admin/enrollmentProcessManagement/dutySchedule";
  import { getConsultationId } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement"
  export default {
    name: "dutySchedule",
    data() {
      return {
        loading: false,
        activeName: "first", //选项卡默认值
        pageTotal: 0,
        pageTotal1: 0,
        value1: "",
        query: {
          startTime: "",
          endTime: "",
          userName: "",
          pageNum: 1,
          pageSize: 10
        },
        semesterQuery: [],
        tableData: [],
        tableData1: [],
        ConsultationDay: ''
      };
    },
    created() {
      this.getPlanList()
    },
    methods: {
      /**
       * 获取计划列表
       * @author lgh
       */
      getPlanList() {
        getPlanList().then(res => {
          this.semesterQuery = res.data
          this.$set(this.query, "planId", res.data[0].planId)
          this.getData();
          this.getDatalist();
        })
      },
      dutyUnusual({ row, rowIndex }) {
        let isUnusual = false
        this.tableData.forEach((item, index) => {
          let judge = (row.dutyDate == item.dutyDate) && (row.userName == item.userName) && (rowIndex != index)
          if (judge) {
            isUnusual = true
          }
        })
        if (isUnusual) {
          return "warning-row";
        } else {
          return "";
        }
      },
      tableRowClassName({ row, rowIndex }) {

        if (row.conflict) {
          return "warning-row";
        }
        return "";
      },
      /**
       * 获取值班列表
       * @author cwz
       */
      getData() {
        this.loading = true
        dutyPage(this.query)
          .then(res => {
            this.tableData = res.data.records;
            this.pageTotal = res.data.total || 0;
            this.loading = false
          })
          .catch(err => {
            console.log(err);
            this.loading = false
          });
      },
      /**
       * 获取异常列表
       * @author cwz
       */
      getDatalist() {
        this.loading = true
        dutypageComplite(this.query)
          .then(res => {
            console.log(res, "res....")
            this.tableData1 = res.data.records;
            this.pageTotal1 = res.data.total || 0;
            this.loading = false
          })
          .catch(err => {
            console.log(err);
            this.loading = false
          });
      },
      /**
       * 跳转 
       * @param type {} type==0 跳转到到校咨询-接待日/type==1 跳转到到校咨询-日常 /type==2||type==3 跳转到宣讲计划-主讲 || 宣讲计划-协助
       * @author cwz
       */
      jump(type, id, date) {
        console.log(type, id, date)
        if (type == 0) {
          this.$router.push({
            path: `/addConsultingSchoolManagementDay`,
            query: {
              date: date,
              isOpenday: true,
              consultationId: id,
              openid: "1"
            }
          })
        } else if (type == 1) {
          this.$router.push({
            path: `/consultingSchoolManagement`
          });
        } else if (type == 2 || type == 3) {
          this.$router.push({ path: `/publicityPlanManagement/edit/${id}` })
        }
      },
      /**
       * 搜索
       * @author cwz
       */
      handleSearch() {
        console.log(this.value1, 111);

        if (this.value1) {
          this.query.startTime = this.value1 + " 00:00:00";
          this.query.endTime = this.value1 + " 23:59:59"
        } else {
          this.query.startTime = "";
          this.query.endTime = ""
        }
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getData();
      },
      // 页数变化
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getData();
      }
    }
  };
</script>
<style scoped>
  .floMar {
    float: left;
    margin-left: 10px;
  }

  .tabs {
    widows: 700px;
  }

  .handle-box {
    margin-bottom: 20px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }

  .ml10 {
    margin-left: 10px;
  }

  >>>.warning-row {
    background-color: #f56c6c;
    color: #f0f0f0;
  }

  /* >>> .el-table tr:hover{
  background-color: #f56c6c ;
} */

  >>>.el-table__body tr.hover-row.warning-row.current-row>td,
  >>>.el-table__body tr.hover-row.warning-row.el-table__row--striped.current-row>td,
  >>>.el-table__body tr.hover-row.warning-row.el-table__row--striped>td,
  >>>.el-table__body tr.hover-row.warning-row>td {
    background-color: red;
    color: yellow;
  }
</style>