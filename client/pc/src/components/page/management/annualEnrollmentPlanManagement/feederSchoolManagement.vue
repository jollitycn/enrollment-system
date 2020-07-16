<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>生源学校管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="handle-box">
      <el-input v-model.trim="query.searchKey" maxlength="32" @keypress.enter.native="handleSearch"
        placeholder="请输入学校名称" class="handle-input mr10"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
      <el-button type="primary" style="float: right;" @click="showBatchImportDialog" icon="el-icon-upload2">批量导入
      </el-button>
      <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="showAddDialog">新增学校</el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%" v-loading="loading" element-loading-text="拼命加载中">
        <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
        <el-table-column prop="sourceSchoolName" label="学校名称" width="270" align="center"></el-table-column>
        <el-table-column prop="province" label="所在省份" align="center"></el-table-column>
        <el-table-column prop="city" label="所在城市" align="center"></el-table-column>
        <el-table-column prop="district" label="所在区域" align="center"></el-table-column>
        <el-table-column label="操作" width="380" align="center">
          <template slot-scope="scope">
            <el-button type="primary" @click="showEditDialog(scope.row)">维护基本信息</el-button>
            <el-button type="primary" @click="showRelationDialog(scope.row.sourceSchoolId)">维护公关信息</el-button>
            <el-button type="danger" @click="handleDelete(scope.row.sourceSchoolId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
          :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
          @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
      </div>

      <!-- 新增学校 -->
      <el-dialog title="新增学校" :close-on-click-modal="false" :visible.sync="addDialogVisible" width="40%" center>
        <el-form ref="addFormSchool" :model="schoolform" :rules="schoolformRule" label-width="80px">
          <el-form-item label="学校名称" prop="sourceSchoolName">
            <el-input v-model.trim="schoolform.sourceSchoolName" maxlength="20" show-word-limit placeholder="请输入学校名称"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="学校类型" prop="typeIds">
            <el-checkbox-group v-model="schoolform.typeIds" placeholder="请选择">
              <el-checkbox v-for="item in schoolTypes" :key="item.dictionaryId" :label="item.dictionaryId">
                {{item.dataValue}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="生源规模" prop="sourceScale">
            <el-input-number v-model="schoolform.sourceScale" :precision="0" :min="0" :max="10000000" size="medium">
            </el-input-number>&emsp;人
          </el-form-item>
          <el-form-item label="学校性质" prop="relationType">
            <el-select v-model="schoolform.relationType" placeholder="请选择">
              <el-option v-for="item in saleTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学校资质" prop="qualification">
            <el-input v-model.trim="schoolform.qualification" maxlength="30" show-word-limit placeholder="请输入学校资质"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="主管部门" prop="superiorDept">
            <el-input v-model.trim="schoolform.superiorDept" maxlength="30" show-word-limit placeholder="请输入主管部门"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="学校地址" prop="address">
            <Address v-model="regions" />
            <el-input v-model.trim="schoolform.address" placeholder="请输入详细地址" maxlength="30" show-word-limit
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="说明" prop="comment">
            <el-input type="textarea" v-model="schoolform.comment" placeholder="请输入详细说明" resize="vertical" :rows="4"
              maxlength="200" show-word-limit></el-input>
          </el-form-item>
        </el-form>
        <el-row slot="footer" type="flex" justify="center">
          <el-button size="middle" type="primary" @click="save(0)">保存</el-button>
          <el-button size="middle" @click="addDialogVisible = false">关闭</el-button>
        </el-row>
      </el-dialog>

      <!-- 基本信息维护 -->
      <el-dialog title="基本信息维护" :close-on-click-modal="false" :visible.sync="editDialogVisible" width="40%" center>
        <el-form ref="editFormSchool" :model="schoolform" :rules="schoolformRule" label-width="80px"
          v-loading="dialogLoading" element-loading-text="拼命加载中">
          <el-form-item label="学校名称" prop="sourceSchoolName">
            <el-input v-model.trim="schoolform.sourceSchoolName" maxlength="20" show-word-limit placeholder="请输入学校名称"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="学校类型" prop="typeIds">
            <el-checkbox-group v-model="schoolform.typeIds" placeholder="请选择">
              <el-checkbox v-for="item in schoolTypes" :key="item.dictionaryId" :label="item.dictionaryId">
                {{item.dataValue}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="生源规模" prop="sourceScale">
            <el-input-number v-model="schoolform.sourceScale" :min="0" size="medium"></el-input-number>&emsp;人
          </el-form-item>
          <el-form-item label="学校性质" prop="relationType">
            <el-select v-model="schoolform.relationType" placeholder="请选择">
              <el-option v-for="item in saleTypeList" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学校资质" prop="qualification">
            <el-input v-model.trim="schoolform.qualification" maxlength="30" show-word-limit placeholder="请输入学校资质"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="主管部门" prop="superiorDept">
            <el-input v-model.trim="schoolform.superiorDept" maxlength="30" show-word-limit placeholder="请输入主管部门"
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="学校地址" prop="address">
            <Address v-model="regions" />
            <el-input v-model.trim="schoolform.address" placeholder="请输入详细地址" maxlength="30" show-word-limit
              :clearable="true"></el-input>
          </el-form-item>
          <el-form-item label="说明" prop="comment">
            <el-input type="textarea" v-model.trim="schoolform.comment" placeholder="请输入详细说明" resize="vertical"
              :rows="4" maxlength="200" show-word-limit></el-input>
          </el-form-item>
        </el-form>
        <el-row slot="footer" type="flex" justify="center">
          <el-button size="middle" @click="editDialogVisible = false">关闭</el-button>
          <el-button size="middle" type="primary" @click="save(1)">保存</el-button>
        </el-row>
      </el-dialog>

      <!-- 维护公关信息 -->
      <el-dialog title="公关信息维护" :close-on-click-modal="false" :visible.sync="relationDialogVisible" width="40%" center>
        <el-form ref="relationForm" :model="relationForm" :rules="relationFormRule" label-width="110px"
          v-loading="dialogLoading" element-loading-text="拼命加载中">
          <!-- <el-form-item label="是否公关"
                        prop="isRelation">
            <el-radio-group v-model="relationForm.isRelation">
              <el-radio v-for="item in isRelation"
                        :key="item.value"
                        :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>-->
          <el-form-item label="跟进等级" prop="relationLevel">
            <el-select v-model="relationForm.relationLevel" placeholder="请选择">
              <el-option v-for="item in relationLevel" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="中招人数" prop="recruitAmount">
            <el-input-number v-model="relationForm.recruitAmount" :min="0" :precision="0" :max="9999" size="medium">
            </el-input-number>&emsp;人
          </el-form-item>
          <el-form-item label="中招方式" prop="recruitWay">
            <el-select v-model="relationForm.recruitWay" multiple placeholder="请选择">
              <el-option v-for="item in recruitWay" :key="item.value" :label="item.label" :value="item.label">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="年级主任" prop="gradeDirectorName">
            <el-input v-model.trim="relationForm.gradeDirectorName" placeholder="请输入年级主任名称" maxlength="20"
              show-word-limit></el-input>
          </el-form-item>
          <el-form-item label="年级主任电话" prop="gradeDirectorTel">
            <el-input v-model.trim="relationForm.gradeDirectorTel" placeholder="请输入年级主任电话" maxlength="11"
              show-word-limit></el-input>
          </el-form-item>
          <el-form-item label="班级数量" prop="classAmount">
            <el-input-number v-model="relationForm.classAmount" :min="0" :precision="0" :max="99999999" size="medium">
            </el-input-number>&emsp;个
          </el-form-item>
          <el-form-item label="学校情况说明" prop="schoolCaseComment">
            <el-input type="textarea" v-model.trim="relationForm.schoolCaseComment" placeholder="请输入学校情况说明"
              resize="vertical" :rows="4" maxlength="200" show-word-limit></el-input>
          </el-form-item>
        </el-form>
        <el-row slot="footer" type="flex" justify="center">
          <el-button size="middle" @click="relationDialogVisible = false">关闭</el-button>
          <el-button size="middle" type="primary" @click="saveRelationForm">保存</el-button>
        </el-row>
      </el-dialog>

      <el-dialog title="批量导入生源学校" :close-on-click-modal="false" :visible.sync="batchImportDialogVisible" width="40%"
        center>
        <el-form ref="relationForm" :model="relationForm" :rules="relationFormRule" label-width="80px">
          <el-form-item label="第一步：">
            <el-button @click="downloadTemplate" icon="el-icon-download">下载模板</el-button>
          </el-form-item>
          <el-form-item label="第二步：">
            <el-upload class="upload-demo" action="/" :http-request="uploadRequest" :before-upload="validUploadFile"
              :file-list="fileList" ref="upload"
              accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
              <el-button icon="el-icon-upload2">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传xls文件，且不超过5M</div>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-row slot="footer" type="flex" justify="center">
          <el-button size="middle" @click="closeImportDialog">关闭</el-button>
          <el-button size="middle" type="primary" @click="closeImportDialog">完成</el-button>
        </el-row>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {
    listSourceSchoolPage,
    deleteSourceSchool,
    addSourceSchool,
    updateSourceSchool,
    listSourceSchoolType,
    getSourceSchoolDetail,
    excelImport,
    getExportSchoolTemplateUrl
  } from "../../../../api/annualEnrollmentPlanManagement/feederSchoolManagement";

  import Address from "@/components/common/Address";

  const DEFAULT_SCHOOLFORM = {
    sourceSchoolId: "",
    province: "",
    city: "",
    district: "",
    typeIds: [],
    sourceSchoolName: "",
    sourceScale: "",
    relationType: "",
    qualification: "",
    address: "",
    superiorDept: "",
    comment: ""
  };

  const DEFAULT_RELATIONFORM = {
    sourceSchoolId: "",
    sourceSchoolName: "",
    isRelation: 0,
    relationLevel: "",
    schoolCaseComment: "",
    recruitAmount: "",
    recruitWay: [],
    gradeDirectorName: "",
    gradeDirectorTel: "",
    classAmount: ""
  };
  export default {
    name: "feederSchoolManagement",
    components: {
      Address
    },
    data() {
      return {
        /**-------- common -----------*/
        saleTypeList: [
          {
            label: "公立",
            value: 0
          },
          {
            label: "民营",
            value: 1
          }
        ],
        loading: false,
        dialogLoading: false,
        schoolTypes: [],
        recruitWay: [
          {
            label: "宣讲",
            value: 0
          },
          {
            label: "班主任指导",
            value: 1
          },
          {
            label: "发宣传册",
            value: 2
          }
        ],
        isRelation: [
          {
            label: "是",
            value: 0
          },
          {
            label: "否",
            value: 1
          }
        ],
        relationLevel: [
          {
            label: "重点公关",
            value: 0
          },
          {
            label: "普通公关",
            value: 1
          },
          {
            label: "重新公关",
            value: 2
          }
        ],
        /**-------- 生源学校列表 -----------*/
        pageTotal: 0,
        query: {
          searchKey: "",
          pageNum: 1,
          pageSize: 10
        },
        tableData: [],

        /**-------- 新增生源学校 -----------*/
        //显示新增弹框
        addDialogVisible: false,
        //新增生源学校
        schoolform: { ...DEFAULT_SCHOOLFORM },
        //地址
        regions: [],
        //新增学校校验规则
        schoolformRule: {
          sourceSchoolName: [
            { required: true, message: "请输入学校名称", trigger: "blur" }
          ],
          district: [{ required: true, message: "请选择区域", trigger: "blur" }],
          address: [
            { required: true, message: "请输入详细地址", trigger: "blur" }
          ],
          typeIds: [
            { required: true, message: "请选择学校类型", trigger: "blur" }
          ],
          sourceScale: [
            { required: true, message: "请输入学校规模", trigger: "blur" }
          ],
          relationType: [
            { required: true, message: "请选择学校性质", trigger: "blur" }
          ],
          qualification: [
            { required: true, message: "请输入学校资质", trigger: "blur" }
          ],
          superiorDept: [
            { required: true, message: "请输入主管部门", trigger: "blur" }
          ]
        },
        /**-------- 编辑生源学校 -----------*/
        //编辑学校
        editDialogVisible: false,

        /**-------- 维护公关信息 -----------*/
        relationDialogVisible: false,
        //维护公关信息
        relationForm: { ...DEFAULT_RELATIONFORM },
        relationFormRule: {
          relationLevel: [
            { required: true, message: "请选择跟进等级", trigger: "blur" }
          ],
          recruitAmount: [
            { required: true, message: "请输入中招人数", trigger: "blur" }
          ],
          recruitWay: [
            {
              type: "array",
              required: true,
              message: "请选择中招方式",
              trigger: "blur"
            }
          ],
          gradeDirectorName: [
            { required: true, message: "请输入年级主任名称", trigger: "blur" }
          ],
          gradeDirectorTel: [
            { required: true, message: "请输入年级主任电话", trigger: "blur" },
            {
              pattern: /^[1]([3-9])[0-9]{9}$/,
              message: '请输入正确的年级主任手机号码',
              trigger: 'blur'
            }
          ],
          classAmount: [
            { required: true, message: "请输入班级数量", trigger: "blur" }
          ]
        },
        /**-------- 批量导入 -----------*/
        batchImportDialogVisible: false,
        fileList: []
      };
    },
    created() {
      //默认查询第一页生源学校数据
      this.handleSearch();
      //生源学校类型
      this.listSourceSchoolType();
    },
    methods: {
      /*----------------- common ----------------*/
      /**
       * 学校类型列表
       * @author lmh
       */
      listSourceSchoolType() {
        this.loading = true
        listSourceSchoolType().then(res => {
          this.schoolTypes = res.data;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        });
      },

      /*----------------- 分页列表 ----------------*/
      /**
       * 获取生源学校分页列表
       * @author lml
       */
      getData() {
        this.loading = true
        listSourceSchoolPage(this.query)
          .then(res => {
            this.tableData = res.data.records;
            this.pageTotal = res.data.total || 0;
            this.loading = false
          })
          .catch(err => {
            console.error(err);
            this.loading = false
          });
      },
      /**
       * 查询第一页数据
       * @author lmh
       */
      handleSearch() {
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },
      /**
       * 分页导航
       * @param val {number} 当前选择页码
       * @author lmh
       */
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getData();
      },
      /**
       * 每页条数改变
       * @param val {number} 每页数量
       * @author lmh
       */
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getData();
      },

      /*----------------- 批量导入生源学校基本信息 ----------------*/
      /**
       * 显示批量导入数据弹框
       * @author lmh
       */
      showBatchImportDialog() {
        //数据初始化

        //显示弹框
        this.batchImportDialogVisible = true;
      },
      /**
       * 批量导入数据
       * @author lmh
       */
      downloadTemplate() {
        getExportSchoolTemplateUrl({}).then(res => {
          //获取到模板地址
          const templateUrl = res.data;
          //模拟触发h5的download功能下载
          const link = document.createElement("a");
          link.style.display = "none";
          link.href = templateUrl;
          //下载的文件名
          link.download = "生源学校-导入模板";
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);
          // this.$message.success('已成功下载导入模板');
        });
      },
      /**
       * 校验上传文件的格式和大小等
       * @param file 当前上传文件封装对象
       * file.type 文件类型，格式如下
       * xlsx格式:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
       * xls格式:application/vnd.ms-excel
       *
       * file.size 文件大小,单位B
       * @author lmh
       */
      validUploadFile(file) {
        console.log("上传之前：", file);
        //xlsx格式:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
        const xlsx =
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        const xls = "application/vnd.ms-excel";
        //限制5M
        const size = 5 * 1024 * 1024;
        // console.log("文件类型", file.type, ",type==", file.type === xlsx);
        // if (!(file.type === xls || file.type === xlsx)) {
        //   this.$message.warning("上传文件类型必须是xls或者xlsx格式");
        //   return false;
        // }
        let type = file.name.substring(file.name.lastIndexOf('.') + 1,file.name.length)
        let arr = ['xls','xlsx']
        if(arr.indexOf(type) === -1){
          return false
        }
        console.log(type,99)
        if (file.size > size) {
          this.$message.warning("上传文件大小不能超过5M");
          return false;
        }
        return true;
      },
      /**
       * 上传excel
       * @param file {object}
       * file.file 源文件
       * @author lmh
       */
      uploadRequest(file) {
        let formData = new FormData();
        formData.append("file", file.file);
        // let type = file.name.substring(file.name.indexOf('.') + 1, file.name.length);
        // formData.append('fileType', type);
        excelImport(formData)
          .then(res => {
            console.log("上传成功返回数据：", res);
            //上传完成
            this.$message.success("上传成功！");
            //刷新列表数据
            this.handleSearch();
          })
          .catch(err => {
            console.error(err);
            this.fileList.pop();
          });
      },
      /**
       * 上传文件后点击关闭与完成后关闭弹出框，并清除上传文件的列表信息
       * @author lgh
       */
      closeImportDialog() {
        this.batchImportDialogVisible = false;
        this.$refs.upload.clearFiles();
      },
      /*----------------- 新增生源学校 ----------------*/
      /**
       * 显示新增弹框
       * @author lmh
       */
      showAddDialog() {
        //清空数据
        this.elFormReset("addFormSchool");
        this.schoolform = { ...DEFAULT_SCHOOLFORM };
        //地址不在form表单内，特殊处理清空
        this.regions = [];
        //显示弹出框
        this.addDialogVisible = true;
      },
      /**
       * 保存学校数据
       * @param type {int} 类型，0,新增，1，编辑
       * @author lmh
       */
      save(type = 0) {
        const refs = ["addFormSchool", "editFormSchool"];
        console.log("type==", type, "ref:", refs[type]);
        if (this.regions && this.regions.length) {
          this.schoolform.province = this.regions[0];
          this.schoolform.city = this.regions[1];
          this.schoolform.district = this.regions[2];
        }
        const data = {
          ...this.schoolform
        };
        console.log("----------", this.schoolform);
        this.$refs[refs[type]].validate(valid => {
          if (valid) {
            if (type === 1) {
              this.editSourceSchool(data);
            } else {
              this.addSourceSchool(data);
            }
          }
        });
      },
      /**
       * 新增学校
       * @param data {Oject} 填写的学校信息
       * @author lmh
       */
      addSourceSchool(data) {
        addSourceSchool(data)
          .then(res => {
            this.$message.success(`新增学校成功`);
            this.addDialogVisible = false;
            this.handleSearch();
          })
          .catch(err => {
            console.log(err);
          });
      },

      /*----------------- 维护生源学校基本信息 ----------------*/
      /**
       * 显示编辑弹框
       * @param row {string} 当前选择行的数据
       * @author lmh
       */
      showEditDialog(row) {
        console.log("编辑行数据：", row);
        //初始化数据
        this.elFormReset("editFormSchool");
        this.schoolform = { ...DEFAULT_SCHOOLFORM };
        //获取详情数据
        this.getSourceSchoolDetail(row.sourceSchoolId).then(info => {
          //信息处理
          const {
            sourceSchoolId,
            province,
            city,
            district,
            sourceSchoolName,
            sourceScale,
            relationType,
            qualification,
            address,
            superiorDept,
            comment,
            schoolTypeList
          } = info;
          //schoolTypeList 学校类型列表需要特殊处理
          let typeIds = [];
          if (schoolTypeList && schoolTypeList.length) {
          }
          typeIds = schoolTypeList.map(item => {
            return item.dictionaryId;
          });
          const sclool = {
            sourceSchoolId,
            province,
            city,
            district,
            sourceSchoolName,
            sourceScale,
            relationType,
            qualification,
            address,
            superiorDept,
            comment,
            typeIds
          };
          this.schoolform = sclool;
        });
        //地址不在form表单内，特殊处理
        this.regions = [row.province, row.city, row.district];
        //显示弹出框
        this.editDialogVisible = true
      },
      /**
       * 编辑学校
       * @param data {Oject} 填写的学校信息
       * @author lmh
       */
      editSourceSchool(data) {
        updateSourceSchool(data)
          .then(res => {
            this.$message.success(`信息维护成功`);
            this.editDialogVisible = false;
            this.relationDialogVisible = false;
            this.handleSearch();
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**
       * 获取生源学校基础信息
       * @param sourceSchoolId {string} 学校id
       * @returns
       * Promise  resolve(info)  info ==> 学校信息
       * @author lmh
       */
      getSourceSchoolDetail(sourceSchoolId) {
        this.dialogLoading = true
        return new Promise((resolve, reject) => {
          getSourceSchoolDetail(sourceSchoolId).then(res => {
            resolve(res.data);
            this.dialogLoading = false
          }).catch(err => {
            console.error(err);
            this.dialogLoading = false
          });
        });
      },

      /*----------------- 维护生源学校公关信息 ----------------*/
      /**
       * 显示公关信息弹框
       * @param sourceSchoolId {string} 学校id
       * @author lmh
       */
      showRelationDialog(sourceSchoolId) {
        // 初始化数据
        this.elFormReset("relationForm");
        this.relationForm = { ...DEFAULT_RELATIONFORM };
        this.dialogLoading = true
        this.getSourceSchoolDetail(sourceSchoolId).then(info => {
          // 数据处理
          const {
            sourceSchoolId,
            sourceSchoolName,
            isRelation,
            relationLevel,
            schoolCaseComment,
            recruitAmount,
            gradeDirectorName,
            gradeDirectorTel,
            classAmount,
            recruitWay
          } = info;
          //中招方式多选，特殊处理,逗号分隔
          let recruitWay2 = "";
          if (recruitWay && recruitWay.length) {
            recruitWay2 = recruitWay.split(",");
          }
          const relationInfo = {
            sourceSchoolId,
            sourceSchoolName,
            isRelation,
            relationLevel,
            schoolCaseComment,
            recruitAmount,
            gradeDirectorName,
            gradeDirectorTel,
            classAmount,
            recruitWay: recruitWay2
          };
          console.log("详情数据：", info);
          this.relationForm = relationInfo;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });

        //显示弹框
        this.relationDialogVisible = true;
      },

      /**
       * 保存公关信息
       * @author lmh
       */
      saveRelationForm() {
        let data = { ...this.relationForm };
        this.$refs["relationForm"].validate(valid => {
          if (valid) {
            //请求数据特殊处理
            if (data.recruitWay && data.recruitWay.length) {
              data.recruitWay = data.recruitWay.join(",");
            }
            this.editSourceSchool(data);
          }
        });
      },

      /*----------------- 删除生源学校 ----------------*/
      /**
       * 删除操作
       *  @param sourceSchoolId {string} 学校id
       * @author lmh
       */
      handleDelete(sourceSchoolId) {
        // 二次确认删除
        this.$confirm("确定要删除吗？", "提示", {
          type: "warning"
        })
          .then(() => {
            this.deleteSourceSchool(sourceSchoolId);
          })
          .catch(() => { });
      },
      /**
       * 删除请求
       * @param sourceSchoolId {string} 学校id
       * @author lmh
       */
      deleteSourceSchool(sourceSchoolId) {
        deleteSourceSchool(sourceSchoolId)
          .then(res => {
            console.log(res);
            this.$message.success("删除成功");
            this.getData();
          })
          .catch(err => {
            console.error("删除生源学校：", err);
            // this.$message.error("删除失败");
          });
      }
    }
  };
</script>

<style scoped>
  .handle-box {
    margin-top: 20px;
    margin-bottom: 20px;
  }

  .handle-input {
    width: 195px;
    display: inline-block;
  }

  .mr10 {
    margin-right: 10px;
  }

  >>>.el-upload--text {
    height: auto;
    width: auto;
    border: none;
  }
</style>