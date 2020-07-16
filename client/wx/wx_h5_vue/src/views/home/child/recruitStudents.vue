<template>
  <div class="schoolDetail">
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">招生对象</span
      ><span class="schoolDetail-list-right">{{from.recruitTarget}}</span>
    </div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">注意事项</span
      ><span class="schoolDetail-list-right">{{from.attention}}</span>
    </div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">报到与录取</span
      ><span class="schoolDetail-list-right">{{from.register}}</span>
    </div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">招生计划</span
      ><span class="schoolDetail-list-right"
             v-html="resetHtml(from.planDescription)"
        ></span
      >
    </div>
  </div>
</template>

<script>
import { queryPlanRecruitInfoByRecruitSchoolId } from "@/api/user/user";
import { webImgDir } from "../../../config";
export default {
  name: "recruitStudents",
  data() {
    return {
      id: "",
      from: {}
    };
  },
  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
    queryPlanRecruitInfoByRecruitSchoolId() {
      queryPlanRecruitInfoByRecruitSchoolId(this.id)
        .then(res => {
          console.log(res, "招生简介");
          this.from = res.data
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.queryPlanRecruitInfoByRecruitSchoolId();
  }
};
</script>

<style scoped lang="stylus">
.schoolDetail
    margin-top: 40px;
    .schoolDetail-title
        text-align center
        color #333
        font-size 17px
        margin-top 20px
        margin-bottom: 35px;
        font-weight: bold;
    .schoolDetail-list
        margin-bottom: 20px;
        font-size 15px
        padding: 0 16px;
    .schoolDetail-list-left
        color fontTitleColor
        text-align: left;
        display block
        font-weight: bold;
        margin-top 12px
    .schoolDetail-list-right
        color #666
        margin-top 8px
        display block
        >>>img
              max-width 100%
        >>>p
            color #666
            line-height: 21px;
            margin-bottom: 15px;
            font-size 14px
</style>
