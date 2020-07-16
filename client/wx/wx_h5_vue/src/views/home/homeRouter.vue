<template>
  <transition :name="transitionName">
    <router-view class="transitionBody"></router-view>
  </transition>
</template>

<script>
export default {
  name: "homeRouter",
  data() {
    return {
      transitionName: "transitionLeft"
    };
  },
  watch: {
    $route(to, from) {
      console.log(to, from, 852);
      const arr = ["home", "homeDetail", "departmentDetail", "objectDetail"];
      this.transitionName =
        arr.indexOf(to.name) > arr.indexOf(from.name)
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
