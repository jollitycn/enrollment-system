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
                  <el-button type="primary" @click="showAddSemesterDialog">新增学期</el-button>
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
                <el-form-item label="说明:" prop="comment">
                  <el-input type="textarea" v-model="regionform.comment"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button type="primary" @click="save">保 存</el-button>
            </el-form-item>
          </el-form>
          <el-dialog title="新增学期" :close-on-click-modal="false" :visible.sync="addSemesterVisible" width="48%" center>
            <el-row type="flex" justify="center">
              <el-col :span="18">
                <el-form ref="addSemesterForm" :model="addSemesterForm" :rules="addSemesterFormRule"
                  label-width="120px">
                  <el-form-item label="学期名称" prop="semesterName">
                    <el-input style="width: 100%;" class="input-custom" v-model.trim="addSemesterForm.semesterName" placeholder="请输入学期名称"
                      maxlength="20" show-word-limit></el-input>
                  </el-form-item>
                  <el-form-item style="width: 100%;" label="学期开始时间" prop="start">
                    <el-date-picker v-model="addSemesterForm.start" value-format="yyyy-MM-dd" type="date"
                      placeholder="选择日期">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item style="width: 100%;" label="学期结束时间" prop="end">
                    <el-date-picker v-model="addSemesterForm.end" value-format="yyyy-MM-dd" type="date"
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
  import { addSemester } from "../../../../api/annualEnrollmentPlanManagement/enrollmentTargetList";

  const DAFULT_ADDSEMESTERFORM = {
    schoolId: "",
    semesterName: ""
  };

  export default {
    name: "addEnrollmentManagement",
    data() {
      return {
        schoolId: JSON.parse(localStorage.getItem("userInfo")),
        addSemesterVisible: false,
        tableData: [],
        semester: [],
        addSemesterForm: { ...DAFULT_ADDSEMESTERFORM },
        addSemesterFormRule: {
          semesterName: [
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
        query: {
          schoolId: "",
          semesterName: ""
        }
      };
    },
    created() {
      //获取学期
      this.gettermlist();
    },
    methods: {
      /**
       * 根据学校id获取学期列表信息
       * @param data {} 学校id
       * @author cwz
       */
      gettermlist() {
        var data = this.schoolId.schoolid;
        termlist(data)
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
       * @author chenweizhe
       * @param {*} data
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
        this.addSemesterForm.schoolId = this.schoolId.schoolid;
        //表单验证
        this.$refs.addSemesterForm.validate(valid => {
          //表单有数据执行
          if (valid) {
            let data = { ...this.addSemesterForm };
            addSemester(data).then(res => {
              this.$message.success("保存学期成功！");
              //隐藏弹出框
              this.addSemesterVisible = false;
              //刷新学期列表
              this.gettermlist();
            });
          }
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