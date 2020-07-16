<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="宣讲评价"></h5-header>
    </van-sticky>
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh" :style="{backgroundColor: list.length ? '#f2f2f2' : '#fff'}">
      <van-list
        v-model="loading"
        :finished="finished"
        :finished-text="list.length ? '没有更多了' : ''"
        @load="onLoad"
        :immediate-check="false"
      >
        <div class="evaluate" v-for="(item, index) in list" :key="index">
          <div style="padding: 0 20px">
            <div class="evaluate-title">{{ item.title }}</div>
            <div class="evaluate-header">
              <div class="evaluate-header-list">
              <span class="evaluate-header-list-left">宣讲时间：</span
              ><span class="evaluate-header-list-right">{{
                item.preachTime
              }}</span>
              </div>
              <div class="evaluate-header-list">
              <span class="evaluate-header-list-left">宣讲地点：</span
              ><span class="evaluate-header-list-right">{{
                item.address
              }}</span>
              </div>
              <div class="evaluate-header-list">
              <span class="evaluate-header-list-left">主讲人：</span
              ><span class="evaluate-header-list-right">{{
                item.speakerName
              }}</span>
              </div>
              <div class="evaluate-header-list">
              <span class="evaluate-header-list-left">联系手机：</span
              ><span class="evaluate-header-list-right">{{
                item.telephone
              }}</span>
              </div>
              <div class="evaluate-header-list">
              <span class="evaluate-header-list-left">计划人数：</span
              ><span class="evaluate-header-list-right">{{
                item.planAmount
              }}</span>
              </div>
            </div>
            <div class="evaluate-school">
              <div class="evaluate-school-title">面向学校</div>
              <div class="evaluate-school-name">
              <span v-for="item2 in item.sourceSchoolIds" :key="item2">{{
                showSchoolName(item2)
              }}</span>
              </div>
            </div>
          </div>
          <div @click="infoDo(item,index)" style="text-align: center;color: #00ccff; border-top: 0.5px solid #eee;padding-top: 20px">{{item.preachFeedback ? '查看':'评价'}}</div>
        </div>
      </van-list>
    </van-pull-refresh>
    <van-popup v-model="show" position="bottom">
      <div class="evaluate-info">
        <div>
          <van-field name="radio" label="宣讲效果">
            <template #input>
              <van-rate v-model="from.performance" />
            </template>
          </van-field>
          <van-field
            v-model="from.feedbackContent"
            rows="4"
            autosize
            label="反馈内容"
            type="textarea"
            maxlength="50"
            placeholder="请输入内容"
            show-word-limit
          />
          <van-button type="primary" color="#00ccff" block @click="addStudentPublicRelation()"
            >{{isAdd ? '新增评价': '关闭评价'}}</van-button
          >
        </div>
      </div>
    </van-popup>
    <h5FinishedShow v-show="isStatus"></h5FinishedShow>
  </div>
</template>

<script>
import { Field, Sticky, PullRefresh, List, Rate, Button, Popup } from "vant";
import Header from "@/components/cheader";
import { listPageByStudentPublicRelationId, addFeedback } from "@/api/user/user";
import { listSourceSchoolPage } from "@/api/student/student";
import FinishedShow from "@/components/finishedShow/finishedShow";
export default {
  name: "evaluate",
  components: {
    vanField: Field,
    vanSticky: Sticky,
    vanPullRefresh: PullRefresh,
    vanList: List,
    vanRate: Rate,
    vanButton: Button,
    vanPopup: Popup,
    h5Header: Header,
    h5FinishedShow: FinishedShow
  },
  data() {
    return {
      isStatus: false,
      show: false,
      message: "",
      data: {
        pageNum: 1,
        pageSize: 10
      },
      params: {
        pageNum: 1,
        pageSize: 10000
      },
      list: [],
      schoolList: [],
      loading: false,
      finished: false,
      refreshing: false,
      from: {
        feedbackContent: "", //反馈内容
        performance: 0, //宣讲效果
        preachPlanId: 0 //招生宣讲计划id
      },
      isAdd: false, //是新增不
      activeIndex: 0,
    };
  },
  filters: {},
  methods: {
    //宣讲评价操作
    infoDo(item,index) {
      this.from.preachPlanId = item.preachPlanId;
      if (item.preachFeedback) {
        this.from.performance = item.preachFeedback.performance;
        this.from.feedbackContent = item.preachFeedback.feedbackContent
        this.isAdd = false;
      } else {
        this.from.performance = "";
        this.from.feedbackContent = ""
        this.isAdd = true;
      }
      this.show = true;
      this.activeIndex = index
    },
    //新增或关闭宣讲评价
    addStudentPublicRelation() {
      if(this.isAdd) {
        this.addFeedback()
      }else {
        this.show = false
      }
    },
    //新增评价
    addFeedback() {
      addFeedback(this.from).then(() => {
        this.$toast.success("评价成功")
        this.show = false;
        this.list[this.activeIndex].preachFeedback = {};
        this.list[this.activeIndex].preachFeedback.performance = this.from.performance;
        this.list[this.activeIndex].preachFeedback.feedbackContent = this.from.feedbackContent
      });
    },
    //显示学校名称
    showSchoolName(id) {
      if (id) {
        let data = this.schoolList;
        for (let i = 0; i < data.length; i++) {
          if (data[i].sourceSchoolId === id) {
            return data[i].sourceSchoolName;
          }
        }
      }
    },
    //滚动触发,是否是滚动触发
    onLoad(status = true) {
      if(status) {
        this.data.pageNum += 1;
      }

      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.listPageByStudentPublicRelationId();
    },
    //下拉刷新时
    onRefresh() {
      //不禁止下拉滚动
      this.finished = false;
      //开启下拉滚动效果
      this.refreshing = true;
      //将当前页码改为1
      this.data.pageNum = 1;
      this.onLoad(false)
      //发送请求
      // this.listPageByStudentPublicRelationId();
    },
    //获取宣讲列表
    listPageByStudentPublicRelationId() {
      listPageByStudentPublicRelationId(this.data).then(res => {
        //获取数据
        let data = res.data.records;
        //若是第一页，则空数组
        let list = res.data.current === 1 ? [] : this.list;
        //判断所有数据是否加载完成,如数据列表长度与当前页面数量大小不等，则所有数据加载完成。
        this.finished = data.length !== res.data.size;
        //合并
        this.list = [...list, ...data];
        this.isStatus = !this.list.length
        //加载已完成
        this.loading = false;
        this.refreshing = false;
      });
    },
    //获取所有学校
    listSourceSchoolPage() {
      listSourceSchoolPage(this.params).then(res => {
        console.log(res, "所有学校");
        this.schoolList = res.data.records;
      });
    }
  },
  created() {
    this.listPageByStudentPublicRelationId();
    this.listSourceSchoolPage();
  }
};
</script>

<style scoped lang="stylus">
.evaluate
    padding 20px 0
    border-top: 1px solid #eee;
    background-color bgColor
    margin: 20px
    border-radius 8px
    .evaluate-title
        font-size fontBigBigSize
        color: fontTitleColor
        margin-bottom 32px
        font-weight: 500
    .evaluate-header
        .evaluate-header-list
            display flex
            margin-bottom: 10px
            .evaluate-header-list-left
                font-size fontMedSize
                color fontMiniFontColor
                min-width: 100px
            .evaluate-header-list-right
                font-size fontMedSize
                color fontTitleColor
    .evaluate-school
        padding-bottom: 20px;
        border-radius: 6px;
        margin-bottom 20px
        background-color #F8F8F8
        .evaluate-school-title
            font-size fontBigSize
            color fontTitleColor
            margin-top: 20px
            font-weight: 500;
            padding-left: 10px;
            padding-top: 10px;
        .evaluate-school-name
            font-size fontMedSize
            color fontMiniFontColor
            margin-left: 20px;
            margin-top: 10px;
            >span
              margin-left 10px
              margin-bottom: 10px
              display inline-block
.evaluate-info
  padding 20px
  >>>.van-cell:not(:last-child)::after
    border-bottom: 0rem solid #ebedf0;
</style>
