<template>
  <div>
    <el-form class="form-card" v-loading="loading" element-loading-text="拼命加载中" :rules="schoolRules" ref="schoolForm"
      :model="schoolForm" label-width="120px" size="mini" label-position="right">
      <el-row :gutter="20">
        <el-col :span="16" class="img">
          <el-form-item prop="recruitSchoolLogo" label="学校LOGO">
            <el-upload class="avatar-uploader" action="/upload" :show-file-list="false" :http-request="uploadImg"
              :before-upload="beforeAvatarUpload" :disabled='isOrNotEdit'>
              <img v-if="schoolForm.recruitSchoolLogo" :src="schoolForm.recruitSchoolLogo | imgUrlFilter"
                class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div class="el-upload__tip">只支持JPG或PNG格式图片，且大小不超过2M</div>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-form-item label="学校名称" prop="recruitSchoolName">
            <el-input v-model="schoolForm.recruitSchoolName" placeholder="请输入学校名称" maxlength="15" show-word-limit
              :disabled='isOrNotEdit'>
            </el-input>
          </el-form-item>
          <el-form-item label="校长" prop="principal">
            <el-input v-model="schoolForm.principal" placeholder="请输入校长名称" maxlength="7" show-word-limit
              :disabled='isOrNotEdit'>
            </el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="telephoneNumber">
            <el-input v-model="schoolForm.telephoneNumber" placeholder="请输入手机号码" maxlength="11" show-word-limit
              :disabled='isOrNotEdit'>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-form-item label="学校地址" prop="address">
            <el-input v-model="schoolForm.address" placeholder="请输入学校地址" maxlength="50" show-word-limit
              :disabled='isOrNotEdit'>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload" :show-file-list="false"
          :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload"></el-upload>
        <el-col :span="16">
          <el-form-item label="学校简介" prop="description">
            <quill-editor ref="quillEditor" v-model="schoolForm.description" :options="editorSchoolOption"
              style="min-height: 200px;" :disabled='isOrNotEdit' @change="limitWordHandle($event)">
            </quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button :loading="btnLoading" align="center" style="width: 100px;" type="primary"
          @click="addOrEditPlanSchoolIntroduction()" v-if="!isOrNotEdit">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import bus from '../../../../../common/bus'
  import config from '../../../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { uploadAdd } from '@/api/admin/API-Controller/APIController.js';
  import {
    addPlanSchoolIntroduction,
    updatePlanSchoolIntroduction,
    queryPlanSchoolIntroduction,
  } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  export default {
    name: 'schoolIntrAdd',
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
        isSee: true,
        isOrNotEdit: false,
        msgCtLength: 0, //富文本框的输入字符的数量
        editorSchoolOption: {
          placeholder: '请输入学校简介',
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
        //学校简介内容
        schoolForm: {
          planSchoolIntroductionId: '',
          recruitSchoolName: '',
          recruitSchoolLogo: '',
          principal: '',
          telephoneNumber: '',
          address: '',
          description: '',
          apiId: '',
          planId: ''
        },
        schoolRules: {
          recruitSchoolName: [
            { required: true, message: '请输入学校名称', trigger: 'blur' },
          ],
          recruitSchoolLogo: [
            { required: true, message: '请上传学校Logo图片', trigger: 'change' },
          ],
          principal: [
            { required: true, message: '请输入校长名称', trigger: 'blur' },
          ],
          telephoneNumber: [
            { required: true, message: '请输入手机号码', trigger: 'blur' },
            {
              pattern: /^[1]([3-9])[0-9]{9}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            },
          ],
          address: [
            { required: true, message: '请输入学校地址', trigger: 'blur' },
          ],
          description: [
            { required: true, message: '请输入学校简介', trigger: 'blur' },
          ],
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
      //基本信息保存后搜索学校简介信息
      getData() {
        if (this.type != "creat") {
          this.queryPlanSchoolIntroduction();
        }
        // if (this.addshow == true) {
        //   this.queryPlanSchoolIntroduction();
        // }
        if (this.type == "see") {
          this.isOrNotEdit = true;
        }
      },
      /**
       * 根据planId获取学校简历数据
       * @author LiuGuoHao
       */
      queryPlanSchoolIntroduction() {
        this.loading = true
        queryPlanSchoolIntroduction(this.planId).then(res => {
          //禁用编辑期
          this.$refs.quillEditor.quill.enable(false);
          this.schoolForm = res.data;
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
       * 图片上传
       */
      uploadImg(option) {
        this.uploadAdd(option.file, imgUrl => {
          this.schoolForm.recruitSchoolLogo = imgUrl;
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
        console.log(file.type, '==>fileType')
        console.log(isJPG, '==>isJPG');

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
       * 添加/编辑学校简介
       * @author LiuGuoHao
       */
      addOrEditPlanSchoolIntroduction() {
        if (this.msgCtLength > 1000) {
          this.$message.error("学校简介不可超过1000个字！")
        } else {
          this.$refs.schoolForm.validate(valid => {
            if (valid) {
              this.schoolForm.planId = this.planId
              console.log(this.schoolForm, "==>schoolForm")
              // if (!this.schoolForm.planSchoolIntroductionId) {
              //   updatePlanSchoolIntroduction(this.schoolForm).then(res => {
              //     this.$message.success("新增学校简介成功！")
              //   })
              // } else {
              this.btnLoading = true
              updatePlanSchoolIntroduction(this.schoolForm).then(res => {
                this.$message.success("编辑学校简介成功！")
                this.btnLoading = false
                this.$emit("school-created", `${this.planId}`)
              })
              // }
            }
          })
        }
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
  }
</script>
<style scoped>
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
    color: gray;
    font-size: 10px;
  }
</style>