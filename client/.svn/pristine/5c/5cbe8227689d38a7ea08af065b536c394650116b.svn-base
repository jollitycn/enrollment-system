<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生目标列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-row>
      <div>
        <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="goToAddTarget">新增</el-button>
      </div>
    </el-row>
    <el-row style="margin-top:20px;">
      <el-table :data="tableData" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column label="序号" type="index" width="50" align="center"></el-table-column>
        <el-table-column prop="semesterName" label="学期" align="center"></el-table-column>
        <el-table-column prop="title" label="标题" align="center"></el-table-column>
        <el-table-column prop="creatorName" label="创建人" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="primary" @click="goToEditTarget(scope.row.targetId)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
          :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
          @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
      </div>
    </el-row>
  </div>
</template>

<script>
  import {
    queryPage,
  } from "@/api/annualEnrollmentPlanManagement/enrollmentTargetList";

  export default {
    name: 'enrollmentTargetList',
    data() {
      return {
        tableData: [],
        pageTotal: 0,
        query: {
          pageNum: 1,
          pageSize: 10,
          schoolId: '',
        },
      }
    },
    created() {
      let userInfo = localStorage.getItem("userInfo") || '{}';
      let schoolid = JSON.parse(userInfo).schoolid.toString();
      this.query.schoolId = schoolid;
      //默认查询第一页招生目标数据
      this.handleSearch();
    },
    methods: {
      /*----------------- 分页列表 ----------------*/
      /**
       * 获取生源学校分页列表
       * @author lml
       */
      getData() {
        this.loading = true
        queryPage(this.query)
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
       * 查询第一页数据
       * @author lmh
       */
      handleSearch() {
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },
      /**
      * 分页导航
      * @param val {number} 当前选择页码
      * @author lmh
      */
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getData();
      },
      /**
       * 每页条数改变
       * @param val {number} 每页数量
       * @author lmh
       */
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },


      /*----------------- 新增或编辑 ----------------*/
      /**
      * 跳转新增招生目标页面
      * @param {string} id 招生目标id
      * @author lmh
      */
      goToAddTarget() {
        this.$confirm("请先定义数据字典中的招生渠道，是否继续创建招生目标？").then(_ => {
          this.routerTo(0);
        });
      },

      /**
       * 跳转编辑招生目标页面
       * @param {string} id 招生目标id
       * @author lmh
       */
      goToEditTarget(id) {
        this.routerTo(id);
      },
      /**
       * 跳转新增招生目标页面
       * @param {string} id 招生目标id
       * @author lmh
       */
      routerTo(id = '') {
        this.$router.push({
          path: `/addEnrollmentTargetList/${id}`
        });
      },
    },
  }
</script>

<style scoped>
</style>