<template>
  <div class="student-target-edit">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :title="title"></h5-header>
    </van-sticky>

    <van-form ref="stuForm" validate-first input-align="right" error-message-align="right">
      <!-- 学生姓名 -->
      <van-field
        label-class="custom-field-title xx"
        v-model.trim="form.studentName"
        clearable
        maxlength="7"
        label="学生姓名"
        placeholder="请填写学生姓名"
        :rules="rules.studentName"
      />
      <!-- 联系方式 -->
      <van-field
        label-class="custom-field-title xx"
        v-model.trim="form.studentTelephone"
        clearable
        maxlength="11"
        label="联系方式"
        :rules="rules.studentTelephone"
        placeholder="请填写联系电话"
      />
      <!-- 父亲姓名 -->
      <van-field
        label-class="custom-field-title"
        v-model.trim="form.fatherName"
        clearable
        maxlength="4"
        label="父亲姓名"
        placeholder="请填写父亲姓名"
      />
      <!-- 联系方式 -->
      <van-field
        :rules="rules.isTelephone"
        label-class="custom-field-title"
        v-model.trim="form.fatherTelephone"
        clearable
        maxlength="11"
        label="联系方式"
        placeholder="请填写父亲的联系电话"
      />
      <!-- 母亲姓名 -->
      <van-field
        label-class="custom-field-title"
        v-model.trim="form.motherName"
        clearable
        maxlength="4"
        label="母亲姓名"
        placeholder="请填写母亲姓名"
      />
      <!-- 联系方式 -->
      <van-field
        :rules="rules.isTelephone"
        label-class="custom-field-title"
        v-model.trim="form.motherTelephone"
        clearable
        maxlength="11"
        label="联系方式"
        placeholder="请填写母亲的联系电话"
      />
      <!-- 跟进等级 -->
      <div class="follow-type">
        <div class="follow-type-title">跟进等级</div>
        <div class="follow-type-list">
          <span
            :v-model="form.level"
            v-for="(item, index) in typeList"
            :key="index"
            @click="changeType(index)"
            :class="{ active: index == form.level }"
          >{{ item.title }}</span>
        </div>
      </div>
      <!-- 跟进方向 -->
      <van-field
        label-class="custom-field-title"
        v-model.trim="form.direction"
        label="跟进方向"
        clearable
        maxlength="20"
        placeholder="请填写跟进方向"
      />
      <!-- 意向原因 -->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model.trim="form.reason"
        rows="6"
        clearable
        maxlength="100"
        label="意向原因"
        type="textarea"
        placeholder="请填写意向原因"
      />
    </van-form>
    <div class="student-target-edit-save">
      <button class="action-save-btn" @click="save">保存</button>
    </div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import Vue from "vue";
import rulesFrom from "@/assets/rules/rulesFrom";
import { Form, Field, Sticky } from "vant";

Vue.use(Form);
Vue.use(Field);
Vue.use(Sticky);

import { saveStudent, detail } from "@/api/student/student-target-manage";

export default {
  name: "student-target-edit",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      studentId: "",
      typeList: [
        { id: 0, title: "高" },
        { id: 1, title: "中" },
        { id: 2, title: "低" }
      ],
      form: {
        aimStudentId: "",
        classId: "",
        createTime: "",
        creatorId: 0,
        creatorName: "",
        direction: "",
        fatherName: "",
        fatherTelephone: "",
        level: 0,
        motherName: "",
        motherTelephone: "",
        reason: "",
        status: "",
        studentName: "",
        studentTelephone: ""
      },
      title: "新增目标生",
      titleList: ["新增目标生", "目标生修改"],
      rules: {
        studentName: [{ required: true, message: "学生姓名不能为空" }],
        studentTelephone: [
          {
            pattern: rulesFrom.phone.rules,
            message: "请输入正确的学生联系电话"
          }
        ],
        isTelephone: [
          { validator: this.isTelephoneRule, message: "请输入正确手机号" }
        ],
        fatherTelephone: [
          {
            pattern: rulesFrom.phone.rules,
            message: "请输入正确的父亲联系电话"
          }
        ],
        motherTelephone: [
          {
            pattern: rulesFrom.phone.rules,
            message: "请输入正确的母亲联系电话"
          }
        ]
      }
    };
  },
  methods: {
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      this.form.level = this.typeList[index].id;
    },
    save() {
      this.$refs["stuForm"].validate().then(
        () => {
          this.saveStudent(this.form);
        },
        err => {
          console.warn("校验失败：", err);
        }
      );
    },
    saveStudent(data) {
      this.$message.loading("正在保存...");
      saveStudent(data)
        .then(res => {
          console.log("返回结果：", res);
          this.$message.success("保存成功！");
          //返回上一个页面
          this.$router.go(-1);
        })
        .catch(err => {
          console.error("保存失败：", err);
        });
    },
    queryDetail(id) {
      this.$message.loading();
      detail(id)
        .then(res => {
          this.$message.close();
          console.log("返回结果：", res);
          this.form = res.data;
        })
        .catch(err => {
          console.error("查询详情失败：", err);
        });
    },
    //验证手机
    isTelephoneRule(val) {
      if (!val) {
        return true;
      }
      let rules = rulesFrom.phone.rules;
      if (rules.test(val)) {
        return true;
      }
      return false;
    }
  },
  created() {
    const studentId = this.$route.params.id;
    this.studentId = studentId;
    //查询详情
    if (studentId) {
      this.title = this.titleList[1];
      this.queryDetail(studentId);
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

.student-target-edit {
  background-color: #fff;

  .van-cell {
    font-size: 15px;
    padding-left: 0;
    padding: 16px;
    // margin-left: 16px;
    line-height: 23px;
  }

  .van-cell:not(:last-child)::after {
    left: 0;
  }

  >>>.custom-field-title {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
    }
  }

  .custom-textarea {
    display: block;

    >>>.van-field__value {
      text-align: left;
      margin-top: 16px;
    }

    >>>.van-field__control {
      text-align: left;
      padding: 12px 16px;
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
      outline: none;
    }
  }
}

.follow-type {
  &:after {
    position: absolute;
    box-sizing: border-box;
    content: ' ';
    pointer-events: none;
    right: 0;
    bottom: 0;
    left: 0;
    border-bottom: 0.02667rem solid #ebedf0;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
  }

  padding: 16px;
  position: relative;

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
      margin-left: 28px;
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
