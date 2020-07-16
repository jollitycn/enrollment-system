<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/enrollmentManagement'}">招生区域管理</el-breadcrumb-item>
        <el-breadcrumb-item>编辑区域</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 20px;">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="区域编辑" name="first">
          <el-form :rules="regionformRule" ref="region" :model="regionform" label-width="80px" v-loading="loading"
            element-loading-text="拼命加载中">
            <el-row>
              <el-col :span="12">
                <el-form-item label="学期:" prop="semester">
                  <el-select v-model="regionform.semester" placeholder="请选择" class="width100" disabled>
                    <el-option v-for="item in semester" :key="item.id" :label="item.name" :value="item.name">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="标题:" prop="title">
                  <el-input v-model="regionform.title" maxlength="30" show-word-limit></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="说明:">
                  <el-input type="textarea" v-model="regionform.comment"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button type="primary" @click="save">保 存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="小组列表" name="second">
          <div class="handle-box">
            <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="clearn">新建组别</el-button>
          </div>
          <!-- 区域列表 -->
          <div style="margin-top: 20px;">
            <el-table :data="tableData" style="width: 100%;" v-loading="loading" element-loading-text="拼命加载中">
              <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
              <el-table-column prop="name" label="组别名称" align="center"></el-table-column>
              <el-table-column prop="countManager" label="小组人数" align="center"></el-table-column>
              <el-table-column prop="countSourceSchool" label="负责学校数" align="center"></el-table-column>
              <el-table-column prop="creatorName" label="创建人" align="center"></el-table-column>
              <el-table-column prop="createTime" label="创建时间" align="center"></el-table-column>
              <el-table-column label="操作" width="400" align="center">
                <template slot-scope="scope">
                  <el-button type="primary" @click="seeuser(scope.row.groupId)">查看</el-button>
                  <el-button type="primary" @click="getGroupDetail(scope.row.groupId)">编辑</el-button>
                  <el-button type="primary" @click="getuser(scope.row.groupId)">组员管理</el-button>
                  <el-button type="primary" @click="getschool(scope.row.groupId)">学校管理</el-button>
                  <el-button type="danger" @click="handleDelete(scope.$index, scope.row.groupId)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="pagination">
              <el-pagination background layout="sizes, prev, pager, next,total" :current-page.sync="query.pageNum"
                :page-size="query.pageSize" :total="pageTotal" :page-sizes="[10, 20, 50, 100]"
                @current-change="handlePageChange" @size-change="handleSizeChange"></el-pagination>
            </div>
            <!-- 新建组 -->
            <el-dialog :visible.sync="editVisible" width="43%" center>
              <el-form :rules="groupInfoformRule" ref="groupInfoform" :model="groupInfoform" label-width="80px">
                <el-form-item label="招生区域" prop="areaId">
                  <el-select style="width: 100%;" v-model="groupInfoform.areaId" placeholder="请选择">
                    <el-option v-for="item in areaId" :key="item.dictionaryId" :label="item.dataValue"
                      :value="item.dictionaryId"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="所属岗位" prop="deptId">
                  <el-select style="width: 100%;" v-model="groupInfoform.deptId" placeholder="请选择">
                    <el-option v-for="item in deptName" :key="item.schoolId" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="招生目标" prop="targetAmount">
                  <!-- <el-input-number :min="1" v-model="groupInfoform.targetAmount"></el-input-number> -->
                  {{groupInfoform.targetAmount}}
                </el-form-item>
                <el-form-item label="组别名称" prop="name">
                  <el-input v-model="groupInfoform.name" maxlength="30" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="说明" prop="comment">
                  <el-input type="textarea" v-model="groupInfoform.comment" maxlength="2000" show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="saveadd">确 认</el-button>
                  <el-button @click="editVisible = false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
            <!-- 编辑组 -->
            <el-dialog :visible.sync="editVisible3" width="43%" center>
              <el-form :rules="groupInfoformRule" ref="groupInfo" :model="groupInfoforma" label-width="80px"
                v-loading="dialogLoading" element-loading-text="拼命加载中">
                <el-form-item label="招生区域" prop="areaId">
                  <el-select style="width: 100%;" v-model="groupInfoforma.areaId" placeholder="请选择">
                    <el-option v-for="item in areaId" :key="item.dictionaryId" :label="item.dataValue"
                      :value="item.dictionaryId"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="所属岗位" prop="deptId">
                  <el-select style="width: 100%;" v-model="groupInfoforma.deptId" placeholder="请选择">
                    <el-option v-for="item in deptName" :key="item.schoolId" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="招生目标" prop="targetAmount">
                  <!-- <el-input-number min="1" v-model="groupInfoforma.targetAmount"></el-input-number> -->
                  {{groupInfoforma.targetAmount}}
                </el-form-item>
                <el-form-item label="组别名称" prop="name">
                  <el-input v-model="groupInfoforma.name" maxlength="30" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="说明" prop="comment">
                  <el-input type="textarea" v-model="groupInfoforma.comment" maxlength="2000" show-word-limit></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="saveedit">确 认</el-button>
                  <el-button @click="editVisible3 = false">取消</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
            <!-- 查看组 -->
            <el-dialog :visible.sync="editVisible4" width="30%" center>
              <el-form ref="groupInfo" :model="groupInfoforma" label-width="80px" v-loading="dialogLoading"
                element-loading-text="拼命加载中">
                <el-form-item label="招生区域" prop="areaId">
                  <el-select disabled v-model="groupInfoforma.areaId" placeholder="请选择">
                    <el-option v-for="item in areaId" :key="item.dictionaryId" :label="item.dataValue"
                      :value="item.dictionaryId"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="所属岗位" prop="deptId">
                  <el-select disabled v-model="groupInfoforma.deptId" placeholder="请选择">
                    <el-option v-for="item in deptName" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="招生目标" prop="targetAmount">
                  <!-- <el-input-number min="1" :disabled="true" v-model="groupInfoforma.targetAmount"></el-input-number> -->
                  {{groupInfoforma.targetAmount}}
                </el-form-item>
                <el-form-item label="组别名称" prop="name">
                  <el-input :disabled="true" v-model="groupInfoforma.name" maxlength="30" show-word-limit></el-input>
                </el-form-item>
                <el-form-item label="说明" prop="comment">
                  <el-input :disabled="true" type="textarea" v-model="groupInfoforma.comment" maxlength="2000" show-word-limit>
                  </el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="editVisible4 = false">关 闭</el-button>
                </el-form-item>
              </el-form>
            </el-dialog>
            <!-- 组员管理 -->
            <el-dialog :visible.sync="editVisible1" width="50%" center>
              <el-form :rules="groupformRule" ref="groupform" :model="groupform" label-width="80px">
                <el-form-item label="组别：">
                  <span>{{this.groupname}}</span>
                </el-form-item>
                <el-form-item label="选择组员" prop="apiId">
                  <el-select v-model="groupform.apiId" placeholder="请选择">
                    <el-option v-for="item in superior" :key="item.apiId" :label="item.name" :value="item.userid">
                    </el-option>
                  </el-select>
                  <el-checkbox style="padding-left:10px;" v-model="isLeader" label="组长"></el-checkbox>
                  <el-button type="primary" style="float: right;" icon="el-icon-lx-add" @click="savesuser">新 增
                  </el-button>
                </el-form-item>
              </el-form>
              <el-table :data="tableData1" max-height="250" style="width: 100%" v-loading="dialogLoading"
                element-loading-text="拼命加载中">
                <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="姓名" align="center"></el-table-column>
                <el-table-column prop="account" label="帐号" align="center"></el-table-column>
                <el-table-column prop="telephone" label="手机号" align="center"></el-table-column>
                <el-table-column prop="isLeader" label="组长" align="center">
                  <template slot-scope="scope">
                    <el-tag :type="!scope.row.isLeader ? 'danger':'success'">{{!scope.row.isLeader ? '否':'是'}}</el-tag>
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作" width="120" align="center">
                  <template slot-scope="scope">
                    <el-button type="danger" @click="chargerDelete(scope.$index, scope.row.chargerId)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div align="center" style="margin-top: 10px;">
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" fixed="center" @click="groupAdd">关闭</el-button>
                </span>
              </div>
            </el-dialog>
            <!-- 学校管理 -->
            <el-dialog :visible.sync="editVisible2" width="50%" center>
              <el-form :rules="schoolformRule" ref="schoolform" :model="schoolform" label-width="80px">
                <el-form-item label="组别：">
                  <span>{{this.groupname}}</span>
                </el-form-item>
                <el-form-item label="选择学校" prop="sourceSchoolId">
                  <el-select v-model="schoolform.sourceSchoolId" placeholder="请选择学校">
                    <el-option v-for="item in addschool" :key="item.sourceSchoolId" :label="item.sourceSchoolName"
                      :value="item.sourceSchoolId"></el-option>
                  </el-select>
                  <el-button type="primary" style="float: right;" @click="saveschool">增加</el-button>
                </el-form-item>
              </el-form>
              <el-table :data="tableData2" max-height="250" style="width: 100%" v-loading="dialogLoading"
                element-loading-text="拼命加载中">
                <el-table-column fixed label="序号" type="index" width="55" align="center"></el-table-column>
                <el-table-column prop="sourceSchoolName" label="学校名称" align="center"></el-table-column>
                <el-table-column fixed="right" label="操作" width="150" align="center">
                  <template slot-scope="scope">
                    <el-button type="danger" @click="schoolDelete(scope.$index,scope.row.sourceSchoolId)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div align="center" style="margin-top: 10px;">
                <span slot="footer" class="dialog-footer">
                  <el-button type="primary" fixed="center" @click="editVisible2=false">关闭</el-button>
                </span>
              </div>
            </el-dialog>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
  import {
    listGroupPage,
    getGroupInfo,
    updateGroupInfo,
    addGroup,
    deleteGroup,
    getGroupDetail,
    deleteCharger,
    deleteGroupSourceRelation,
    updateGroup,
    addCharger,
    listRecruitZone,
    addGroupSourceRelation,
    listSourceSchool
  } from "../../../../api/annualEnrollmentPlanManagement/groupController";
  import {
    term,
    termlist,
    superior,
    struct
  } from "../../../../api/admin/API-Controller/APIController";
  import { getSemesterList, } from "@/api/annualEnrollmentPlanManagement/enrollmentTargetList";
  export default {
    name: "editEnrollmentManagement",
    data() {
      return {
        loading: false,//页面加载
        dialogLoading: false,//dialog加载

        schoolId: JSON.parse(localStorage.getItem("userInfo")),
        tableData: [],
        tableData1: [],
        tableData2: [],
        editVisible3: false,
        editVisible: false,
        editVisible1: false,
        editVisible2: false,
        editVisible4: false,
        isLeader: false,
        pageTotal: 0,
        activeName: "first",
        groupname: "",
        semester: [],
        superior: [],
        addschool: [],
        deptName: [],
        areaId: [],
        schoolform: [],
        //新增负责人
        groupform: {
          groupId: "",
          account: "",
          isLeader: 0,
          name: "",
          telephone: "",
          apiId: ""
        },
        //编辑区域信息
        regionform: {
          semesterId: "",
          semester: "",
          title: "",
          comment: ""
        },
        //新建小组
        groupInfoform: {
          groupInfoId: "",
          areaId: "",
          deptId: "",
          targetAmount: "0",
          name: "",
          comment: "",
          deptName: ""
        },
        //查看/编辑小组
        groupInfoforma: {
          groupInfoId: "",
          areaId: "",
          deptId: "",
          targetAmount: "0",
          name: "",
          comment: "",
          deptName: "",
          groupId: ""
        },
        //新增学校
        schoolform: {
          groupId: "",
          sourceSchoolId: ""
        },
        query: {
          groupId: "",
          sourceSchoolId: "",
          groupInfoId: 0,
          pageNum: 1,
          pageSize: 10
        },
        //编辑基本信息校验
        regionformRule: {
          semester: [{ required: true, message: "请选择学期", trigger: "blur" }],
          title: [{ required: true, message: "请输入标题", trigger: "blur" }]
        },
        //新建区域校验
        groupInfoformRule: {
          areaId: [
            { required: true, message: "请选择招生区域", trigger: "change" }
          ],
          deptId: [
            { required: true, message: "请选择所属岗位", trigger: "change" }
          ],
          name: [{ required: true, message: "请输入组别名称", trigger: "blur" }],

        },
        //新增组员校验
        groupformRule: {
          apiId: [{ required: true, message: "请选择负责人", trigger: "blur" }],
          account: [
            { required: true, message: "请选择负责人", trigger: "change" }
          ]
        },
        //新增学校校验
        schoolformRule: {
          sourceSchoolId: [
            { required: true, message: "请选择学校", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      //获取基本信息
      this.getGroupInfo(this.$route.params.id);
      //获取列表信息
      this.getData(this.$route.params.id);
      //获取学期
      this.gettermlist();
      //根据学校id获取负责人
      this.struct();
      //获取招生区域（数据字典配置）
      this.listRecruitZone();
    },
    methods: {
      /**--------------------------基本信息---------------------------- */
      /**
       * 根据学校id获取学期
       * @param data {string} 学校id
       *  @author cwz
       */
      gettermlist() {
        var data = this.schoolId.schoolid;
        getSemesterList(data).then(res => {
          if (res.data) {
            this.semester = res.data.data;
          }
        });
      },
      // getSemesterList () {
      //   getSemesterList(this.schoolId).then(res => {
      //     //特殊处理
      //     if (res.data) {
      //       this.semesterList = res.data.data;
      //     }
      //     console.log('semesterList:', this.semesterList)
      //   });
      // },
      /**
       * 获取区域基本信息
       * @param id {string} 区域id
       *  @author cwz
       */
      getGroupInfo(id) {
        this.loading = true
        getGroupInfo(id).then(res => {
          this.regionform = res.data;
          this.loading = false
        }).catch(err => {
          console.error();
          this.loading = false
        });
      },
      /**
       * 根据名称获取ID
       * @param name{number} 获取的id所需要的名称
       * @param query{array} 需要获取的id对应的数组
       * @author LiuGuoHao
       */
      getIdByName(name, query) {
        var obj = query.find(function (x) {
          return x.name == name;
        });
        return obj.id
      },
      /**
       * 组员弹框关闭后初始化
       * @author LiuGuoHao
       */
      groupAdd() {
        this.groupform.apiId = "";
        this.groupform.isLeader = 0
        this.editVisible1 = false
      },
      /**
       * 编辑区域基本信息保存
       * @author cwz
       */
      save() {
        console.log(this.semester, "==>semester");
        // this.regionform.semesterId = this.getIdByName(this.regionform.semester, this.semester)

        // this.semester.name = this.regionform.semester;
        const data = { ...this.regionform };
        this.$refs["region"].validate(valid => {
          if (valid) {
            this.updateGroupInfo(data);
          }
        });
      },
      /**
       * 编辑区域基本信息
       * @param data {obj} 包含 学期、标题、说明（* 为必填）
       * 学期 semester *
       * 标题 title *
       * 说明 comment
       * @author cwz
       */
      updateGroupInfo(data) {
        updateGroupInfo(data)
          .then(() => {
            this.$message.success(`编辑区域成功`);
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**--------------------------组员管理---------------------------- */
      /**
       * 获取组员信息列表
       * @param id {string} 小组id
       *  @author cwz
       */
      getuser(id) {
        //初始化
        this.groupform.groupId = id;
        this.groupname = '';
        this.tableData1 = [];
        this.elFormReset("groupform");
        this.editVisible1 = true;
        this.dialogLoading = true
        getGroupDetail(id).then(res => {
          this.tableData1 = res.data.chargerList;
          this.groupname = res.data.name;
          this.query.groupId = id;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });
      },
      /**
       * 根据学校id获取负责人、所属岗位
       */
      struct() {
        var data = this.schoolId.schoolid;
        struct(data).then(res => {
          this.deptName = res.data.data.child;
          // this.superior = res.data.data.child[2].child;
          this.superior = [];
          this.recursionStruct(res.data.data);
          console.log(res.data.data, "==>superior");
        });
      },
      /**
       * 递归遍历结构树，将所有人员数据拼接成一个集合
       * 只收集没有child的子节点
       * @param {node }struct 结构树节点 
       * @author lmh
       */
      recursionStruct(struct) {
        if (typeof (struct.child) == "object") {
          console.log(struct, "==>");
          struct.child.forEach(item => {
            this.recursionStruct(item);
          });
        } else {
          //只收集没有child的子节点
          this.superior.push(struct);
        }
      },
      /**
       * 新增组员保存
       * @author cwz
       */
      savesuser() {
        if (this.isLeader === false) {
          this.groupform.isLeader = 0;
        } else if (this.isLeader === true) {
          this.groupform.isLeader = 1;
        }
        //表单验证
        this.$refs["groupform"].validate(valid => {
          //表单有数据执行
          if (valid) {
            var id = this.groupform.apiId;
            var obj = this.superior.find(function (x) {
              return x.userid == id;
            });
            this.groupform.semesterId = this.regionform.semesterId
            this.groupform.account = obj.account;
            this.groupform.telephone = obj.phone;
            this.groupform.name = obj.name;
            const data = { ...this.groupform };
            this.addCharger(data);
          }
        });
      },
      /**
       * 新增组员
       * @param data {obj} 包含apiid,名字，电话，小组id
       * @author cwz
       */
      addCharger(data) {
        addCharger(data)
          .then(() => {
            this.$message.success(`新增负责人成功`);
            this.getData(this.$route.params.id);
            this.getuser(data.groupId);
            this.isLeader = false;
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**
       * 删除组员操作
       * @author cwz
       */
      chargerDelete(index, chargerId) {
        // 二次确认删除
        this.$confirm("确定要删除该组员吗？删除后其关联的数据将可能会出错！", "提示", {
          type: "warning"
        })
          .then(() => {
            this.deleteCharger(index, chargerId);
          })
          .catch(() => { });
      },
      /**
       * 删除组员
       * @param chargerId {} 组员id
       * @author cwz
       */
      deleteCharger(index, chargerId) {
        deleteCharger(chargerId).then(res => {
          this.$message.success("删除成功");
          this.getData(this.$route.params.id);
          this.getuser(this.query.groupId);
        });
      },
      /**--------------------------小组管理---------------------------- */
      /**
       * 查看指定小组信息
       * @param id {} groupId 小组id
       * @author cwz
       */
      seeuser(id) {
        this.editVisible4 = true;
        this.dialogLoading = true
        getGroupDetail(id).then(res => {
          this.groupInfoforma.areaId = res.data.areaId;
          this.groupInfoforma.comment = res.data.comment;
          this.groupInfoforma.deptId = res.data.deptId;
          this.groupInfoforma.deptName = res.data.deptName;
          this.groupInfoforma.groupInfoId = res.data.groupInfoId;
          this.groupInfoforma.name = res.data.name;
          this.groupInfoforma.targetAmount = res.data.targetAmount;
          this.groupInfoforma.groupId = id;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });
      },
      /**
       * 编辑指定小组的详细信息
       * @param id {} groupId 小组id
       * @author cwz
       */
      getGroupDetail(id) {
        this.elFormReset("groupInfo");
        this.editVisible3 = true;
        this.dialogLoading = true
        getGroupDetail(id).then(res => {
          this.groupInfoforma.areaId = res.data.areaId;
          this.groupInfoforma.comment = res.data.comment;
          this.groupInfoforma.deptId = res.data.deptId;
          this.groupInfoforma.deptName = res.data.deptName;
          this.groupInfoforma.groupInfoId = res.data.groupInfoId;
          this.groupInfoforma.name = res.data.name;
          this.groupInfoforma.targetAmount = res.data.targetAmount;
          this.groupInfoforma.groupId = id;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });
      },

      /**
       * 删除小组操作
       * @author cwz
       */
      handleDelete(index, groupId) {
        // 二次确认删除
        this.$confirm("确定要删除该小组吗？删除后其关联的数据将可能会出错！", "提示", {
          type: "warning"
        })
          .then(() => {
            this.deleteGroup(index, groupId);
          })
          .catch(() => { });
      },
      /**
       * 删除小组请求
       * @param groupId 小组id
       * @author cwz
       */
      deleteGroup(index, groupId) {
        deleteGroup(groupId).then(res => {
          this.$message.success("删除成功");
          this.getData(this.$route.params.id);
        });
      },
      /**
       * 编辑小组信息
       * @author cwz
       */
      saveedit() {
        this.groupInfoforma.semesterId = this.regionform.semesterId
        const data = { ...this.groupInfoforma };
        //表单验证
        this.$refs["groupInfo"].validate(valid => {
          //表单有数据执行
          if (valid) {
            this.updateGroup(data);
            this.editVisible3 = false;
          }
        });
      },
      /**
       * 编辑小组信息
       * @param data {obj} 包含 招生区域*、所属岗位*、招生目标*、组别名称*、说明（*代表必填）
       * @author cwz
       */
      updateGroup(data) {
        updateGroup(data).then(() => {
          this.$message.success(`编辑区域成功`);
          this.getData(this.$route.params.id);
        });
      },
      /**--------------------------学校管理---------------------------- */
      /**
       * 获取生源学校
       * @param id {}  生源学校id
       * @author cwz
       */
      listSourceSchool(id) {
        this.dialogLoading = true
        listSourceSchool(id).then(res => {
          this.addschool = res.data;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });
      },
      /**
       * 新增学校保存
       * @author cwz
       */
      saveschool() {
        this.schoolform.semesterId = this.regionform.semesterId
        // this.addschool.sourceSchoolId = this.schoolform.sourceSchoolId;
        const data = { ...this.schoolform };
        //表单验证
        this.$refs.schoolform.validate(valid => {
          //表单有数据执行
          if (valid) {
            this.addGroupSourceRelation(data);
            this.editVisible = false;
          }
        });
      },
      /**
       * 新增学校
       * @param data {}  包含 生源学校名称、id
       * @author cwz
       */
      addGroupSourceRelation(data) {
        addGroupSourceRelation(data).then(() => {
          this.$message.success(`新增学校成功`);
          this.getData(this.$route.params.id);
          this.getschool(data.groupId);
        });
      },
      /**
       * 获取学校信息
       * @param id groupId
       * @author cwz
       */
      getschool(id) {
        //初始化
        this.schoolform.groupId = id;
        this.schoolform.sourceSchoolId = '';
        this.tableData2 = [];
        this.groupname = '';
        this.elFormReset("schoolform");
        this.dialogLoading = true
        //获取生源学校
        this.listSourceSchool(id);
        this.query.groupId = id;
        this.editVisible2 = true;
        getGroupDetail(id).then(res => {
          if (res.data.sourceSchoolList && res.data.sourceSchoolList.length) {
            this.tableData2 = res.data.sourceSchoolList;
            this.dialogLoading = false
          }
          this.groupname = res.data.name;
          this.dialogLoading = false
        }).catch(err => {
          console.error(err);
          this.dialogLoading = false
        });
      },
      /**
       * 删除学校
       * @author cwz
       */
      schoolDelete(index, sourceSchoolId) {
        this.query.sourceSchoolId = sourceSchoolId;
        // 二次确认删除
        this.$confirm("确定要删除该学校吗？删除后其关联的数据将可能会出错！", "提示", {
          type: "warning"
        })
          .then(() => {
            this.deleteGroupSourceRelation(index, sourceSchoolId);
          })
          .catch(() => { });
      },
      /**
       * 删除学校请求
       * @param sourceSchoolId {} 学校id
       * @author cwz
       */
      deleteGroupSourceRelation(index, sourceSchoolId) {
        let data = {
          sourceSchoolId: this.query.sourceSchoolId,
          groupId: this.query.groupId
        };
        deleteGroupSourceRelation(data).then(res => {
          this.$message.success("删除成功");
          this.getData(this.$route.params.id);
          this.getschool(data.groupId);
        });
      },
      /**--------------------------区域管理---------------------------- */

      /**
       * 获取区域（小组）列表信息
       * @param id {} 区域id
       * @author cwz
       */
      getData(id) {
        this.query.groupInfoId = id;
        this.loading = true
        listGroupPage(this.query).then(res => {
          this.tableData = res.data.records;
          this.pageTotal = res.data.total || 0;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        });
      },
      /**
       * 获取招生区域
       * @param
       * @author cwz
       */
      listRecruitZone() {
        listRecruitZone().then(res => {
          this.areaId = res.data;
        });
      },
      /**
       * 新增区域保存
       * @author cwz
       */
      saveadd() {
        //表单验证
        this.$refs.groupInfoform.validate(valid => {
          //表单有数据执行
          if (valid) {
            this.groupInfoform.groupInfoId = this.$route.params.id;
            var id = this.groupInfoform.deptId;
            var obj = this.deptName.find(function (x) {
              return x.id == id;
            });
            this.groupInfoform.deptName = obj.name;
            this.groupInfoform.semesterId = this.regionform.semesterId
            const data = { ...this.groupInfoform };
            this.addGroup(data);
            this.editVisible = false;
          }
        });
      },
      /**
       * 新增区域
       * @param data {obj} 招生区域*、所属岗位*、招生目标*、组别名称*、说明（*为必填）
       * @author cwz
       */
      addGroup(data) {
        addGroup(data)
          .then(() => {
            this.$message.success(`新增区域成功`);
            this.getData(this.$route.params.id);
          })
          .catch(err => {
            console.log(err);
          });
      },
      /**
       * 清空新建区域（新增小组）表单
       * @author cwz
       */
      clearn() {
        this.groupInfoform.groupInfoId = this.$route.params.id;
        this.groupInfoform.areaId = "";
        this.groupInfoform.comment = "";
        this.groupInfoform.deptId = "";
        this.groupInfoform.deptName = "";
        this.groupInfoform.name = "";
        this.groupInfoform.targetAmount = 0;
        this.elFormReset("groupInfoform");
        this.editVisible = true;
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, "pageNum", val);
        this.getData(this.$route.params.id);
      },
      //每页条数改变
      handleSizeChange(val) {
        this.$set(this.query, "pageSize", val);
        this.$set(this.query, "pageNum", 1);
        this.getData(this.$route.params.id);
      }
    }
  };
</script>

<style scoped>
  .handle-box {
    margin-bottom: 20px;
  }

  .table {
    width: 100%;
    font-size: 14px;
  }

  .red {
    color: #ff0000;
  }
  .width100{
    width: 100%;
  }
</style>