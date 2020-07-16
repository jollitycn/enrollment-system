<template>
  <div class="counseling-add-person-form">
    <van-form validate-first input-align="right" error-message-align="right" @submit="save">
      <!-- 学校 -->
      <van-field
        label-class="xx"
        readonly
        clickable
        name="sourceSchoolName"
        :value="form.sourceSchoolName"
        label="学校"
        placeholder="请选择学校"
        :rules="rules.schoolRules"
        @click="showPopupHandle(0)"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 班级 -->
      <van-field
        label-class="xx"
        readonly
        clickable
        name="className"
        :value="form.className"
        label="班级"
        placeholder="请选择班级"
        :rules="rules.classRules"
        @click="showPopupHandle(1)"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 学生姓名 -->
      <van-field
        label-class="xx"
        readonly
        clickable
        name="studentName"
        :value="form.studentName"
        label="学生姓名"
        placeholder="请选择学生姓名"
        :rules="rules.studentRules"
        @click="showPopupHandle(2)"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 联系方式 -->
      <van-field
        label-class="xx"
        v-model.trim="form.telephone"
        label="联系方式"
        :rules="rules.phoneRules"
        placeholder="请填写联系方式"
      />
      <!-- 到校时间 -->
      <van-field
        label-class="xx"
        readonly
        clickable
        name="time"
        :value="form.attendTime"
        label="到校时间"
        placeholder="请选择到校时间"
        :rules="rules.arrTimeRules"
        @click="showTimePopupHandle"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 是否用餐 -->
      <van-field label-class="xx" label="是否用餐" :rules="rules.lunchRules">
        <template #input>
          <van-radio-group v-model="form.isLunch" checked-color="#00CCFF" direction="horizontal">
            <van-radio name="0">是</van-radio>
            <van-radio name="1">否</van-radio>
          </van-radio-group>
        </template>
      </van-field>
      <!-- 说明 -->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model="form.comment"
        rows="6"
        label="说明"
        type="textarea"
        placeholder="请填写说明"
        maxlength="200"
      />
      <div class="counseling-add-person-form-save">
        <button class="action-save-btn" native-type="save">保存</button>
      </div>
    </van-form>

    <!-- 学校/班级/学生 弹出框 -->
    <van-popup v-model="showPopup" position="bottom">
      <van-picker
        show-toolbar
        :columns="columns"
        :value-key="customTextField"
        @confirm="onConfirm"
        @cancel="showPopup = false"
      />
    </van-popup>

    <!-- 时间选择弹出框 -->
    <van-popup v-model="showTimePopup" position="bottom">
      <van-datetime-picker
        type="time"
        min-hour="9"
        max-hour="18"
        title="请选择到校时间"
        @confirm="onConfirmTime"
        @cancel="showTimePopup = false"
      />
    </van-popup>
  </div>
</template>

<script>
import {
  listSourceSchoolPage1,
  listSchoolClassPage
} from "../../../../api/sourceSchool/sourceSchool";
import { listStudentInfoPage } from "../../../../api/Student-info/Student-info";
import rules from "@/assets/rules/rulesFrom";
import Vue from "vue";
import {
  Form,
  Field,
  Icon,
  Popup,
  Picker,
  DatetimePicker,
  RadioGroup,
  Radio
} from "vant";

Vue.use(Form);
Vue.use(Field);
Vue.use(Icon);
Vue.use(Popup);
Vue.use(Picker);
Vue.use(DatetimePicker);
Vue.use(RadioGroup);
Vue.use(Radio);
export default {
  name: "counseling-add-person-form",
  props: {
    info: {
      type: Object,
      default: () => {
        return {};
      }
    },
    onSave: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      rules: {
        schoolRules: [{ required: true, message: "请选择学校" }],
        classRules: [{ required: true, message: "请选择班级" }],
        studentRules: [{ required: true, message: "请选择学生" }],
        phoneRules: [
          {
            pattern: rules.phone.rules,
            message: rules.phone.message
          }
        ],
        arrTimeRules: [{ required: true, message: "请选择到校时间" }],
        lunchRules: [{ required: true, message: "请选择是否用餐" }]
      },
      searchQuery: {
        pageNum: 1,
        pageSize: 100
      },
      activeIndex: 0,
      typeList: [
        { id: 0, title: "高" },
        { id: 1, title: "中" },
        { id: 2, title: "低" }
      ],
      form: {},
      //弹出框
      showPopup: false,
      columns: [],
      customTextFields: ["sourceSchoolName", "className", "studentName"],
      //弹出宽类型，0，学校列表，1，班级列表，2，学生列表
      popupType: 0,
      //自定义显示的字段名称
      customTextField: "text",
      //学校列表
      schoolList: [],
      //班级列表
      classList: [],
      //学生列表
      studentList: [
        { studentName: "学生1", id: "1" },
        { studentName: "学生2", id: "2" },
        { studentName: "学生3", id: "3" },
        { studentName: "学生4", id: "4" }
      ],

      showTimePopup: false
    };
  },
  created() {
    this.form = this.info;
    this.listSourceSchoolPage1();
  },
  methods: {
    /**
     * 分页获取生源学校信息列表
     * @author LGH
     */
    listSourceSchoolPage1() {
      listSourceSchoolPage1(this.searchQuery).then(res => {
        this.schoolList = res.data;
        if (this.schoolList.length) {
          this.$set(
            this.form,
            "sourceSchoolName",
            this.schoolList[0].sourceSchoolName
          );
          this.$set(
            this.form,
            "sourceSchoolId",
            this.schoolList[0].sourceSchoolId
          );
          this.listSchoolClassPage();
          console.log(this.form);
        }
      });
    },
    /**
     * 分页获取生源学校下的班级
     * @author LGH
     */
    listSchoolClassPage() {
      this.searchQuery.sourceSchoolId = this.form.sourceSchoolId;
      this.$message.loading("正在加载班级信息...");
      listSchoolClassPage(this.searchQuery)
        .then(res => {
          this.classList = res.data.records;
          this.$message.close();
          if (!this.classList || this.classList.length === 0) {
            this.$message.error("该学校下面没有班级！");
            this.$set(this.form, "className", "");
            this.$set(this.form, "classId", "");
            this.$set(this.form, "studentName", "");
            this.$set(this.form, "studentId", "");
            this.studentList = [];
          } else {
            this.$set(this.form, "className", this.classList[0].className);
            this.$set(this.form, "classId", this.classList[0].classId);
            console.log(this.classList);
            this.listStudentInfoPage();
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$set(this.form, "className", "");
          this.$set(this.form, "classId", "");
          this.$message.close();
          this.$message.error("加载班级失败！");
        });
    },
    /**
     * 分页获取所有学生基本信息列表
     * @author LGH
     */
    listStudentInfoPage() {
      let data = { ...this.searchQuery };
      data.classId = this.form.classId;
      listStudentInfoPage(data).then(res => {
        this.studentList = res.data.records;
        if (this.studentList.length) {
          console.log(this.studentList);
          this.$set(
            this.form,
            "studentPublicRelationId",
            this.studentList[0].studentPublicRelationId
          );
          this.$set(this.form, "studentName", this.studentList[0].studentName);
        } else {
          this.$set(this.form, "studentPublicRelationId", "");
          this.$set(this.form, "studentName", "");
        }
      });
    },
    /**
     * 表单班级校验
     * @author LGH
     */
    // validator(val) {
    //   console.log(val, "==>");
    //   if (this.classList.classId == null) {
    //     return false;
    //   }
    //   return true
    // },
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      this.activeIndex = index;
      this.from.type = this.typeList[this.activeIndex].id;
    },
    save() {
      this.onSave && this.onSave(this.form);
    },
    /**
     * 选择器确认处理
     */
    onConfirm(value) {
      //根据类型处理不同的数据
      switch (this.popupType) {
        case 0:
          this.form.sourceSchoolId = value.sourceSchoolId;
          this.form.sourceSchoolName = value.schoolName;
          this.listSchoolClassPage();
          break;
        case 1:
          this.form.classId = value.classId;
          this.form.className = value.className;
          this.listStudentInfoPage();
          break;
        case 2:
          this.form.studentName = value.studentName;
          this.form.studentPublicRelationId = value.studentPublicRelationId;
          break;
      }
      //显示选择的值
      this.form[this.customTextField] = value[this.customTextField];
      this.showPopup = false;
    },
    /**
     * 点击选择时弹出框处理
     * @param type {number} 弹出宽类型，0，学校列表，1，班级列表，2，学生列表
     */
    showPopupHandle(type) {
      console.log("type:", type);
      this.popupType = type;
      this.customTextField = this.customTextFields[type];
      let columns = [];
      switch (type) {
        case 0:
          columns = this.schoolList;
          break;
        case 1:
          columns = this.classList;
          break;
        case 2:
          columns = this.studentList;
          break;
      }
      this.columns = columns;

      this.showPopup = true;
    },
    /**
     * 显示时间弹出框处理
     */
    showTimePopupHandle() {
      this.showTimePopup = true;
    },
    onConfirmTime(date) {
      this.form.attendTime = date;
      this.showTimePopup = false;
    }
  }
};
</script>

<style scoped lang="stylus">
.counseling-add-person-form {
  .van-cell {
    font-size: 15px;
    padding: 16px;
    line-height: 23px;
    // border-bottom: 1px solid #DDDDDD;
  }

  >>>.custom-field-title {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
    }
  }

  >>>.xx {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
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

  >>>.van-radio__label {
    color: #999999;
  }

  >>>.van-radio__icon--checked+span {
    color: #00CCFF;
  }

  .custom-textarea {
    display: block;

    >>>.van-field__value {
      text-align: left;
      margin-top: 16px;
    }

    >>>.van-field__control {
      text-align: left;
      padding: 0 16px;
      background-color: #F2F2F2;
    }
  }

  &-save {
    margin-top: 100px;

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
}

.follow-type {
  padding: 16px;
  // border-bottom: solid 1px #DDDDDD;

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;
  }

  &-list {
    >span {
      color: #999;
      background-color: #F2F2F2;
      padding: 10px 40px;
      display: inline-block;
      border-radius: 4px;
      margin-left: 16px;
    }

    >span:first-child {
      margin-left: 0;
    }

    .active {
      color: #FFFFFF;
      background-color: #00CCFF;
    }
  }
}
</style>
