<template>
  <div class="schoolDetail">
    <div class="schoolDetail-header">
      <div>
        <img :src="imgReset(from.recruitSchoolLogo)" alt="" />
      </div>
    </div>
    <div class="schoolDetail-title">{{from.recruitSchoolName}}</div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">校长</span
      ><span class="schoolDetail-list-right">{{from.principal}}</span>
    </div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">手机号码</span
      ><span class="schoolDetail-list-right">{{from.telephoneNumber}}</span>
    </div>
    <div class="schoolDetail-list">
      <span class="schoolDetail-list-left">学校地址</span
      ><span class="schoolDetail-list-right">{{from.address}}</span>
    </div>
    <div class="schoolDetail-list-content">
      <div class="schoolDetail-list-content-left">学校简介</div
      ><div class="schoolDetail-list-content-right"
             v-html="resetHtml(from.description)"
        ></div
      >
    </div>
  </div>
</template>

<script>
import { getByRecruitSchoolId } from "@/api/user/user";
import { webImgDir } from "../../../config";
export default {
  name: "schoolDetail",
  data() {
    return {
      img: require("../../../assets/image/student1.png"),
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
    // 重新设置图片地址
    imgReset(img) {
      return webImgDir + "/static/" + img;
    },
    getByRecruitSchoolId() {
      getByRecruitSchoolId(this.id)
        .then(res => {
          console.log(res, "学校简介");
          this.from = res.data
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.getByRecruitSchoolId();
  }
};
</script>

<style scoped lang="stylus">
.schoolDetail
  .schoolDetail-header
    margin-top 34px
    text-align: center;
    >div
        width 72px
        height 72px
        border-radius: 50%;
        margin 0 auto
        >img
          width 100%
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
        min-width: 80px;
        text-align: left;
        display block
        font-weight: bold;
    .schoolDetail-list-right
        color #666
        margin-top 8px
        display: inline-block;
  .schoolDetail-list-content
    padding: 0 16px;
    .schoolDetail-list-content-left
      color fontTitleColor
      display block
      font-weight: bold;
    .schoolDetail-list-content-right
      color #666
      margin-top 12px
      display block
      font-size 14px
      >>>img
        max-width 100%
      >>>p
        color #666
        line-height: 21px;
        margin-bottom: 15px;
        font-size 14px
      >>>span
         color #666
         line-height: 21px;
         font-size 14px
</style>
