<template>
  <div>
    <van-sticky :offset-top="0" v-if="isShow">
      <h5-header :leftStatus="true" title="志愿管理"></h5-header>
    </van-sticky>
    <div class="volunteer">
      <van-tabs
        color="#00CCFF"
        :line-height="4"
        title-active-color="#00CCFF"
        :ellipsis="false"
        v-model="active"
        :sticky="true"
        :offset-top="isShow ? 60 : 0"
      >
        <van-tab
          :title="item.collegeName"
          v-for="(item, index) in list"
          :key="index"
        >
          <div
            class="volunteer-item"
            v-for="item1 in item.majors"
            :key="item1.majorId"
          >
            <div class="volunteer-item-title">
              <span class="volunteer-item-title-left">{{
                item1.majorName
              }}</span>
              <span
                :class="[
                  'volunteer-item-title-right',
                  { isDisabled: isActive(item1.majorId) }
                ]"
                @click="activeMajor(item1, isActive(item1.majorId))"
                >{{ isActive(item1.majorId) ? "已选择" : "选择该专业" }}</span
              >
            </div>
            <div class="content">
              <div class="content-first">
                <span class="content-first-one">专业</span>
                <span class="content-first-two">{{ item1.majorName }}</span>
              </div>
              <div class="content-first">
                <span class="content-first-one">学费</span>
                <span class="content-first-two">{{ item1.fee }}/学期</span>
              </div>
              <div class="content-first">
                <span class="content-first-one">学制</span>
                <span class="content-first-two">{{ item1.majorType }}年</span>
              </div>
              <div class="content-second">
                {{ item1.careerForward }}
              </div>
              <div
                class="content-first"
                style="margin-bottom: 0;margin-top: 30px"
              >
                <div
                  class="content-first-one content-first-one-new"
                  style="width: 100%"
                  @click="
                    activeId === item1.majorId
                      ? (activeId = 0)
                      : (activeId = item1.majorId)
                  "
                >
                  <span>详细介绍</span>
                  <img
                    :src="img"
                    alt=""
                    :style="{
                      transform:
                        activeId === item1.majorId
                          ? 'rotate(90deg)'
                          : 'rotate(0deg)'
                    }"
                  />
                </div>
                <div
                  class="content-comment"
                  v-show="activeId === item1.majorId"
                  v-html="resetHtml(item1.comment)"
                ></div>
              </div>
            </div>
          </div>
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
import { Sticky, Tab, Tabs } from "vant";
import Header from "@/components/cheader";
import { queryCollegeAndMajor } from "@/api/volunteer/volunteer";
import { webImgDir } from "@/config/index";
export default {
  name: "activeMajor",
  components: {
    vanSticky: Sticky,
    vanTab: Tab,
    vanTabs: Tabs,
    h5Header: Header
  },
  data() {
    return {
      img: require("../../../../../assets/image/right.png"),
      activeId: "",
      showToast: false,
      active: 0,
      list: [],
      level: 1,
      // 如果是线下环境，显示，方便调试。线上环境，去掉
      isShow: process.env.NODE_ENV === "development" ? true : false
    };
  },
  filters: {},
  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
    //判断该专业是否已经选择
    isActive(id) {
      return this.activeData.indexOf(id) !== -1;
    },
    //获取所有信息
    queryCollegeAndMajor() {
      queryCollegeAndMajor()
        .then(res => {
          console.log(res);
          this.list = res.data;
          if (this.showToast) {
            this.showToast.clear();
            this.showToast = false;
          }
        })
        .catch(err => {
          this.showToast.clear();
          console.log(err);
        });
    },
    //选择该专业
    activeMajor(item, type) {
      if (type) {
        return;
      }
      let data = item;
      data.level = this.level;
      this.$router.push({
        name: "volunteer",
        params: data
      });
    }
  },
  created() {
    this.showToast = this.$toast.loading({
      duration: 0, // 持续展示 toast
      forbidClick: true,
      message: "加载中..."
    });
  },
  activated() {
    this.level = this.$route.query.level;
    this.activeData = this.$route.query.data;
    this.queryCollegeAndMajor();
    console.log(this.activeData);
  }
};
</script>

<style scoped lang="stylus">
.volunteer
  .volunteer-item
    border 1px solid #eee
    border-radius 10px
    background-color bgColor
    padding 15px
    margin: 16px
    >>>.van-cell
      padding 0
    .volunteer-item-title
      color fontTitleColor
      font-size fontBigSize
      font-weight bold
      margin-bottom 30px
      display: flex;
      justify-content: space-between
      height: 40px;
      align-items: center;
      >.volunteer-item-title-right
        font-size fontMinSize
        border: 1px solid
        padding: 6px 12px
        border-radius: 4px
        color btnBgColor
      >.isDisabled
        color #969799
    .content
      .content-first
        margin-bottom: 10px;
        margin-top: 15px;
        >span
          display inline-block
        .content-first-one-new
          display flex
          justify-content: space-between;
          >img
            width 6px
            height 10px
            align-items: center;
        .content-first-one
          width 30%
          color fontMiniFontColor
        .content-first-two
          color fontTitleColor
        .content-first-three
          width 30%
          text-align right
          color btnBgColor
        .content-comment
          padding: 10px;
          line-height: 24px;
          >>>img
              max-width 100%
      .content-second
        margin-top: 20px;
        background-color: #f8f8f8;
        padding: 20px;
</style>
