
<template>
  <van-empty
    class="custom-image"
    :image="calcOption.img"
    :description="calcOption.desc"
    :style="{height:height}"
  />
</template>

<script >
import EmptyData from "@/assets/images/empty-data.png";
import NetworkError from "@/assets/images/network-error.png";

import Vue from "vue";
import {Empty } from "vant";
Vue.use(Empty);


export default {
  name: "custom-empty",
  props: {
    type: {
      type: Number,
      default: 1
    },
    height:String
  },
  data(){
      return {
      }
  },
  computed:{
      calcOption(){
          let img= null;
          let desc = '';
          switch (this.type) {
              case 1:
                  img = EmptyData;
                  desc = '暂无内容';
                  break;
              case 2:
                  img = NetworkError;
                  desc = '请检查您的网络';
                  break;
              default:
                  img = EmptyData;
                  desc = '暂无内容';
                  break;
          }
          return {
              img,
              desc
          };
      }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
.custom-image {
    >>>.van-empty__image {
        width: 187px;
        height: 144px;
    }
}

</style>
