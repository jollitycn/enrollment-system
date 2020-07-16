<template>
  <div class="finished-show">
    <img :src="img" alt="">
    <div>暂无数据</div>
  </div>
</template>

<script>
export default {
  name: "finishedShow",
  data () {
    return {
      img: require("../../assets/image/not.png")
    };
  }
};
</script>

<style scoped lang="stylus">
.finished-show
  margin-top 35%
  width: 100%;
  text-align: center;
  img
    width 187px
    height 144px
  >div
    color: #999999;
    font-size: 14px;
</style>
