<template>
  <div class="appeal-list-item">
    <div class="appeal-list-item-header">
      <div class="header-left">
        <span class="appeal-type-tag appeal-type-tag-1">{{info.requestType}}</span>
      </div>
      <div class="header-right">{{info.createTime}}</div>
    </div>
    <div class="appeal-list-item-content">
      <div class="content-title">{{info.requestContent}}</div>
      <div class="content-info" v-if="showDetail">
        <div class="content-info-item">
          <div class="content-info-item-label">学生姓名</div>
          <div class="content-info-item-val">{{info.studentName}}</div>
        </div>
        <div class="content-info-item">
          <div class="content-info-item-label">联系电话</div>
          <div class="content-info-item-val">{{info.studentTelephone}}</div>
        </div>
        <div class="content-info-item">
          <div class="content-info-item-label">学校</div>
          <div class="content-info-item-val">{{info.sourceSchoolName}}</div>
        </div>
        <div class="content-info-item">
          <div class="content-info-item-label">班级</div>
          <div class="content-info-item-val">{{info.className}}</div>
        </div>
      </div>
    </div>
    <div class="appeal-list-item-footer">
      <div v-if="status" class="reply-info">
        <div class="reply-info-main">
          <div class="reply-person">
            <div class="reply-person-label">回复人</div>
            <div class="reply-person-val">{{info.replyName}}</div>
          </div>
          <div class="reply-content">
            <div class="reply-content-label">回复</div>
            <div class="reply-content-val">{{info.replyContent}}</div>
          </div>
        </div>
        <div class="reply-time">{{info.replyTime}}</div>
      </div>
      <div class="reply-action" v-else>
        <div class="reply-btn" @click="reply">回复</div>
      </div>
    </div>
    <reply-dialog :visible.sync="show" @change="onChange" :info="info" />
  </div>
</template>

<script>
import ReplyDialog from "./reply-dialog";

export default {
  name: "appeal-list-item",
  components: {
    "reply-dialog": ReplyDialog
  },
  props: {
    status: {
      type: Boolean,
      default: false
    },
    info: {
      type: Object,
      default: () => {
        return {};
      }
    },
    showDetail: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      appealRequst: {
        status: 1,
        studentPublicRelationId: 0
      },
      show: false,
      message: ""
    };
  },
  created() {},
  methods: {
    reply() {
      this.show = true;
    },
    onChange(val) {
      console.log("val==", val, ",message==", this.message);
    }
  }
};
</script>

<style lang="stylus" scoped>
.appeal-list-item {
  background: rgba(255, 255, 255, 1);
  border-radius: 8px;
  padding: 12px;
  padding-bottom: 0;
  margin: 16px;
  margin-bottom: 0;
  box-sizing: border-box;

  &-header {
    display: flex;
    align-items: center;
    font-size: 12px;
    font-weight: 400;

    .header-left {
      flex: 1;
      justify-content: flex-start;
    }

    .header-right {
      flex: 1;
      text-align: right;
    }
  }

  &-content {
    margin-top: 14px;

    .content-title {
      font-size: 17px;
      font-weight: 900;
      color: rgba(51, 51, 51, 1);
      line-height: 26px;
      word-break: break-all;
      margin-bottom: 12px;
    }

    .content-info {
      margin: 4px 0;
      font-size: 14px;
      font-weight: 400;
      line-height: 28px;

      &-item {
        display: flex;
        align-items: center;

        &-label {
          width: 56px;
          color: #999999;
        }

        &-val {
          margin-left: 12px;
          flex: 1;
        }
      }
    }
  }

  &-footer {
    position: relative;

    .reply-action {
      display: flex;
      justify-content: center;
      align-items: center;

      &::before {
        content: ' ';
        position: absolute;
        top: 0;
        left: -12px;
        height: 1px;
        width: 343px;
        background: #DDD;
        transform: scaleY(0.5);
      }

      .reply-btn {
        width: 30px;
        height: 23px;
        padding: 12px 100px;
        line-height: 23px;
        font-size: 15px;
        font-weight: 500;
        color: rgba(0, 204, 255, 1);
      }
    }

    .reply-info {
      // border-top: solid 1px #DEDEDE;
      margin-top: 12px;
      font-size: 14px;
      font-weight: 400;
      line-height: 28px;

      .reply-info-main {
        padding-left: 16px;
        background-color: #F8F8F8;

        .reply-person {
          display: flex;
          align-items: center;
          padding-top: 12px;

          &-label {
            width: 42px;
            color: #999999;
          }

          &-val {
            margin-left: 12px;
            flex: 1;
          }
        }

        .reply-content {
          display: flex;
          align-items: center;
          padding-top: 4px;
          padding-bottom: 12px;

          &-label {
            width: 42px;
            color: #999999;
          }

          &-val {
            margin-left: 12px;
            flex: 1;
            line-height: 21px;
          }
        }
      }

      .reply-time {
        font-size: 12px;
        font-weight: 400;
        color: #999999;
        line-height: 18px;
        text-align: right;
        margin-top: 8px;
      }
    }
  }
}

.appeal-type-tag {
  display: inline-block;
  padding: 2px 8px;
  border-radius: 4px;
}

.appeal-type-tag-1 {
  color: #00CCFF;
  background: rgba(229, 250, 255, 1);
}
</style>