<template>
  <div class="visit-school-record">
    <h5-header
      :isLeft="true"
      :leftStatus="true"
      rigText="新增"
      title="拜访记录查看"
      @clickHeadLeft="clickHeadLeft"
      @clickHeadAction="clickHeadAction"
    ></h5-header>
    <van-dropdown-menu>
      <van-dropdown-item
        v-model="visitSearch.sourceSchoolId"
        :options="visitOptions"
        @change="goVisitRecord"
      />
    </van-dropdown-menu>
    <van-list
      v-if="form&&form.length"
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      @load="onLoad"
    >
      <div class="visit" v-for="(item,index) in form" :key="index">
        <div class="visit-news" v-if="isFinish">
          <div class="visit-news-left">
            <div class="visit-news-name">学校名称</div>
            <div class="visit-news-name">沟通人</div>
            <div class="visit-news-name">联系方式</div>
            <div class="visit-news-name">负责人</div>
            <div class="visit-news-name">开始时间</div>
            <div class="visit-news-name">结束时间</div>
          </div>
          <div class="visit-news-middle">
            <div class="visit-news-detail">{{item.sourceSchoolName}}</div>
            <div class="visit-news-detail">{{item.contacter}}</div>
            <div class="visit-news-detail">{{item.telephone}}</div>
            <div class="visit-news-detail">{{item.creatorName}}</div>
            <div class="visit-news-detail">{{item.startTime}}</div>
            <div class="visit-news-detail">{{item.endTime}}</div>
          </div>
        </div>
        <div class="visit-content" v-if="isFinish">
          <div class="visit-conname">拜访内容</div>
          <div class="visit-condetail">
            <p>{{item.content}}</p>
          </div>
        </div>
        <div class="visit-btn" v-if="item.isMine||isFinish">
          <div class="visit-btn-del" @click="visitDelete(item.publicRelation)">删除</div>
          <div class="visit-btn-edit" @click="visitEdit(item.publicRelation)">修改</div>
        </div>
      </div>
    </van-list>
    <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
  </div>
</template>

<script>
// import moduleName from 'module';
import CustomEmpty from "@/components/custom-empty";
import { listAllSourceSchoolGroup } from "@/api/admissions-process/preaching-plan";
import {
  listGroupCount,
  deleteGroupCount
} from "../../../api/visit-school-record/visit-school-record";
import Header from "@/components/cheader";
import Vue from "vue";
import {
  Col,
  Row,
  Cell,
  CellGroup,
  DropdownMenu,
  DropdownItem,
  Toast,
  List
} from "vant";
Vue.use(Toast);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Cell);
Vue.use(CellGroup);
Vue.use(Col);
Vue.use(Row);
Vue.use(List);
export default {
  name: "visit-record-list",
  components: {
    h5Header: Header,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      isFinish: false, //用作渲染，数据加载完成时才做渲染处理
      total: 0,
      loading: false,
      finished: false,
      form: {
        sourceSchoolName: ""
      },
      visitSearch: {
        pageNum: 1,
        pageSize: 10,
        sourceSchoolId: this.$route.params.sourceSchoolId
      },
      visitOptions: []
    };
  },
  watch: {
    "$route.params.sourceSchoolId"() {
      this.listGroupCount(this.visitSearch);
    }
  },
  created() {
    this.listAllSourceSchoolGroup();
  },
  methods: {
    onLoad() {
      let visitSearch = { ...this.visitSearch };
      this.listGroupCount(visitSearch);
    },
    /**
     * 删除按钮处理
     * @param id{int} 删除操作梭需要的id
     */
    visitDelete(id) {
      this.$message.loading("正在删除...");
      deleteGroupCount(id)
        .then(() => {
          this.$message.close();
          this.$message.success("删除成功！");
          this.visitSearch = {
            pageNum: 1,
            pageSize: 10,
            sourceSchoolId: this.$route.params.sourceSchoolId
          };
          this.listGroupCount(this.visitSearch);
        })
        .catch(err => {
          console.error("删除失败：", err);
          this.$message.close();
        });
    },
    /**
     * 修改按钮处理
     * @param id{int} 修改操作所需要的id，用于获取对应学校的详情
     * @author LGH
     */
    visitEdit(id) {
      this.$router.push({
        path: `/admissions-process/visit-record-add/${id}`
      });
    },
    /**
     * 修改搜索框的拜访学校时操作
     * @author LGH
     */
    goVisitRecord() {
      this.isFinish = false;
      this.finished = false;
      this.loading = false;
      // this.listGroupCount(this.visitSearch);
      this.visitSearch.pageNum = 1;
      this.form = { sourceSchoolName: "" };
      this.$router.push({
        path: `/admissions-process/visit-record-list/${this.visitSearch.sourceSchoolId}`
      });
    },
    /**
     * 返回学校拜访列表
     */
    clickHeadLeft() {
      this.$router.push({
        path: `/admissions-process/visit-school-record`
      });
    },
    /**
     * 获取招生学校下的所有的生源学校
     * @author LGH
     */
    listAllSourceSchoolGroup() {
      ``;
      this.$message.loading("正在加载...");
      listAllSourceSchoolGroup()
        .then(res => {
          this.$message.close();
          this.visitOptions = res.data;
          //使用正则表达式全局替换所需要的字段
          this.visitOptions = JSON.parse(
            JSON.stringify(this.visitOptions).replace(
              /sourceSchoolName/g,
              "text"
            )
          );
          this.visitOptions = JSON.parse(
            JSON.stringify(this.visitOptions).replace(
              /sourceSchoolId/g,
              "value"
            )
          );
          this.listGroupCount(this.visitSearch);
          this.isFinish = true;
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 拜访学校列表分页内容
     * @param visitSearch{array} 查询拜访学校列表的分页数据
     * @author LGH
     */
    listGroupCount(visitSearch) {
      this.$message.loading("正在加载...");
      listGroupCount(visitSearch)
        .then(res => {
          this.visitSearch.pageSize = res.data.size;
          this.total = res.data.total;
          let data = res.data.current === 1 ? [] : this.form;
          this.form = data.concat(res.data.records);
          for (let i in this.form) {
            this.form[i].sourceSchoolName = this.getSchoolNameById(
              this.form[i].sourceSchoolId
            );
          }
          this.isFinish = true;

          this.visitSearch.pageNum = this.visitSearch.pageNum + 1;
          //加载完成后，将加载状态改为已完成
          if (this.form.length >= this.total) {
            this.finished = true;
          }
          //取消加载中状态
          this.$message.close();
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 新增页面跳转
     * @author LGH
     */
    clickHeadAction() {
      console.log(this.visitSearch.sourceSchoolId,1111);
      this.$router.push({
        path: "/admissions-process/visit-record-add",
        query: { sourceSchoolId: this.visitSearch.sourceSchoolId }
      });
    },
    /**
     * 根据面向学校ID获取学校名称
     * @param sourceSchoolId{String} 面向学校ID
     * @author LiuGuoHao
     */
    getSchoolNameById(sourceSchoolId) {
      var obj = this.visitOptions.find(function(x) {
        return x.value == sourceSchoolId;
      });
      return obj.text;
    }
  }
};
</script>

<style scoped lang="stylus">
.visit-school-record {
  background-color: rgba(248, 248, 248, 1);
  min-height: 100vh;
}

.visit {
  height: 375px;
  margin-top: 8px;
  background-color: rgba(255, 255, 255, 1);

  .visit-news {
    display: flex;
    align-items: center;
    margin-left: 16px;
    margin-top: 32px;

    .visit-news-left {
      color: #999999;
      margin-top: 16px;

      .visit-news-name {
        width: 76px;
        height: 25px;
        align-items: center;
      }
    }

    .visit-news-middle {
      margin-top: 16px;

      .visit-news-detail {
        height: 25px;
      }
    }
  }

  .visit-content {
    margin-top: 12px;
    margin-bottom: 16px;
    margin-left: 16px;
    width: 343px;
    height: 112px;
    background: rgba(248, 248, 248, 1);
    padding-top: 8px;

    .visit-conname {
      font-weight: 600;
      width: 60px;
      margin-top: 8px;
      margin-left: 12px;
    }

    .visit-condetail {
      width: 311px;
      margin-top: 8px;
      color: #666666;
      margin-left: 12px;

      p {
        line-height: 23px;
      }
    }
  }

  .visit-btn {
    display: flex;
    align-content: center;
    text-align: center;
    border-top: solid 1px rgba(0, 0, 0, 0.08);
    height: 51px;
    line-height: 51px;

    .visit-btn-del {
      color: #FF4C4C;
      flex: 1;
      text-align: center;
    }

    .visit-btn-edit {
      color: #00CCFF;
      flex: 1;
      border-left: solid 1px rgba(0, 0, 0, 0.08);
    }
  }
}
</style>