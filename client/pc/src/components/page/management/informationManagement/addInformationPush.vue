<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/informationPush'}">信息推送</el-breadcrumb-item>
        <el-breadcrumb-item>新建信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form class="form-card" :rules="formRule" ref="form" :model="form" label-width="120px" size="mini"
      label-position="right">
      <el-row>
        <el-col :span="14">
          <el-form-item label="标题" prop="title">
            <el-input placeholder="请输入标题" class="wid100" v-model="form.title " maxlength="30"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="信息类型" prop="messageType">
            <el-select class="wid100" v-model="form.messageType" placeholder="请选择信息类型" size="mini">
              <el-option v-for="item in messageType" :key="item.dataCode" :label="item.dataValue"
                :value="item.dataCode"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item label="接收端" prop="recevier">
            <el-select class="wid100" v-model="form.recevier" placeholder="请选择接收端">
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
            <el-select class="wid100" v-model="form.planId" placeholder="请选择所属计划" size="mini">
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
              style="min-height: 200px;" @change="limitWordHandle($event)"></quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="14">
          <el-form-item>
            <el-button align="center" style="width: 100px;" type="success" @click="save(1)">发布</el-button>
            <el-button align="center" style="width: 100px;" type="primary" @click="save(0)">保存</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
  import { addOrEdit } from "../../../../api/admin/informationManagement/informationPush";
  import { queryAllFinishedPlan } from "../../../../api/admin/enrollmentPlanManagement/enrollmentPlan";
  import { getMessageTypeList } from "../../../../api/admin/systems-management/number-dictionaries";
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import "quill/dist/quill.core.css";
  import "quill/dist/quill.snow.css";
  import "quill/dist/quill.bubble.css";
  import { quillEditor } from "vue-quill-editor";
  import config from '../../../../config/index'
  export default {
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
        belongPlan: [],
        messageType: [],
        form: {
          newsId: "",
          title: "",
          messageType: "",
          recevier: "",
          recevier: "",
          belongPlan: "",
          messageContent: "",
          newsStatus: ""
        },
        query: {
          pageNum: 1,
          pageSize: 10
        },
        msgCtLength: 0, //富文本框的输入字符的数量
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
        }
      };
    },
    created() {
      // //获取招生计划
      this.queryAllFinishedPlan();
      //获取信息类型
      this.getMessageTypeList();
    },
    methods: {
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
       * 新增新闻保存
       * @author cwz
       * @param {*} newsStatus 0：草稿，1：已发布
       */
      save(newsStatus) {
        this.form.newsStatus = newsStatus;
        //表单验证
        this.$refs.form.validate(valid => {
          //表单有数据执行
          if (valid) {
            const data = { ...this.form };
            this.addOrEdit(data);
          }
        });
      },
      /**
       * 新增新闻请求
       * @author chenweizhe
       * @param {*} data
       */
      addOrEdit(data) {
        addOrEdit(data)
          .then(() => {
            this.$message.success(`新增新闻成功`);
            //跳转到信息推送
            this.$router.push({
              path: `/informationPush`
            });
          })
          .catch(err => {
            console.log(err);
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