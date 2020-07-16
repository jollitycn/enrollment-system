<template>
  <div class="student-counseling">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="到校咨询"></h5-header>
    </van-sticky>
    <div class="person-info-list" v-if="list.length!=0&&list!=null&&list!=[]">
      <div class="person-info-list-item" v-for="(item,index) in list" :key="index">
        <!-- <div class="person-info-list-item-title">{{item.studentName}}{{index}}</div> -->
        <div class="person-info-list-item-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">预约时间</div>
            <div class="content-info-item-val">{{item.attendTime}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">联系方式</div>
            <div class="content-info-item-val">{{item.telephone}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">计划到校时间</div>
            <div class="content-info-item-val">{{item.attendTime}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">是否午餐</div>
            <div class="content-info-item-val">{{isLunch[item.isLunch]}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">其他说明</div>
            <div class="content-info-item-val">{{item.comment}}</div>
          </div>
        </div>
      </div>
    </div>
    <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
  </div>
</template>

<script>
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import Vue from "vue";
import { Sticky } from "vant";
Vue.use(Sticky);

import { getConsultationListByStudent } from "@/api/admissions-process/to-school-counseling-manage";

export default {
  name: "student-counseling",
  components: {
    "h5-header": Header,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      studentId: null,
      studentPublicRelationId: null,
      isLunch: ["是", "否"],
      // 列表
      list: [],
      loading: false,
      finished: false
    };
  },
  methods: {
    getConsultationListByStudent() {
      let id = this.studentPublicRelationId;
      this.$message.loading("正在加载...");
      getConsultationListByStudent(id)
        .then(res => {
          this.$message.close();
          this.list = res.data;
          console.log(this.list, 111);
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.studentId = this.$route.params.id;
    this.studentPublicRelationId = this.$route.params.relationId;
    //获取学生的咨询信息
    this.getConsultationListByStudent();
  }
};
</script>

<style lang="stylus" scoped>
.student-counseling {
  height: 100vh;
  background-color: #F5F5F5;

  .person-info-list {
    &-item {
      margin: 16px;
      background-color: #fff;
      border-radius: 4px;
      font-size: 14px;
      font-weight: 400;
      line-height: 21px;

      &-title {
        padding-left: 16px;
        font-weight: 600;
        color: #333333;
      }
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
    }
  }
}
</style>