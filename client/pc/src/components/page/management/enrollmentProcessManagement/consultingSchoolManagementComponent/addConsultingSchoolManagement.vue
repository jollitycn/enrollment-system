<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path : '/consultingSchoolManagement' }">到校咨询管理</el-breadcrumb-item>
        <el-breadcrumb-item>咨询信息基本设置</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="add-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-row :gutter="20">
          <el-col :span="15">
            <el-form-item label="日常接待人">
              <el-select v-model="dailyReceptionistIndex" placeholder="请选择日常接待人" class="wid100">
                <el-option v-for="(item,index) in options" :key="index" :label="item.title" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="15">
            <el-form-item label="联系方式" required prop="telephone">
              <el-input placeholder="请输入联系方式" v-model="ruleForm.telephone" maxlength="11"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="15">
            <el-form-item label="咨询时间" prop="searchTime">
              <el-date-picker class="wid100" v-model="searchTime" type="daterange" @change="changeTime"
                format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="15">
            <el-form-item label="接待行程">
              <quillEditor ref="scheduleEditor" v-model="ruleForm.schedule" @focus="clickQuil(0)"
                @change="schWordHandle($event)" :options="editorOption">
              </quillEditor>
              <span class="editor-word-limit">{{msgscLength}}/1000</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="15">
            <el-form-item label="接待地址">
              <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
                :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
              </el-upload>
              <quillEditor ref="quillEditor" v-model="ruleForm.address" @focus="clickQuil(1)"
                @change="addrWordHandle($event)" :options="editorOption">
              </quillEditor>
              <span class="editor-word-limit">{{msgadLength}}/1000</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即{{title}}</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import config from "../../../../../config/index"
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import { addOrEditConsultation, getConsultationDetail } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement";
  import { getStruct } from '@/api/management/enrollmentProcessManagement/publicityPlanManagement'
  import {
    quillEditor
  } from 'vue-quill-editor';
  import rules from "@/assets/rules/rulesFrom";
  export default {
    name: "addConsultingSchoolManagement",
    components: {
      quillEditor
    },
    watch: {
      msgCtLength(val) {
        if (val > 1000) {
          this.planForm.comment = this.planForm.comment.substring(0, 1050)
        }
      }
    },
    data() {
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
        var evt = document.createEvent('MouseEvents');
        evt.initEvent('click', true, true);
        uploadElement.dispatchEvent(evt);
      };
      let phone = (rule, value, callback) => {
        if (!rules.phone.rules.test(value)) {
          return callback(new Error("请输入正确的手机号"));
        }
        return callback();
      };
      var timeValidatePass = (rule, value, callback) => {
        let val = this.searchTime
        console.log(val, 111111);
        if (!val) {
          callback(new Error('请选择咨询时间'));
        } else if ((typeof val) !== 'object') {
          callback(new Error('请选择咨询时间'));
        } else {
          callback();
        }
      };
      return {
        status: 0,
        msgscLength: 0,//接待行程长度
        msgadLength: 0,//接待地址长度
        title: '',
        dailyReceptionistIndex: "", //日常接待人
        options: [],
        searchTime: [],//宣讲时间段
        dailyReceptionist: '', //日常接待人
        ruleForm: {
          "address": "",
          "dailyReceptionistId": 2,
          "dailyReceptionistName": "",
          "endTime": "",
          "schedule": "",
          "startTime": "",
          "telephone": ""
        },
        rules: {
          searchTime: [
            { required: true, validator: timeValidatePass, trigger: 'change' }
          ],
          telephone: [{ validator: phone, trigger: "blur" }]
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
      }
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
      /**
       * 往富文本框中插入图片
       * @author lgh
       */
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

      /**
       * 上传图片接口
       * @param file {object} 二进制文件数据
       * @param callback{}
       * @author cwz
       */
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
      /**
      * 根据ID获取对应的数据
      * @param id{number} 获取的名称所需要的id
      * @param query{array} 需要获取的name对应的数组
      * @author LiuGuoHao
      */
      getObjById(id, query) {
        var obj = query.find(function (x) {
          return x.consultationId == id;
        });
        return obj
      },
      //获取组织结构
      getStruct() {
        let id = JSON.parse(localStorage.getItem('userInfo')).schoolid
        let data1 = { schoolId: id }
        let arr = []
        getStruct(data1).then(res => {
          let data = res.data.data.child;
          for (let i = 0; i < data.length; i++) {
            for (let j = 0; j < data[i].child.length; j++) {
              arr.push({
                userid: data[i].child[j].userid,
                name: data[i].child[j].name,
                title: data[i].name + '-----' + data[i].child[j].name
              })
            }
          }
          this.options = arr;
          if (this.$route.params.type === 'edit') {
            //获取详细信息
            let id = this.$route.query.id;
            this.getConsultationDetail(id)
          }
        }).catch(err => {
          console.log(err);
        })
      },
      //选择事件
      changeTime(time) {
        if (time) {
          this.$set(this.ruleForm, 'startTime', time[0]);
          this.$set(this.ruleForm, 'endTime', time[1]);
        } else {
          this.$set(this.ruleForm, 'startTime', "");
          this.$set(this.ruleForm, 'endTime', "");
        }
        this.searchTime = time
        console.log(this.ruleForm, 5656, this.searchTime, time)
      },
      //获取咨询详情
      getConsultationDetail(id) {
        getConsultationDetail(id).then(res => {
          this.$refs.quillEditor.quill.enable(false);
          console.log(res, 55556666);
          // let detail = this.getObjById(id, res.data)
          let detail = res.data
          console.log(detail, 111111);

          this.ruleForm = detail;
          this.searchTime = [detail.startTime, detail.endTime]
          //禁用编辑期
          this.$nextTick(function () {
            //丢掉编辑器焦点并重新启用编辑器
            this.$refs.quillEditor.quill.blur();
            this.$refs.quillEditor.quill.enable(true);
          });
          //设置默认选中的人
          let arr = this.options;
          for (let i = 0; i < arr.length; i++) {
            console.log(arr[i].userid, this.ruleForm.dailyReceptionistId);

            if (arr[i].userid == this.ruleForm.dailyReceptionistId) {
              this.dailyReceptionistIndex = i;
              break
            }
          }
        }).catch(err => {
          console.log(err);
        })
      },
      submitForm(formName) {
        //设置接待人信息
        let daily = this.options[this.dailyReceptionistIndex]
        if (daily) {
          this.ruleForm.dailyReceptionistId = daily.userid;
          this.ruleForm.dailyReceptionistName = daily.name;
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            addOrEditConsultation(this.ruleForm).then(res => {
              this.$message.success(`${this.title}成功`)
              this.$router.push({ path: `/consultingSchoolManagement` });
            }).catch(err => {
              console.log(err);
            })
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
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

      this.title = this.$route.params.type === 'edit' ? '修改' : '新增'
      //获取组织结构
      this.getStruct()
    }
  }
</script>

<style scoped>
  .add-content {
    margin-top: 40px;
  }

  .editor-word-limit {
    color: gray;
    font-size: 10px;
    float: left;
  }

  .wid100 {
    width: 100%;
  }
</style>