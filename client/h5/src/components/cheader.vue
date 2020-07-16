<template>
  <div class="cheader">
    <span class="cheader-left">
      <img
        src="@/assets/images/return.png"
        name="arrow-left"
        @click="emitLeft"
        class="arrow-left-img"
        :style="{ opacity: leftStatus ? 1 : 0 }"
      />
    </span>
    <span class="cheader-title">{{ title }}</span>
    <span class="cheader-right" @click="emit">{{ rigText }}</span>
  </div>
</template>

<script>
// import { Icon } from "vant";
export default {
  name: "cheader",
  // components: {
  //   vanIcon: Icon
  // },
  props: {
    leftStatus: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: "首页"
    },
    rigText: {
      type: String,
      default: ""
    },
    isLeft: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    emit() {
      this.$emit("clickHeadAction");
    },
    emitLeft() {
      if (this.isLeft) {
        this.$emit("clickHeadLeft");
      } else {
        this.$router.go(-1);
      }
    }
  }
};
</script>

<style scoped lang="stylus">
.cheader {
  display: flex;
  justify-content: space-between;
  height: 30px;
  align-items: center;
  // border-bottom: 0.5px solid #eee;
  padding: 10px 16px;
  background-color: bgColor;
  padding-top: 54px;

  span {
    color: fontTitleColor;
    font-size: 18px;
  }

  .cheader-left {
    font-size: 0.5rem;
    width: 60px;
    height: 22px;

    .arrow-left-img {
      width: 12px;
      height: 22px;
    }
  }

  .cheader-title {
    font-weight: bold;
    flex: 1;
    text-align: center;
  }

  .cheader-right {
    color: #00ccff;
    font-size: 15px;
    text-align: right;
    width: 60px;
    height: 22px;
  }
}
</style>
