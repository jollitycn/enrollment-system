<template>
  <div class="preach">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="宣讲总结"></h5-header>
    </van-sticky>
    <div class="preach-evaluate">
      <div class="preach-evaluate-left">宣讲效果</div>
      <div class="preach-evaluate-right">
        <van-radio-group v-model="form.performance" direction="horizontal" :disabled="isSee">
          <van-radio name="1">好</van-radio>
          <van-radio name="2">一般</van-radio>
          <van-radio name="3">差</van-radio>
        </van-radio-group>
      </div>
    </div>
    <div class="preach-feedback">
      <van-field
        :disabled="isSee"
        class="preach-feedback-record"
        v-model="form.feedbackContent"
        rows="3"
        label="反馈内容"
        type="textarea"
        placeholder="请输入反馈内容"
      />
    </div>
    <div class="preach-preach">
      <van-field
        :disabled="isSee"
        class="preach-preach-result"
        v-model="form.summery"
        rows="4"
        label="宣讲总结"
        type="textarea"
        placeholder="请输入宣讲总结"
      />
    </div>
    <div class="add-form-save">
      <!-- <van-button color="#00CCFFFF" size="large" @click="addPreachSummary">提交</van-button> -->
      <button class="action-save-btn" type="submit" @click="addPreachSummary" v-if="!isSee">提交</button>
    </div>
  </div>
</template>

<script>
import {
  addPreachSummary,
  getPreachSummary
} from "../../../api/admissions-process/preaching-plan";
import Header from "@/components/cheader";
import Vue from "vue";
import { Sticky, RadioGroup, Radio, Field, Button } from "vant";
Vue.use(Radio);
Vue.use(Field);
Vue.use(Button);
Vue.use(RadioGroup);
export default {
  name: "preaching-plan-summary",
  components: {
    h5Header: Header,
    vanSticky: Sticky
  },
  data() {
    return {
      isSee: false,
      type: "",
      form: {
        feedbackContent: null,
        performance: null,
        preachPlanId: null,
        summery: null
      },
      query: {
        pageNum: 1,
        pageSize: 10,
        preachPlanId: this.$route.params.id
      }
    };
  },
  methods: {
    /**
     * 新增宣讲总结
     * @author LGH
     */
    addPreachSummary() {
      this.form.preachPlanId = this.$route.params.id;
      if (this.form.performance == null) {
        this.$message.error("请选择宣讲效果！");
      } else {
        this.$message.loading("正在提交...");
        addPreachSummary(this.form)
          .then(() => {
            this.$message.close();
            this.$message.success("添加宣讲总结成功！");
            this.$router.go(-1);
          })
          .catch(err => {
            console.error("添加失败：", err);
          });
      }
    },
    /**
     * 判断当前页面是否为查询页面 type为see时是查看页面
     * @param {String} type 当前页面是否为查看判断，
     * @author LGH
     */
    issee() {
      if (this.type == "see") {
        this.isSee = true;
      } else {
        this.isSee = false;
      }
    },
    /**
     * 获取宣讲信息
     * @author LGH
     */
    getPreachSummary() {
      if (this.$route.params.type) {
        this.$message.loading("正在加载...");
        getPreachSummary(this.query)
          .then(res => {
            this.$message.close();
            this.form = res.data.records[0];
            this.form.performance = String(this.form.performance)
          })
          .catch(err => {
            console.error("加载失败：", err);
          });
      }
    }
  },
  created() {
    this.type = this.$route.params.type;
    this.issee();
    this.getPreachSummary();
  }
};
</script>

<style scoped lang="stylus">
.preach {
  background-color: #FFFFFFFF;

  .preach-evaluate {
    display: flex;
    align-items: center;
    margin-top: 20px;
    margin-bottom: 16px;

    .preach-evaluate-left {
      flex: 1.3;
      margin-left: 16px;
      font-weight: 600;
    }

    .preach-evaluate-right {
      .van-radio--horizontal {
        margin-right: 1rem;

        >>>.van-radio__icon--checked .van-icon {
          color: #fff;
          background-color: #00CCFFFF;
          border-color: #00CCFFFF;
        }
      }
    }
  }

  .preach-feedback {
    margin-bottom: 24px;

    .preach-feedback-record {
      display: block;

      >>>.van-cell__title {
        margin-bottom: 8px;

        span {
          font-weight: 600;
        }
      }

      >>>.van-field__value {
        text-align: left;
      }

      >>>.van-field__control {
        text-align: left;
        padding: 8px 16px;
        background-color: #F2F2F2;
      }
    }
  }

  .preach-preach {
    margin-bottom: 24px;

    .preach-preach-result {
      display: block;

      >>>.van-cell__title {
        margin-bottom: 8px;

        span {
          font-weight: 600;
        }
      }

      >>>.van-field__value {
        text-align: left;
      }

      >>>.van-field__control {
        text-align: left;
        padding: 8px 16px;
        background-color: #F2F2F2;
      }
    }
  }

  .action-save-btn {
    width: 100%;
    height: 49px;
    color: #fff;
    background: rgba(0, 204, 255, 1);
    // 消除默认样式
    margin: 0px;
    padding: 0px;
    border: 0px;
  }
}
</style>
