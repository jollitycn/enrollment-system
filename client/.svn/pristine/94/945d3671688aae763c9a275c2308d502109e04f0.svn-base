<template>
  <!-- 回复弹框 -->
  <van-popup v-model="show" round position="bottom">
    <div class="reply-dialog">
      <div class="reply-dialog-header">
        <div class="reply-dialog-header-left">
          <span class="cancel-btn" @click="cancel">取消</span>
        </div>
        <div class="reply-dialog-header-middle van-ellipsis">{{info.requestContent}}</div>
        <div class="reply-dialog-header-right">
          <span class="confirm-btn" @click="confirm">发送</span>
        </div>
      </div>
      <div class="reply-dialog-content">
        <div>
          <van-field
            v-model="replyInfo.replyContent"
            rows="4"
            autosize
            label
            type="textarea"
            maxlength="200"
            placeholder="请输入留言"
            show-word-limit
          />
        </div>
      </div>
      <div class="reply-dialog-footer"></div>
    </div>
  </van-popup>
</template>

<script>
import Vue from "vue";
import bus from "@/components/bus";
import { Popup, Field, Toast } from "vant";
Vue.use(Popup);
Vue.use(Field);
Vue.use(Toast);
import { saveOrUpdateRequestInfo } from "../../../api/appeal/appeal-management.js";
export default {
  name: "reply-dialog",
  props: {
    visible: {
      required: true,
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {
      show: false,
      replyInfo: {
        requestId: "",
        replyContent: ""
      }
    };
  },
  created() {},
  computed: {},
  watch: {
    visible(val) {
      this.show = val;
    },
    show(val) {
      if (val !== this.visible) {
        this.$emit("update:visible", val);
      }
    }
  },
  methods: {
    cancel() {
      this.$emit("update:visible", false);
    },
    /**
     * 回复操作
     * @author LiuGuoHao
     */
    confirm() {
      this.replyInfo.requestId = this.info.requestId;
      saveOrUpdateRequestInfo(this.replyInfo).then(() => {
        this.$message.success("回复成功！");
        //回复成功后通过bus进行组件通信，调用数据刷新
        bus.$emit('reply-completed')
        this.$emit("update:visible", false);
      });
    }
  }
};
</script>

<style lang="stylus" scoped>
.reply-dialog {
  &-header {
    display: flex;
    font-size: 14px;
    line-height: 21px;
    margin: 16px 0;

    &-left, &-right {
      flex: 1;
      display: inline-block;
      padding: 0 16px;
      font-weight: 400;
      color: #00CCFF;
      text-align: center;
    }

    &-middle {
      width: 206px;
      font-weight: 600;
      color: #333333;
    }
  }

  &-content {
    font-size: 15px;
    font-weight: 400;
    line-height: 23px;
  }

  &-footer {
  }
}
</style>