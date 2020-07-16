<template>
  <div>
    <van-tabbar active-color="#00ccff" v-model="active" route>
      <van-tabbar-item to="/list">
        任务列表
        <template #icon="props">
          <img :src="props.active?icon.listInactive:icon.listActive" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item icon="search" to="/analysis">
        进度分析
        <template #icon="props">
          <img :src="props.active?icon.analysisInactive:icon.analysisActive" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item icon="friends-o" to="/admissions-process">
        招生过程管理
        <template #icon="props">
          <img :src="props.active?icon.admissionsInactive:icon.admissionsActive" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item icon="user-o" to="/student">
        生源管理
        <template #icon="props">
          <img :src="props.active?icon.studentInactive:icon.studentActive" />
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
      icon: {
        listActive: require("@/assets/images/listActive.png"),
        listInactive: require("@/assets/images/listInactive.png"),
        analysisActive: require("@/assets/images/analysisActive.png"),
        analysisInactive: require("@/assets/images/analysisInactive.png"),
        admissionsActive: require("@/assets/images/admissionsActive.png"),
        admissionsInactive: require("@/assets/images/admissionsInactive.png"),
        studentActive: require("@/assets/images/studentActive.png"),
        studentInactive: require("@/assets/images/studentInactive.png")
      }
    };
  }
};
</script>

<style scoped>
.a {
  background-color: #00ccff;
}
</style>
