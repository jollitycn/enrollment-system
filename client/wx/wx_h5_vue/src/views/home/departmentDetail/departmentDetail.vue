<template>
  <div class="departmentall">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="院系详情"></h5-header>
    </van-sticky>
    <div class="object">
      <div class="object-title">院系专业</div>
      <div class="object-item">
        <div class="object-item-list1 tit">
          <div class="object-item-list-name">专业名称</div>
          <div class="object-item-list-obj-num">学制</div>
          <div class="object-item-list-need-num">招生人数</div>
          <div style="opacity: 0">
            <img :src="img" alt="" />
          </div>
        </div>
        <div class="object-item-list" v-for="(item,index) in list" :key="index" @click="go(item.majorId)">
          <div class="object-item-list-name">{{item.majorName}}</div>
          <div class="object-item-list-obj-num">{{item.majorType}}</div>
          <div class="object-item-list-need-num">{{item.recruitAmount}}</div>
          <div>
            <img :src="img" alt="" />
          </div>
        </div>
      </div>
    </div>
    <div>
      <div class="department">
        <div class="department-list">
          <div class="department-list-title">{{ form.collegeName }}</div>
          <div class="department-list-center">
            <div class="department-list-center-left">招生人数</div>
            <div class="department-list-center-right">
              {{ form.recruitAmount }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">院系负责人</div>
            <div class="department-list-center-right">
              {{ form.collegeManager }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">招生对象</div>
            <div class="department-list-center-right">
              {{ form.recruitTarget }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">培养目标</div>
            <div class="department-list-center-right">
              {{ form.cultivateAim }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">就业方向</div>
            <div class="department-list-center-right">
              {{ form.careerForward }}
            </div>
          </div>
          <div class="schoolDetail-list-content">
            <div class="schoolDetail-list-content-left">院系简介</div>
            <div
              class="schoolDetail-list-content-right"
              v-html="resetHtml(form.comment)"
            ></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Sticky } from "vant";
import Header from "@/components/cheader";
import { mapMutations } from "vuex";
import {
  queryCollegeInfoById,
  queryMajorInfoByCollegeId
} from "@/api/user/user";
import { webImgDir } from "../../../config";
export default {
  name: "departmentDetail",
  components: {
    h5Header: Header,
    vanSticky: Sticky
  },
  data() {
    return {
      form: {},
      list: [],
      id: "",
      img: require("../../../assets/image/right.png")
    };
  },
  methods: {
    ...mapMutations(["setActiveIndex"]),
    //去往专业详情页面
    go(id) {
      this.$router.push({
        path: `/objectDetail/${this.id}/${id}`
      });
    },
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
    //获取具体的院系详细信息
    queryCollegeInfoById() {
      queryCollegeInfoById(this.id)
        .then(res => {
          console.log(res);
          this.form = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 获取学校院系下所有的专业列表信息
    queryMajorInfoByCollegeId() {
      queryMajorInfoByCollegeId(this.id)
        .then(res => {
          console.log(res, "专业列表信息");
          this.list = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.queryCollegeInfoById();
    this.queryMajorInfoByCollegeId();
  },
  beforeDestroy() {
    this.setActiveIndex(2)
  }
};
</script>

<style scoped lang="stylus">
.departmentall
  margin-bottom: 20px;
  .department
      margin-top: 12px;
      margin-bottom: 20px;
      .department-list
          margin: 0 16px;
          border-radius: 4px;
          background-color #fff
          .department-list-title
              font-size: 18px;
              font-weight: bold;
              color: #333;
              margin-bottom: 24px;
          .department-list-header
              display flex
              justify-content space-between
              align-items center
              .department-list-header-left
                  font-size 18px
                  font-weight bold
              .department-list-header-right
                  font-size 15px
                  color #999
                  >img
                      width: 5px;
                      margin-left 10px
          .department-list-center
            margin-bottom: 20px;
            font-size 15px
          .department-list-center-left
            color fontTitleColor
            text-align: left;
            display block
            font-weight: bold;
            margin-top 12px
          .department-list-center-right
            color fontMiniFontColor
            margin-top 8px
            display block
          .schoolDetail-list-content
            margin-top: 10px;
          .schoolDetail-list-content-left
            color fontTitleColor
            font-weight bold
          .schoolDetail-list-content-right
            color #333
            margin-top 10px
            >>>img
              max-width 100%
            >>>p
              line-height: 22px;
              margin-bottom: 15px;
              color #999
  .object
    border-bottom: 8px solid #f5f5f5;
    .object-title
      font-size 18px
      color #333
      font-weight: bold;
      padding 12px 16px
    .object-item
      .tit
        border-bottom 0.5px solid #DDDDDD
        >div
          color #999 !important
      .object-item-list1
        display flex
        justify-content: space-between
        padding: 12px 16px;
        .object-item-list-name
          width 100px
        .object-item-list-obj-num
          width 50px
          text-align: center;
        .object-item-list-need-num
          width 70px
          text-align: center;
      .object-item-list:nth-child(odd)
        border-bottom 0.5px solid #DDDDDD
      .object-item-list
        display flex
        justify-content: space-between
        padding: 0px 16px;
        height: 55px;
        align-items: center;
        >div
          color #333
          font-size 15px
          font-weight bold
        .object-item-list-name
          width 100px
        .object-item-list-obj-num
          width 50px
          text-align: center;
        .object-item-list-need-num
          width 70px
          text-align: center;
         img
           width: 6px;
</style>
