<template>
  <div class="message">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="新增诉求"></h5-header>
    </van-sticky>
    <div class="info" style="padding: 20px;">
      <div class="info-title">诉求类型</div>
      <div class="info-list">
        <span
          v-for="(item, index) in list"
          :key="index"
          @click="changeType(index)"
          :class="{ activeB: index == activeIndex }"
        >
          {{ item.title }}
        </span>
      </div>
      <div class="info-title" style="margin-top: 30px;margin-bottom: 19px;">我的诉求</div>
      <van-field
        v-model.trim="from.requestContent"
        rows="4"
        autosize
        type="textarea"
        maxlength="120"
        placeholder="请输入你的诉求"
        show-word-limit
        style="background-color: #f8f8f8"
      />
    </div>
    <van-button
      :loading="isLoading"
      :disabled="isLoading"
      type="primary"
      loading-text="新增中..."
      block
      color="#00CCFF"
      @click="submit"
      class="submit-btn"
      safe-area-inset-bottom
      >确定</van-button
    >
  </div>
</template>

<script>
import { Field, Sticky, Button } from "vant";
import Header from "@/components/cheader";
import { saveOrUpdateRequestInfo } from "@/api/user/user";
export default {
  name: "myNeed",
  components: {
    vanField: Field,
    vanSticky: Sticky,
    vanButton: Button,
    h5Header: Header
  },
  data() {
    return {
      showToast: null,
      isLoading: false,
      from: {
        requestContent: "",
        requestType: "专业诉求"
      },
      requestContent: "",
      activeIndex: 0,
      list: [
        { id: 0, title: "专业诉求" },
        { id: 1, title: "费用诉求" },
        { id: 2, title: "毕业诉求" },
        { id: 3, title: "环境诉求" },
        { id: 4, title: "其他诉求" }
      ]
    };
  },
  methods: {
    //新增诉求
    changeType(index) {
      this.activeIndex = index;
      this.from.requestType = this.list[this.activeIndex].title;
    },
    //确认新增
    submit() {
      if (!this.from.requestContent.length) {
        this.$toast("诉求不能为空");
        return;
      }
      this.isLoading = true;
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });
      this.saveOrUpdateRequestInfo();
    },
    //新增诉求接口
    saveOrUpdateRequestInfo() {
      saveOrUpdateRequestInfo(this.from)
        .then(res => {
          console.log(res);
          this.isLoading = false;
          this.showToast.clear()
          this.$toast({
            message: "新增诉求成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(() => {
          this.isLoading = false;
          this.showToast.clear();
        });
    }
  }
};
</script>

<style scoped lang="stylus">
.message
    font-size fontMedSize
    .info
        padding 20px
        background bgColor
        .info-title
            color fontTitleColor
            font-size fontBigSize
            font-weight bold
            margin-bottom: 1px;
            margin-top: 1px;
  .info-list
            >span
                color: #999;
                background-color: #f2f2f2;
                padding: 10px;
                margin: 18px 20px 0px 0px;
                display: inline-block;
                border-radius 4px
            .activeB
                color: bgColor;
                background-color activeBtnColor
  .submit-btn
    position: fixed;
    bottom: 1rem;
    width: 100%;
    text-align: center;
    z-index: -1;
</style>
