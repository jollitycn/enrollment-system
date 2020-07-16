<template>
  <div>
    <van-divider :style="{margin: '0' }" />
    <div class="school-list-item">
      <div class="school-list-item-info">
        <div class="school-list-item-info-content">
          <div class="school-list-item-info-content-label">{{index}}.{{info.sourceSchoolName}}</div>
          <div class="school-list-item-info-content-val">{{width}}%</div>
        </div>
        <div class="chart-progress-bar school-list-item-info-chart">
          <div class="chart-bg"></div>
          <div
            :class="{'progress-good':isGood,'progress-bad':!isGood,}"
            class="chart-progress progress-bad"
            :style="{'width':`${width}%`}"
          ></div>
        </div>
      </div>
      <div class="school-list-item-icon">
        <van-icon name="arrow" color="#999999" size="small" />
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import { Divider } from "vant";
Vue.use(Divider);
export default {
  name: "school-list-item",
  props: {
    info: {
      required: true,
      type: Object,
      default: () => {
        return {};
      }
    },
    index: {
      required: true,
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      isGood: false,
      //进度条宽度，设置范围 0-100
      width: 50
    };
  },
  methods: {},
  created() {
    //显示百分百
    this.width = this.info.completeRate * 100;
    //超过this.width大于60，滑块颜色变为蓝色
    if (this.width >= 60) {
      this.isGood = true;
    }
  }
};
</script>

<style scoped lang="stylus">
.school-list-item {
  display: flex;
  align-items: center;
  padding: 0px 16px;
  height: 83px;

  &-info {
    flex: 1;
    margin-right: 16px;
    font-size: 14px;

    &-content {
      display: flex;

      &-label {
        color: #333333;
        text-align: left;
        flex: 1;
        font-weight: 600;
        line-height: 21px;
      }

      &-val {
        color: #999999;
        flex: 1;
        text-align: right;
      }
    }
  }
}

// 模拟进度条
.chart-progress-bar {
  position: relative;
  height: 24px;

  .chart-bg {
    position: absolute;
    top: 8px;
    left: 0;
    height: 22px;
    width: 100%;
    background: #F2F2F2;
    border-radius: 11px;
  }

  .chart-progress {
    position: absolute;
    top: 8px;
    left: 0;
    height: 22px;
    width: 0;
    border-radius: 11px;
  }

  .progress-bad {
    background: linear-gradient(to right, #01A2FF, #00CCFF);
  }

  .progress-good {
    background: linear-gradient(to right, #01A2FF, #00CCFF);
  }
}
</style>