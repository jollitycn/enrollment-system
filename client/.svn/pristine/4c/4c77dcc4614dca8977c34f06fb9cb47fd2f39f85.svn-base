<template>
  <el-form class="form-card" ref="planRecruitFrom" :rules='recruitRules' :model="planRecruitFrom" label-width="120px"
    size="mini" label-position="right" v-loading="loading" element-loading-text="拼命加载中">
    <el-row :gutter="20">
      <el-col :span="16" class="img">
        <el-form-item prop="recruitmentImage" label="学校LOGO">
          <el-upload class="avatar-uploader" action="/upload" :show-file-list="false" :http-request="uploadImg"
            :before-upload="beforeAvatarUpload" :disabled='isOrNotEdit'>
            <img v-if="planRecruitFrom.recruitmentImage" :src="planRecruitFrom.recruitmentImage | imgUrlFilter"
              class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="el-upload__tip">只支持JPG或PNG格式图片，且大小不超过2M</div>
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item label="招生对象" prop="recruitTarget">
      <el-input v-model="planRecruitFrom.recruitTarget" maxlength="50" show-word-limit class="wid60"
        :disabled='isOrNotEdit' placeholder="请输入招生对象">
      </el-input>
    </el-form-item>
    <el-form-item label="注意事项" prop="attention">
      <el-input v-model="planRecruitFrom.attention" type="textarea" class="wid60" :autosize="{ minRows: 4, maxRows: 13}"
        placeholder="请输入注意事项" :disabled='isOrNotEdit' maxlength="2000" show-word-limit>
      </el-input>
    </el-form-item>
    <el-form-item label="报到与录取" prop="register">
      <el-input v-model="planRecruitFrom.register" maxlength="50" show-word-limit class="wid60" :disabled='isOrNotEdit'
        placeholder="请输入报道与录取信息">
      </el-input>
    </el-form-item>
    <el-form-item label="招生计划" prop="planDescription">
      <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload" :show-file-list="false"
        :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload"></el-upload>
      <quill-editor ref="quillEditor" v-model="planRecruitFrom.planDescription" :options="editorOption"
        style="min-height: 200px;width: 60%;" :disabled='isOrNotEdit' @change="limitWordHandle($event)">
      </quill-editor>
      <span class="editor-word-limit">{{msgCtLength}}/1000</span>
    </el-form-item>
    <el-form-item>
      <el-button :loading="btnLoading" align="center" style="width: 100px;" type="primary"
        @click="addOrEditPlanRecruitInfo()" v-if="!isOrNotEdit">保存
      </el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  import bus from "../../../../../common/bus";
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import config from '../../../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { uploadAdd } from '@/api/admin/API-Controller/APIController.js';
  import {
    addPlanRecruitInfo,
    updatePlanRecruitInfo,
    queryRecruitSchoolInfo,
  } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  export default {
    name: 'studentRecruit',
    components: {
      quillEditor
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
      return {
        btnLoading: false,
        loading: false,
        isOrNotEdit: false,
        addShow: false, //展示学校与招生简介，增加保存时使用
        msgCtLength: 0, //富文本框的输入字符的数量
        editorOption: {
          placeholder: '请输入计划说明',
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
        //招生简介内容
        planRecruitFrom: {
          planId: '',
          recruitTarget: '',
          attention: '',
          register: '',
          planDescription: ''
        },
        recruitRules: {
          recruitmentImage: [
            { required: true, message: '请上传学校Logo图片', trigger: 'change' },
          ],
          recruitTarget: [
            { required: true, message: '请输入招生对象', trigger: 'blur' },
          ],
          attention: [
            { required: true, message: '请输入注意事项', trigger: 'blur' },
          ],
          register: [
            { required: true, message: '请输入报到与录取', trigger: 'blur' },
          ],
          planDescription: [
            { required: true, message: '请输入招生计划说明', trigger: 'blur' },
          ]
        },
      }
    },
    props: {
      planId: { type: String, default: '' },
      operation: { type: String, default: '' },
      addshow: { type: Boolean, default: false },
      type: { type: String, default: '' }
    },
    watch: {
      msgCtLength(val) {
        if (val > 1000) {
          this.planForm.comment = this.planForm.comment.substring(0, 1050)
        }
      }
    },
    created() {
      this.getData()
    },
    methods: {
      /**
       * 添加招生简介信息
       * @author LiuGuoHao
       */
      addOrEditPlanRecruitInfo() {
        if (this.msgCtLength > 1000) {
          this.$message.error("招生计划不可超过1000个字！")
        } else {
          this.$refs.planRecruitFrom.validate(valid => {
            if (valid) {
              this.planRecruitFrom.planId = this.planId
              this.btnLoading = true
              if (!this.planRecruitFrom.planRecruitInfoId) {
                updatePlanRecruitInfo(this.planRecruitFrom).then(res => {
                  this.$message.success("新增招生简介成功！")
                  this.btnLoading = false
                  this.$emit("recruit-created")
                })
              } else {
                updatePlanRecruitInfo(this.planRecruitFrom).then(res => {
                  this.$message.success("编辑招生简介成功！")
                  this.btnLoading = false
                  this.$emit("recruit-created")
                })
              }
            }
          })
        }
      },
      getData() {
        if (this.type != "creat") {
          this.queryRecruitSchoolInfo();
        }
        // if (this.addshow == true) {
        //   this.queryRecruitSchoolInfo();
        // }
        if (this.type == "see") {
          this.isOrNotEdit = true;
        }
      },
      /**
       * 图片上传
       */
      uploadImg(option) {
        this.uploadAdd(option.file, imgUrl => {
          console.log(option.file, '==>file')
          console.log(imgUrl, '==>imgUrl')
          this.planRecruitFrom.recruitmentImage = imgUrl;
        });
      },
      /**
       * 上传图片接口
       * @param file {object} 二进制文件数据
       * @param callback{} 
       * @author LiuGuoHao
       */
      uploadAdd(file, callback) {
        let formData = new FormData();
        let type = file.name.substring(file.name.indexOf('.') + 1, file.name.length);
        formData.append('file', file);
        formData.append('fileType', type);
        uploadAdd(formData)
          .then(res => {
            callback(res.data);
          })
          .catch(err => {
            console.error(err);
          });
      },
      /**
       * 图片上传成功后执行
       * @param res(array) 接口对接返回的内容
       *  @param file(文件名)
       * @author LiuGuoHao
       */
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      /**
       * 上传图片进行格式分析
       * @param file(String) 图片内容
       * @author LiuGuoHao
       */
      beforeAvatarUpload(file) {
        const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
        // console.log(file.type,'==>fileType')
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG或PNG 格式!');
        }
        else if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },

      /**
       * 根据planId获取招生简介数据
       * @author LiuGuoHao
       */
      queryRecruitSchoolInfo() {
        this.loading = true
        queryRecruitSchoolInfo(this.planId).then(res => {
          //禁用编辑期
          this.$refs.quillEditor.quill.enable(false);
          this.planRecruitFrom = res.data;
          this.$nextTick(function () {
              //丢掉编辑器焦点并重新启用编辑器
              this.$refs.quillEditor.quill.blur();
              this.$refs.quillEditor.quill.enable(true);
            });
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      },
      /**
       * 富文本编辑插入图片
       * @param option {object} 二进制文件数据
       * @author LiuGuoHao
       */
      uploadImgForEditor(option) {
        this.uploadAdd(option.file, imgUrl => {
          //插入富文本中
          let quill = this.$refs['quillEditor'].quill;
          let length = quill.getSelection().index;
          quill.insertEmbed(length, 'image', config.imgDir + imgUrl);
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
        this.msgCtLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
      }
    },
  }
</script>
<style scoped>
  .wid60 {
    width: 60%;
  }

  .form-card {
    margin-top: 10px;
  }

  .img>>>.el-upload--text {
    /* margin-left: -80px; */
    width: 40%;
    height: 200px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 100%;
    height: 100%;
    display: block;
  }

  .editor-word-limit {
    float: left;
    color: gray;
    font-size: 10px;
  }
</style>