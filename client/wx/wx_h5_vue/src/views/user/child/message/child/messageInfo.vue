<template>
  <div class="message-info">
    <h5-header :leftStatus="true" title="报名资料"></h5-header>
    <div class="message-info-content" v-html="content"></div>
    <div class="message-info-btn" v-show="status === 0">
      <van-button color="#00CCFF" block @click="confirm">按时报到</van-button>
    </div>
    <div class="message-info-btn" v-show="status1 === 1">
      <van-button color="#aeaeae" block >已确认</van-button>
    </div>
  </div>
</template>

<script>
import { getAdmissionInfo, confirm } from "@/api/user/user";
import { Button } from "vant";
import Header from "@/components/cheader";
export default {
  name: "messageInfo",
  components: {
    vanButton: Button,
    h5Header: Header
  },
  data() {
    return {
      id: "",
      content: "",
      status: 1,
      status1: 0
    };
  },
  methods: {
    //获取入选通知信息
    getAdmissionInfo(id) {
      getAdmissionInfo(id).then(res => {
        console.log(res);
        this.content = res.data.content;
        this.status = res.data.confirmStatus;
        this.status1 = res.data.confirmStatus
      });
    },
    //确认通知
    confirm() {
      confirm(this.id).then(res => {
        console.log(res, "确认通知");
        this.status = 1;
        this.status1 = 1;
        this.$toast({
          message: "确认成功，欢迎你的加入",
          onClose: () => {
            this.$router.go(-1);
          }
        });
      });
    }
  },
  created() {
    let id = this.$route.query.id;
    this.getAdmissionInfo(id);
    this.id = id;
  }
};
</script>

<style scoped lang="stylus">
.message-info
    background-color bgColor
    color #656565
    >.message-info-content
        padding 20px
        line-height 26px
    >.message-info-btn
        padding 20px
        margin-top 50px
</style>
