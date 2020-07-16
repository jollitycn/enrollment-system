<template>
  <div>
    <div class="handle-box" align="right">
      <el-button type="primary" v-if="type!='see'" icon="el-icon-lx-add" @click="addOrEditMajor('create')">新建专业
      </el-button>
      <!-- <el-button type="primary" icon="el-icon-refresh" @click="">复制模板</el-button>
      <el-button type="primary" icon="el-icon-search" @click="">转为模板</el-button> -->
    </div>
    <div>
      <el-table :data="majorList" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column fixed width="55" label="序号" align="center">
          <template slot-scope="scope">{{scope.$index + 1}}</template>
        </el-table-column>
        <el-table-column width="100" label="专业名称" prop="majorName" align="center"></el-table-column>
        <el-table-column label="招生目标" prop="cultivateAim" align="center" width='200'></el-table-column>
        <!-- <el-table-column label="招生计划" prop="professionalQuantity" align="center"></el-table-column> -->
        <el-table-column label="学费" prop="fee" align="center"></el-table-column>
        <el-table-column label="学制" prop="majorType" align="center"></el-table-column>
        <el-table-column label="创建人" prop="creatorName" align="center"></el-table-column>
        <el-table-column label="创建日期" prop="createTime" align="center" width='200'></el-table-column>
        <el-table-column fixed="right" label="操作" width="300" align="center">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-lx-attention" @click="queryMajorInfoByMajorId(scope.row.majorId)">
              查看</el-button>
            <el-button type="primary" v-if="type!='see'" icon="el-icon-setting"
              @click="addOrEditMajor(scope.row.majorId)">
              编辑</el-button>
            <!-- <el-button type="primary" icon="el-icon-setting" @click="">专业管理</el-button> -->
            <el-button type="danger" v-if="type!='see'" icon="el-icon-delete"
              @click="deleteMajorDetailed(scope.row.majorId)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div>
        <!-- <el-button type="primary" @click="returnFaculty()">
          返回院系管理</el-button> -->
      </div>
    </div>
  </div>
</template>
<script>
  import { queryMajorInfoByCollegeId, deleteMajorInfo } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js'
  export default {
    name: "majorList",
    data() {
      return {
        loading: true,
        collegeId: this.$route.params.collegeId,
        majorList: [],
        planId: this.$route.params.planId,
        apiId: this.$route.params.apiId,
        type: this.$route.params.type
      }
    },
    created() {
      this.queryMajorInfoByCollegeId(this.collegeId)
    },
    methods: {
      /**
       * 查看招生院系信息
       * @param collegeId{String} 获取专业列表时所需的对应院系Id
       * @author LiuGuoHao
       */
      queryMajorInfoByCollegeId(collegeId) {
        this.loading = true
        queryMajorInfoByCollegeId(collegeId).then(res => {
          this.majorList = res.data
          this.loading = false
          console.log(this.majorList, '==>majorList')
        }).catch(err => {
          this.loading = false
          console.error(err);
        })
      },
      /**
       * 进入专业新增页面
       * @param majorId{String} 编辑的专业新增ID 若为addMajor则表示新增
       * @author LiuGuoHao
       */
      addOrEditMajor(majorId) {
        this.$router.push({
          path: `/enrollmentPlanList/major-edit/${this.type}/${this.planId}/${this.collegeId}/${majorId}/${this.apiId}`
        })
      },
      /**
       * 进入专业查看页面
       * @param majorId{String} 查看对应的专业ID
       * @param apiId{number} 用于获取当前的专业信息
       * @author LiuGuoHao
       */
      queryMajorInfoByMajorId(majorId) {
        this.$router.push({
          //this.collegeId用于回跳专业新增页面
          path: `/enrollmentPlanList/major-show/${this.type}/${this.planId}/${this.collegeId}/${majorId}/${this.apiId}`
        })
      },
      /**
       * 删除院系专业
       * @param majorId{String} 删除专业所用的majorId
       * @author LiuGuoHao
       */
      deleteMajorDetailed(majorId) {
        this.$confirm('是否要删除该院系专业？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除院系专业成功！'
          });
          deleteMajorInfo(majorId).then(res => {
            this.queryMajorInfoByCollegeId(this.collegeId)
          })
        });
      },
    },
  }

</script>
<style>
  .handle-box {
    margin-bottom: 20px;
  }
</style>