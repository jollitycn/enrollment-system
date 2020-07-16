<template>
  <div class="preaching-plan-summary">
    <div class="preaching-plan-summary-header">
      <van-sticky :offset-top="0">
        <h5-header :leftStatus="true" title="宣讲查看"></h5-header>
      </van-sticky>
    </div>
    <div class="preaching-plan-summary-content">
      <!-- 宣讲相关信息 -->
      <div class="preaching-plan-info">
        <div class="preaching-plan-info-title">奋达学校2020秋季招生介绍</div>
        <div class="preaching-plan-info-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">宣讲时间</div>
            <div class="content-info-item-val">2020-04-20 19:00</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">图层宣讲地点</div>
            <div class="content-info-item-val">宝安西乡体育馆会议厅</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">主讲人</div>
            <div class="content-info-item-val">王老师</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">联系手机</div>
            <div class="content-info-item-val">13276936312</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">计划人数</div>
            <div class="content-info-item-val">80</div>
          </div>
        </div>
        <div class="preaching-plan-info-footer">
          <div class="desc-label">面向学校</div>
          <div class="desc-content">宝安中学、西乡中学、碧海湾中学、宝安中学、西乡中学、碧海湾中学、宝安中学、西乡中学、碧海湾中学</div>
        </div>
      </div>
      <!-- 评估结果 -->
      <div class="assessment-result">
        <div class="assessment-result-title">评估结果</div>
        <div class="assessment-result-content">
          <div class="assessment-result-content-item">
            <div class="result-desc good">好</div>
            <div class="result-value">23人</div>
          </div>
          <div class="assessment-result-content-item">
            <div class="result-desc normal">一般</div>
            <div class="result-value">3人</div>
          </div>
          <div class="assessment-result-content-item">
            <div class="result-desc bad">差</div>
            <div class="result-value">0人</div>
          </div>
        </div>
      </div>

      <!-- 反馈 -->
      <div class="preaching-feedback">
        <div class="preaching-feedback-title">反馈</div>
        <div class="preaching-feedback-content">
          <div class="preaching-feedback-content-item">
            <div class="feedback-title">
              <span class="feedback-name">暨莺之</span>&emsp;&emsp;
              <span class="feedback-phone">14294103737</span>
            </div>
            <div class="feedback-content">几历法动会层非铁观所往响结花象号住行做事素地体的色造组说式单市过花明研结生你律关需特反流向生政。</div>
            <div class="feedback-time">2020-04-05 18:31</div>
          </div>
          <div class="preaching-feedback-content-item">
            <div class="feedback-title">
              <span class="feedback-name">暨莺之</span>&emsp;&emsp;
              <span class="feedback-phone">14294103737</span>
            </div>
            <div class="feedback-content">几历法动会层非铁观所往响结花象号住行做事素地体的色造组说式单市过花明研结生你律关需特反流向生政。</div>
            <div class="feedback-time">2020-04-05 18:31</div>
          </div>
          <div class="preaching-feedback-content-item">
            <div class="feedback-title">
              <span class="feedback-name">暨莺之</span>&emsp;&emsp;
              <span class="feedback-phone">14294103737</span>
            </div>
            <div class="feedback-content">况量很在场西该争反作指建率持火最军政下务界空资写该领话细例太可象直断院当养就层度她织与些技些回造今道放去。者象层定周各商而据高文叫题代江流指流工但领温领一干证制断场常着物需花革思飞作身边向路至天才科要强身大原约民入从才按便酸社快使战平位量的。也习转历形土立量从快王青整各住响出使值样飞战听事子导马济消海半很往亲存地图开回只单管任入大历书步们中图斯作条向求克半时解和儿状日。</div>
            <div class="feedback-time">2020-04-05 18:31</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky } from "vant";

Vue.use(Sticky);

export default {
  name: "preaching-plan-summary",
  components: {
    "h5-header": Header
  },
  data() {
    return {};
  },
  methods: {},
  created() {}
};
</script>

<style scoped lang="stylus">
.preaching-plan-summary {
  &-content {
    .preaching-plan-info {
      background-color: #fff;

      &-title {
        padding: 16px;
        height: 32px;
        font-size: 21px;
        font-weight: 600;
        color: #333333;
        line-height: 32px;
      }

      &-footer {
        background-color: #F8F8F8;
        margin: 16px;
        padding: 16px 16px;
        font-size: 15px;
        color: #333333;
        line-height: 23px;

        .desc-label {
          font-weight: 600;
        }
      }
    }

    .assessment-result {
      margin: 16px 0;
      padding: 0 16px;
      font-size: 15px;
      line-height: 23px;
      color: #333333;
      background-color: #fff;

      &-title {
        font-weight: 600;
        padding: 12px 0;
      }

      &-content {
        display: flex;
        align-items: center;
        text-align: center;

        &-item {
          flex: 1;

          .result-desc {
            display: inline-block;
            width: 40px;
            height: 40px;
            border-radius: 20px;
            line-height:40px;
          }

          .result-desc.good {
            background-color: #E5FAFF;
            color: #00CCFF;
          }

          .result-desc.normal {
            background-color: #FFF4EA;
            color: #FF9638;
          }

          .result-desc.bad {
            background-color: #FFEDED;
            color: #FF4C4C;
          }

          .result-value{
            padding:8px;
          }
        }
      }
    }

    .preaching-feedback{
      padding:16px;
      background-color: #fff;

      &-title{
        padding-top:12px 0;
        font-size:15px;
        line-height:23px;
        font-weight:600;
        color:#333333;
      }
      &-content{
        font-size:14px;
        font-weight:400;
        color:#999999;
        line-height:21px;

        &-item{
          .feedback-title{
            padding-top:12px;

            .feedback-name{
              color:#333333;
            }
          }
          .feedback-content{
            padding-top:12px;
          }
          .feedback-time{
            padding-top:12px;
            text-align:right;
          }
        }
      }
    }
  }
}

.content-info-content-items {
  font-size: 15px;
  font-weight: 400;
  line-height: 28px;
  padding: 4px 16px;
  background: rgba(255, 255, 255, 1);

  .content-info-item {
    display: flex;

    &-label {
      width: 60px;
      color: #999999;
    }

    &-val {
      margin-left: 12px;
      flex: 1;
    }
  }
}
</style>
