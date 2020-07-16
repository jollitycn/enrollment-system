<template>
  <div class="cheader" v-show="isShow">
    <van-icon
      name="arrow-left"
      @click="back"
      class="cheader-left"
      :style="{ opacity: leftStatus ? 1 : 0 }"
    />
    <span class="cheader-title">{{ title }}</span>
    <span class="cheader-right" :style="{ color :rigTextColor}" @click="emit">{{ rigText }}</span>
  </div>
</template>

<script>
import { Icon } from "vant";
export default {
  name: "cheader",
  data() {
    return {
      // 如果是线下环境，显示，方便调试。线上环境，去掉
      isShow: process.env.NODE_ENV === "development" ? true : false
    }
  },
  components: {
    vanIcon: Icon
  },
  props: {
    leftStatus: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ""
    },
    rigText: {
      type: String,
      default: ""
    },
    rigTextColor:{
      type: String,
      default: "#00CCFF"
    }
  },
  methods: {
    emit() {
      this.$emit("clickHeadAction");
    },
    back() {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang="stylus">
.cheader
    display: flex
    justify-content space-between
    height 60px
    align-items center
    border-bottom 0.5px solid #eee
    padding: 0 10px;
    background-color bgColor
    span
        color fontTitleColor
        font-size fontBigSize
        font-weight bold
    .cheader-title
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        word-break: keep-all;
    .cheader-left
        font-size: 0.5rem
    .cheader-right
        font-size fontMedSize
</style>
