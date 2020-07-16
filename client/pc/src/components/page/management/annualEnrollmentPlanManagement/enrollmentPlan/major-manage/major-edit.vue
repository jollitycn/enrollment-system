<template>
  <div class="mrl10">
    <span>专业名称：{{majorForm.majorName}}</span>
    <el-form class="form-card" ref="majorForm" :rules='majorRules' :model="majorForm" label-width="130px" size="mini"
      label-position="right" style="margin-left: 10px;" v-loading="loading" element-loading-text=" 拼命加载中">
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="专业名称" prop="majorName">
            <el-input v-model="majorForm.majorName" maxlength="10" show-word-limit placeholder="请输入专业名称">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="学费（元/学期）" prop="fee">
            <el-input v-model="majorForm.fee" maxlength="8" show-word-limit placeholder="请输入学费">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="学制" prop="majorType">
            <el-input v-model="majorForm.majorType" maxlength="6" show-word-limit placeholder="请输入学制">
              <template slot="append">年</template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="所属专业" prop="apiId">
            <el-select v-model="majorForm.apiId" placeholder="请选择专业" class="wid100" @change="getAmount">
              <!-- <el-option  label="请选择(来源学校负责人)" value="0"></el-option> -->
              <el-option v-for="item in majorQuery" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="所属院校">
            <span>{{majorForm.collegeName}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="计划招生人数" prop="recruitAmount">
            <el-input v-model="majorForm.recruitAmount" maxlength="8" show-word-limit placeholder="请输入计划招生人数">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="专业目标">
            <span>{{amount}}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="培养目标" prop="cultivateAim">
            <el-input v-model="majorForm.cultivateAim" maxlength="15" show-word-limit placeholder="请输入培养目标">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="主要课程" prop="mainCourse">
            <el-input v-model="majorForm.mainCourse" maxlength="10" show-word-limit placeholder="请输入主要课程">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="就业方向" prop="careerForward">
            <el-input v-model="majorForm.careerForward" maxlength="15" show-word-limit placeholder="请输入就业方向"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="专业简介" prop="comment">
            <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
              :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload">
            </el-upload>
            <quill-editor ref="quillEditor" v-model="majorForm.comment" :options="editorSchoolOption"
              style="min-height: 200px;" @change="limitWordHandle($event)">
            </quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button align="center" style="width: 100px;" type="primary" @click="returnMajorList()">
          取消
        </el-button>
        <el-button align="center" style="width: 100px;" type="primary" @click="addOrEditMajorNews">
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
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  import config from '../../../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { queryMajorInfoByMajorId, addMajorInfo, updateMajorInfo, majorlist, queryCollegeInfoById } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js'
  export default {
    components: {
      quillEditor
    },
    name: 'majorEdit',
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
        amount: "",//专业目标数量
        msgCtLength: 0, //富文本框的输入字符的数量
        planId: '',
        majorId: '',
        apiId: '',
        collegeId: '',
        majorForm: {},
        majorQuery: [],
        editorSchoolOption: {
          placeholder: '请输入专业简介',
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
        majorForm: {},
        majorRules: {
          majorName: [
            { required: true, message: '请输入专业名称', trigger: 'blur' },
          ],
          fee: [
            { required: true, message: '请输入学费', trigger: 'blur' },
            {
              pattern: /^(([0-9]+)|([0-9]+\.[0-9]{0,2}))$/,
              message: '请输入最多两位小数的正数',
              trigger: 'blur'
            }
          ],
          majorType: [
            { required: true, message: '请输入学制', trigger: 'blur' },
          ],
          apiId: [
            { required: true, message: '请选择专业', trigger: 'change' },
          ],
          recruitAmount: [
            { required: true, message: '请输入计划招生人数', trigger: 'blur' },
            {
              pattern: /^\d+$/,
              message: '请输入正整数',
              trigger: 'blur'
            }
          ],
          cultivateAim: [
            { required: true, message: '请输入培养目标', trigger: 'blur' },
          ],
          mainCourse: [
            { required: true, message: '请输入主要课程', trigger: 'blur' },
          ],
          careerForward: [
            { required: true, message: '请输入就业方向', trigger: 'blur' },
          ],
          comment: [
            { required: true, message: '请输入专业简介', trigger: 'blur' },
          ],
        },
      }
    },
    created() {
      console.log(this.$route.params, 111);

      this.planId = this.$route.params.planId;
      this.majorId = this.$route.params.majorId;
      this.apiId = this.$route.params.apiId;
      this.collegeId = this.$route.params.collegeId;
      this.getData()
    },
    watch: {
      msgCtLength(val) {
        if (val > 1000) {
          this.planForm.comment = this.planForm.comment.substring(0, 1050)
        }
      }
    },
    methods: {
      getData() {
        this.majorlist();
        this.queryCollegeInfoById(this.collegeId)
        if (this.majorId != 'create') {
          this.loading = true
          let query = { majorId: this.majorId, planId: this.planId }
          queryMajorInfoByMajorId(query).then(res => {
            this.majorForm = res.data
            this.amount = this.getAmountById(this.majorForm.apiId, this.majorQuery)
            this.loading = false
          }).catch(err => {
            console.error(err);
            this.loading = false
          })
        }
      },
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
       * 展示编辑招生院校
       * @param collegeId{String} 院系ID
       * @author LiuGuoHao
       */
      queryCollegeInfoById(collegeId) {
        queryCollegeInfoById(collegeId).then(res => {
          this.majorForm.collegeName = res.data.collegeName
        })
      },
      /**
      * 根据apiId获取专业信息
      * @author LiuGuoHao
      */
      majorlist() {
        let api = { departId: this.apiId, planId: this.planId }
        majorlist(api).then(res => {
          this.majorQuery = res.data
        })
      },
      /**
      * 选择所属专业后对应的专业目标跟随变化
      * @author LiuGuoHao
      */
      getAmount(value) {
        this.amount = this.getAmountById(value, this.majorQuery)
        console.log(this.amount);

      },
      /**
       * 根据ID获取名称
       * @param id{number} 获取的名称所需要的id
       * @param query{array} 获取amount对应的数组
       * @author LiuGuoHao
       */
      getAmountById(id, query) {
        var obj = query.find(function (x) {
          return x.id == id;
        });
        return obj.amount
      },
      /**
      * 保存后新增或编辑
      * @author LiuGuoHao
      */
      addOrEditMajorNews() {
        if (this.msgCtLength > 1000) {
          this.$message.error("专业简介不可超过1000个字！")
        } else {
          this.$refs.majorForm.validate(valid => {
            if (valid) {
              this.$confirm(`确定要保存这些专业数据吗？`, '提示', {
                type: 'warning'
              })
                .then(() => {
                  if (this.majorId == 'create') {
                    this.addMajorInfo()
                  } else {
                    this.updateMajorInfo(this.majorId)
                  }
                })
            }
          })
        }
      },
      /**
      * 返回专业管理页面
      * @author LiuGuoHao
      */
      returnMajorList() {
        this.$confirm(`确定要返回专业管理吗？未保存的数据将会丢失！`, '提示', {
          type: 'warning'
        })
          .then(() => {
            this.$router.go(-1)
          })
      },
      /**
      * 新建专业
      * @author LiuGuoHao
      */
      addMajorInfo() {
        this.majorForm.collegeId = this.collegeId
        addMajorInfo(this.majorForm).then(() => {
          this.$message.success("新增院系专业成功！")
          this.$router.go(-1)
        })
      },
      /**
      * 编辑专业
      * @param majorId{number} 编辑对应的专业ID
      * @author LiuGuoHao
      */
      updateMajorInfo(majorId) {
        this.$refs.majorForm.validate(valid => {
          if (valid) {
            this.majorForm.majorId = majorId
            updateMajorInfo(this.majorForm).then(() => {
              this.$message.success("编辑院系专业成功！")
              this.$router.go(-1)
            })
          }
        })
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
  .form-card {
    margin-top: 10px;
  }

  .mrl10 {
    margin-left: 20px;
  }

  .wid100 {
    width: 100%;
  }

  .editor-word-limit {
    color: gray;
    font-size: 10px;
    float: left;
  }
</style>