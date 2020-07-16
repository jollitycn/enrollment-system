<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="志愿管理"></h5-header>
    </van-sticky>
    <div class="volunteer">
      <div class="volunteer-item" v-for="(item, index) in list" :key="index">
        <div class="volunteer-item-title">{{ ys[item.level] }}</div>
        <div v-show="!item.majorName"  class="volunteer-item-title-new">
          <span>专业</span>
          <van-field
            v-model="item.majorName"
            @click="changeSchool(item.level, 'creat')"
            placeholder=""
            disabled
            class="activeClass"
          >
            <template #button >
              <van-button size="small" class="activeBtn">选择专业<van-icon style="position: relative;
    left: 6px; top: 2px" :name="img" /></van-button>
            </template>
          </van-field>
        </div>
        <div v-show="item.majorName" class="content">
          <div class="content-first">
            <span class="content-first-one">专业</span>
            <span class="content-first-two">{{ item.majorName }}</span>
            <span
              class="content-first-three"
              @click="changeSchool(item.level, 'edit', item.majorId)"
              >修改</span
            >
          </div>
          <div class="content-first">
            <span class="content-first-one">学费</span>
            <span class="content-first-two">{{ item.fee }}/学期</span>
          </div>
          <div class="content-first">
            <span class="content-first-one">学制</span>
            <span class="content-first-two">{{ item.majorType }}年</span>
          </div>
          <div class="content-first">
            <span class="content-first-one">计划招生</span>
            <span class="content-first-two">{{ item.recruitAmount }}人</span>
          </div>
          <div class="content-first">
            <span class="content-first-one">已报名数</span>
            <span class="content-first-two">{{ item.amount }}人</span>
          </div>
          <div class="content-second">
            {{ item.careerForward }}
          </div>
        </div>
        <div class="content">
          <div class="content-first">
            <span class="content-first-one volunteer-item-title-new-one">调配</span>
            <span class="content-first-two" style="color: #999"
              >是否接受调配</span
            >
            <span class="content-first-three"
              ><van-switch
                v-model="item.isAdjust"
                :active-value="1"
                :inactive-value="0"
                active-color="#00CCFF"
                size="21px"
            /></span>
          </div>
        </div>
      </div>
      <div class="information-btn">
        <van-button type="primary" color="#00CBFF" block @click="submit">保存</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { Field, Button, Switch, Sticky, Icon } from "vant";
import Header from "@/components/cheader";
import {
  queryAimMajorInfo,
  saveOrUpdateAimMajorInfo,
  queryMajorInfoByMajorId
} from "@/api/volunteer/volunteer";
export default {
  name: "volunteer",
  components: {
    vanField: Field,
    vanButton: Button,
    vanSwitch: Switch,
    vanSticky: Sticky,
    vanIcon: Icon,
    h5Header: Header
  },
  data() {
    return {
      img: require("../../../../assets/image/right.png"),
      ys: {
        1: "第一志愿",
        2: "第二志愿",
        3: "第三志愿"
      },
      value: "",
      checked: false,
      isShow: true,
      isShow2: true,
      isShow3: true,
      list: [
        {
          isAdjust: 0, //是否接受调剂
          level: 1, //志愿等级
          majorId: "" //招生专业ID
        },
        {
          isAdjust: 0,
          level: 2,
          majorId: ""
        },
        {
          isAdjust: 0,
          level: 3,
          majorId: ""
        }
      ]
    };
  },
  methods: {
    //选择学校
    changeSchool(level, type, id) {
      //定义不可选择的专业
      let arr = [];
      for (let i = 0; i < this.list.length; i++) {
        this.list[i].majorId && arr.push(this.list[i].majorId);
      }
      if (type === "edit") {
        arr.splice(arr.indexOf(id), 1);
      }
      console.log(arr);
      this.$router.push({
        path: "/signList/activeMajor",
        query: {
          level,
          data: arr
        }
      });
    },
    //获取学校
    queryAimMajorInfo() {
      queryAimMajorInfo().then(res => {
        console.log(res);
        let data = res.data;
        if(!data.length) {
          this.list = [
            {
              isAdjust: 0, //是否接受调剂
              level: 1, //志愿等级
              majorId: "" //招生专业ID
            },
            {
              isAdjust: 0,
              level: 2,
              majorId: ""
            },
            {
              isAdjust: 0,
              level: 3,
              majorId: ""
            }
          ];
          return
        }
        let arr = [];
        for (let i = 0; i < data.length; i++) {
          if(data[i].majorId) {
            let json = {
              majorId: data[i].majorId
            };
            arr.push(queryMajorInfoByMajorId(json));
          }
          this.$set(this.list, i , data[i]);
        }
        Promise.all(arr).then(r => {
          console.log(r,this.list);
          for (let i = 0; i < r.length; i++) {
            for (let j = 0; j < this.list.length; j++) {
              if(this.list[j].majorId === r[i].data.majorId ) {
                let data = { ...r[i].data, ...this.list[j] }
                this.$set(this.list, j, data);
              }
            }
          }
        });
      });
    },
    //点击保存
    submit() {
      let arr = [];
      for (let i = 0; i < this.list.length; i++) {
        let json = {
          isAdjust: "",
          level: "",
          majorId: ""
        };
        // if (this.list[i].majorId) {
          if (this.list[i].aimMajorId) {
            json.aimMajorId = "";
            json.studentPublicRelationId = "";
          }
          for (let key in json) {
            json[key] = this.list[i][key];
          }
          arr.push(json);
        // }
      }
      this.saveOrUpdateAimMajorInfo(arr);
    },
    //添加或修改专业
    saveOrUpdateAimMajorInfo(arr) {
      let json = {
        aimMajors: arr
      };
      saveOrUpdateAimMajorInfo(json).then(res => {
        this.$toast({
          message: "保存成功",
          onClose: () => {
            this.$router.push({
              path: "/signList"
            });
          }
        });
        console.log(res, "新增或修改");
      });
    }
  },
  created() {},
  activated() {
    if (this.$route.params.status) {
      this.queryAimMajorInfo();
    } else if (JSON.stringify(this.$route.params) !== "{}") {
      let data = [];
      for (let i = 0; i < this.list.length; i++) {
        data[i] = this.list[i];
        if (this.list[i].level === this.$route.params.level) {
          this.list[i] = { ...this.list[i], ...this.$route.params };
          data[i] = this.list[i];
        }
      }
      this.list = data;
    }
    console.log(this.list, 8888);
  }
};
</script>

<style scoped lang="stylus">
.volunteer
    min-height 100vh
    padding 1px 0px
    background-color #f5f5f5
    margin-bottom 50px
    .volunteer-item
        border 1px solid #eee
        border-radius 10px
        background-color bgColor
        padding 20px 15px 5px 15px
        margin: 16px;
        >>>.van-cell
            padding 0
        .volunteer-item-title
            color fontTitleColor
            font-size 17px
            font-weight bold
            padding-bottom: 0.53333rem;
            border-bottom: 1px solid #eee;
        .content
            .content-first
                margin-bottom: 10px;
                margin-top: 15px;
                display: flex;
                align-items: center;
                >span
                    display inline-block
                .volunteer-item-title-new-one
                    font-size 16px
                    color fontTitleColor !important
                .content-first-one
                    width 30%
                    color fontTextColor
                .content-first-two
                    width 40%
                    color fontTitleColor
                .content-first-three
                    width 30%
                    text-align right
                    color btnBgColor
            .content-second
                margin-top: 20px;
                background-color: #f8f8f8;
                padding: 20px;
        .volunteer-item-title-new
            display flex
            align-items: center;
            color fontTitleColor
            font-size 17px
            margin-top: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            >span
              width 100px
              font-size 16px
            >>>img
                  width 6px
    .information-btn
      position: fixed;
      bottom: 0;
      width: 100%;
      z-index: 99;
    .activeClass
      display flex
      align-items center
      color fontTitleColor
      font-size 16px
      font-weight bold
      .activeBtn
        border 0
        font-size 16px
        color #999999
</style>
