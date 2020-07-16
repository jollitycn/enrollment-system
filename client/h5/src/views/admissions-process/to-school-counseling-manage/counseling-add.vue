<template>
  <div class="counseling-add">
    <div class="counseling-add-header">
      <van-sticky :offset-top="0">
        <h5-header :leftStatus="true" title="到校咨询管理"></h5-header>
      </van-sticky>
    </div>
    <div class="counseling-add-content">
      <!-- 咨询接待信息 -->
      <div class="reception-info">
        <div class="reception-info-title">接待日期：{{ scheduList.opendayDate }}</div>
        <div class="reception-info-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">接待人</div>
            <div class="content-info-item-val">{{ scheduList.receptionistName }}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">联系方式</div>
            <div class="content-info-item-val">{{ scheduList.telephone }}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">接待人数</div>
            <div class="content-info-item-val">{{ scheduList.actualAmount }}</div>
          </div>
        </div>
      </div>
      <!-- 个人/学校导航切换 -->
      <div class="person-school-navs">
        <div :class="{ active: isActive }" @click="changeType(1)" class="person-school-navs-item">个人</div>
        <div
          :class="{ active: !isActive }"
          @click="changeType(0)"
          class="person-school-navs-item"
        >学校</div>
      </div>
      <van-divider :style="{margin: '0' }" />
      <!-- 个人 -->
      <counseling-add-person-form v-show="isActive" :info="form" :on-save="save" />
      <!-- 学校 -->
      <counseling-add-school-form v-show="!isActive" :info="form2" :on-save="saveSchool" />
    </div>
  </div>
</template>

<script>
import { addConsultationRegister } from "../../../api/admissions-process/to-school-counseling-manage";
import Header from "@/components/cheader";
import CounselingAddPersonForm from "./components/counseling-add-person-form";
import CounselingAddSchoolForm from "./components/counseling-add-school-form";
import Vue from "vue";
import { Sticky } from "vant";
import { Divider } from "vant";
Vue.use(Divider);
Vue.use(Sticky);
export default {
  name: "counseling-add",
  components: {
    "h5-header": Header,
    "counseling-add-person-form": CounselingAddPersonForm,
    "counseling-add-school-form": CounselingAddSchoolForm
  },
  data() {
    return {
      scheduList: {},
      isActive: true,
      form: {
        amount: "",
        attendTime: "",
        classId: "",
        comment: "",
        consultationDate: "",
        consultationType: 0,
        isLunch: 0,
        sourceSchoolId: "",
        studentName: "",
        studentPublicRelationId: 0,
        teacher: "",
        telephone: ""
      },
      form2: {
        amount: "",
        attendTime: "",
        classId: "",
        comment: "",
        consultationDate: "",
        consultationType: 1,
        isLunch: 0,
        sourceSchoolId: "",
        studentName: "",
        studentPublicRelationId: 0,
        teacher: "",
        telephone: ""
      }
    };
  },
  created() {
    console.log(this.$route.query);
    this.scheduList = this.$route.query;
  },
  methods: {
    save(form) {
      form.amount = 1;
      this.addConsultationRegister(form);
    },
    saveSchool(form) {
      this.addConsultationRegister(form);
    },
    changeType(type) {
      if (type) {
        this.form.consultationType = 0;
        this.isActive = true;
      } else {
        this.form.consultationType = 1;
        this.isActive = false;
      }
    },
    /**
     * 增加咨询登记
     * @param form{object} 增加咨询登记信息数据
     * @author LGH
     */
    addConsultationRegister(form) {
      let data = { ...form };
      data.consultationDate = this.scheduList.opendayDate;
      data.consultationId = this.scheduList.consultationId;
      data.attendTime =
        this.scheduList.opendayDate + " " + data.attendTime + ":00";
      addConsultationRegister(data).then(() => {
        this.$message.success("增加咨询登记成功！");
        this.$router.go(-1);
      });
    }
  }
};
</script>

<style lang="stylus" scoped>
.counseling-add {
  background-color: #fff;

  &-content {
    .reception-info {
      margin: 8px 16px;
      padding: 16px;
      background-color: #F8F8F8;
      border-radius: 4px;
      font-size: 14px;
      font-weight: 400;
      line-height: 21px;

      &-title {
        // padding-left: 16px;
        font-weight: 600;
        color: #333333;
      }
    }

    .person-school-navs {
      display: flex;

      &-item {
        flex: 1;
        height: 37px;
        text-align: center;
        margin: 16px;
        line-height: 37px;
        background: #FFFFFF;
        border-radius: 20px;
        color: #333333;
        font-size: 15px;
        font-weight: 500;
      }

      &-item.active {
        background: #00CCFF;
        color: #fff;
      }
    }
  }
}

.content-info-content-items {
  font-size: 15px;
  font-weight: 400;
  line-height: 28px;

  // padding: 4px 16px;
  .content-info-item {
    display: flex;

    &-label {
      width: 60px;
      color: #999999;
    }

    &-val {
      margin-left: 12px;
      flex: 1;
    }
  }
}
</style>
