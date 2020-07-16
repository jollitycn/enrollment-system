<template>
  <div class="page-container" v-loading="loading" element-loading-text="拼命加载中">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/informationPush'}">信息推送</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.title}}信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form class="form-card" ref="form" :model="form" :rules="formRule" label-width="120px" size="mini"
      label-position="right">
      <el-row>
        <el-col :span="14">
          <el-form-item label="标题" prop="title">
            <el-input placeholder="请输入标题" class="wid100" v-model="form.title" :disabled="isSee" maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="信息类型" prop="messageType">
            <el-select class="wid100" v-model="form.messageType" :disabled="isSee" placeholder="请选择信息类型" size="mini">
              <el-option :disabled="isSee" v-for="item in messageType" :key="item.dataCode" :label="item.dataValue"
                :value="item.dataCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="接收端" prop="recevier">
            <el-select :disabled="isSee" v-model="form.recevier" placeholder="请选择接收端" class="wid100 mr10">
              <el-option label="全部" :value="0"></el-option>
              <el-option label="学生" :value="1"></el-option>
              <el-option label="家长" :value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="所属计划" prop="planId">
            <el-select class="wid100" :disabled="isSee" v-model="form.planId" placeholder="请选择所属计划" size="mini">
              <el-option v-for="item in belongPlan" :key="item.planId" :label="item.title" :value="item.planId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="信息内容">
            <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
              :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
            </el-upload>
            <quill-editor ref="quillEditor" v-model="form.messageContent" :options="editorOption"
              style="min-height: 200px;" :disabled="isSee" @change="limitWordHandle($event)"></quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item>
            <el-button align="center" style="width: 100px;" type="success"
              v-if="form.newsStatus==0 && this.$route.params.type == 1" @click="save(1)">发布</el-button>
            <el-button align="center" style="width: 100px;" type="primary" v-if="this.$route.params.type == 1"
              @click="save(0)">保存修改</el-button>
            <el-button align="center" style="width: 100px;" type="primary" v-if="this.$route.params.type == 0"
              @click="go">关闭</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import {
    detail,
    addOrEdit
  } from "../../../../api/admin/informationManagement/informationPush";
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import { queryAllFinishedPlan } from "../../../../api/admin/enrollmentPlanManagement/enrollmentPlan";
  import { getMessageTypeList } from "../../../../api/admin/systems-management/number-dictionaries";
  import "quill/dist/quill.core.css";
  import "quill/dist/quill.snow.css";
  import "quill/dist/quill.bubble.css";
  import { quillEditor } from "vue-quill-editor";
  import config from '@/config/index.js'
  export default {
    components: {
      quillEditor
    },
    data() {
      // 工具栏配置
      const toolbarOptions = [
        ["bold", "italic", "underline", "strike"], // toggled buttons
        ["blockquote", "code-block"],

        [
          {
            header: 1
          },
          {
            header: 2
          }
        ], // custom button values
        [
          {
            list: "ordered"
          },
          {
            list: "bullet"
          }
        ],
        [
          {
            script: "sub"
          },
          {
            script: "super"
          }
        ], // superscript/subscript
        [
          {
            indent: "-1"
          },
          {
            indent: "+1"
          }
        ], // outdent/indent
        [
          {
            direction: "rtl"
          }
        ], // text direction

        [
          {
            size: ["small", false, "large", "huge"]
          }
        ], // custom dropdown
        [
          {
            header: [1, 2, 3, 4, 5, 6, false]
          }
        ],

        [
          {
            color: []
          },
          {
            background: []
          }
        ], // dropdown with defaults from theme
        [
          {
            font: []
          }
        ],
        [
          {
            align: []
          }
        ],
        ["clean"], // remove formatting button
        ["link", "image", "video"]
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
      return {
        loading: false,
        title: "",
        belongPlan: [],
        messageType: [],
        informationType: [],
        isSee: false,
        msgCtLength: 0, //富文本框的输入字符的数量
        form: {
          newsId: "",
          title: "",
          messageType: "",
          recevier: "",
          belongPlan: "",
          messageContent: "",
          planId: "",
          newsStatus: ""
        },
        query: {
          pageNum: 1,
          pageSize: 10
        },
        //信息校验
        formRule: {
          title: [{ required: true, message: "请输入标题", trigger: "blur" }],
          messageType: [
            { required: true, message: "请选择信息类型", trigger: "change" }
          ],
          recevier: [
            { required: true, message: "请选择接收端", trigger: "change" }
          ],
          planId: [
            { required: true, message: "请选择所属计划", trigger: "change" }
          ]
        },
        editorOption: {
          placeholder: "请输入信息内容",
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
        }
      };
    },
    created() {
      //获取信息类型
      this.getMessageTypeList();
      //获取计划信息
      this.queryAllFinishedPlan();
      //获取新闻信息
      this.detail(this.$route.params.id);
      //查看 || 编辑
      this.isSee = this.$route.params.type == "0" ? true : false;
      this.title = this.$route.params.type == "0" ? "查看" : "编辑";
    },
    methods: {
      /**
       * 往富文本框中插入图片
       * @author lgh
       */
      uploadImgForEditor(option) {
        this.uploadAdd(option.file, imgUrl => {
          //插入富文本中
          console.log(imgUrl)
          let quill = this.$refs["quillEditor"].quill;
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
       * 获取招生计划信息
       * @author cwz
       */
      queryAllFinishedPlan() {
        queryAllFinishedPlan(this.query).then(res => {
          this.belongPlan = res.data;
        });
      },
      /**
       * 获取信息类型
       * @author cwz
       */
      getMessageTypeList() {
        getMessageTypeList().then(res => {
          this.messageType = res.data
          // if( res.data && res.data.length == 3 ){
          // }
        });
      },
      /**
       * 获取新闻 详细数据
       * @author cwz
       */
      detail(id) {
        this.loading = true
        detail(id)
          .then(res => {
            this.form = res.data;
            this.loading = false
          })
          .catch(err => {
            console.error(err);
            this.loading = false
          });
      },
      /**
       * 保存新闻
       * @author cwz
       */
      save(newsStatus) {
        if (this.msgCtLength > 1000) {
          this.$message.error("消息内容不可超过1000个字！")
        } else {
          const data = { ...this.form };
          data.newsStatus = newsStatus;
          this.$refs.form.validate(valid => {
            if (valid) {
              this.addOrEdit(data);
            }
          });
        }
      },
      /**
       * 编辑新闻
       * @author cwz
       */
      addOrEdit(data) {
        addOrEdit(data)
          .then(() => {
            this.$message.success(`编辑新闻成功`);
            //跳转到信息列表
            this.$router.push({
              path: `/informationPush`
            });
          })
          .catch(err => {
            console.log(err);
          });
      },
      go() {
        this.$router.push({
          path: `/informationPush`
        });
      },
      /**
       * 富文本框消息内容的长度限制1000个字符
       * @param {*} event
       * @author LGH
       */
      limitWordHandle(event) {
        console.log(event, "==>e");
        this.msgCtLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
      }
    }
  };
</script>
<style scoped>
  .editor-word-limit {
    float: right;
    color: gray;
    font-size: 10px;
  }

  .wid100 {
    width: 100%;
  }
</style>