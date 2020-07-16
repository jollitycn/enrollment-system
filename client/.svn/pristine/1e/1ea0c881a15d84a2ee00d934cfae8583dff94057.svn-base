<template>
  <transition :name="transitionName">
    <router-view class="transitionBody"></router-view>
  </transition>
</template>

<script>
export default {
  name: "user",
  data() {
    return {
      transitionName: "transitionLeft"
    };
  },
  watch: {
    $route(to, from) {
      console.log(to, from, 852);
      const arr = [
        "/user",
        "/user/seeRate",
        "/user/myNeed",
        "/user/addMyNeed",
        "/user/inSchool",
        "/user/addInSchool",
        "/user/evaluate",
        "/user/message",
        "/user/binding",
        "/user/addInSchool",
        "/user/messageInfo"
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
