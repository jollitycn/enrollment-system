<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>数据字典</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <leftBorder title="数据字典"></leftBorder>
      <div class="container-content" v-loading="loading" element-loading-text="拼命加载中">
        <div class="container-content-one">
          <el-tree :default-expand-all="true" :data="dicList" :highlight-current="true" :props="defaultProps"
            @node-click="handleNodeClick"></el-tree>
        </div>
        <div class="container-content-two">
          <leftBorder v-if="hasSubs" :borderBottomWidth="0" :title="parentData.subTitle"></leftBorder>
          <el-button type="primary" class="container-content-three" icon="el-icon-lx-add"
            v-if="parentData.subTitle!='学校标签'" v-show="hasSubs" @click="handleEdit(0)">新建数据</el-button>
          <div>
            <el-table v-if="hasSubs" :data="tableData" border class="table" ref="multipleTable"
              header-cell-class-name="table-header" v-loading="rightLoading" element-loading-text="拼命加载中">
              <el-table-column prop="id" type="index" label="序号" align="center"></el-table-column>
              <el-table-column prop="dataCode" label="编号" align="center">
              </el-table-column>
              <el-table-column prop="dataValue" label="数据名称" align="center">
              </el-table-column>
              <el-table-column prop="dataEnglishValue" label="英文名称" align="center">
              </el-table-column>
              <el-table-column prop="dataType" label="类型" align="center">
                <template slot-scope="scope">{{scope.row.dataType|dataTypeHandle}}</template>
              </el-table-column>
              <!-- <el-table-column prop="dataOrder" label="排序号" align="center">
              </el-table-column> -->
              <el-table-column prop="dataDesc" label="备注" align="left">
              </el-table-column>
              <el-table-column label="操作" align="center" width="200">
                <template slot-scope="scope">
                  <el-button v-if="parentData.subTitle!='学校标签'" type="primary" @click="handleEdit(1, scope.row)">编辑
                  </el-button>
                  <el-button type="danger" @click="deleteDic(scope.row.dictionaryId)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="dic-detail" v-else>
              <el-row>
                <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      编号
                    </div>
                    <div class="item-right">
                      {{detailInfo.dataCode}}
                    </div>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      英文名称
                    </div>
                    <div class="item-right">
                      {{detailInfo.dataEnglishValue}}
                    </div>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      类型
                    </div>
                    <div class="item-right">
                      {{detailInfo.dataType|dataTypeHandle}}
                    </div>
                  </div>
                </el-col>
              </el-row>

              <el-row>
                <div class="detail-item">
                  <div class="item-left">
                    数据名称
                  </div>
                  <div class="item-right">
                    {{detailInfo.dataValue}}
                  </div>
                </div>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      最后更改人
                    </div>
                    <div class="item-right">
                      {{detailInfo.modifyName}}
                    </div>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      最后更改时间
                    </div>
                    <div class="item-right">
                      {{detailInfo.modifyTime|resetTime}}
                    </div>
                  </div>
                </el-col>
              </el-row>
              <!-- <el-col :span="12">
                  <div class="detail-item">
                    <div class="item-left">
                      排序号
                    </div>
                    <div class="item-right">
                      {{detailInfo.dataOrder}}
                    </div>
                  </div>
                </el-col> -->
              <el-row>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <div class="detail-item">
                    <div class="item-left">
                      备注
                    </div>
                    <div class="item-right">
                      {{detailInfo.dataDesc}}
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
      <el-dialog :title="dialogTitles[dialogType]" :close-on-click-modal="false" @close="cancelDialog"
        :visible.sync="editVisible" width="30%" center v-loading="dialogLoading" element-loading-text="拼命加载中">
        <el-form ref="dicForm" :rules="rules" :model="form" label-width="100px">
          <el-form-item label="编号" prop="dataCode">
            <el-input v-model.trim="form.dataCode" :disabled="isEdit" maxlength="100"></el-input>
          </el-form-item>
          <el-form-item label="数据名称" prop="dataValue">
            <el-input autocomplete="off" v-model.trim="form.dataValue" maxlength="2000" show-word-limit>
            </el-input>
          </el-form-item>
          <el-form-item label="英文名称" prop="dataEnglishValue">
            <el-input autocomplete="off" v-model.trim="form.dataEnglishValue" maxlength="30"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="dataType">
            <el-select style="width: 100%;" v-model="form.dataType" placeholder="请选择">
              <el-option v-for="item in dataTypes" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="排序号" prop="dataOrder">
            <el-input v-model.trim="form.dataOrder" maxlength="6"></el-input>
          </el-form-item> -->
          <el-form-item label="备注" prop="dataDesc">
            <el-input autocomplete="off" v-model="form.dataDesc" maxlength="100"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelDialog">取 消</el-button>
          <el-button type="primary" @click="saveEdit">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import { fetchData } from '@/api/index';
  import {
    getAllDictionaryList,
    sysDictionaryList,
    sysDictionaryAdd,
    sysDictionaryDelete,
    sysDictionaryUpdate,
    sysDictionaryDetail,
  } from '@/api/admin/systems-management/number-dictionaries';

  const FORM_DEFAULT = {
    dataCode: '',
    dataValue: '',
    dataEnglishValue: '',
    dataOrder: 0,
    dataType: '',
    dataDesc: '',
    dictionaryId: '',
    parentId: '',
  }
  const dataTypes = [
    { value: '1', label: '文本' },
    { value: '2', label: '布尔值' },
    { value: '3', label: '数值' },
    { value: '4', label: '日期' },
  ]
  export default {
    name: 'number-dictionaries',
    filters: {
      /**
       * 数据类型枚举
       */
      dataTypeHandle(val) {
        let idx = parseInt(val) | 1;
        return dataTypes[idx - 1].label;
      }
    },
    data() {
      return {
        loading: false,
        rightLoading: false,
        dialogLoading: false,
        isEdit: false,
        /**-------------  左侧树 --------------- */
        dicList: [],
        defaultProps: {
          children: 'subs',
          label: 'dataValue'
        },
        parentData: {
          parentId: 0,
          subTitle: undefined,
          recruitSchoolId: '',
        },
        /**-------------  中间数据展示 --------------- */
        hasSubs: true,
        tableData: [],
        detailInfo: {},

        /**-------------  新增/编辑弹框 --------------- */
        editVisible: false,
        dialogTitles: ['创建字典', '编辑字典'],
        dialogType: 0,//0创建字典，1编辑字典
        form: FORM_DEFAULT,
        rules: {
          dataCode: [
            { required: true, message: '请输入编号', trigger: 'blur' },
          ],
          dataValue: [
            { required: true, message: '请输入数据名称', trigger: 'blur' },
          ],
          dataEnglishValue: [
            { required: true, message: '请输入英文数据名称', trigger: 'blur' },
            { pattern: /^[_a-zA-Z0-9]/, message: '请输入英文名称', trigger: 'blur' }
          ],
          // dataOrder: [
          //   { required: true, message: '请输入排序号', trigger: 'blur' },
          //   { pattern: /^\d{1,6}$/, message: '请输入小于1000000的整数', trigger: 'blur' },
          // ],
          dataType: [
            { required: true, message: '请选择数据类型', trigger: 'blur' },
          ],
        },
        dataTypes: dataTypes,
      };
    },
    created() {
      //获取当前学校id
      let userInfo = localStorage.getItem("userInfo") || '{}';
      let schoolid = JSON.parse(userInfo).schoolid.toString();
      // this.schoolid = schoolid;
      this.parentData.recruitSchoolId = schoolid;
      //获取左侧树字典列表
      this.getDicList();
      //获取默认的第一个子节点数据展示
      this.getSubDicList();
    },
    methods: {
      /**-------------  左侧树数据获取 --------------- */
      /**
       * 获取全部的字典列表
       * @author lmh
       */
      getDicList() {
        this.loading = true
        getAllDictionaryList({}).then(res => {
          this.dicList = res.data;
          this.loading = false
        }).catch(err => {
          console.error(err);
          this.loading = false
        });
      },
      /**
       * 点击节点树处理方法
       * @author lmh
       */
      handleNodeClick(node) {
        //刷新该节点下的字典列表详情
        this.parentData.parentId = node.dictionaryId;
        this.parentData.subTitle = node.dataValue;
        // this.detailInfo = node;
        this.getSubDicList();
      },

      /**-------------  中间数据展示获取 --------------- */
      /**
       * 获取节点下所有子节点的字典列表
       * @author lmh
       */
      getSubDicList() {
        this.rightLoading = true
        sysDictionaryList(this.parentData).then(res => {
          this.tableData = res.data;
          //控制显示列表还是详情，如果有子节点数据则显示列表
          if (res.data && res.data.length > 0) {
            this.hasSubs = true;
          } else {
            this.getDicDetail();
            this.hasSubs = false;
          }
          this.rightLoading = false
        }).catch(err => {
          console.error(err);
          this.rightLoading = false
        });
      },
      /**
       * 获取字典详情
       * @author lmh
       */
      getDicDetail() {
        sysDictionaryDetail(this.parentData.parentId).then(res => {
          this.detailInfo = res.data || {};
        });
      },

      /**-------------  新增/编辑操作 --------------- */
      /**
       * 编辑处理
       * @param type 0创建，1编辑
       * @param row 编辑的行数据
       * @author lmh
       */
      handleEdit(type = 0, row) {
        //清空表单
        this.form = { ...FORM_DEFAULT };
        //初始化
        if (type === 1) {
          this.isEdit = true
          this.form = { ...row };
        } else {
          this.isEdit = false
        }
        this.dialogType = type;
        this.editVisible = true;
      },

      /**
       * 保存或编辑字典
       * @author lmh
       */
      saveEdit() {
        this.$refs['dicForm'].validate(valid => {
          if (valid) {
            if (this.dialogType === 1) {
              this.editDic();
            } else {
              this.createDic();
            }
          }
        })
      },
      /**
       * 取消字典弹出框
       * @author lgh
       */
      cancelDialog() {
        this.editVisible = false;
        this.$refs.dicForm.resetFields();
      },
      /**
       * 创建数据字典
       * @author lmh
       */
      createDic() {
        let data = { ...this.form, ...this.parentData }
        sysDictionaryAdd(data).then(res => {
          console.log('创建数据字典：', res)
          this.$message.success('创建数据字典成功！')
          this.editVisible = false;
          this.$refs['dicForm'].resetFields();
          this.getSubDicList();
          this.getDicList();
        });
      },
      /**
      * 编辑数据字典
      * @author lmh
      */
      editDic() {
        let data = { ...this.form, ...this.parentData }
        sysDictionaryUpdate(data).then(res => {
          console.log('编辑数据字典：', res)
          this.$message.success('编辑数据字典成功！')
          this.editVisible = false;
          this.$refs['dicForm'].resetFields();
          this.getSubDicList();
          this.getDicList();
        });
      },

      /**-------------  删除操作 --------------- */

      /**
       * 删除数据字典
       * @author lmh
       */
      deleteDic(id) {
        this.$confirm('确定要删除吗？', '提示', {
          type: 'warning'
        }).then(() => {
          sysDictionaryDelete(id).then(res => {
            console.log('删除数据字典：', res)
            this.$message.success('删除数据字典成功！')
            this.getSubDicList();
            this.getDicList();
          })
        }).catch(() => { });
      },
    }
  };
</script>

<style scoped>
  >>>.el-input__count-inner {
    margin-top: 48px;
  }

  .container-content {
    display: flex;
  }

  .container-content-one {
    width: 300px;
  }

  .container-content-two {
    flex: 1;
    position: relative;
  }

  .container-content-three {
    float: right;
    position: absolute;
    top: 9px;
    right: 0;
  }

  .dic-detail {
    margin: 10px 40px;
  }

  .detail-item {
    overflow: hidden;
    /* height: 40px; */
    line-height: 40px;
    zoom: 1;
  }

  .detail-item .item-left {
    color: #909399;
    float: left;
    width: 120px;
    margin-right: 20px;
  }

  .detail-item .item-right {
    overflow: hidden;
    zoom: 1;
  }
</style>