<template>
  <div class="page-container">
    <div>
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生区域管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 30px;">
      <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="clearn">新增</el-button>
    </div>
    <div style="margin-top: 80px;">
      <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
        <el-table-column prop="semester" label="学期" width="320" align="center"></el-table-column>
        <el-table-column prop="title" label="标题" width="320" align="center"></el-table-column>
        <el-table-column prop="creatorName" label="创建人" width="320" align="center"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="320" align="center"></el-table-column>
        <el-table-column fixed="right" label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button @click="getGroupInfo(scope.row.groupInfoId)">复制</el-button>
            <el-button type="primary" @click="toeditInfo(scope.row.groupInfoId)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
          :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
          @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
      </div>
      <!-- 新增招生区域 -->
      <el-dialog :title='reginTitle' :visible.sync="editVisible" width="60%" center>
        <el-form :rules="regionformRule" ref="regionform" :model="regionform" label-width="80px">
          <el-row>
            <el-col :span="11">
              <el-form-item label="学期:" prop="semesterId">
                <el-select v-model="regionform.semesterId" placeholder="请选择学期">
                  <el-option v-for="item in semester" :key="item.schoolId" :label="item.name" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item>
                <el-button type="primary" @click="showAddSemesterDialog">新增学期</el-button>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="17">
              <el-form-item label="标题:" prop="title">
                <el-input v-model="regionform.title" maxlength="30" placeholder="请输入标题" show-word-limit></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="17">
              <el-form-item label="说明:" prop="comment">
                <el-input type="textarea" v-model="regionform.comment" maxlength="1000" show-word-limit></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button @click="editVisible = false">关 闭</el-button>
            <el-button type="primary" @click="save">保 存</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 新增学期 -->
      <el-dialog title="新增学期" :close-on-click-modal="false" :visible.sync="addSemesterVisible" width="48%" center>
        <el-row type="flex" justify="center">
          <el-col :span="18">
            <el-form ref="addSemesterForm" :model="addSemesterForm" :rules="addSemesterFormRule" label-width="120px">
              <el-form-item label="学期名称" prop="name">
                <el-input style="width: 100%;" class="input-custom" v-model.trim="addSemesterForm.name"
                  placeholder="请输入学期名称" maxlength="20" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="学期开始时间" prop="start">
                <el-date-picker style="width: 100%;" v-model="addSemesterForm.start" value-format="yyyy-MM-dd"
                  type="date" placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="学期结束时间" prop="end">
                <el-date-picker style="width: 100%;" v-model="addSemesterForm.end" value-format="yyyy-MM-dd" type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row slot="footer" type="flex" justify="center">
          <el-button size="middle" @click="addSemesterVisible = false">取消</el-button>
          <el-button size="middle" type="primary" @click="saveSemester">保存</el-button>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {
    listGroupInfoPage,
    updateGroupInfo,
    addGroupInfo,
    copyGroupInfo,
    getGroupInfo
  } from "../../../../api/annualEnrollmentPlanManagement/groupController";
  import {
    term,
    termlist
  } from "../../../../api/admin/API-Controller/APIController";
  import { getSemesterList } from "../../../../api/annualEnrollmentPlanManagement/enrollmentTargetList";
  const DAFULT_ADDSEMESTERFORM = {
    schoolId: "",
    semesterName: ""
  };

  export default {
    name: "enrollmentManagement",
    data() {
      return {
        loading: true,
        schoolId: JSON.parse(localStorage.getItem("userInfo")),
        addSemesterForm: { ...DAFULT_ADDSEMESTERFORM },
        editVisible: false,
        addSemesterVisible: false,
        semester: [],
        tableData: [],
        pageTotal: 0,
        addSemesterFormRule: {
          name: [
            { required: true, message: "请输入学期名称", trigger: "blur" }
          ],
          start: [
            { required: true, message: "请选择学期开始时间", trigger: "blur" }
          ],
          end: [
            { required: true, message: "请选择学期结束时间", trigger: "blur" }
          ]
        },
        regionformRule: {
          semesterId: [
            { required: true, message: "请选择学期", trigger: "change" }
          ],
          title: [{ required: true, message: "请输入标题", trigger: "blur" }]
        },
        reginTitle: "新增招生区域",
        regionform: {
          semesterId: "",
          semester: "",
          title: "",
          comment: ""
        },
        termform: {
          name: ""
        },
        query: {
          pageNum: 1,
          pageSize: 10,
          schoolId: "",
          semesterName: ""
        }
      };
    },
    created() {
      //获取列表
      this.getData();
      //获取学期
      this.gettermlist();
    },
    methods: {
      /**
       * 获取招生区域信息列表
       * @author cwz
       */
      getData() {
        this.loading = true
        listGroupInfoPage(this.query)
          .then(res => {
            this.tableData = res.data.records;
            this.pageTotal = res.data.total || 0;
            this.loading = false
          })
          .catch(err => {
            console.error(err);
            this.loading = false
          });
      },
      /**
       * 跳转到招生区域编辑
       * @author cwz
       */
      toeditInfo(id) {
        this.$router.push({ path: `/editEnrollmentManagement/${id}` });
      },
      /**---------------------新增----------------------- */
      /**
       * 获取指定区域详情信息
       * @param id {string} 区域id
       * @author cwz
       */
      getGroupInfo(id) {
        getGroupInfo(id)
          .then(res => {
            this.elFormReset("regionform");
            // this.regionform.semesterId = res.data.semesterId;
            // this.regionform.comment = res.data.semester;
            this.regionform.title = res.data.title;
            this.regionform.groupInfoId = res.data.groupInfoId;
            this.regionform.comment = res.data.comment;
            this.reginTitle = "复制招生区域"
            this.editVisible = true;
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 根据学校id获取学期列表信息
       * @param data {} 学校id
       * @author cwz
       */
      gettermlist() {
        var data = this.schoolId.schoolid;
        getSemesterList(data)
          .then(res => {
            this.semester = res.data.data;
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 新增区域保存
       * @author cwz
       * @param {*} data 包含学期id和学期名称
       */

      save() {
        //表单验证
        this.$refs.regionform.validate(valid => {
          //表单有数据执行
          if (valid) {
            var id = this.regionform.semesterId;
            var obj = this.semester.find(function (x) {
              return x.id == id;
            });
            this.regionform.semester = obj.name;
            const data = { ...this.regionform };
            if (this.reginTitle === "新增招生区域") {
              this.addGroupInfo(data);
            }else{
              this.copyGroupInfo(data);
            }
          }
        });
      },
      /**
       * 新增区域请求
       * @author chenweizhe
       * @param {*} data
       */
      addGroupInfo(data) {
        addGroupInfo(data)
          .then(() => {
            this.$message.success(`新增区域成功`);
            this.editVisible = false;
            this.getData()
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 复制区域请求
       * @author lgh
       * @param {*} data
       */
       copyGroupInfo(data) {
        copyGroupInfo(data)
          .then(() => {
            this.$message.success(`复制区域成功`);
            this.editVisible = false;
            this.getData()
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 新增学期按钮
       * @author cwz
       */
      showAddSemesterDialog() {
        //初始化数据
        this.elFormReset("addSemesterForm");
        this.addSemesterForm = { ...DAFULT_ADDSEMESTERFORM };
        //显示弹窗框
        this.addSemesterVisible = true;
      },
      /**
       * 新增学期
       * @param data 学期名称、学校id
       * @author cwz
       */
      saveSemester() {
        // this.addSemesterForm.schoolId = this.schoolId.schoolid;
        //表单验证
        this.$refs.addSemesterForm.validate(valid => {
          //表单有数据执行
          if (valid) {
            let data = { ...this.addSemesterForm };
            term(data).then(res => {
              this.$message.success("保存学期成功！");
              //隐藏弹出框
              this.addSemesterVisible = false;
              //刷新学期列表
              this.gettermlist();
            });
          }
        });
      },
      clearn() {
        this.elFormReset("regionform");
        // this.regionform.semesterId = "";
        // this.regionform.comment = "";
        this.regionform.title = "";
        // this.regionform.comment = "";
        this.reginTitle = "新增招生区域"
        this.editVisible = true;
      },
      // 分页导航()
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

  /* background-color: #f2f8fe;
      color: #1989fa
  } */

  .canlendar-main ul li.active {
    color: red;
  }
</style>