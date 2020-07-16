<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/enrollmentTargetList'}">招生目标列表</el-breadcrumb-item>
        <el-breadcrumb-item>{{title}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tabs">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <el-tab-pane label="目标信息" name="first">
          <el-row type="flex" justify="center">
            <el-col :span="12">
              <el-card class="box-card">
                <el-form
                  ref="targetForm"
                  :model="targetForm"
                  :rules="targetFormRule"
                  label-width="120px"
                  v-loading="loading"
                  element-loading-text="拼命加载中"
                >
                  <el-form-item label="学期" prop="semesterId">
                    <el-select
                      v-if="hasTarget"
                      v-model="targetForm.semesterId"
                      :disabled="hasTarget"
                      placeholder="请选择"
                      @change="addTargePlanInfo"
                    >
                      <el-option
                        v-for="item in semesterList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      ></el-option>
                    </el-select>
                    <el-select
                      v-else
                      v-model="targetForm.semesterId"
                      :disabled="hasTarget"
                      placeholder="请选择"
                      @change="addTargePlanInfo"
                    >
                      <el-option
                        v-for="item in semesterList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      ></el-option>
                    </el-select>

                    <el-button
                      v-if="!hasTarget"
                      style="margin-left:10px;"
                      type="primary"
                      @click="showAddSemesterDialog"
                    >新增学期</el-button>
                  </el-form-item>
                  <el-form-item label="标题" prop="title">
                    <el-input
                      class="input-custom"
                      v-model.trim="targetForm.title"
                      placeholder="请输入标题"
                      maxlength="20"
                      show-word-limit
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="目标人数" prop="targetAmount">
                    <el-input-number
                      v-model="targetForm.targetAmount"
                      :min="0"
                      :max="9999999"
                      size="medium"
                    ></el-input-number>&emsp;人
                  </el-form-item>
                </el-form>
                <el-row type="flex" justify="center">
                  <el-button size="middle" @click="backTargetList">返回</el-button>
                  <el-button size="middle" type="primary" @click="saveTarget">保存</el-button>
                </el-row>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="各组目标制定" name="second" :disabled="!hasTarget">
          <el-row type="flex" justify="center">
            <vue2-org-tree
              name="test"
              node-key="id"
              :data="channelTree"
              :label-class-name="labelClassName"
              :renderContent="renderContent"
            >></vue2-org-tree>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="专业目标制定" name="third" :disabled="!hasTarget">
          <el-row type="flex" justify="center">
            <vue2-org-tree
              name="test"
              node-key="id"
              :data="majorTree"
              :label-class-name="labelClassName"
              :renderContent="renderContent"
            >></vue2-org-tree>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 新增学期弹出框 -->
    <el-dialog
      title="新增学期"
      :close-on-click-modal="false"
      :visible.sync="addSemesterVisible"
      width="48%"
      center
    >
      <el-row type="flex" justify="center">
        <el-col :span="18">
          <el-form
            ref="addSemesterForm"
            :model="addSemesterForm"
            :rules="addSemesterFormRule"
            label-width="120px"
          >
            <el-form-item label="学期名称" prop="name">
              <el-input
                style="width: 100%;"
                class="input-custom"
                v-model.trim="addSemesterForm.name"
                placeholder="请输入学期名称"
                maxlength="20"
                show-word-limit
              ></el-input>
            </el-form-item>
            <el-form-item label="学期开始时间" prop="start">
              <el-date-picker
                style="width: 100%;"
                v-model="addSemesterForm.start"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="学期结束时间" prop="end">
              <el-date-picker
                style="width: 100%;"
                v-model="addSemesterForm.end"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row slot="footer" type="flex" justify="center">
        <el-button size="middle" @click="addSemesterVisible = false">取消</el-button>
        <el-button size="middle" type="primary" @click="saveSemester">保存</el-button>
      </el-row>
    </el-dialog>

    <el-dialog :title="nodeName" :visible.sync="editNodeDialogVisible" width="40%" center>
      <el-form
        ref="editNodeForm"
        :model="editNodeForm"
        :rules="editNodeFormRule"
        label-width="80px"
      >
        <el-form-item label="目标数量" prop="editNodeForm">
          <el-input-number v-model="editNodeForm.targetAmount" :min="0" :max="999999" size="medium"></el-input-number>&emsp;人
        </el-form-item>
        <el-form-item label="理由" prop="comment">
          <el-input
            type="textarea"
            v-model.trim="editNodeForm.comment"
            placeholder="请输入定制目标的理由"
            resize="vertical"
            :rows="4"
            maxlength="200"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <el-row slot="footer" type="flex" justify="center">
        <el-button size="middle" @click="editNodeDialogVisible = false">取消</el-button>
        <el-button size="middle" type="primary" @click="saveNode">保存</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {
  addOrEdit,
  getDetail,
  editChannelOrMajorNode,
  getSemesterList
} from "@/api/annualEnrollmentPlanManagement/enrollmentTargetList";
import { term } from "@/api/admin/API-Controller/APIController.js";

const DAFULT_EDITNODEFORM = {
  comment: "",
  targetAmount: 0
};

const DAFULT_ADDSEMESTERFORM = {
  schoolId: "",
  semesterName: ""
};

export default {
  data() {
    return {
      loading: false,
      /**-------- common -----------*/
      title: "",
      activeName: "first",
      schoolId: "",
      targetId: "",
      semesterList: [],
      hasTarget: false, //是否有建立目标
      semesterList: [],
      targetDetail: {},

      /**-------- 目标信息 -----------*/
      targetForm: {
        recruitSchoolId: "",
        targetId: "",
        semesterId: "",
        semester: "",
        title: "",
        targetAmount: 0
      },

      targetFormRule: {
        semesterId: [
          { required: true, message: "请选择学期", trigger: "blur" }
        ],
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        targetAmount: [
          { required: true, message: "请输入目标人数", trigger: "blur" }
        ]
      },
      addSemesterVisible: false,
      addSemesterForm: { ...DAFULT_ADDSEMESTERFORM },
      addSemesterFormRule: {
        name: [{ required: true, message: "请输入学期名称", trigger: "blur" }],
        start: [
          { required: true, message: "请选择学期开始时间", trigger: "blur" }
        ],
        end: [
          { required: true, message: "请选择学期结束时间", trigger: "blur" }
        ]
      },
      /**-------- 目标制定 -----------*/
      labelClassName: "bg-color-orange",
      channelTree: {},
      majorTree: {},
      editNodeDialogVisible: false,
      nodeName: "编辑节点",
      editNodeForm: { ...DAFULT_EDITNODEFORM },
      editNodeFormRule: {
        targetAmount: [
          { required: true, message: "请输入目标数量", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    let userInfo = localStorage.getItem("userInfo") || "{}";
    let schoolid = JSON.parse(userInfo).schoolid.toString();
    this.schoolId = schoolid;
    //获取路径中的targetId
    console.log("params:", this.$route.params);
    console.log("schoolId:", schoolid);
    let targetId = this.$route.params.id;
    targetId = targetId === "0" ? "" : targetId;
    this.targetId = targetId;
    const actions = ["新增", "编辑"];
    let action = actions[0];
    if (targetId) {
      //查询详情数据
      this.getDetail(targetId);
      action = actions[1];
    }

    this.getSemesterList();
    this.title = `${action}招生目标`;
  },
  methods: {
    /*----------------- common ----------------*/
    handleClick(tab) {
      console.log("click tab：", tab);
      switch (tab.name) {
        case "first":
          break;
        case "first":
          break;
        case "first":
          break;
        default:
      }
    },
    /**
     * 根据ID获取开始时间和结束时间
     * @param id{number} 获取的名称所需要的id
     * @param query{array} 需要获取的name对应的数组
     * @author LiuGuoHao
     */
    getTimeById(id, query) {
      var obj = query.find(function(x) {
        return x.id == id;
      });
      let time = { start: obj.start, end: obj.end, semesterName: obj.name };
      return time;
    },
    /**
     * 学期改变时，改变对应的默认时间
     * @author LiuGuoHao
     */
    addTargePlanInfo() {
      let semesterInfo = this.getTimeById(
        this.targetForm.semesterId,
        this.semesterList
      );
      this.targetForm.semesterEnd = semesterInfo.end;
      this.targetForm.semesterStart = semesterInfo.start;
      this.targetForm.semester = semesterInfo.semesterName;
      console.log(this.targetForm,111);
    },
    /**
     * 返回目标列表
     * @author lmh
     */
    backTargetList() {
      this.$router.go(-1);
    },
    /**
     * 获取学期列表
     * @author lmh
     */
    getSemesterList() {
      this.loading = true;
      getSemesterList(this.schoolId)
        .then(res => {
          //特殊处理
          if (res.data) {
            this.semesterList = res.data.data;
          }
          console.log("semesterList:", this.semesterList);
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    /**
     * 获取目标详情数据
     * @param {string} targetId 招生目标ID
     * @author lmh
     */
    getDetail(targetId) {
      this.loading = true;
      getDetail(targetId)
        .then(res => {
          //元素数据备份
          this.targetDetail = res.data;
          //数据解析转换
          this.converToTargetForm(res.data);
          this.converToChannelTree(res.data);
          this.converToMajorTree(res.data);

          //允许设置目标
          this.hasTarget = true;
          this.loading = false;
        })
        .catch(err => {
          console.error(err);
          this.loading = false;
        });
    },
    /**
     * 将目标详情数据转换成target表单数据
     * @param {object} targetDetail
     * @author lmh
     */
    converToTargetForm(targetDetail) {
      const {
        targetId,
        schoolId,
        semesterId,
        semesterName,
        title,
        targetAmount
      } = targetDetail;
      const form = {
        recruitSchoolId: schoolId,
        semester: semesterName,
        targetId,
        semesterId,
        title,
        targetAmount
      };
      this.targetForm = form;
    },
    /**
     * 将目标详情数据转换成渠道目标树
     * @param {object} targetDetail
     * targetDetail.channelTreeVO 渠道目标
     * targetDetail.majorTreeVO 专业目标
     * @author lmh
     */
    converToChannelTree(targetDetail) {
      const tree = this.converToTree(0, targetDetail);
      console.log("渠道目标数结构：", tree);
      this.channelTree = tree;
    },
    /**
     * 将目标详情数据转换成专业目标树
     * @param {object} targetDetail
     * targetDetail.channelTreeVO 渠道目标
     * targetDetail.majorTreeVO 专业目标
     * @author lmh
     */
    converToMajorTree(targetDetail) {
      const tree = this.converToTree(1, targetDetail);
      console.log("专业目标树结构：", tree);
      this.majorTree = tree;
    },

    /**
       * 将目标详情数据转换成专业目标树
       * @param {int} type 类型，0,渠道目标，1，专业目标
       * @param {object} targetDetail 
       * targetDetail.channelTreeVO 渠道目标
       * targetDetail.majorTreeVO 专业目标
       * @returns tree 返回渠道目标/专业目标对应的数据结构
       * 层级结构如下：
       * {
          id:1,//唯一键
          label:'',//显示标签
          number:1,//数据展示
          comment:'',//评论
          children:[],//子节点
        }
       * @author lmh 
       */
    converToTree(type, targetDetail) {
      console.log(type, "==>type");

      const types = ["channelTreeVO", "majorTreeVO"];
      const { targetId, title, targetAmount } = targetDetail;
      let tree = {
        id: targetId,
        label: title,
        number: targetAmount,
        comment: "",
        children: []
      };
      let node = null,
        subNode = null;
      const nodes = targetDetail[types[type]];
      // console.log('type==',type,',types[type]==',types[type],',nodes==',nodes)
      if (nodes && nodes.length) {
        for (let item of nodes) {
          node = {
            id: item.targetDecompositionId,
            label: item.nodeName,
            number: item.targetAmount,
            comment: item.comment,
            children: []
          };
          if (item.nodeList && item.nodeList.length) {
            for (let subItem of item.nodeList) {
              subNode = {
                id: subItem.targetDecompositionId,
                label: subItem.nodeName,
                number: subItem.targetAmount,
                comment: subItem.comment,
                children: []
              };
              node.children.push(subNode);
            }
          }
          tree.children.push(node);
        }
      }
      return tree;
    },

    /*----------------- 保存或编辑目标基本信息 ----------------*/
    /**
     * 保存招生目标数据
     * @author lmh
     */
    saveTarget() {
      this.$refs["targetForm"].validate(valid => {
        if (valid) {
          //其他参数处理
          let data = { ...this.targetForm };
          data.recruitSchoolId = data.recruitSchoolId || this.schoolId;
          data.targetId = data.targetId || this.targetId;
          if (!data.semester && this.targetForm.semesterId) {
            let selectedSemester = this.semesterList.find(item => {
              return item.id === this.targetForm.semesterId;
            });
            if (selectedSemester && selectedSemester.name) {
              data.semester = selectedSemester.name;
            }
          }
          console.log(data,2222222);
          
          addOrEdit(data).then(res => {
            console.log("保存目标数据：", res.data);
            const actions = ["新增", "修改"];
            const actionsIndex = data.targetId ? 1 : 0;
            this.$message.success(`${actions[actionsIndex]}成功！`);
            this.targetId = res.data;
            this.getDetail(this.targetId);
            //跳转到渠道目标制定
            this.activeName = "second";
          });
        }
      });
    },

    /**
     * 保存招生目标数据
     * @author lmh
     */
    showAddSemesterDialog() {
      //初始化数据
      this.elFormReset("addSemesterForm");
      this.addSemesterForm = { ...DAFULT_ADDSEMESTERFORM };
      //显示弹窗框
      this.addSemesterVisible = true;
    },
    /**
     * 保存学期数据
     * @author lmh
     */
    saveSemester() {
      // this.addSemesterForm.schoolId = this.schoolId;
      this.$refs["addSemesterForm"].validate(valid => {
        if (valid) {
          let data = { ...this.addSemesterForm };
          term(data).then(res => {
            //隐藏弹出框
            this.addSemesterVisible = false;
            this.$message.success("保存学期成功！");
            //刷新学期列表
            this.getSemesterList();
          });
        }
      });
    },

    /*----------------- 目标制定 ----------------*/
    /**
     * 自定义树节点渲染
     * @author peizhengyong
     */
    renderContent(h, data) {
      // console.log('tree  node:',data)
      return (
        <span class="bg-color-orange">
          <span>{data.label}</span>
          <hr></hr>
          <span>
            <el-button
              class="black"
              size="small"
              type="text"
              on-click={() => this.showEditNodeDialog(data)}
            >
              {data.number}人
            </el-button>
          </span>
        </span>
      );
    },
    /**
     * 显示更新节点输入框
     * @param node {object}
     * node.label 节点名称
     * node.id  节点id
     * node.number  节点数量
     * @author lmh
     */
    showEditNodeDialog(node) {
      //根节点不支持修改
      if (this.targetDetail.targetId === node.id) {
        return;
      }
      //弹框数据初始化
      this.elFormReset("editNodeForm");
      this.editNodeForm = { ...DAFULT_EDITNODEFORM };
      console.log("this.editNodeForm:", this.editNodeForm, ",node:", node);
      this.nodeName = node.label;
      this.editNodeForm.targetAmount = node.number;
      this.editNodeForm.targetDecompositionId = node.id;
      this.editNodeForm.comment = node.comment;
      //显示弹框
      this.editNodeDialogVisible = true;
    },
    /**
     * 保存该节点数据
     * @author lmh
     */
    saveNode() {
      this.$refs["editNodeForm"].validate(valid => {
        if (valid) {
          let data = { ...this.editNodeForm };
          this.editChannelOrMajorNode(data);
        }
      });
    },

    /**
     * 树节点数据保存
     * @param data {object}
     * data.comment 说明
     * data.targetAmount * 数量
     * data.targetDecompositionId * id
     * @author lmh
     */
    editChannelOrMajorNode(data) {
      console.log("保存节点数据：", data);
      editChannelOrMajorNode(data).then(res => {
        // this.$message.success('节点更新成功')
        //刷新数据
        this.getDetail(this.targetId);
        //隐藏弹出框
        this.editNodeDialogVisible = false;
      });
    }
  }
};
</script>

<style>
.bg-color-orange {
  color: #fff;
  background-color: #009fcc;
}

.black {
  color: #062464;
}

.input-custom {
  width: 300px;
}
</style>