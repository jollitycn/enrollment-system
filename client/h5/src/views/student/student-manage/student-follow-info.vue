<template>
  <div class="student-follow-info">
    <van-sticky :offset-top="0">
      <h5-header
        :leftStatus="true"
        title="跟进信息"
        rigText="新增沟通"
        :isLeft="true"
        @clickHeadLeft="clickHeadLeft"
        @clickHeadAction="navToAddCommunication"
      ></h5-header>
    </van-sticky>

    <van-form validate-first input-align="right" @submit="save">
      <!-- 跟进等级 -->
      <div class="follow-type">
        <div class="follow-type-title">跟进等级</div>
        <div class="follow-type-list">
          <span
            v-for="(item, index) in levelList"
            :key="index"
            @click="changeLevel(index)"
            :class="{ choice: index == activeIndex }"
          >{{ item.title }}</span>
        </div>
      </div>
      <!-- 跟进类型-多选 -->
      <div class="follow-type">
        <div class="follow-type-title">
          跟进类型
          <span class="follow-type-title-tip">（可多选）</span>
        </div>
        <div class="follow-type-list">
          <span
            v-for="item in typeList"
            :key="item.id"
            @click="changeType(item.id)"
            :class="{ active: activeIndex2.indexOf(item.id)>-1,'mult-checked':activeIndex2.indexOf(item.id)>-1}"
          >{{ item.title }}</span>
        </div>
      </div>

      <!-- 负责人 -->
      <div class="form-split-title">负责人</div>
      <!-- 学生所属区域 -->
      <van-field
        label-class="custom-field-title"
        :value="form.groupName"
        readonly
        clickable
        @click="showPicker(0)"
        label="学生所属区域"
        placeholder="请选择所属区域"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 默认组长 -->
      <van-field
        label-class="custom-field-title xx"
        :value="form.creatorName"
        error-message-align="right"
        readonly
        clickable
        @click="showPicker(1)"
        label="默认组长"
        placeholder="请选择组长"
        :rules="[{ required: true, message: '请选择组长' }]"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 意向原因 -->
      <!-- <van-field
        label-class="custom-field-title"
        v-model="form.reason"
        label="意向原因"
        placeholder="请填写意向原因"
        maxlength="100"
      />-->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model="form.reason"
        rows="6"
        label="意向原因"
        type="textarea"
        maxlength="100"
        placeholder="请填写意向原因"
      />
      <!-- 跟进方向 -->
      <!-- <van-field
        label-class="custom-field-title"
        v-model="form.direction"
        label="跟进方向"
        placeholder="请填写跟进方向"
        maxlength="100"
      />-->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model="form.direction"
        rows="6"
        label="跟进方向"
        type="textarea"
        maxlength="100"
        placeholder="请填写跟进方向"
      />

      <!-- 沟通记录-列表 -->
      <!-- <div class="form-split-title">沟通记录</div> -->
      <van-list
        class="communication-list"
        v-model="loading"
        :finished="finished"
        finished-text="没有更多沟通记录数据了"
        @load="onLoad"
      >
        <div v-for="(item,index) in list" :key="index">
          <div class="form-split-title">沟通记录{{index+1}}</div>
          <div class="communication-list-item content-info-content-items">
            <div class="content-info-item">
              <div class="content-info-item-label">沟通日期</div>
              <div class="content-info-item-val">{{item.communicationDate}}</div>
            </div>
            <div class="content-info-item">
              <div class="content-info-item-label">沟通方式</div>
              <div class="content-info-item-val">{{commList[item.communicationWay].title}}</div>
            </div>
            <div class="content-info-item">
              <div class="content-info-item-label">沟通结果</div>
              <div class="content-info-item-val">{{item.communicationResult}}</div>
            </div>
          </div>
        </div>
      </van-list>
      <div class="student-follow-info-save">
        <button class="action-save-btn" native-type="save">保存</button>
      </div>
    </van-form>

    <!-- 学生所属区域弹框 -->
    <van-popup v-model="showPlace" position="bottom">
      <van-picker
        value-key="name"
        show-toolbar
        :columns="columns"
        @cancel="showPlace = false"
        @confirm="onConfirm"
      />
    </van-popup>
    <!-- 默认组长弹框 -->
    <van-popup v-model="showPopup" position="bottom">
      <van-picker
        value-key="name"
        show-toolbar
        :columns="groupcolumns"
        @cancel="showPopup = false"
        @confirm="onConfirm"
      />
    </van-popup>
  </div>
</template>

<script>
import {
  queryStudentPublicRelation,
  saveOrUpdateStudentPublicRelation,
  queryPrincipal,
  getGroupDetail
} from "../../../api/Student-info/student-follow-info";
import Header from "@/components/cheader";
import Vue from "vue";
import { Sticky, Form, Field, Popup, Picker, Icon, List } from "vant";

Vue.use(Sticky);
Vue.use(Form);
Vue.use(Field);
Vue.use(Popup);
Vue.use(Picker);
Vue.use(Icon);
Vue.use(List);

export default {
  name: "student-follow-info",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      type: "", //用于判断是哪个按钮的确认 0：学生所属区域，1：默认组长
      studentId: null,
      showPopup: false,
      showPlace: false,
      columns: [],
      groupcolumns: [],
      activeIndex: 0,
      levelList: [
        { id: 0, title: "高" },
        { id: 1, title: "中" },
        { id: 2, title: "低" }
      ],
      activeIndex2: [],
      typeList: [
        { id: "0", title: "宣讲" },
        { id: "1", title: "班主任指导" },
        { id: "2", title: "电话" },
        { id: "3", title: "现场咨询" }
      ],
      commList: [
        { id: "0", title: "电话" },
        { id: "1", title: "微信" },
        { id: "2", title: "见面" }
      ],
      form: {
        classId: 0,
        createTime: "",
        creatorId: 0,
        creatorName: "",
        direction: "",
        groupId: 0,
        level: 0,
        reason: "",
        recruitSchoolId: 0,
        registerStatus: 0,
        relationType: "",
        semester: "",
        semesterId: 0,
        sourceSchoolId: 0,
        status: 0,
        statusChangeTime: "",
        studentInfo: 0,
        studentNo: 0,
        studentPublicRelationId: 0
      },
      list: [],
      loading: false,
      finished: false
    };
  },
  created() {
    this.studentId = this.$route.params.id;
    this.form.studentPublicRelationId = this.$route.params.relationId;
    this.queryStudentPublicRelation(this.studentId);
  },
  methods: {
    onLoad() {
      this.finished = true;
    },
    //跳转新增沟通页面
    navToAddCommunication() {
      let id = this.studentId;
      this.$router.push({
        path: `/student-manage/student-communication-add/${id}/${this.form.studentPublicRelationId}`
      });
    },
    /**
     * 跟进等级切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeLevel(index) {
      this.activeIndex = index;
    },
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      let idx = this.activeIndex2.indexOf(index);
      if (idx > -1) {
        this.activeIndex2.splice(idx, 1);
      } else {
        this.activeIndex2.push(index);
      }
    },
    /**
     * 添加或编辑学生公关信息
     * @author lgh
     */
    save() {
      this.form.level = this.activeIndex;
      this.form.relationType = this.activeIndex2.join(",");
      this.$message.loading("正在保存...");
      saveOrUpdateStudentPublicRelation(this.form)
        .then(() => {
          this.$message.close();
          this.$message.success("保存成功");
          this.$router.go(-1);
        })
        .catch(err => {
          console.error("保存失败：", err);
        });
    },
    /**
     * 返回学生详情页面
     * @author LGH
     */
    clickHeadLeft() {
      let id = this.studentId;
      this.$router.push({
        path: `/student-manage/student-detail/${id}`
      });
    },
    /**
     * 弹出显示
     * @param {int}type 用于判断是哪个弹出框 0：学生所属区域，1：默认组长
     * @author LGH
     */
    showPicker(type) {
      this.type = type;
      if (type == 0) {
        this.showPlace = true;
      } else {
        this.showPopup = true;
      }
    },
    /**
     * 学生所属区域与默认组长弹框确认按钮
     * @param {array} value 不需要自己传参数，该参数用于获取当前选择的值数据
     * @author LGH
     */
    onConfirm(value) {
      if (this.type == 0) {
        this.form.groupId = value.groupId;
        this.form.groupName = value.name;
        this.showPlace = false;
        this.getGroupDetail(value.groupId);
      } else {
        console.log(value, 111);

        this.form.creatorId = value.apiId;
        this.form.creatorName = value.name;
        this.showPopup = false;
      }
    },
    /**
     * 根据学生区域ID获取学生区域名称
     * @param groupId{String} 学生所属区域ID
     * @author LGH
     */
    getNameById(groupId) {
      var obj = this.columns.find(function(x) {
        return x.groupId == groupId;
      });
      return obj.name;
    },
    /**
     * 获取学生公关信息
     * @param {number} studentInfoId 学生的公关ID
     * @author lgh
     */
    queryStudentPublicRelation(studentInfoId) {
      this.$message.loading("加载中...");
      queryStudentPublicRelation(studentInfoId)
        .then(res => {
          this.$message.close();
          this.form = res.data;
          this.list = res.data.communicationRecords;
          this.activeIndex = this.form.level;
          if (this.form.relationType) {
            this.activeIndex2 = this.form.relationType.split(",");
          }
          this.queryPrincipal();
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    },
    /**
     * 查询公关信息中的招生组下拉列表
     * @author lgh
     */
    queryPrincipal() {
      this.$message.loading("加载中...");
      let query = {
        studentInfoId: this.studentId
      };
      queryPrincipal(query)
        .then(res => {
          this.$message.close();
          this.columns = res.data;
          //若groupId存在时，根据groupId查询对应的名称
          if (this.form.groupId) {
            this.form.groupName = this.getNameById(this.form.groupId);
          }
          if (this.form.groupName) {
            //若查询出来的名称存在，则按默认查询负责人信息
            this.getGroupDetail(this.form.groupId);
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    },
    /**
     * 获取分组下的负责人详情
     * @param {String}groupId 分组ID
     * @author lgh
     */
    getGroupDetail(groupId) {
      this.$message.loading("加载中...");
      getGroupDetail(groupId)
        .then(res => {
          this.$message.close();
          this.groupcolumns = res.data.chargerList;
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    }
  }
};
</script>

<style scoped lang="stylus">
>>>.xx {
  >span {
    position: relative;

    &:after {
      position: absolute;
      right: -10px;
      color: #ee0a24;
      font-size: 0.37333rem;
      content: '*';
    }
  }
}
.student-follow-info {
  background-color: #F2F2F2;

  .custom-textarea {
    display: block;

    >>>.van-field__value {
      text-align: left;
      margin-top: 16px;
    }

    >>>.van-field__control {
      text-align: left;
      padding: 0 16px;
      padding-top: 8px;
      background-color: #F2F2F2;
    }
  }

  >>>.custom-field-title {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
    }
  }

  >>>.van-cell:not(:last-child)::after {
    left: 0;
    border-color: #DDD;
  }

  &-save {
    margin-top: 40px;

    .action-save-btn {
      width: 100%;
      height: 49px;
      color: #fff;
      background: rgba(0, 204, 255, 1);
      // 消除默认样式
      margin: 0px;
      padding: 0px;
      border: 0px;
    }
  }

  .communication-list {
    &-item {
      margin-left: 16px;
    }
  }
}

.form-split-title {
  height: 32px;
  line-height: 32px;
  font-size: 14px;
  padding: 0 16px;
  color: #999999;
  background-color: #F2F2F2;
}

.follow-type {
  padding: 16px;
  // border-bottom: solid 1px #DDDDDD;
  background-color: #fff;
  margin-bottom: 1px;

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;

    &-tip {
      color: #999999;
      font-size: 12px;
    }
  }

  &-list {
    >span {
      box-sizing: border-box;
      width: 103px;
      height: 37px;
      line-height: 37px;
      text-align: center;
      color: #999;
      background-color: #F2F2F2;
      display: inline-block;
      border-radius: 4px;
      margin-left: 16px;
      margin-top: 12px;
      position: relative;

      &:nth-child(3n+1) {
        margin-left: 0;
      }

      &.mult-checked::after {
        content: '';
        position: absolute;
        right: -8px;
        top: -8px;
        width: 16px;
        height: 16px;
        background: url('../img/checkbox-selected.png') center center no-repeat;
      }
    }

    .active, .choice {
      color: #FFFFFF;
      background-color: #00CCFF;
    }
  }
}

.content-info-content-items {
  font-size: 15px;
  font-weight: 400;
  line-height: 28px;
  padding: 4px 16px;
  background: rgba(255, 255, 255, 1);

  .content-info-item {
    display: flex;

    &-label {
      width: 90px;
      color: #999999;
    }

    &-val {
      text-align: left;
      margin-left: 12px;
      flex: 1;
      width: 150px;
      word-break: break-all;
      word-wrap: break-word;
    }
  }
}
</style>
