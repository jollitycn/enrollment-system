<template>
  <div>
    <div class="left-border" :style="{color: color,fontSize : size+'px',borderBottomWidth: borderBottomWidth+'px'}">
      {{title}}
    </div>
  </div>
</template>

<script>
    export default {
        name: 'LeftBorder',
        props:{
            title :{
                type: String,
                default : '信息展示'
            },
            color :{
                type: String,
                default : '#029AE6'
            },
            size :{
                type: Number,
                default : 18
            },
            borderBottomWidth: {
                type: Number,
                default : 1
            }
        }
    };
</script>

<style scoped>
.left-border{
  margin-left: 16px;
  /* height: 40px; */
  line-height: 40px;
  /* border-bottom: 1px solid #cdcdcd; */
  padding-bottom: 7px;
  position: relative;
  padding-top: 3px;
}
.left-border:before{
  content: '';
  position: absolute;
  background: #029AE6;
  width: 3px;
  height: 40px;
  left: -11px;
}
</style>