<template>
  <div>
    <el-form class="form-card" :rules="planRules" ref="planForm" :model="planForm" label-width="120px" size="mini"
      label-position="right" v-loading="loading" element-loading-text=" 拼命加载中">
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="学期" prop="semesterId">
            <el-col :span="18">
              <el-select v-model="planForm.semesterId" class="wid100" placeholder="请选择学期" :disabled='isOrNotEdit'
                @change="queryChargerBySemesterId(true)">
                <el-option v-for="item in semesterQuery" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-col>
            <el-col :span="3">
              <el-button type="primary" @click="showAddSemesterDialog">新增学期</el-button>
            </el-col>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="季度选择" prop="season">
            <el-select v-model="planForm.season" class="wid100" placeholder="请选择季度" :disabled='isOrNotEdit'>
              <el-option v-for="item in seasonQuery" :key="item.seasonId" :label="item.season" :value="item.seasonId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-form-item label="标题" prop="title">
            <el-input v-model="planForm.title" placeholder="请输入标题" :disabled='isOrNotEdit' maxlength="25"
              show-word-limit>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="招生开始日期" prop="startTime">
            <el-date-picker class="wid100" format="yyyy年MM月dd日" value-format="yyyy-MM-dd HH:mm:ss"
              v-model="planForm.startTime" type="date" placeholder="选择开始日期时间" :disabled='isOrNotEdit'>
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="招生结束日期" prop="endTime">
            <el-date-picker class="wid100" format="yyyy年MM月dd日" value-format="yyyy-MM-dd HH:mm:ss"
              v-model="planForm.endTime" type="date" placeholder="选择结束日期时间" :disabled='isOrNotEdit'>
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="10">
          <el-form-item label="招生负责人" prop="chargerId">
            <el-select class="wid100" v-model="planForm.chargerId" placeholder="请选择(来源学校负责人)" :disabled='isOrNotEdit'>
              <!-- <el-option  label="请选择(来源学校负责人)" value="0"></el-option> -->
              <el-option v-for="item in chargerQuery" :key="item.chargerId" :label="item.name" :value="item.chargerId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="手机号码" prop="telephoneNumber">
            <el-input v-model="planForm.telephoneNumber" maxlength="11" show-word-limit placeholder="请输入手机号码"
              :disabled='isOrNotEdit'></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="20">
          <el-upload ref="uploadForEditor" v-show="false" class="avatar-uploader" action="/upload"
            :show-file-list="false" :http-request="uploadImgForEditor" :before-upload="beforeAvatarUpload"></el-upload>
          <el-form-item label="说明" prop="comment">
            <quill-editor ref="quillEditor" id="quillEditor" v-model="planForm.comment" :options="editorOption"
              style="min-height: 200px;" :disabled='isOrNotEdit' @change="limitWordHandle($event)">
            </quill-editor>
            <span class="editor-word-limit">{{msgCtLength}}/1000</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button :loading="btnLoading" align="center" style="width: 100px;" type="primary"
          @click="addOrEditPlanInfo()" v-if="!isOrNotEdit">
          保存
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 新增学期 -->
    <el-dialog title="新增学期" :close-on-click-modal="false" :visible.sync="addSemesterVisible" width="48%" center>
      <el-row type="flex" justify="center">
        <el-col :span="18">
          <el-form ref="addSemesterForm" :model="addSemesterForm" :rules="addSemesterFormRule" label-width="120px">
            <el-form-item label="学期名称" prop="name">
              <el-input class="input-custom" v-model.trim="addSemesterForm.name" placeholder="请输入学期名称" maxlength="20"
                show-word-limit style="width: 100%;"></el-input>
            </el-form-item>
            <el-form-item label="学期开始时间" prop="start">
              <el-date-picker style="width: 100%;" v-model="addSemesterForm.start" value-format="yyyy-MM-dd" type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="学期结束时间" prop="end">
              <el-date-picker style="width: 100%;" v-model="addSemesterForm.end" value-format="yyyy-MM-dd" type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row slot="footer" type="flex" justify="center">
        <el-button size="middle" @click="addSemesterVisible = false">取消</el-button>
        <el-button size="middle" type="primary" @click="saveSemester">保存</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>
<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import config from '../../../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { termlist } from '@/api/admin/API-Controller/APIController.js';
  import bus from '../../../../../common/bus';
  import { uploadAdd } from "@/api/admin/API-Controller/APIController";
  // import { addSemester } from '../../../../../../api/annualEnrollmentPlanManagement/enrollmentTargetList';
  import { term } from '@/api/admin/API-Controller/APIController.js';
  import { queryPlanInfo, addPlanInfo, addCollegeInfo, updatePlanInfo, queryChargerBySemesterId } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  const DAFULT_ADDSEMESTERFORM = {
    schoolId: "",
    semesterName: ""
  };
  export default {
    components: {
      quillEditor
    },
    name: "baseInfoAdd",
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
      var validate = (rule, value, callback) => {
        if (this.planForm.startTime > this.planForm.endTime) {
          callback(new Error('招生的结束时间不可早于开始时间！'));
        } else {
          callback();
        }
      };
      return {
        addSemesterForm: { ...DAFULT_ADDSEMESTERFORM },
        addSemesterVisible: false,
        btnLoading: false,
        loading: false,
        msgCtLength: 0, //富文本框的输入字符的数量
        isOrNotEdit: false,
        semesterQuery: [],
        schoolId: '',
        chargerQuery: [],
        seasonQuery: [
          {
            seasonId: 0,
            season: '春季'
          }, {
            seasonId: 1,
            season: '夏季'
          }, {
            seasonId: 2,
            season: '秋季'
          }, {
            seasonId: 3,
            season: '冬季'
          },
        ],
        addSemesterFormRule: {
          name: [
            { required: true, message: "请输入学期名称", trigger: "blur" }
          ],
          start: [
            { required: true, message: "请选择学期开始时间", trigger: "blur" }
          ],
          end: [
            { required: true, message: "请选择学期结束时间", trigger: "blur" }
          ]
        },
        //基本信息内容
        planForm: {},
        planRules: {
          semesterId: [
            { required: true, message: '请选择学期', trigger: 'change' },
          ],
          season: [
            { required: true, message: '请选择季节', trigger: 'change' },
          ],
          startTime: [
            { required: true, message: '请选择开始时间', trigger: 'blur' },
            { validator: validate, trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '请选择结束时间', trigger: 'blur' },
            { validator: validate, trigger: 'blur' }
          ],
          chargerId: [
            { required: true, message: '请选择负责人', trigger: 'blur' },
          ],
          telephoneNumber: [
            { required: true, message: '请输入手机号码', trigger: 'blur' },
            {
              pattern: /^[1]([3-9])[0-9]{9}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            }
          ],
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
          ],
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
    watch: {
      msgCtLength(val) {
        if (val > 1000) {
          this.planForm.comment = this.planForm.comment.substring(0, 1050)
        }
      }
    },
    props: {
      planId: { type: String, default: '' },
      type: { type: String, default: '' }
    },
    created() {
      this.schoolId = JSON.parse(localStorage.getItem("userInfo")).schoolid
      this.getData();
    },
    methods: {
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
      getData() {
        this.termlist("queryPlanInfo");
        // this.queryPlanInfo();
        if (this.type == "see") {
          this.isOrNotEdit = true;
        }
      },
      /**
       * 根据planId获取基本信息数据
       * @author LiuGuoHao
       */
      queryPlanInfo() {
        if (this.planId != "add") {
          this.loading = true
          queryPlanInfo(this.planId).then(res => {
            this.planForm = res.data
            this.loading = false
            this.queryChargerBySemesterId(false)
          }).catch(err => {
            console.error(err);
            this.loading = false
          })
        }
      },
      /**
       * 根据ID获取名称
       * @param id{number} 获取的名称所需要的id
       * @param query{array} 需要获取的name对应的数组
       * @author LiuGuoHao
       */
      getNameById(id, query) {
        var obj = query.find(function (x) {
          return x.id == id;
        });
        return obj.name
      },
      /**
       * 根据学校ID获取学期信息
       * @param {function} callback 获取学期信息后所需要调用的方法
       * @author lgh
       */
      termlist(callback = '') {
        termlist(this.schoolId).then(res => {
          this.semesterQuery = res.data.data
          if (callback) {
            this[callback]()
          }
          console.log(this.semesterQuery, "==>term");
        })
      },
      /**
       * 新增学期按钮
       * @author lgh
       */
      showAddSemesterDialog() {
        //初始化数据
        this.elFormReset("addSemesterForm");
        this.addSemesterForm = { ...DAFULT_ADDSEMESTERFORM };
        //显示弹窗框
        this.addSemesterVisible = true;
      },
      /**
       * 新增学期
       * @param data 学期名称、学校id
       * @author lgh
       */
      saveSemester() {
        // this.addSemesterForm.schoolId = this.schoolId.schoolid;
        //表单验证
        this.$refs.addSemesterForm.validate(valid => {
          //表单有数据执行
          if (valid) {
            let data = { ...this.addSemesterForm };
            term(data).then(res => {
              this.$message.success("保存学期成功！");
              //隐藏弹出框
              this.addSemesterVisible = false;
              //刷新学期列表
              this.termlist();
            });
          }
        });
      },
      /**
      * 根据ID获取开始时间和结束时间
      * @param id{number} 获取的名称所需要的id
      * @param query{array} 需要获取的name对应的数组
      * @author LiuGuoHao
      */
      getTimeById(id, query) {
        var obj = query.find(function (x) {
          return x.id == id;
        });
        let time = { start: obj.start, end: obj.end, semesterName: obj.name }
        return time
      },
      /**
       * 查询基本信息的负责人列表
       * @param isClick{boolean} 判断是否为选择学期后获取的负责人列表
       * @author LiuGuoHao
       */
      queryChargerBySemesterId(isClick) {
        let semesterInfo = this.getTimeById(this.planForm.semesterId, this.semesterQuery)
        this.planForm.semesterEnd = semesterInfo.end
        this.planForm.semesterStart = semesterInfo.start
        this.planForm.semester = semesterInfo.semesterName
        let semesterId = {
          semesterId: this.planForm.semesterId
        }
        queryChargerBySemesterId(semesterId).then(res => {
          this.chargerQuery = res.data
          if (isClick) {
            //设置默认值
            if (this.chargerQuery[0] != undefined) {
              this.planForm.chargerId = this.chargerQuery[0].chargerId
              this.planForm = { ...this.planForm }
            } else {
              this.planForm.chargerId = ""
              this.planForm = { ...this.planForm }
            }
          }
        }).catch(err => {
          console.error(err);
          this.loading = false
        })
      },
      /**
       * 添加招生院系信息
       * @param planId{number} 添加招生院系下的planId
       * @author LiuGuoHao
       */
      addCollegeInfo(planId) {
        addCollegeInfo(planId).then(ref => {
          this.queryCollegeInfo(this.planId);  //查询院系列表
        })
      },
      /**
       * 添加基本信息
       * @author LiuGuoHao
       */
      addOrEditPlanInfo() {
        if (this.msgCtLength > 1000) {
          this.$message.error("说明不可以超过1000个字！")
        } else {
          this.$refs.planForm.validate(valid => {
            if (valid) {
              this.$confirm(`确定要保存该基本信息吗？`, '提示', {
                type: 'warning'
              })
                .then(() => {
                  this.btnLoading = true
                  if (this.type == "creat") {
                    this.planForm.semester = this.getNameById(this.planForm.semesterId, this.semesterQuery);
                    addPlanInfo(this.planForm).then(res => {
                      this.planId = res.data
                      this.$message.success("新增信息成功！")
                      this.btnLoading = false
                      this.$emit("plan-created", "true", `${this.planId}`)
                    })
                  } else if (this.type == "edit") {
                    this.planForm.planId = this.planForm.planId.toString()
                    this.planForm.semester = this.getNameById(this.planForm.semesterId, this.semesterQuery);
                    updatePlanInfo(this.planForm).then(res => {
                      this.$message.success("编辑信息成功！")
                      this.btnLoading = false
                      this.$emit("plan-created", "true", `${this.planId}`)
                    })
                  }
                })
            }
          });
        }
      },
      /**
       * 富文本框消息内容的长度限制1000个字符
       * @param {*} event
       * @author LGH
       */
      limitWordHandle(event) {
        this.msgCtLength = event.text.length - 1;
        event.quill.deleteText(1000, 4);
        //使用粘贴功能时触发
        // let result = true
        // document.getElementById("quillEditor").onpaste = function (e) {
        //   if (!(e.clipboardData && e.clipboardData.items)) {
        //     return;
        //   }
        //   for (var i = 0, len = e.clipboardData.items.length; i < len; i++) {
        //     var item = e.clipboardData.items[i];
        //     if (item.kind === "string") {
        //       item.getAsString(function (str) {
        //         console.log(str);
        //         if (str.indexOf("<img") != -1) {
        //           result = false
        //         }
        //       })
        //     } else if (item.kind === "file") {
        //       var f = item.getAsFile();
        //       if (f.indexOf("<img") != -1) {
        //         result = false
        //       }
        //       console.log(f, "111111111111112");
        //     }
        //     console.log(result,"===>>>>???");
            
        //   }
        //   return result
        // }
      },
    },
  }
</script>

<style scoped>
  .form-card {
    margin-top: 10px;
  }

  .wid100 {
    width: 100%;
  }

  .editor-word-limit {
    color: gray;
    font-size: 10px;
  }
</style>