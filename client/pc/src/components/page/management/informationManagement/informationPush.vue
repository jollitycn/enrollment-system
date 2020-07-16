<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息推送</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top:20px;">
      <el-form :inline="true">
        <el-form-item label="信息类型">
          <el-select v-model="query.messageType" placeholder="请选择信息类型" size="mini" style="width: 130px;">
            <el-option v-for="item in messageType" :key="item.dataCode" :label="item.dataValue" :value="item.dataCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收端">
          <el-select v-model="query.recevier" placeholder="请选择接收端" class="mr10" style="width: 150px;">
            <el-option label="全部" :value="0"></el-option>
            <el-option label="学生" :value="1"></el-option>
            <el-option label="家长" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="所属计划">
          <el-select v-model="query.belongPlan" placeholder="请选择所属计划" size="mini" style="width: 130px;">
            <el-option v-for="item in belongPlan" :key="item.belongPlan" :label="item.title" :value="item.planId">
            </el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="标题">
          <el-input v-model="query.title" placeholder="请输入标题" style="width: 200px;"></el-input>
        </el-form-item>
        <el-button class="ml10" type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="handleReset">重置</el-button>
        <el-button class="ml10" style="float: right;" type="primary" icon="el-icon-plus" @click="handleadd">新增
        </el-button>
      </el-form>
    </div>

    <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
      <el-table-column fixed width="55" label="序号" align="center">
        <template slot-scope="scope">{{scope.$index + 1}}</template>
      </el-table-column>
      <el-table-column prop="title" label="标题" align="center" width="100"></el-table-column>
      <el-table-column prop="messageTypeName" label="信息类型" align="center"></el-table-column>
      <el-table-column prop="recevier" label="接收端" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.recevier===1?'info':(scope.row.recevier===2?'info':'info')">
            {{scope.row.recevier===1?'学生':(scope.row.recevier===2?'家长':'全部')}}</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="planName" label="所属计划" align="center"></el-table-column> -->
      <el-table-column prop="creatorName" label="创建人" align="center"></el-table-column>
      <el-table-column prop="createTime" label="创建日期" width="85" align="center"></el-table-column>
      <el-table-column prop="newsStatus" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="!scope.row.newsStatus ? 'warning':'success'">{{!scope.row.newsStatus ? '草稿':'已发布'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="400">
        <template slot-scope="scope">
          <el-button type="success" icon="el-icon-position" @click="releaseNews(scope.$index, scope.row.newsId)"
            v-if="scope.row.newsStatus==0">发布</el-button>
          <el-button type="primary" icon="el-icon-lx-attention" @click="handleEdit(scope.row.newsId,'0')">查看</el-button>
          <el-button type="primary" icon="el-icon-setting" @click="handleEdit(scope.row.newsId,'1')">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteNews(scope.$index, scope.row.newsId)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination background layout="sizes, prev, pager, next,total" :current-page="query.pageNum"
        :page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"
        :page-sizes="[10, 20, 50, 100]" @size-change="handleSizeChange"></el-pagination>
    </div>
  </div>
</template>

<script>
  import {
    newslist,
    addOrEdit,
    deleteNews,
    releaseNews
  } from "../../../../api/admin/informationManagement/informationPush";
  import { queryAllFinishedPlan } from "../../../../api/admin/enrollmentPlanManagement/enrollmentPlan";
  import { getMessageTypeList } from "../../../../api/admin/systems-management/number-dictionaries";
  export default {
    data() {
      return {
        loading: false,
        //信息类型
        messageType: [],
        //所属计划
        belongPlan: [],
        //接收端
        recevier: [],
        pageTotal: 4,
        tableData: [],
        form: {},
        query: {
          recevier:0,
          pageNum: 1,
          pageSize: 10
        }
      };
    },
    created() {
      //获取新闻列表
      this.getData();
      //获取招生计划
      this.queryAllFinishedPlan();
      //获取信息类型
      this.getMessageTypeList();
    },
    methods: {
      /**-----------------------功能------------------------ */
      /**
       * 发布新闻
       * @param newsId {string}  新闻id
       * @author cwz
       */
      releaseNews(index, newsId) {
        this.$confirm("确定要发布吗？", "提示", {
          type: "warning"
        }).then(() => {
          releaseNews(newsId).then(res => {
            this.$message.success("发布成功");
            this.getData();
          });
        });
      },
      /**
       * 删除新闻请求
       * @param newsId {string}  新闻id
       * @author cwz
       */
      deleteNews(index, newsId) {
        this.$confirm("确定要删除吗？", "提示", {
          type: "warning"
        }).then(() => {
          deleteNews(newsId).then(res => {
            this.$message.success("删除成功");
            this.getData();
          });
        });
      },
      /**
       * 跳转新增页面
       * @author cwz
       */
      handleadd() {
        this.$router.push({
          path: "/informationPush/addInformationPush"
        });
      },
      /**
       * 跳转编辑页面
       * @param id {string}  新闻id
       * @author cwz
       */
      handleEdit(id, type) {
        this.$router.push({
          path: `/informationPush/editInformationPush/${type}/${id}`
        });
      },
      /**-------------------------搜索------------------------ */
      /**
       * 触发搜索按钮
       * @author cwz
       */
      handleSearch() {
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },
      /**
       * 触发重置按钮
       * @author cwz
       */
      handleReset() {
        this.$set(this.query, "pageNum", 1);
        this.$set(this.query, "pageSize", 10);
        this.$set(this.query, "messageType", null);
        this.$set(this.query, "recevier", null);
        this.$set(this.query, "belongPlan", null);
        this.$set(this.query, "title", null);
        this.getData();
      },
      /**
       * 获取招生计划信息
       * @author cwz
       */
      queryAllFinishedPlan() {
        this.loading = true
        queryAllFinishedPlan(this.query).then(res => {
          this.belongPlan = res.data.records;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        });
      },
      /**
       * 获取信息类型
       * @author cwz
       */
      getMessageTypeList() {
        getMessageTypeList().then(res => {
          this.messageType = res.data;
        });
      },

      /**-----------------列表------------------- */
      /**
       * 获取新闻列表
       * @author cwz
       */
      getData() {
        let data = {
          pageNum: this.query.pageNum,
          pageSize: this.query.pageSize,
          messageType: this.query.messageType,
          recevier: this.query.recevier,
          belongPlan: this.query.belongPlan,
          title: this.query.title
        };
        this.loading = true
        newslist(data).then(res => {
          console.log("res====>", res);
          this.tableData = res.data.records;
          this.pageTotal = res.data.total || 0;
          this.loading = false
        });
      },

      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
      },
      // 页数变化
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.handlePageChange(1);
      }
    }
  };
</script>
<style scoped>
  .tabs {
    widows: 700px;
  }

  .handle-box {
    margin-bottom: 20px;
    margin-top: 20px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }

  .red {
    color: red;
  }

  .ml10 {
    margin-left: 10px;
  }
</style>