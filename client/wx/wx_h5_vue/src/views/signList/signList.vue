<template>
  <transition :name="transitionName">
    <keep-alive>
      <router-view class="transitionBody"></router-view>
    </keep-alive>
  </transition>
</template>

<script>
export default {
  name: "signList",
  data() {
    return {
      transitionName: "transitionLeft"
    };
  },
  watch: {
    $route(to, from) {
      console.log(to, from, 852);
      const arr = [
        "/signList",
        "/signList/backups",
        "/signList/volunteer",
        "/signList/schoolList",
        "/signList/information",
        "/signList/contact",
        "/signList/schoolInfo",
        "/signList/family",
        "/signList/activeMajor",
        "/signList/schoolDetailInfo"
      ];
      this.transitionName =
        arr.indexOf(to.path) > arr.indexOf(from.path)
          ? "transitionLeft"
          : "transitionRight";
    }
  }
};
</script>

<style scoped>
.transitionBody {
  transition: all 0.26s ease-out;
}
.transitionLeft-enter,
.transitionRight-leave-active {
  -webkit-transform: translate(100%, 0);
  transform: translate(100%, 0);
}
.transitionLeft-leave-active,
.transitionRight-enter {
  -webkit-transform: translate(-100%, 0);
  transform: translate(-100%, 0);
}
.transitionLeft-enter-active,
.transitionRight-enter-active {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
}
</style>
