<template>
  <div class="bott">
    <van-tabbar v-model="active" route :safe-area-inset-bottom="true">
      <van-tabbar-item  active-color="#00ccff" v-for="(item,index) in list" :key="index" :to="item.router">
        <span>{{item.title}}</span>
        <template #icon="props">
          <img :src="props.active ? item.active : item.inactive" />
        </template>
      </van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { Tabbar, TabbarItem } from "vant";
export default {
  name: "ctabbar",
  components: {
    vanTabbar: Tabbar,
    vanTabbarItem: TabbarItem
  },
  data() {
    return {
      active: 0,
      list: [
        {
          title: "首页",
          active: require("../assets/image/home_.png"),
          inactive: require("../assets/image/home.png"),
          router: "/"
        },
        {
          title: "报名资料",
          active: require("../assets/image/info_.png"),
          inactive: require("../assets/image/info.png"),
          router: "/signList"
        },
        {
          title: "我的",
          active: require("../assets/image/user_.png"),
          inactive: require("../assets/image/user.png"),
          router: "/user"
        }
      ]
    };
  }
};
</script>

<style scoped lang="stylus">
.bott
  >>>.van-tabbar-item--active
        color: #00ccff;
</style>
