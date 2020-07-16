<template>
  <div id="app">
    <router-view />
  </div>
</template>
<style lang="stylus">
#app {
  background-color: #f2f2f2;
  font-size: fontMedSize;
}

@mixin border-bottom border-top margin-bottom {
  position: relative;
  transform: scaleY(0.5);

  &::after {
    position: absolute;
    content: '';
    left: 0;
    bottom: 0;
    width: 100%;
    height: 1px;
    background: #ededed;
    transform: scaleY(0.5);
  }
}
</style>
