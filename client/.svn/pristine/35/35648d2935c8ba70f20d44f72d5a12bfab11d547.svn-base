<!-- 级联地址组件 -->
<template>
  <div class='components-address'>
    <!-- <el-cascader :props="props" v-model="c_address"></el-cascader> -->
    <el-cascader :options="options" v-model="c_address"></el-cascader>
  </div>
</template>

<script>
  import { getProvinceList, getCityList, getDistrictList } from '@/api/admin/sys-region/sys-region';
  import areaList from "@/assets/json/area";

  export default {
    name: 'Address',
    components: {},
    model: {
      prop: 'value',
      event: 'change'
    },
    props: {
      value: {
        type: Array,
        default: () => {
          return [];
        }
      },
    },
    data() {
      const loadRegionData = this.loadRegionData;
      return {
        options: [],
        address: [],
        props: {
          lazy: true,
          newTree: [],
          lazyLoad(node, resolve) {
            loadRegionData(node, resolve);
          }
        }
      };
    },
    computed: {
      c_address: {
        get() {
          return this.address;
        },
        set(val) {
          console.log('城市级联数据：', val)
          this.address = val;
          //触发change事件,改变model值
          this.$emit('change', val)
        }
      }
    },
    watch: {
      value: {
        immediate: true,
        handler(val, oldVal) {
          // console.log('默认获取到的级联数据：',val,',',oldVal)
          this.address = this.value;
          console.log(this.c_address, "===================");
        }
      }
    },
    created() {
      this.options = areaList
    },
    mounted() {

    },
    beforeDestroy() { },
    destroyed() { },
    activated() { },
    methods: {
      /**
       * 获取省份数据
       * @param node {object} tree的节点数据
       * @param resolve {function} 返回数据处理函数
       * @param depth {number} 级联调用深度，默认3级
       * @author lmh
       */
      loadRegionData(node, resolve, depth = 3) {
        const {
          level,
          root
        } = node;
        const converData = data => {
          let nodes = data.map(item => ({
            value: item.name,
            regionId: item.regionId,
            label: item.name,
            leaf: item.level >= depth
          }));
          resolve(nodes);
        };
        if (level == 0) {
          //加载省份数据
          getProvinceList().then(res => {
            converData(res.data);
          });
        } else if (level == 1) {
          //加载城市数据
          getCityList(node.pathNodes[0].data.regionId).then(res => {
            converData(res.data);
          });
        } else if (level == 2) {
          //加载区域数据
          getDistrictList(node.pathNodes[1].data.regionId).then(res => {
            converData(res.data);
          });
        }
      }
    },
  }
</script>
<style lang='scss' scoped>
</style>