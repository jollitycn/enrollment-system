<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path : '/consultingSchoolManagement' }">到校咨询管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{isCreatStatus ? '修改': '新增'}}咨询日程</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="add-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="是否取消">
          <el-switch v-model="isNo" ></el-switch>
        </el-form-item>
        <div v-show="!isNo">
        <el-form-item label="是否接待日">
          <el-switch v-model="statusDay"></el-switch>
        </el-form-item>
        <el-form-item label="日常接待人">
          <el-select v-model="dailyReceptionistIndex" placeholder="请选择">
            <el-option v-for="(item,index) in options" :key="index" :label="item.title" :value="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" required prop="telephone">
          <el-input v-model="ruleForm.telephone"></el-input>
        </el-form-item>
        <el-form-item label="接待日期" required>
          <el-date-picker :disabled="true" v-model="ruleForm.opendayDate" type="date" format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-form-item label="接待行程">
          <quillEditor ref="scheduleEditor" v-model="ruleForm.schedule" @focus="clickQuil(0)"
            @change="schWordHandle($event)" :options="editorOption">
          </quillEditor>
          <span class="editor-word-limit">{{msgscLength}}/1000</span>
        </el-form-item>
        <el-form-item label="接待地址">
          <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
            :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
          </el-upload>
          <quillEditor ref="quillEditor" v-model="ruleForm.address" @focus="clickQuil(1)"
            @change="addrWordHandle($event)" :options="editorOption">
          </quillEditor>
          <span class="editor-word-limit">{{msgadLength}}/1000</span>
        </el-form-item>
        </div>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即{{isCreatStatus ? '修改': '新增'}}</el-button>
          <!-- <el-button type="primary" @click="submitForm('ruleForm')">立即修改</el-button> -->
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import "quill/dist/quill.core.css";
  import "quill/dist/quill.snow.css";
  import "quill/dist/quill.bubble.css";
  import {
    addOrEditConsultationDay,
    getConsultationDayDetail
  } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement";
  import { getStruct } from "@/api/management/enrollmentProcessManagement/publicityPlanManagement";
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import { quillEditor } from "vue-quill-editor";
  import rules from "@/assets/rules/rulesFrom";
  import config from '../../../../../config/index.js'
  export default {
    name: "addConsultingSchoolManagementDay",
    components: {
      quillEditor
    },
    data() {
      let phone = (rule, value, callback) => {
        if (!rules.phone.rules.test(value)) {
          return callback(new Error("请输入正确的手机号"));
        }
        return callback();
      };
      // 工具栏配置
      const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'], // toggled buttons
        ['blockquote', 'code-block'],

        [{ header: 1 }, { header: 2 }], // custom button values
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ script: 'sub' }, { script: 'super' }], // superscript/subscript
        [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
        [{ direction: 'rtl' }], // text direction

        [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
        [{ header: [1, 2, 3, 4, 5, 6, false] }],

        [{ color: [] }, { background: [] }], // dropdown with defaults from theme
        [{ font: [] }],
        [{ align: [] }],
        ['clean'], // remove formatting button
        ['link', 'image', 'video']
      ];
      //主动调用上传
      const triggerUpload = () => {
        //获取upload 的dom元素
        const uploadElement = this.$refs['uploadForEditor'].$el.querySelector('.el-upload');
        //模拟触发file的点击事件
        let evt = document.createEvent('MouseEvents');
        evt.initEvent('click', true, true);
        uploadElement.dispatchEvent(evt);
      };
      return {
        isNo: false, // 是否取消
        msgscLength: 0,//接待行程长度
        msgadLength: 0,//接待地址长度
        dailyReceptionistIndex: 0, //日常接待人
        options: [],
        isCreatStatus: false,
        statusDay: true,
        searchTime: "", //宣讲时间段
        param: {
          date: "2020-04-27",
          consultationId: "1251023513171562497"
        },
        ruleForm: {},
        rules: {
          telephone: [
            { required: true, message: '请输入正确的手机号', trigger: 'change' }, { validator: phone, trigger: "blur" }]
        },
        editorOption: {
          placeholder: '',
          readOnly: true,
          modules: {
            toolbar: {
              container: toolbarOptions, // 工具栏
              handlers: {
                image: function (value) {
                  if (value) {
                    console.log('自定义图片:', value);
                    triggerUpload();
                  } else {
                    this.quill.format('image', false);
                  }
                }
              }
            }
          }
        },
      };
    },
    methods: {
      /**
       * 获取焦点事件，用于判断目前所在的位置
       * @param {int} status 0-行程接待 1-接待地址
       * @author lgh
       */
      clickQuil(status) {
        this.status = status
      },
      /**
       * 上传图片限制
       */
      beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG或PNG 格式!');
        }
        else if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      uploadImgForEditor(option) {
        this.uploadAdd(option.file, imgUrl => {
          //用于判断目前插入图片的富文本框
          let ref = ""
          if (this.status == 1) {
            ref = "quillEditor"
          } else {
            ref = "scheduleEditor"
          }
          //插入富文本中
          let quill = this.$refs[ref].quill;
          console.log(this.status, quill, 1111, quill.getSelection(), quill.getSelection().index, 900);
          let length = quill.getSelection().index;
          quill.insertEmbed(length, "image", config.imgDir + imgUrl);
          //光标移动一格
          quill.setSelection(length + 1);
        });
      },
      uploadAdd(file, callback) {
        let formData = new FormData();
        let type = file.name.substring(
          file.name.indexOf(".") + 1,
          file.name.length
        );
        formData.append("file", file);
        formData.append("fileType", type);
        uploadAdd(formData)
          .then(res => {
            callback(res.data);
          })
          .catch(err => {
            console.error(err);
          });
      },
      //选择事件
      changeTime(time) {
        // this.$set(this.ruleForm, 'startTime', time[0]);
        // this.$set(this.ruleForm, 'endTime', time[1]);
        // console.log(this.ruleForm, 5656)
      },
      //获取组织结构
      getStruct() {
        //获取学校ID
        let id = JSON.parse(localStorage.getItem("userInfo")).schoolid;
        let data = { schoolId: id };
        let arr = [];
        //便利所有结构，tree转为一维数组结构
        getStruct(data)
          .then(res => {
            let data = res.data.data.child;
            for (let i = 0; i < data.length; i++) {
              for (let j = 0; j < data[i].child.length; j++) {
                arr.push({
                  userid: data[i].child[j].userid,
                  name: data[i].child[j].name,
                  title: data[i].name + "-----" + data[i].child[j].name
                });
              }
            }
            this.options = arr;

            //设置默认选中的人
            for (let i = 0; i < arr.length; i++) {
              if (arr[i].userid == this.ruleForm.receptionistId) {
                this.dailyReceptionistIndex = i;
                break;
              }
            }
          })
          .catch(err => {
            console.log(err);
          });
      },

      submitForm(formName) {
        console.log(this.ruleForm, 5656);

        this.$refs[formName].validate(valid => {
          if (valid) {
            //判断是否取消
            if(!this.isNo) {
              //判断是否设为咨询日
              this.ruleForm.isOpenday = this.statusDay ? 1 : 0;
            }else {
              this.ruleForm.isOpenday = 2
            }


            //获取到校咨询ID
            this.ruleForm.consultationId = this.$route.query.consultationId;

            //设置接待人信息
            let daily = this.options[this.dailyReceptionistIndex];
            this.ruleForm.receptionistId = daily.userid;
            this.ruleForm.receptionistName = daily.name;

            //判断是新增还是修改
            if (!this.ruleForm.opendayId) {
              //新增  删除opendayId参数
              delete this.ruleForm.opendayId;
            }
            addOrEditConsultationDay(this.ruleForm)
              .then(res => {
                let title = this.isCreatStatus ? "修改" : "新增";
                this.$message.success(title + "成功");
                this.$router.push({
                  path: `/consultingSchoolManagement`
                });
              })
              .catch(err => {
                let title = this.isCreatStatus ? "修改" : "新增";
                this.$message.success(title + "失败");
              });
          } else {
            console.log("error submit!!");
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      //获取当前日期的详细信息
      getConsultationDayDetail() {
        getConsultationDayDetail(this.param)
          .then(res => {
            //禁用编辑期
            this.$refs.quillEditor.quill.enable(false);
            this.ruleForm = res.data;
            this.$nextTick(function () {
              //丢掉编辑器焦点并重新启用编辑器
              this.$refs.quillEditor.quill.blur();
              this.$refs.quillEditor.quill.enable(true);
            });
            //获取日常接待人
            this.getStruct();
            console.log(res, "获取当前日期的详细信息");
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**
       * 接待行程富文本框消息内容的长度限制1000个字符
       * @param {*} event
       * @author LGH
       */
      schWordHandle(event) {
        this.msgscLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
      },
      /**
       * 接待地址富文本框消息内容的长度限制1000个字符
       * @param {*} event
       * @author LGH
       */
      addrWordHandle(event) {
        this.msgadLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
      }
    },
    created() {
      let data = this.$route.query;

      //获取请求参数
      this.param = {
        date: data.date,
        consultationId: data.consultationId
      };

      // 是否已取消
      if( data.isOpenday == 2) {
          this.isNo = true
      }else {
        //是否是接待日
        this.statusDay = Boolean(data.isOpenday);
        this.isNo = false
      }
      //判断是新建还是修改 true 为修改  false 为新增
      this.isCreatStatus = Number(data.openid) ? true : false;

      //获取当前日期的具体信息
      this.getConsultationDayDetail();
    }
  };
</script>

<style scoped>
  .add-content {
    margin-top: 40px;
  }
</style>