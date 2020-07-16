<template>
  <div class="class-detail-info">
    <content-info :show-title="false" @click.native="clickHandle" :items="items" :labelWidth="labelWidth" />
  </div>
</template>

<script>
import ContentInfo from "./content-info";
export default {
  name: "class-detail-info",
  components: {
    "content-info": ContentInfo
  },
  props: {
    info:{
      type: Object,
      default: () => {
        return {
          classId:null
        };
      }
    }
  },
  data() {
    return {
      labelWidth: "60",
      items: [
        {
          label: "跟进等级",
          name:'level',
          value: ""
        },
        {
          label: "班主任",
          name:'adviser',
          value: ""
        },
        {
          label: "联系方式",
          name:'telephone',
          value: ""
        },
        {
          label: "学生人数",
          name:'amount',
          value: ""
        },
        {
          label: "目标招生",
          name:'willingAmount',
          value: ""
        },
        {
          label: "说明",
          name:'comment',
          value: ""
        },
      ]
    };
  },
  methods: {
    convetInfo(info){
      this.classId = info.classId
      if(info.level == 0){
        info.level = "高" 
      }else if(info.level == 1){
        info.level = "中"
      }else{
        info.level = "低"
      }
      this.items.forEach(item=>{
        item.value = info[item.name]
      })
    },
    clickHandle() {
      //获取班级ID传递出去.
      const id = this.classId;
      this.$emit("click",id);
    }
  },
  watch:{
    info(){
      this.convetInfo(this.info)
    }
  }
};
</script>

<style scoped lang="stylus"></style>