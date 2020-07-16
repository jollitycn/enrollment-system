<template>
  <div>
    <div class="handle-box" align="right">
      <!-- <el-button type="primary" icon="el-icon-search" @click="">新建院系</el-button> -->
      <!-- <el-button type="primary" icon="el-icon-refresh" @click="">复制模板</el-button>
      <el-button type="primary" icon="el-icon-search" @click="">转为模板</el-button> -->
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column fixed width="55" label="序号" align="center">
          <template slot-scope="scope">{{scope.$index + 1}}</template>
        </el-table-column>
        <el-table-column width="100" label="院系名称" prop="collegeName" align="center"></el-table-column>
        <el-table-column label="院系负责人" prop="collegeManager" align="center" width='110'></el-table-column>
        <!-- <el-table-column label="院系名称" prop="apiCollegeName" align="center"></el-table-column> -->
        <el-table-column label="专业数量" prop="majorCount" align="center"></el-table-column>
        <!-- <el-table-column label="招生人数" prop="recruitAmount" align="center"></el-table-column> -->
        <el-table-column label="创建人" prop="creatorName" align="center"></el-table-column>
        <el-table-column label="创建日期" prop="createTime" align="center" width='200'></el-table-column>
        <el-table-column fixed="right" label="操作" width="400" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-lx-attention" @click="seeShowFaculty(scope.row.collegeId)">
              查看</el-button>
            <el-button v-if="type!='see'" type="primary" icon="el-icon-setting"
              @click="editShowFaculty(scope.row.collegeId)">编辑
            </el-button>
            <el-button type="primary" icon="el-icon-setting" @click="getFourth(scope.row.collegeId,scope.row.apiId)">
              专业管理
            </el-button>
            <!-- <el-button type="danger" icon="el-icon-delete" @click="">删除</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import { queryCollegeInfo, queryCollegeInfoById } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js'
  export default {
    name: 'departmentList',
    data() {
      return {
        loading: true,
        tableData: [],
        collegeId: '',
        apiId: "",
      }
    },
    props: {
      planId: { type: String, default: '' },
      addshow: { type: Boolean, default: '' },
      type: { type: String, default: '' }
    },
    created() {
      this.queryCollegeInfo()
    },
    methods: {
      /**
       * 查看招生院系信息
       * @author LiuGuoHao
       */
      queryCollegeInfo() {
        this.loading = true
        if (this.addshow == true) {
          queryCollegeInfo(this.planId).then(res => {
            this.tableData = res.data
            console.log(res, '==>res')
            this.loading = false
          }).catch(err => {
            console.error(err);
            this.loading = false
          })
        }
      },
      /**
       * 展示编辑招生院校
       * @param collegeId{String} 根据院系ID查找具体数据
       * @author LiuGuoHao
       */
      editShowFaculty(collegeId) {
        this.$router.push({
          path: `/enrollmentPlanList/department/department-edit/${this.type}/${this.planId}/${collegeId}`
        })
      },
      /**
       * 查看招生院校
       * @param collegeId{String} 根据院系ID查找具体数据
       * @author LiuGuoHao
       */
      seeShowFaculty(collegeId) {
        this.$router.push({
          path: `/enrollmentPlanList/department/department-show/${this.type}/${this.planId}/${collegeId}`
          // path:`/enrollmentPlanList/addenrollmentPlan/department/departmentShow/see/${collegeId}`
        })
      },
      /**
       * 跳转到院系下面的所有招生专业信息的页面
       * @param collegeId{String} 根据院系ID查找具体数据
       * @param apiId{String} 获取apiId数据
       * @author LiuGuoHao
       */
      getFourth(collegeId, apiId) {
        this.$router.push({
          path: `/enrollmentPlanList/major/major-list/${this.type}/${this.planId}/${collegeId}/${apiId}`
        })
      },
    }
  }
</script>

<style scoped>

</style>