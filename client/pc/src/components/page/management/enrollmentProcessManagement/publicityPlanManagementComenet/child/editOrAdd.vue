<template>
  <div class="page-container">
    <div class="add-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm"
        v-loading="loading" element-loading-text="拼命加载中">
        <el-form-item label="所属计划">
          {{currentPlanTitle}}
        </el-form-item>
        <el-form-item label="宣讲时间" prop="date">
          <!-- <el-col :span="11">
            <el-form-item prop="preachTime">
              <el-date-picker
                v-model="searchTime"
                type="datetimerange"
                @change="changeTime"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
          </el-col> -->
          <!-- <el-date-picker
            v-model="searchTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            @change="changeTime"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker> -->
          <el-col :span="6">
            <div class="block mr10">
              <span class="demonstration"></span>
              <el-date-picker class="wid100" @change="changeDate" value-format="yyyy-MM-dd" v-model="searchDate"
                type="date" placeholder="请选择宣讲日期"></el-date-picker>
            </div>
          </el-col>
          <el-col :span="8">
            <el-time-picker class="wid100" is-range @change="changeTime" v-model="searchTime" value-format="HH:mm:ss"
              format="HH:mm" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围">
            </el-time-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="主讲人" prop="dailyReceptionistIndex">
          <el-col :span="14">
            <el-select class="wid100" v-model="ruleForm.dailyReceptionistIndex" placeholder="请选择主讲人">
              <el-option v-for="(item,index) in options" :key="index" :label="item.title" :value="index"></el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="计划人数" prop="planAmount">
          <el-col :span="14">
            <el-input-number class="wid100" :precision="0" :min="0" :max="10000000" v-model="ruleForm.planAmount">
            </el-input-number>
          </el-col>
        </el-form-item>
        <el-form-item label="联系方式" prop="telephone">
          <el-col :span="14">
            <el-input class="wid100" maxlength="11" placeholder="请输入联系方式" show-word-limit v-model="ruleForm.telephone">
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="协助人" prop="defaultProps">
          <el-tree :data="data" ref="tree" show-checkbox node-key="userid" :default-checked-keys="activeId"
            :props="defaultProps" @check-change="handleCheckChange"></el-tree>
        </el-form-item>
        <el-form-item label="主题" prop="title">
          <el-col :span="14">
            <el-input class="wid100" v-model="ruleForm.title" maxlength="30" placeholder="请输入主题" show-word-limit>
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="宣讲地点" prop="address">
          <el-col :span="14">
            <el-input class="wid100" v-model="ruleForm.address" maxlength="100" placeholder="请输入宣讲地点" show-word-limit>
            </el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="面向学校" prop="sourceSchools">
          <el-col :span="14">
            <el-select class="wid100" v-model="ruleForm.sourceSchoolIds" value-key="sourceSchoolName" multiple
              placeholder="请选择面向学校">
              <el-option v-for="item in optionsSchool" :key="item.sourceSchoolId" :label="item.sourceSchoolName"
                :value="item.sourceSchoolId"></el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="备注">
          <el-col :span="14">
            <el-input class="wid100" type="textarea" v-model="ruleForm.comment" maxlength="1000" rows="5"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即{{submitTitle}}</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {
    addPreach,
    getStruct,
    listAllSourceSchool,
    getDetail,
    updateData
  } from "@/api/management/enrollmentProcessManagement/publicityPlanManagement";
  import { dateFormat } from "@/utils/format";
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    name: "editOrAdd",
    components: {},
    data() {
      var timeValidatePass = (rule, value, callback) => {
        let val = this.searchDate
        let valTime = this.searchTime
        if (val == null || valTime === null || val == "") {
          callback(new Error('请选择宣讲时间'));
        } else if ((typeof valTime) !== 'object') {
          callback(new Error('请选择宣讲时间'));
        } else {
          callback();
        }
      };
      return {
        currentPlanTitle: '',//传于当前默认计划的标题，仅展示
        loading: false,
        submitTitle: "",
        
        options: [],
        optionsSchool: [],
        activeId: [],
        defaultProps: {
          children: "child",
          label: "name"
        },
        searchTime: "", //宣讲时间段
        searchDate: "", //宣讲日期
        ruleForm: {
          dailyReceptionistIndex: "", //日常接待人
          address: "", //宣讲地点
          assistantIds: [], //协助人ID列表
          comment: "", //备注
          planAmount: "", //计划人数
          preachTime: "", //宣讲开始时间
          endTime: "", //宣讲结束时间
          sourceSchoolIds: [], //面向学校ID
          speakerId: "", //主讲人ID
          speakerName: "", //主讲人名称
          telephone: "", //联系方式
          title: "" //主题
        },
        data: [],
        rules: {
          date: [
            { required: true, validator: timeValidatePass, trigger: 'change' }
          ],
          // dailyReceptionistIndex: [{ required: true, message: "请选择主讲人", trigger: "blur" }],
          planAmount: [
            {
              required: true,
              message: "请输入计划人数",
              trigger: "blur"
            }
          ],
          // defaultProps:[{ required: true, message: "请选择协助人", trigger: "blur" }],
          telephone: [
            { required: true, message: "请输入联系方式", trigger: "blur" },
            {
              pattern: /^1[34578]\d{9}$/,
              message: "请输入正确手机号",
              trigger: "blur"
            }
          ],
          dailyReceptionistIndex: [{ required: true, message: "请选择主讲人", trigger: "blur" }],
          title: [
            { required: true, message: "请输入主题", trigger: "blur" },
            {
              pattern: /^[0-9a-zA-Z\u4e00-\u9fa5]{1,30}$/,
              message: "请输入主题,不包含特殊字符",
              trigger: "blur"
            }
          ],
          address: [
            { required: true, message: "请输入宣讲地点", trigger: "blur" },
            {
              pattern: /^[0-9a-zA-Z\u4e00-\u9fa5]{1,100}$/,
              message: "请输入宣讲地点,不包含特殊字符"
            }
          ],
          sourceSchoolIds: [
            { required: true, message: "请选择学校", trigger: "change" }
          ]
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
          this.currentPlanTitle = this.semesterQuery[0].title
          this.getStruct();
        })
      },
      //主讲人
      handleCheckChange2() {
        let res = this.$refs.tree1.getCheckedNodes();
        let arr = [];
        res.forEach(item => {
          if (item.userid) {
            arr.push(item);
          }
        });
        this.ruleForm.speakerId = arr[0].userid;
        this.ruleForm.speakerName = arr[0].name;
      },
      //协助人
      handleCheckChange() {
        let res = this.$refs.tree.getCheckedNodes();
        console.log(res, "==>res");
        // let arr = [];
        // res.forEach((item, index) => {
        //   // if(item.userid) {
        //   //    arr.push(item.userid)
        //   // }
        //   //传入协助人数据的下标
        //   let indexArr = 0;
        //   if (item.userid) {
        //     arr[indexArr] = {
        //       id: item.userid,
        //       name: item.name
        //     };
        //     indexArr++;
        //   }
        // });
        res = JSON.parse(
          JSON.stringify(res).replace(
            /userid/g,
            "id"
          )
        );
        res.forEach((item, index) => {
          console.log(item, index);
          if (item.child) {
            res.splice(index, 1)
          }
        })
        this.ruleForm.assistantIds = res;
        console.log(this.ruleForm.assistantIds, "==>>ids");

      },
      //选择日期事件
      changeDate(time) {
        this.searchDate = dateFormat(time, "YYYY-mm-dd")
        console.log(this.searchDate, 1);

        if (!this.searchTime) {
          this.searchTime = ["00:00:00", "23:59:59"]
        }
        // this.$set(this.ruleForm, "preachTime", time[0] );
        // this.$set(this.ruleForm, "endTime", time[1] );
        // console.log(this.ruleForm, 5656);
      },
      //选择时间事件
      changeTime(time) {

        if (!this.searchDate) {
          this.searchDate = dateFormat(new Date, "YYYY-mm-dd")
        }
        this.searchTime = time
        // this.$set(this.ruleForm, "preachTime", time[0] );
        // this.$set(this.ruleForm, "endTime", time[1] );
        // console.log(this.ruleForm, 5656);
      },
      //新增宣讲计划
      addPreach() {
        //设置接待人信息
        this.ruleForm.preachTime = this.searchDate + ' ' + this.searchTime[0]
        this.ruleForm.endTime = this.searchDate + ' ' + this.searchTime[1]
        let daily = this.options[this.ruleForm.dailyReceptionistIndex];
        this.ruleForm.speakerId = daily.userid;
        this.ruleForm.speakerName = daily.name;
        addPreach(this.ruleForm)
          .then(res => {
            this.$message.success("新增成功");
            this.$router.push({
              path: `/publicityPlanManagement`
            });
          })
          .catch(err => {
            console.error(err);
          });
      },

      //获取当前登录学校信息和账号信息
      getStruct() {
        let data = {
          schoolId: JSON.parse(localStorage.getItem("userInfo")).schoolid
        };
        this.loading = true
        getStruct(data)
          .then(res => {
            let data = res.data.data.child;
            this.data = data;
            let arr = [];
            for (let i = 0; i < data.length; i++) {
              for (let j = 0; j < data[i].child.length; j++) {
                console.log(data[i].child[j].name, 8888);
                if (!data[i].child[j].name) {
                  data[i].child[j].name = ""
                  arr.push({
                    userid: data[i].child[j].userid,
                    name: data[i].child[j].name,
                    title: data[i].name
                  });
                } else {
                  arr.push({
                    userid: data[i].child[j].userid,
                    name: data[i].child[j].name,
                    title: data[i].name + "-----" + data[i].child[j].name
                  });
                }
              }
            }
            this.options = arr;
            this.listAllSourceSchool();
            this.loading = false
          })
          .catch(err => {
            console.error(err);
            this.loading = false
          });
      },

      //获取面向学校
      listAllSourceSchool() {
        listAllSourceSchool()
          .then(res => {
            this.optionsSchool = res.data;
            if (this.$route.params.type === "edit") {
              let id = this.$route.params.id;
              this.getDetail(id);
            }
          })
          .catch(err => {
            console.error(err);
          });
      },
      //更新数据
      updateData() {
        console.log(this.searchDate, this.searchTime);

        this.ruleForm.preachTime = this.searchDate + ' ' + this.searchTime[0]
        this.ruleForm.endTime = this.searchDate + ' ' + this.searchTime[1]
        let daily = this.options[this.ruleForm.dailyReceptionistIndex];
        this.ruleForm.speakerId = daily.userid;
        this.ruleForm.speakerName = daily.name;
        this.ruleForm.sourceSchoolIds = this.ruleForm.sourceSchoolIds.filter(item => { return item })
        updateData(this.ruleForm)
          .then(res => {
            this.$message.success("修改成功");
            this.$router.push({
              path: `/publicityPlanManagement`
            });
          })
          .catch(err => {
            console.error(err);
          });
      },
      submitForm(formName) {
        //表单验证
        this.$refs.ruleForm.validate(valid => {
          //表单有数据执行
          if (valid) {
            //修改
            if (this.$route.params.type === "edit") {
              this.updateData();
            } else {
              this.addPreach();
            }
          }
        });

        // this.$refs[formName].validate((valid) => {
        //     if (valid) {
        //         alert('submit!');
        //     } else {
        //         console.log('error submit!!');
        //         return false;
        //     }
        // });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      //获取信息详情
      getDetail(id) {
        getDetail(id)
          .then(res => {
            this.ruleForm = res.data;
            this.activeId = res.data.assistantIds;
            this.searchDate = dateFormat(res.data.preachTime, "YYYY-mm-dd");
            this.searchTime = [dateFormat(res.data.preachTime, "HH:MM:SS"), dateFormat(res.data.endTime, "HH:MM:SS")];
            console.log(this.searchDate, this.searchTime);

            //显示当前的主讲人
            for (let i = 0; i < this.options.length; i++) {
              if (res.data.speakerId == this.options[i].userid) {
                this.ruleForm.dailyReceptionistIndex = i;
                break;
              }
            }
          })
          .catch(err => {
            console.error(err);
          });
      }
    },
    created() {
      if (this.$route.params.type === "edit") {
        this.submitTitle = "修改";
      } else {
        this.submitTitle = "新增";
      }
      this.queryAllFinishedPlan()
    }
  };
</script>

<style scoped>
  .add-content {
    margin-top: 40px;
  }

  >>>.el-tree__empty-text {
    left: 28%;
  }

  .wid100 {
    width: 100%;
  }

  .mr10 {
    margin-right: 10px;
  }
</style>