<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item>宣讲计划管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 50px;">
      <div style="margin-top: 20px;">
        <el-form :inline="true" :model="query" class="demo-form-inline">
          <el-form-item label="宣讲日期">
            <el-date-picker v-model="searchTime" type="daterange" @change="changeTime" format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="宣讲人">
            <el-input @keyup.enter.native="getList" v-model="query.speakerName" maxlength="32" placeholder="请输入宣讲人名称"
              class="handle-input mr10"></el-input>
          </el-form-item>
          <el-form-item label="招生计划">
            <el-select v-model="query.semesterId" placeholder="请选择招生计划">
              <el-option v-for="item in semesterQuery" :key="item.semesterId" :label="item.title"
                :value="item.semesterId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="go('add','add')">新增</el-button>
          <el-button type="primary" style="float: right;margin-right: 20px" @click="getList">查询</el-button>
        </el-form>
      </div>
      <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
        <el-table-column prop="preachTime" label="宣讲日期" width="160" align="center">
          <template slot-scope="scope">{{scope.row.preachTime}} - {{scope.row.endTime}}</template>
        </el-table-column>
        <el-table-column prop="title" label="主题" width="250" align="center"></el-table-column>
        <el-table-column prop="speakerName" label="宣讲人" width="250" align="center"></el-table-column>
        <el-table-column prop="planAmount" label="计划人数" width="250" align="center"></el-table-column>
        <el-table-column prop="signAmount" label="签到人数" width="250" align="center"></el-table-column>
        <el-table-column prop="statusName" label="状态" width="250" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" width="300" align="center">
          <template slot-scope="scope">
            <!-- status 0删除，1草稿(发布，编辑，查看，删除)，2发布，(编辑，查看)，3完成(查看) -->
            <el-button type="success" v-if="scope.row.status==1" @click="publishPlan(scope.row.preachPlanId)">发布
            </el-button>
            <el-button type="primary" v-if="scope.row.status>0" @click="go('see',scope.row.preachPlanId)">查看</el-button>
            <el-button type="primary" v-if="scope.row.status==1||scope.row.status==2"
              @click="go('edit',scope.row.preachPlanId)">编辑</el-button>
            <el-button type="danger" v-if="scope.row.status==1" @click="del(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
          :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
          @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getList,
    deleteData,
    publish
  } from "@/api/management/enrollmentProcessManagement/publicityPlanManagement";
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    name: "publicityPlanManagement",
    data() {
      return {
        semesterQuery: [],
        loading: false,
        value1: "",
        value2: "",
        tableData: [],
        tableData1: [
          {
            date: "宝安1组",
            name: "3",
            address: "10",
            addrss: "13413513600"
          }
        ],
        tableData2: [],
        editVisible: false,
        editVisible1: false,
        editVisible2: false,
        pageTotal: 0,
        searchTime: "", //搜索时间
        form: {
          name: "",
          region: "",
          date1: "",
          date2: "",
          delivery: false,
          type: [],
          resource: "",
          desc: ""
        },
        //搜索表单
        query: {
          pageNum: 1,
          pageSize: 10,
          speakerName: "",
          preachTime: "",
          endTime: "",
          planId: ""
        }
      };
    },
    methods: {
      /**
       * 获取所有已发布计划信息
       * @author lgh
       */
      queryAllFinishedPlan() {
        queryAllFinishedPlan().then(res => {
          this.semesterQuery = res.data
          this.$set( this.query,'semesterId',this.semesterQuery[0].semesterId)
          this.getList();
        })
      },
      //选择事件
      changeTime(time) {
        console.log(time, 555);
        if (time) {
          this.$set(this.query, "preachTime", time[0] + " 00:00:00");
          this.$set(this.query, "endTime", time[1] + " 23:59:59");
        } else {
          this.$set(this.query, "preachTime", "");
          this.$set(this.query, "endTime", "");
        }

        console.log(this.query, 5656);
      },
      /**
      * 发布操作处理
      * @param {string } preachPlanId 宣讲计划ID
      * @author lmh
      */
      publishPlan(preachPlanId) {
        this.$confirm(`发布后将不允许删除，请确认是否发布`, "发布提示", {
          type: "warning",
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        }).then(() => {
          this.publish(preachPlanId);
        }, () => {
          console.warn('已取消发布')
        })
      },
      /**
       * 发布请求处理
       * @param {string } preachPlanId 宣讲计划ID
       * @author lmh
       */
      publish(preachPlanId) {
        publish(preachPlanId).then(res => {
          this.$message.success("发布成功！");
          this.getList();
        });
      },

      //点击增改查
      go(type, id) {
        this.$router.push({ path: `/publicityPlanManagement/${type}/${id}` });
      },

      // 删除操作
      del(item) {
        this.open(item);
      },
      //删除提示框
      open(item) {
        console.log(item);
        this.$confirm(`请确定是否删除 ${item.title} 主题`, "删除操作", {
          type: "warning",
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        })
          .then(() => {
            this.deleteId(item.preachPlanId);
          })
          .catch(() => { });
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getList();
      },
      //每页条数改变
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getList();
      },
      //获取列表
      getList() {
        this.loading = true
        getList(this.query)
          .then(res => {
            console.log(res);
            this.tableData = res.data.records;
            this.$set(this.query, "pageNum", res.data.current);
            this.$set(this.query, "pageNum", res.data.current);
            this.pageTotal = res.data.total;
            this.loading = false
          })
          .catch(err => {
            console.log(err);
            this.loading = false
          });
      },
      //删除
      deleteId(id) {
        deleteData(id)
          .then(res => {
            this.$message.success("删除主题成功！");
            this.getList();
          })
          .catch(err => {
            console.log(err, "删除");
          });
      }
    },
    created() {
      this.queryAllFinishedPlan()
    }
  };
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 195px;
    display: inline-block;
  }

  .table {
    width: 100%;
    font-size: 14px;
  }

  .red {
    color: #ff0000;
  }

  .blue {
    color: #673ab7;
  }

  .mr10 {
    margin-right: 10px;
  }

  .refundButton {
    width: 90px;
    position: relative;
    top: -50px;
    float: right;
  }

  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }
</style>