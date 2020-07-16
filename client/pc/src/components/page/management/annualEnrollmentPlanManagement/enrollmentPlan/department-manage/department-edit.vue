<template>
  <div>
    <span style="margin-left: 20px;">api院系名称：{{facultyForm.apiCollegeName}}</span>
    <el-form class="form-card" ref="facultyForm" :rules='facultyRules' :model="facultyForm" label-width="120px"
      size="mini" label-position="right" style="margin-left: 10px;" v-loading="loading" element-loading-text="拼命加载中">
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item label="院系负责人" prop="collegeManager">
            <el-input v-model="facultyForm.collegeManager" maxlength="7" show-word-limit class="wid195" placeholder="请输入院系负责人">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="院系名称" prop="collegeName">
            <el-input v-model="facultyForm.collegeName" maxlength="10" show-word-limit class="wid195" placeholder="请输入院系名称">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="培养目标" prop="cultivateAim">
            <el-input v-model="facultyForm.cultivateAim" maxlength="50" show-word-limit class="wid104" placeholder="请输入培养目标">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="招生人数" prop="recruitAmount">
            <el-input-number v-model="facultyForm.recruitAmount" class="wid104" :min="0" :max="9999999" :precision="0">
            </el-input-number>
            <!--<el-input v-model="facultyForm.recruitAmount" class="wid104" placeholder="请输入计划招生人数"></el-input> -->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="招生对象" prop="recruitTarget">
            <el-input v-model="facultyForm.recruitTarget" maxlength="50" show-word-limit class="wid104" placeholder="请输入招生对象">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="就业方向" prop="careerForward">
            <el-input v-model="facultyForm.careerForward" maxlength="50" show-word-limit class="wid104" placeholder="请输入就业方向">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="院系简介" prop="comment">
            <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
              :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
            </el-upload>
            <quill-editor ref="quillEditor" v-model="facultyForm.comment" :options="editorSchoolOption"
              style="min-height: 200px;width:104%;" @change="limitWordHandle($event)">
            </quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button align=" center" style="width: 100px;" type="primary" @click="returnFaculty">取消
        </el-button>
        <el-button align="center" style="width: 100px;" type="primary" @click="editFacultyDetailed">
          保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import config from '../../../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import { queryCollegeInfoById, updateCollegeInfo } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js'
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
    name: 'departmentEdit',
    data() {
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
        loading: false,
        msgCtLength: 0, //富文本框的输入字符的数量
        collegeId: this.$route.params.collegeId,
        //查看/新建院系|专业表单内容
        facultyForm: {
          //新建/编辑院系的数据
          careerForward: '',
          collegeId: '',
          collegeManager: '',
          collegeName: '',
          comment: '',
          cultivateAim: '',
          mainCourse: '',
          recruitAmount: '',
          recruitTarget: '',
        },
        facultyRules: {
          collegeManager: [
            { required: true, message: '请输入院系负责人', trigger: 'blur' },
          ],
          collegeName: [
            { required: true, message: '请输入院系名称', trigger: 'blur' },
          ],
          cultivateAim: [
            { required: true, message: '请输入培养目标', trigger: 'blur' },
          ],
          recruitAmount: [
            { required: true, message: '请输入招生人数', trigger: 'blur' },
          ],
          recruitTarget: [
            { required: true, message: '请输入招生对象', trigger: 'blur' },
          ],
          careerForward: [
            { required: true, message: '请输入就业方向', trigger: 'blur' },
          ],
          comment: [
            { required: true, message: '请输入院系简介', trigger: 'blur' },
          ],
        },
        editorSchoolOption: {
          placeholder: '请输入院系简介',
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
    props: {
      planId: { type: String, default: '' },
      addshow: { type: Boolean, default: '' },
      type: { type: String, default: '' }
    },
    created() {
      this.editShowFaculty(this.collegeId)
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
       * 上传图片限制
       */
       beforeAvatarUpload(file) {
        const isLt2M = file.size / 1024 / 1024 < 2;
        const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
        if (!isJPG) {
          this.$message.error('上传头像只能是 JPG或PNG 格式!');
        }
        else if (!isLt2M) {
          this.$message.error('上传头像大小不能超过 2MB!');
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
       * 展示编辑招生院校
       * @param collegeId{String} 根据院系ID查找具体数据
       * @author LiuGuoHao
       */
      editShowFaculty(collegeId) {
        this.editFaculty = true;
        this.faculty = false;
        this.loading = true
        queryCollegeInfoById(collegeId).then(res => {
          this.facultyForm = res.data
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      },
      /**
       * 返回院系介绍列表
       * @author LiuGuoHao
       */
      returnFaculty() {
        this.$confirm(`确定要返回院系管理吗？未保存的数据将会丢失！`, '提示', {
          type: 'warning'
        })
          .then(() => {
            this.$router.go(-1)
          })
      },
      //编辑院系管理操作
      editFacultyDetailed() {
        if (this.msgCtLength > 1000) {
          this.$message.error("院系简介不可超过1000个字！")
        } else {
          this.$refs.facultyForm.validate(valid => {
            if (valid) {
              this.$confirm('确定要保存编辑吗？', '提示', {
                type: 'warning'
              })
                .then(() => {
                  updateCollegeInfo(this.facultyForm).then(res => {
                    this.$message.success("编辑院系成功！")
                    this.$router.go(-1)
                  })
                })
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
<style>
  .wid195 {
    width: 110%;
  }

  .wid104 {
    width: 104%;
  }

  .form-card {
    margin-top: 10px;
  }

  .editor-word-limit {
    color: gray;
    font-size: 10px;
    float: right;
  }
</style>