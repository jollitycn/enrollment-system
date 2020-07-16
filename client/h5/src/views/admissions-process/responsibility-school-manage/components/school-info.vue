<template>
  <div class="school-info">
    <content-info
      :title="this.info.sourceSchoolName"
      value="编辑"
      @click="clickHandle"
      :items="items"
      :labelWidth="labelWidth"
    />
  </div>
</template>

<script>
import ContentInfo from "./content-info";
export default {
  name: "school-info",
  components: {
    "content-info": ContentInfo
  },
  props: {
    info: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {
      labelWidth: "110px",
      items: [
        {
          label: "学校类型",
          name: "schoolType",
          value: ""
        },
        {
          label: "中招人数",
          name: "recruitAmount",
          value: ""
        },
        {
          label: "跟进方式",
          name: "recruitWay",
          value: ""
        },
        {
          label: "年级主任",
          name: "gradeDirectorName",
          value: ""
        },
        {
          label: "年级主任电话",
          name: "gradeDirectorTel",
          value: ""
        },
        {
          label: "班级数量",
          name: "classAmount",
          value: ""
        },
        {
          label: "学校情况说明",
          name: "schoolCaseComment",
          value: ""
        }
      ]
    };
  },
  methods: {
    clickHandle() {
      this.$emit("click");
    },
    convetInfo(info) {
      console.log(info, "<info");
      //学校类型
      // if (info.schoolType == 0) {
      //   info.schoolType = "重点";
      // } else if (info.schoolType == 1) {
      //   info.relationLevel = "普通";
      // } else if(info.schoolType == 2) {
      //   info.schoolType = "重新";
      // }
      let schoolType = "";
      info.schoolTypeList.forEach((item, index) => {
        if (index + 1 === info.schoolTypeList.length) {
          schoolType = schoolType + item.dataValue;
        } else {
          schoolType = schoolType + item.dataValue + ",";
        }
      });
      info.schoolType = schoolType;

      //赋值
      this.items.forEach(item => {
        item.value = info[item.name];
      });
    }
  },
  watch: {
    info() {
      this.convetInfo(this.info);
    }
  }
};
</script>

<style scoped lang="stylus"></style>