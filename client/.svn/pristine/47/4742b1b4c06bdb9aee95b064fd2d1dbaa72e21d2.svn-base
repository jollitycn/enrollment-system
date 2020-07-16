<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生计划管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-form :inline="true" :model="query" class="demo-form-inline">
        <el-form-item label="学期">
          <!-- <el-select placeholder="请选择学期" class="mr10" style="width: 200px;" v-model="query.semester">
            <el-option @keyup.enter.native="handleSearch" v-for="item in semesterQuery" :key="item.name"
              :label="item.name" :value="item.name">
            </el-option>
          </el-select>-->
          <el-input @keyup.enter.native="handleSearch" maxlength="15" placeholder="请输入学期名称" class="handle-input mr10"
            v-model="query.semester"></el-input>
        </el-form-item>
        <el-form-item label="标题">
          <el-input @keyup.enter.native="handleSearch" maxlength="32" placeholder="请输入标题" class="handle-input mr10"
            v-model="query.title"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
          <el-button type="primary" icon="el-icon-refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-button type="primary" style="float: right;margin-bottom: 10px;" icon="el-icon-lx-add"
        @click="createOrSee(0,'add')">新增</el-button>

      <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column label="序号" type="index" width="55" align="center"></el-table-column>
        <el-table-column prop="semester" label="学期" width="180" align="center"></el-table-column>
        <el-table-column prop="title" label="计划标题" width="180" align="center"></el-table-column>
        <!-- <el-table-column prop="planId" label="计划Id" width="180" align="center"></el-table-column> -->
        <el-table-column prop="creatorName" label="创建人" width="180" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建日期" width="180" align="center">
          <!-- <template slot-scope="scope">{{createTime|resetTime}} </template> -->
        </el-table-column>
        <el-table-column prop="status" label="状态" width="180" align="center">
          <template slot-scope="scope">{{statusNews[scope.row.status]}}</template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="180" align="center">
          <!-- <template slot-scope="scope">{{publishTime|resetTime2}} </template> -->
        </el-table-column>
        <el-table-column prop="publisherName" label="发布人" width="180" align="center"></el-table-column>
        <!-- <el-table-column prop="isDefaulted" label="默认" width="180" align="center">
          <template slot-scope="scope">{{scope.row.isDefaulted==1?'是':'否'}}</template>
        </el-table-column>-->
        <el-table-column fixed="right" label="操作" width="354" align="center">
          <template slot-scope="scope">
            <el-button @click="copeOperation(scope.row.planId)" v-if="scope.row.status!=3">复制</el-button>
            <el-button type="success" @click="updatePlanStatus(2,scope.row.planId)"
              v-if="scope.row.status==1&&scope.row.status!=3">发布</el-button>
            <!-- <el-button type="warning" @click="updateIsDefaulted(scope.row.planId)"
              v-if="scope.row.isDefaulted==0&&scope.row.status==2">默认
            </el-button>-->
            <el-button type="primary" @click="createOrSee(1,scope.row.planId)">查看</el-button>
            <el-button type="primary" @click="createOrSee(2,scope.row.planId)"
              v-if="scope.row.status!=2&&scope.row.status!=3">编辑</el-button>
            <el-button type="danger" @click="updatePlanStatus(0,scope.row.planId)"
              v-if="scope.row.status!=2&&scope.row.status!=3">删除</el-button>
            <el-button type="success" @click="updatePlanStatus(3,scope.row.planId)"
              v-if="!(scope.row.status==1)&&scope.row.status!=3">完成</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination">
      <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
        :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
        @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
    </div>
    <!-- 复制弹出框 -->
    <el-dialog title="请选择学期" :visible.sync="dialogVisible" width="30%" :before-close="handleClose" v-loading="loading">
      <el-select placeholder="请选择学期" class="mr10" style="width: 100%;" v-model="copeForm.semesterId"
        @change="getSemesterTime()">
        <el-option @keyup.enter.native="handleSearch" v-for="item in semesterQuery" :key="item.id" :label="item.name"
          :value="item.id"></el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCopyPlanInfo()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { termlist } from "@/api/admin/API-Controller/APIController.js";
  import { addCollegeInfo } from "@/api/admin/enrollmentPlanList/addenrollmentPlan.js";
  import {
    listGroupInfoPage,
    updateIsDefaulted,
    updatePlanStatus,
    addCopyPlanInfo,
    updatePlanStatusEnd
  } from "@/api/admin/enrollmentPlanList/enrollmentPlan.js";
  export default {
    name: "enrollmentPlanManagement",
    data() {
      return {
        loading: false,
        dialogVisible: false,
        //发布状态
        statusNews: {
          0: "删除",
          1: "未发布",
          2: "已发布",
          3: "已完成"
        },
        copeForm: {
          semesterEnd: '',
          semesterStart: '',
          copyId: "",
          semesterId: "",
          semester: "",
          creatorId: "",
          creatorName: ""
        },
        planStatus: {
          planId: "",
          status: ""
        },
        semesterQuery: [],
        query: {
          semester: "",
          title: "",
          pageNum: 1,
          pageSize: 10
        },
        defaultedQuery: {
          planId: "",
          isDefaulted: 1
        },
        pageTotal: 0,
        tableData: []
      };
    },
    created() {
      this.schoolId = JSON.parse(localStorage.getItem("userInfo")).schoolId;
      this.getData();
      this.termlist();
    },
    methods: {
      getData() {
        this.listGroupInfoPage();
      },
      //获取计划管理基本信息
      listGroupInfoPage() {
        this.loading = true;
        listGroupInfoPage(this.query)
          .then(res => {
            this.tableData = res.data.records;
            this.pageTotal = res.data.total || 0;
            this.loading = false;
          })
          .catch(err => {
            console.error();
            this.loading = false;
          });
      },

      // 取消复制操作
      handleClose() {
        this.$confirm("确定要取消复制吗？").then(_ => {
          this.copeForm = {
            copyId: "",
            semester: "",
            semesterEnd: '',
            semesterStart: '',
            creatorId: "",
            creatorName: ""
          };
          this.dialogVisible = false;
        });
      },
      /**
       * 复制弹出框操作
       * @param copeId{String} 需要复制的计划的planId
       * @author LiuGuoHao
       */
      copeOperation(copyId) {
        this.dialogVisible = true;
        this.copeForm.copyId = copyId;
      },
      //根据学校ID获取学期信息
      termlist() {
        this.loading = true;
        termlist(this.schoolId)
          .then(res => {
            this.semesterQuery = res.data.data;
            this.loading = false;
          })
          .catch(err => {
            console.error();
            this.loading = false;
          });
      },
      /**
       * 编辑计划的默认状态
       * @param planId{int} 编辑默认状态所需的计划ID
       * @author LiuGuoHao
       */
      updateIsDefaulted(planId) {
        this.$confirm(
          "确认要设置该计划为默认吗？设置成功后将重新登录！",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        ).then(() => {
          this.defaultedQuery.planId = planId;
          updateIsDefaulted(this.defaultedQuery).then(res => {
            this.$router.push("/login");
            localStorage.clear();
          });
          this.$message({
            type: "success",
            message: "更改默认状态成功！请重新登录！"
          });
        });
      },
      /**
       * 根据ID获取开始时间和结束时间
       * @param id{number} 获取的名称所需要的id
       * @param query{array} 需要获取的name对应的数组
       * @author LiuGuoHao
       */
      getTimeById(id, query) {
        var obj = query.find(function (x) {
          return x.id == id;
        });
        let time = { start: obj.start, end: obj.end, semesterName: obj.name };
        return time;
      },
      /**
       * 复制学期改变时，赋予对应的学期开始以及结束时间
       * @author LiuGuoHao
       */
      getSemesterTime() {
        let semesterInfo = this.getTimeById(
          this.copeForm.semesterId,
          this.semesterQuery
        );
        this.copeForm.semesterEnd = semesterInfo.end;
        this.copeForm.semesterStart = semesterInfo.start;
        this.copeForm.semesterName = semesterInfo.semesterName;
      },
      /**
       * 发布与删除的操作
       * @param status{int} 需要更改的状态（0：删除，1：未发布，2：已发布，3:已完成）
       * @param status{String} 需要更改的计划的ID
       * @author LiuGuoHao
       */
      updatePlanStatus(status, planId) {
        this.planStatus.status = status;
        this.planStatus.planId = planId;
        let title = ["删除", "取消发布", "发布", "完成"]; //修改成功提示词

        if (status == 2) {
          this.$confirm(
            "确认要发布该计划吗？设置成功后将重新登录！",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }
          ).then(() => {
            updatePlanStatus(this.planStatus).then(res => {
              this.$router.push("/login");
              localStorage.clear();
              this.$message({
                type: "success",
                message: "发布计划成功！请重新登录！"
              });
            })
          });
        }
        else {
          this.$confirm(`确定要${title[status]}该计划吗？`, "提示", {
            type: "warning"
          }).then(() => {
            if (status == 3) {
              updatePlanStatusEnd(this.planStatus).then(res => {
                this.$message.success(title[status] + "成功！");
                this.getData();
              });
            } else {
              updatePlanStatus(this.planStatus).then(res => {
                this.$message.success(title[status] + "成功！");
                this.getData();
              });
            }
          });
        }
      },
      /**
       * 复制操作
       * @author LiuGuoHao
       */
      addCopyPlanInfo() {
        this.copeForm.semester = this.getNameById(
          this.copeForm.semesterId,
          this.semesterQuery
        );
        addCopyPlanInfo(this.copeForm).then(res => {
          let planId = res.data;
          // addCollegeInfo(planId).then(ref => { }); //新增院系
          this.$message.success("复制成功！");
          this.copeForm.semesterId = "";
          this.dialogVisible = false;
          this.getData();
        });
      },
      /**
       * 根据ID获取名称
       * @param id{number} 获取的名称所需要的id
       * @param query{array} 需要获取的name对应的数组
       * @author LiuGuoHao
       */
      getNameById(id, query) {
        var obj = query.find(function (x) {
          return x.id == id;
        });
        return obj.name;
      },
      handleSearch() {
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },
      handleReset() {
        this.query = {
          semester: "",
          title: "",
          pageNum: 1,
          pageSize: 10
        };
        this.getData();
      },
      open() {
        this.$alert("这是一段内容", "标题名称", {
          confirmButtonText: "确定",
          callback: action => {
            this.$message({
              type: "success",
              message: `搞定`
            });
          }
        });
      },
      /**
       * 新增/查看/编辑操作
       * @param operation{int} 操作判断，0为新增，1为查看，2为编辑
       * @param planId{int} 查看或编辑操作所需要的计划id
       */
      createOrSee(operation, planId) {
        let typeQuery = ["creat", "see", "edit"];
        let type = typeQuery[operation];
        this.$router.push({
          // path: `/enrollmentPlanList/addenrollmentPlan/${type}/${operation}/${planId}`
          path: `/enrollmentPlanList/base-info/${type}/${planId}`
        });
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getData();
      },
      //每页条数改变
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getData();
      }
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