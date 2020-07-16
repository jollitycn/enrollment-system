<template>
  <div class="school-class-form">
    <van-form
      validate-first
      :show-error="false"
      error-message-align="right"
      input-align="right"
      @submit="save"
    >
      <!-- 跟进类型 -->
      <div class="follow-type">
        <div class="follow-type-title">跟进等级</div>
        <div class="follow-type-list">
          <span
            :v-model="form.level"
            v-for="(item, index) in typeList"
            :key="index"
            @click="changeType(index)"
            :class="{ active: index == activeIndex }"
          >{{ item.title }}</span>
        </div>
      </div>
      <!-- 班级名称 -->
      <van-field
        label-class="custom-field-title xx"
        v-model="form.className"
        label="班级名称"
        placeholder="请填写班级名称"
        :rules="rules.className"
        maxlength="10"
      />
      <!-- 班主任 -->
      <van-field
        label-class="custom-field-title xx"
        v-model="form.adviser"
        label="班主任"
        placeholder="请填写班主任名称"
        :rules="rules.adviser"
        maxlength="7"
      />
      <!-- 联系电话 -->
      <van-field
        label-class="custom-field-title xx"
        v-model="form.telephone"
        clearable
        maxlength="11"
        label="联系电话"
        placeholder="请填写联系电话"
        :rules="rules.telephone"
      />
      <!-- 学生人数 -->
      <van-field
        label-class="custom-field-title xx"
        v-model="form.amount"
        label="学生人数"
        maxlength="3"
        placeholder="请填写学生人数"
        :rules="rules.planAmount"
      />
      <!-- 目标生 -->
      <van-field
        label-class="custom-field-title xx"
        v-model="form.willingAmount"
        label="目标生"
        maxlength="3"
        placeholder="请填写目标生数量"
        :rules="rules.planAmount"
      />
      <!-- 说明 -->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model="form.comment"
        rows="6"
        label="说明"
        type="textarea"
        placeholder="请填写说明"
        maxlength="50"
      />
      <!-- <div class="school-class-form-save">
        <van-button color="#00CCFFFF" size="large" native-type="submit">提交</van-button>
      </div>-->
      <div class="school-class-form-save">
        <button class="action-save-btn" type="submit">保存</button>
      </div>
    </van-form>
  </div>
</template>

<script>
import Vue from "vue";
import rules from "@/assets/rules/rulesFrom";
import { Form, Field } from "vant";

Vue.use(Form);
Vue.use(Field);

export default {
  name: "school-class-form",
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
      activeIndex: 0,
      typeList: [
        { id: 0, title: "高" },
        { id: 1, title: "中" },
        { id: 2, title: "低" }
      ],
      form: {
        level: 0,
        sourceSchoolId: null
      },
      rules: {
        className: [
          {
            required: true,
            message: "请填写班级名称"
          }
        ],
        adviser: [
          {
            required: true,
            message: "请填写班主任名称"
          }
        ],
        planAmount: [
          {
            pattern: /^\+?[1-9]\d*$/,
            message: "人数为大于0的整数"
          }
        ],
        telephone: [
          {
            pattern: rules.phone.rules,
            message: rules.phone.message
          }
        ]
      }
    };
  },
  watch: {
    info() {
      this.form = this.info;
      this.typeList[0].id = this.form.level;
      console.log("this.typeList[0].id", this.typeList[0].id);
    }
  },
  methods: {
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      this.activeIndex = index;
      this.form.level = this.typeList[this.activeIndex].id;
    },
    save() {
      this.onSave && this.onSave(this.form);
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

.school-class-form {
  .van-cell {
    font-size: 15px;
    padding: 16px;
    line-height: 23px;

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
      padding: 8px 16px;
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
  position: relative;

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
