<template>
  <div class="visit-school-record">
    <h5-header :leftStatus="true" :isLeft="true" @clickHeadLeft="clickHeadLeft" title="学校拜访记录"></h5-header>
    <div class="visit-school-title ml16 hig line">
      <div class="num">序号</div>
      <div class="school-name">学校名称</div>
      <div class="visit-frequency">拜访次数</div>
    </div>
    <div class="school-news" v-if="list&&list.length">
      <div class="ml16 hig line" v-for="(item,index) in list" :key="index">
        <div class="visit-school-info">
          <div class="num-new">
            <span>{{index+1}}</span>
          </div>
          <div class="school-news-info">
            <van-cell
              @click="goVisitSchool(item.sourceSchoolId)"
              :title="item.sourceSchoolName"
              is-link
              :value="item.amount"
              value-class="school-news-cell"
              title-class="school-news-schoName"
            >
              <template #right-icon>
                <van-icon
                  size="small"
                  name="arrow"
                  style="line-height: inherit;"
                  class="school-news-icon"
                />
                <!-- <img
                  style="width:16px;height:32px; margin-left: 8px;"
                  src="../../../assets/images/right.png"
                  name="arrow-left"
                /> -->
              </template>
            </van-cell>
          </div>
        </div>
        <van-divider :style="{margin: '0', marginLeft: '-100px'}" />
      </div>
    </div>
    <custom-empty v-else :type="1" height="calc(100vh - 110px)" />
  </div>
</template>

<script>
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import { pageGroupCount } from "../../../api/visit-school-record/visit-school-record";
import Vue from "vue";
import { Col, Row, Cell, CellGroup, Icon } from "vant";
import { Divider } from "vant";
Vue.use(Divider);
Vue.use(Cell);
Vue.use(CellGroup);
Vue.use(Col);
Vue.use(Row);
Vue.use(Icon);
export default {
  name: "visit-school-record",
  components: {
    h5Header: Header,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      query: {
        pageNum: 1,
        pageSize: 1000000
      },
      list: []
    };
  },
  created() {
    this.pageGroupCount(this.query);
  },
  methods: {
    /**
     * 跳转到查看拜访学校记录明细
     * @param schoolID{number} 查看对应拜访学校记录明细所需的ID
     * @author LiuGuoHao
     */
    goVisitSchool(sourceSchoolId) {
      this.$router.push({
        path: `/admissions-process/visit-record-list/${sourceSchoolId}`
      });
    },
    /**
     * 返回招生过程管理
     * @author LGH
     */
    clickHeadLeft() {
      this.$router.push({
        path: `/admissions-process`
      });
    },
    /**
     * 拜访学校列表分页查询
     * @param query{array} 存放分页数据
     * @author LGH
     */
    pageGroupCount(query) {
      this.$message.loading("正在加载...");
      pageGroupCount(query)
        .then(res => {
          this.$message.close();
          this.list = res.data.records;
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    }
  }
};
</script>

<style scoped lang="stylus">
.visit-school-record {
  .ml16 {
    padding-left: 16px;
  }

  .hig {
    height: 53px;
  }

  .line {
    // border-bottom: solid 1px #DDDDDD;
  }

  .gray {
    color: #999999;
  }

  .visit-school-title {
    background-color: #F2F2F2;
    color: #999999;
    display: flex;
    align-items: center;
    height: 30px;

    .num {
      width: 70px;
    }

    .visit-frequency {
      width: 80px;
    }

    .school-name {
      flex: 1;
    }
  }

  .school-news {
    background: #FFFFFF;

    .visit-school-info {
      display: flex;
      align-items: center;
      height: 53px;
    }

    .school-news-cell {
      // width: 9px;
      height: 23px;
      font-size: 15px;
      font-weight: 400;
      color: rgba(153, 153, 153, 1);
      line-height: 23px;
      flex: none;
    }

    .school-news-schoName {
      height: 23px;
      line-height: 23px;

      span {
        font-weight: 600;
        font-size: 15px;
      }
    }

    .school-news-icon {
      color: rgba(153, 153, 153, 1);
      margin-left: 8px;
    }

    .num-new {
      width: 56px;
      height: 23px;
      font-size: 15px;
      font-weight: 400;
      color: rgba(51, 51, 51, 1);
      line-height: 23px;
    }

    .school-news-info {
      flex: 1;
    }
  }
}
</style>