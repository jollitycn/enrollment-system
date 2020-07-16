<template>
  <div class="class-info">
    <content-info
      :title="info.className"
      value="进入班级"
      @click="clickHandle"
      :items="items"
      :classId="classId"
      :schoolId="schoolId"
      :labelWidth="labelWidth"
    />
  </div>
</template>

<script>
import ContentInfo from "./content-info";
export default {
  name: "class-info",
  components: {
    "content-info": ContentInfo
  },
  props: {
    info: {
      type: Object,
      default: () => {
        return {};
      }
    },
    classId: {
      type: String,
      default: ""
    },
    schoolId: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      labelWidth: "60px",
      levelList: ["高", "中", "低"],
      items: [
        {
          label: "跟进等级",
          name: "level",
          value: ""
        },
        {
          label: "班主任",
          name: "adviser",
          value: ""
        },
        {
          label: "联系方式",
          name: "telephone",
          value: ""
        },
        {
          label: "学生人数",
          name: "amount",
          value: ""
        },
        {
          label: "目标招生",
          name: "willingAmount",
          value: ""
        },
        {
          label: "说明",
          name: "comment",
          value: ""
        }
      ]
    };
  },
  methods: {
    clickHandle() {
      this.$emit("click");
    }
  },
  created() {
    this.items.forEach(item => {
      if (item.name == "level") {
        item.value = this.levelList[this.info[item.name]];
      } else {
        item.value = this.info[item.name];
      }
    });
  }
};
</script>

<style scoped lang="stylus">
.class-info {
  background: #FFFFFF;
  margin-bottom: 10px;
}
</style>