<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="联系方式"></h5-header>
    </van-sticky>
    <div class="info">
      <div class="info-con">
        <van-form validate-first @submit="submit">
          <van-field
            v-model="from.parentTelephone"
            label="家长手机"
            label-class="xx"
            name="parentTelephone"
            placeholder="请输入手机"
            :rules="rules.phone"
            maxlength="11"
            input-align="right"
            readonly
          />
          <van-field
            v-model="from.wechat"
            label="学生微信"
            name="wechat"
            placeholder="学生微信"
            :rules="rules.wechat"
            maxlength="20"
            input-align="right"
          />
          <van-field
            v-model="from.studentTelephone"
            label="学生手机"
            name="studentTelephone"
            placeholder="学生手机"
            maxlength="11"
            :rules="rules.isTelephone"
            input-align="right"
          />
          <van-field
            v-model="location"
            name="location"
            label="家庭住址"
            label-class="xx"
            placeholder="请选择家庭住址"
            readonly
            class="activeClass"
            :rules="rules.location"
            :right-icon="img"
            @click="showArea = true"
            input-align="right"
          >
          </van-field>
          <van-field
            v-model="from.address"
            name="address"
            label=""
            placeholder="街道、小区、门牌号"
            maxlength="30"
            input-align="right"
          />
          <div class="btn">
            <van-button block color="#00CCFF" type="info" native-type="submit">
              保存
            </van-button>
          </div>
        </van-form>
      </div>

      <van-popup v-model="showArea" position="bottom">
        <van-area
          :area-list="areaList"
          @confirm="onConfirmarea"
          @cancel="showArea = false"
        />
      </van-popup>
    </div>
  </div>
</template>

<script>
import { Field, Button, Popup, Form, Sticky, Area } from "vant";
import Header from "@/components/cheader";
import { phone } from "../../../../../../assets/rules/rulesFrom";
import areaList from "../../../../../../assets/json/area";
import {
  addStudentInfo,
  queryStudentInfo,
  updateStudentInfo
} from "@/api/signList/backups/backups";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanPopup: Popup,
    vanForm: Form,
    vanArea: Area,
    vanSticky: Sticky,
    h5Header: Header
  },
  name: "contact",
  data() {
    return {
      img: require("../../../../../../assets/image/right.png"),
      isAdd: true,
      showArea: false,
      areaList: areaList, // 数据格式见 Area 组件文档
      from: {
        wechat: "", //学生微信
        parentTelephone: "", //家长手机
        address: "" //家庭详细地址
      },
      location: "", //家庭住址
      rules: {
        phone: [{ pattern: phone.rules, message: phone.message }],
        wechat: [{ validator: this.isWechat, message: "请输入正确微信号" }],
        isTelephone: [
          { validator: this.isTelephoneRule, message: "请输入正确手机号" }
        ],
        location: [{ validator: this.isLocation, message: "请选择地址" }]
      },
      showToast: false
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.showArea) {
      this.showArea = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    //微信号验证
    isWechat(val) {
      if (!val) {
        return true;
      }
      let rules = /^[a-zA-Z]([-_a-zA-Z0-9]{5,20})$/;
      if (rules.test(val)) {
        return true;
      }
      return false;
    },
    //验证手机
    isTelephoneRule(val) {
      if (!val) {
        return true;
      }
      let rules = phone.rules;
      if (rules.test(val)) {
        return true;
      }
      return false;
    },
    //确定户籍地址
    onConfirmarea(values) {
      this.location = values.map(item => item.name).join("/");
      this.showArea = false;
    },
    submit(data) {
      let from = data;
      let allLocation = {
        familyProvince: from.location.substring(0, from.location.indexOf("/")),
        familyCity: from.location.substring(
          from.location.indexOf("/") + 1,
          from.location.lastIndexOf("/")
        ),
        familyDistrict: from.location.substring(
          from.location.lastIndexOf("/") + 1,
          from.location.length
        )
      };
      let newData = { ...from, ...allLocation };
      delete newData.location;
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });
      if (this.isAdd) {
        this.addStudentInfo(newData);
      } else {
        this.updateStudentInfo(newData);
      }
    },
    //地址校验
    isLocation(val) {
      return val.length > 4;
    },
    //新增联系方式信息
    addStudentInfo(data) {
      addStudentInfo(data)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.$toast({
            message: "新增成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    //获取学生联系方式
    queryStudentInfo() {
      queryStudentInfo()
        .then(res => {
          if (res.data) {
            this.isAdd = false;
            this.from = res.data;
            let familyProvince = res.data.familyProvince
              ? res.data.familyProvince + "/"
              : "";
            let familyCity = res.data.familyCity
              ? res.data.familyCity + "/"
              : "";
            let familyDistrict = res.data.familyDistrict
              ? res.data.familyDistrict
              : "";
            this.location = familyProvince + familyCity + familyDistrict;
          } else {
            this.isAdd = true;
          }

          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    //编辑学生联系
    updateStudentInfo(data) {
      updateStudentInfo(data)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.$toast({
            message: "编辑成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(err => {
          this.showToast.clear();
          console.log(err);
        });
    }
  },
  created() {},
  activated() {
    this.queryStudentInfo();
  }
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  margin-top: 20px
  background bgColor
  min-height calc(100vh - 64px)
  .info-title
    color fontTitleColor
    font-size fontBigSize
    font-weight bold
    margin-bottom: 10px;
    margin-top: 20px;
  .content
    margin-bottom: 40px;
    margin-top: 20px;
    .content-first
      margin-bottom: 10px;
      >span
        display inline-block
      .content-first-one
        width 30%
        color fontMiniFontColor
      .content-first-two
        width 40%
        color fontTitleColor
  >>>.van-form
        span
          color #333;
          font-size 15px
  .btn
    position: fixed;
    bottom: 0;
    width: 100%;
    height 49px
    >>>button
        height 49px
        >span
            font-size 18px
            color #fff
  >>>.van-cell
        min-height 55px
        display flex
        align-items center
        i
          width: auto;
        img
          width 6px
  .info-con
    >>>.xx
      >span
        position relative
        &:after
          position: absolute;
          right: -10px;
          color: #ee0a24;
          font-size: 0.37333rem;
          content: '*';
    >>>.van-cell:not(:last-child)::after
      left 0px
.activeClass
  display flex
  align-items center
  .activeBtn
    border 0
    color activeBtnColor
</style>
