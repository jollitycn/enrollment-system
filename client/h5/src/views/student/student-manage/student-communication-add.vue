<template>
  <div class="student-communication-add">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="新增沟通" rigText="保存" @clickHeadAction="save"></h5-header>
    </van-sticky>

    <van-form validate-first input-align="right">
      <!-- 沟通日期 -->
      <van-field
        label-class="custom-field-title"
        :value="form.communicationDate"
        readonly
        clickable
        @click="showCalendar = true"
        label="沟通日期"
        placeholder="请选择沟通日期"
      >
        <van-icon slot="right-icon" name="arrow" />
      </van-field>
      <!-- 沟通方式 -->
      <div class="follow-type">
        <div class="follow-type-title">沟通方式</div>
        <div class="follow-type-list">
          <span
            v-for="(item, index) in typeList"
            :key="index"
            @click="changeType(index)"
            :class="{ active: index == form.communicationWay }"
          >{{ item.title }}</span>
        </div>
      </div>

      <!-- 沟通结果 -->
      <van-field
        label-class="custom-field-title"
        class="custom-textarea"
        v-model="form.communicationResult"
        rows="6"
        label="沟通结果"
        type="textarea"
        maxlength="100"
        placeholder="请填写沟通结果"
      />
    </van-form>
    <!-- 弹框 -->
    <van-calendar v-model="showCalendar" @confirm="onConfirm" color="#00CCFF" :round="false" />
  </div>
</template>

<script>
import { addCommunicationRecord } from "../../../api/Student-info/student-follow-info";
import Header from "@/components/cheader";
import Vue from "vue";
import { Sticky, Form, Field, Icon, Calendar } from "vant";

Vue.use(Sticky);
Vue.use(Form);
Vue.use(Field);
Vue.use(Icon);
Vue.use(Calendar);

import { dateFormat } from "@/utils/format";
export default {
  name: "student-communication-add",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      studentId: null,
      showCalendar: false,
      date: "",
      activeIndex: 0,
      typeList: [
        { id: 0, title: "电话" },
        { id: 1, title: "微信" },
        { id: 2, title: "见面" }
      ],
      form: {
        communicationDate: "",
        communicationResult: "",
        communicationWay: 0,
        studentPublicRelationId: this.$route.params.relationId
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
      this.form.communicationWay = index;
    },
    save() {
      this.$message.loading("正在保存...");
      addCommunicationRecord(this.form)
        .then(() => {
          this.$message.close();
          this.$message.success("保存成功");
          this.$router.go(-1);
        })
        .catch(err => {
          console.error("保存失败：", err);
        });
    },
    onConfirm(date) {
      this.showCalendar = false;
      this.form.communicationDate = dateFormat(date, "YYYY-mm-dd");
    }
  },
  created() {
    this.studentId = this.$route.params.id;
    console.log(this.$route.params, 11111111111);
  }
};
</script>

<style scoped lang="stylus">
.student-communication-add {
  background-color: #fff;

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
      padding: 0 16px;
      padding-top:8px;
      background-color: #F2F2F2;
    }
  }
}

.follow-type {
  padding: 16px;
  border-bottom: solid 1px #DDDDDD;

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;
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
        margin-left: 0px;
      }
    }

    .active {
      color: #FFFFFF;
      background-color: #00CCFF;
    }
  }
}
</style>
