<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="家庭成员"></h5-header>
    </van-sticky>
    <div class="info">
      <div style="margin-bottom: 50px;">
        <van-form>
          <div v-for="(item, index) in from" :key="index" class="familyItem">
            <van-field
              v-model="item.relation"
              label="关系"
              placeholder="关系"
              readonly
              class="activeClass"
              input-align="right"
            >
            </van-field>
            <van-field
              v-model="item.name"
              name="pattern"
              label="家长姓名"
              placeholder="请填写家长姓名"
              maxlength="4"
              input-align="right"
            />
            <van-field
              v-model="item.company"
              name="pattern"
              label="工作单位"
              placeholder="请填写工作单位"
              maxlength="20"
              input-align="right"
            />
            <van-field
              v-model="item.position"
              name="pattern"
              label="职务"
              placeholder="请填写职务"
              maxlength="10"
              input-align="right"
            />
            <van-field
              v-model="item.telephone"
              name="pattern"
              label="联系电话"
              placeholder="请填写联系电话"
              :rules="rules.isTelephone"
              maxlength="11"
              input-align="right"
              type="tel"
            />
            <van-field
              v-model="item.location"
              name="location"
              label="户籍地址"
              placeholder="请选择户籍所在地"
              readonly
              input-align="right"
              class="activeClass"
              :right-icon="img"
              @click="changeArea(index)"
            >
            </van-field>
          </div>
          <div class="information-btn">
            <van-button block color="#00CCFF" type="info" @click="submit">
              保存
            </van-button>
          </div>
        </van-form>
      </div>

      <van-popup v-model="showPicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="columns"
          @cancel="showPicker = false"
          @confirm="onConfirm"
        />
      </van-popup>
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
import { Field, Button, Picker, Popup, Form, Sticky, Area } from "vant";
import { phone } from "../../../../../../assets/rules/rulesFrom";
import Header from "@/components/cheader";
import {
  listGroupInfoPage,
  saveOrUpdateFamilyMemberInfo
} from "@/api/signList/backups/backups";
import areaList from "../../../../../../assets/json/area";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanSticky: Sticky,
    vanArea: Area,
    h5Header: Header
  },
  name: "family",
  data() {
    return {
      img: require("../../../../../../assets/image/right.png"),
      showToast: null,
      areaList: areaList, // 数据格式见 Area 组件文档
      showArea: false,
      activeIndex: 0, //选择第几项
      columns: ["父亲", "母亲"],
      from: [
        {
          company: "", //工作单位
          name: "", //家长姓名
          position: "", //职务
          relation: "父亲", //关系
          telephone: "", //联系电话
          location: "" //户籍所在地
        },
        {
          company: "", //工作单位
          name: "", //家长姓名
          position: "", //职务
          relation: "母亲", //关系
          telephone: "", //联系电话
          location: "" //户籍所在地
        }
      ],
      rules: {
        isTelephone: [
          { validator: this.isTelephoneRule, message: "请输入正确手机号" }
        ]
      },
      showPicker: false
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.showArea || this.showPicker) {
      this.showArea = false;
      this.showPicker = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
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
    //选择户籍
    onConfirmarea(values) {
      let location = values.map(item => item.name).join("/");
      this.from[this.activeIndex].location = location;
      this.showArea = false;
    },
    //点击户籍
    changeArea(index) {
      console.log(1);
      this.activeIndex = index;
      this.showArea = true;
    },
    //选择关系
    changeSchool(index) {
      console.log(1);
      this.activeIndex = index;
      this.showPicker = true;
    },
    onConfirm(value) {
      this.from[this.activeIndex].relation = value;
      console.log(this.from[this.activeIndex].relation);
      this.showPicker = false;
    },
    //点击保存
    submit() {
      const data = this.from;
      const olData = JSON.stringify(data);
      const newData = JSON.parse(olData);
      let arr = [];
      let errText = "";
      for (let i = 0; i < newData.length; i++) {
        if (arr.indexOf(newData[i].relation) == -1) {
          arr.push(newData[i].relation);
        } else {
          errText = newData[i].relation;
        }
        newData[i].relation = this.columns.indexOf(newData[i].relation);
      }
      if (errText) {
        this.$toast("法律不允许你有两个" + errText);
        return;
      }
      console.log(arr, errText);
      const f = {
        addFamilyMemberQueries: newData
      };
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });
      this.saveOrUpdateFamilyMemberInfo(f);
      console.log(f);
    },
    //获取家庭成员信息
    listGroupInfoPage() {
      listGroupInfoPage().then(res => {
        console.log(res, "家庭成员信息");
        if (res.data.length) {
          this.from = res.data;
          for (let i = 0; i < this.from.length; i++) {
            this.from[i].relation = this.columns[this.from[i].relation];
          }
        }
      });
    },
    //添加或修改家庭成员信息
    saveOrUpdateFamilyMemberInfo(data) {
      saveOrUpdateFamilyMemberInfo(data)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.$toast({
            message: "保存成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  activated() {
    this.listGroupInfoPage();
  }
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  padding 20px 0px
  background bgColor
  min-height calc(100vh - 66px)
  .familyItem
    margin-bottom 20px
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
  .information-btn
    position: fixed;
    bottom: 0;
    width: 100%;
    height 49px
    >>>button
      height 49px
      >span
        font-size 18px
  >>>.van-cell
    display flex
    min-height 55px
    align-items: center;
  >>>img
    width: 6px;
.activeClass
  display flex
  align-items center
  .activeBtn
    border 0
    color activeBtnColor
</style>
