<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>学校信息管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form class="form-card" ref="schoolForm" :model="schoolForm" label-width="120px" size="mini"
      label-position="right" v-loading="loading" element-loading-text="拼命加载中">
      <el-row>
        <el-col :span="9">
          <el-form-item label="学校名称">
            <el-input v-model="schoolForm.recruitSchoolName"></el-input>
          </el-form-item>
          <el-form-item label="校长">
            <el-input v-model="schoolForm.principal"></el-input>
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="schoolForm.telephoneNumber" maxlength="13"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" class="img">
          <el-upload class="avatar-uploader" action="/upload" :show-file-list="false" :http-request="uploadImg"
            :before-upload="beforeAvatarUpload">
            <img v-if="schoolForm.recruitSchoolLogo" :src="schoolForm.recruitSchoolLogo | imgUrlFilter"
              class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="el-upload__tip">只支持JPG或PNG格式图片<br>且大小不超过2M</div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="学校地址">
            <el-input v-model="schoolForm.address" maxlength="48"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <el-form-item label="学校简介">
            <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
              :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
            </el-upload>
            <quill-editor ref="quillEditor" v-model="schoolForm.description" :options="editorOption"
              style="min-height: 200px;" @change="limitWordHandle($event)"></quill-editor>
          </el-form-item>
          <span class="editor-word-limit">{{msgCtLength}}/1000</span>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button align="center" style="width: 100px;" type="primary" @click="updateRecruitSchoolInfo">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import "quill/dist/quill.core.css";
  import "quill/dist/quill.snow.css";
  import "quill/dist/quill.bubble.css";
  import config from '../../../config/index'
  import { quillEditor } from "vue-quill-editor";
  import {
    updateRecruitSchoolInfo,
    RecruitSchoolInfo
  } from "@/api/admin/enrollmentPlanList/addenrollmentPlan";
  import { uploadAdd } from "../../../api/admin/API-Controller/APIController";
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
        loading: false,
        isSee: "",
        msgCtLength: 0, //富文本框的输入字符的数量
        schoolId: JSON.parse(localStorage.getItem("userInfo")),
        recruitSchoolLogo: '',
        schoolForm: {
          recruitSchoolName: "",
          recruitSchoolLogo: '',
          principal: "",
          telephoneNumber: "",
          address: "",
          description: "",
          apiId: "",
          planId: ""
        },
        imageUrl: "",
        editorOption: {
          placeholder: "请输入学校简介",
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
        //校验
        schoolformRule: {
          recruitSchoolName: [{ required: true, message: "请输入学校名称", trigger: "blur" }],
          principal: [{ required: true, message: "请输入校长", trigger: "blur" }],
          telephoneNumber: [{ required: true, message: "请输入联系电话", trigger: "blur" }],
          address: [{ required: true, message: "请输入学校地址", trigger: "blur" }]
        }
      };
    },
    created() {
      this.getData();
    },
    methods: {
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      /**
       * 图片上传
       * @author cwz
       */
      uploadImg(option) {
        debugger
        console.log(option, '==>option')
        this.uploadAdd(option.file, imgUrl => {
          console.log("imgUrl", imgUrl)
          this.schoolForm.recruitSchoolLogo = imgUrl;
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
       * 上传图片进行格式分析
       * @param file(String) 图片内容
       * @author cwz
       */
      beforeAvatarUpload(file) {
        const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error("上传图片只能是 JPG/PNG 格式!");
        }
        else if (!isLt2M) {
          this.$message.error("上传图片大小不能超过 2MB!");
        }
        return isJPG && isLt2M;
      },
      /**
       * 上传图片接口
       * @param file {object} 二进制文件数据
       * @author cwz
       */
      productUploadProductCover(file, callback) {
        let formData = new FormData();
        let type = file.name.substring(
          file.name.indexOf(".") + 1,
          file.name.length
        );
        formData.append("file", file);
        formData.append("fileType", type);
        productUploadProductCover(formData)
          .then(res => {
            callback(res.data);
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 富文本编辑插入图片
       * @param option {object} 二进制文件数据
       * @author cwz
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
       * 富文本框消息内容的长度限制1000个字符
       * @param {*} event
       * @author LGH
       */
      limitWordHandle(event) {
        console.log(event, "==>e");
        this.msgCtLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
      },
      /**
       * 获取学校信息
       * @param data {object} 学校id
       * @author cwz
       */
      getData() {
        var data = this.schoolId.schoolid;
        this.loading = true
        RecruitSchoolInfo(data).then(res => {
          this.schoolForm = res.data;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        });
      },
      /**
       * 编辑学校信息
       * @param data {} 包含了学校名称、校长、联系电话、学校地址、说明
       * @author cwz
       */
      updateRecruitSchoolInfo(data) {
        if (this.msgCtLength > 1000) {
          this.$message.error("招生简介不可超过1000个字！")
        } else {
          updateRecruitSchoolInfo(this.schoolForm).then(res => {
            this.$message.success("编辑学校简介成功！");
          });
        }
      },
    }
  };
</script>
<style scoped>
  .logoImg {
    width: 145px;
    height: 130px;
  }

  .form-card {
    margin-top: 20px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 14px;
    color: #8c939d;
    width: 178px;
    line-height: 148px;
  }

  .avatar {
    width: 128px;
    height: 128px;
    display: block;
  }

  .img>>>.el-upload--text {
    margin-left: 20px;
    width: auto;
    height: 130px;
  }

  .editor-word-limit {
    color: gray;
    font-size: 10px;
  }

  .el-upload__tip {
    margin-left: 3%;
    margin-bottom: 10px;
  }
</style>