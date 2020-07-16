<template>
  <div class="page-container">
    <div class="page-container">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/enrollmentManagement'}">招生区域管理</el-breadcrumb-item>
        <el-breadcrumb-item>区域创建</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 20px;">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="新增区域" name="first">
          <el-form :rules="regionformRule" ref="region" :model="regionform" label-width="80px">
            <el-row>
              <el-col :span="6">
                <el-form-item label="学期:" prop="semesterId">
                  <el-select v-model="regionform.semesterId" placeholder="请选择">
                    <el-option v-for="item in semester" :key="item.schoolId" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-button type="primary" @click="editVisible= true">新增学期</el-button>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="11">
                <el-form-item label="标题:" prop="title">
                  <el-input v-model="regionform.title"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="11">
                <el-form-item label="说明:">
                  <el-input type="textarea" v-model="regionform.comment"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button type="primary" @click="save">保 存</el-button>
            </el-form-item>
          </el-form>
          <el-dialog :visible.sync="editVisible" width="20%" center>
            <el-form :rules="addSemesterFormRule" ref="term" :model="termform" label-width="80px">
              <el-row>
                <el-col :span="20">
                  <el-form-item label="新增学期:" prop="name">
                    <el-input v-model.trim="termform.name"></el-input>
                  </el-form-item>
                </el-col>
                <el-col>
                  <el-form-item>
                    <el-button type="primary" @click="saveterm">确认</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-dialog>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
  import {
    addGroupInfo,
    getGroupInfo
  } from "../../../../api/annualEnrollmentPlanManagement/groupController";
  import {
    term,
    termlist
  } from "../../../../api/admin/API-Controller/APIController";
  export default {
    name: "copyEnrollmentManagement",
    data() {
      return {
        schoolId: JSON.parse(localStorage.getItem("userInfo")),
        editVisible: false,
        tableData: [],
        semester: [],
        activeName: "first",
        regionform: {
          semesterId: "",
          semester: "",
          title: "",
          comment: ""
        },
        termform: {
          name: ""
        },
        addSemesterFormRule: {
          semesterName: [
            { required: true, message: "请输入学期名称", trigger: "blur" },
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
        query: {
          name: ""
        }
      };
    },
    created() {
      //获取学期
      this.gettermlist();
      //获取基本信息
      this.getGroupInfo(this.$route.params.id);
    },
    methods: {
      /**
       * 获取指定区域详情信息
       * @param id {string} 区域id
       * @author cwz
       */
      getGroupInfo(id) {
        getGroupInfo(id)
          .then(res => {
            this.regionform.semesterId = res.data.semesterId;
            this.regionform.semester = res.data.semester;
            this.regionform.title = res.data.title;
            this.regionform.comment = res.data.comment;
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 根据学校id获取学期列表信息
       * @author cwz
       */
      gettermlist() {
        var data = this.schoolId.schoolid;
        termlist(data).then(res => {
          this.semester = res.data.data;
        });
      },
      /**
       * 新增区域保存
       * @param data {*} data 包含学期id和学期名称
       * @author cwz
       */

      save() {
        //表单验证
        this.$refs["region"].validate(valid => {
          //表单有数据执行
          if (valid) {
            var id = this.regionform.semesterId;
            var obj = this.semester.find(function (x) {
              return x.id == id;
            });
            this.regionform.semester = obj.name;
            const data = { ...this.regionform };
            this.addGroupInfo(data);
          }
        });
      },
      /**
       * 新增区域请求
       * @param {*} data 包含学期*、标题*、说明（*为必填）
       * @author cwz
       */
      addGroupInfo(data) {
        addGroupInfo(data)
          .then(() => {
            this.$message.success(`新增区域成功`);
            //跳转到招生区域管理
            this.$router.push({
              path: `/enrollmentManagement`
            });
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**
       * 新增学期保存
       * @author cwz
       */
      saveterm() {
        this.query.name = this.termform.name;
        const data = { ...this.query };
        this.$refs["term"].validate(valid => {
          if (valid) {
            this.term(data);
          }
        });
      },
      /**
       * 新增学期请求
       * @param {*} data 包含学期id、学期名称
       * @author cwz
       */
      term(data) {
        term(data)
          .then(() => {
            if (res && res.data && res.data.status === 200) {
              this.$message.success("保存学期成功！");
              this.editVisible = false;
            } else {
              this.$message.warning(res.data.msg);
            }
          })
          .catch(err => {
            console.log(err);
          });
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