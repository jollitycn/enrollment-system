<template>
  <div class="home">
    <div class="login-title">招生系统登录</div>
    <div class="login-form">
      <van-form ref="loginForm" validate-first :show-error="false">
        <van-field
          label-class="custom-field-title"
          v-model.trim="form.account"
          clearable
          maxlength="20"
          label="用户名"
          placeholder="请填写用户名"
          :rules="rules.account"
        />
        <van-field
          label-class="custom-field-title"
          v-model.trim="form.pwd"
          clearable
          type="password"
          maxlength="11"
          label="密码"
          :rules="rules.pwd"
          placeholder="请填写密码"
        />
        <van-button
          class="submit-btn"
          type="info"
          @click="submit"
          :loading="loading"
          loading-text="登录中..."
          block
        >登录</van-button>
      </van-form>
      <!-- <van-button class="submit-btn" type="primary" @click="login" block>登录</van-button> -->
    </div>
  </div>
</template>

<script>
    // @ is an alias to /src
    import Vue from "vue";
    import {Button, Field, Form, Sticky} from "vant";
    import {login} from "../api/login/login";

    Vue.use(Form);
Vue.use(Field);
Vue.use(Sticky);
Vue.use(Button);

    export default {
  name: "Home",
  components: {
    vanButton: Button
  },
  data() {
    return {
      loading: false,
      form: {
        account: "",
        pwd: ""
      },
      rules: {
        account: [{ required: true, message: "用户名不能为空" }],
        pwd: [{ required: true, message: "密码不能为空" }]
      }
    };
  },
  methods: {
    /**
     * 登录接口引入
     */
    submit() {
      this.$refs.loginForm.validate().then(
        () => {
          //校验通过
          this.login();
        },
        err => {
          console.error(err);
        }
      );
    },
    login() {
      let data = { ...this.form };
      this.$message.loading("登录中...");
      this.loading = true;
      console.log("data:", data);
      login(data)
        .then(res => {
          this.$message.close();
          this.loading = false;
          localStorage.setItem("ACCESS_TOKEN", res.data.token);
          localStorage.setItem("tokenTime", new Date().getTime());
          const userInfo = { ...res.data.apiInfo };
          userInfo.schoolid = userInfo.schoolId;
          localStorage.setItem("userInfo", JSON.stringify(userInfo));
          localStorage.setItem("currentSchoolId", JSON.stringify(res.data.currentSchoolId));
          this.$message.success("登录成功");
          this.$router.push({
            path: "/list"
          });
        })
        .catch(err => {
          console.error("登录失败：", err);
          this.loading = false;
        });
    }
  },
  created() {
    localStorage.clear();
  },
  mounted() {
    window.addEventListener('offline', ()=>{
     // 网络由正常常到异常时触发
     window.sessionStorage.locationUrl=window.location.href;
     this.$router.replace('/networkError')
    });
    window.addEventListener('online',()=>{
      window.location.href=window.sessionStorage.locationUrl
    });
  }
};
</script>
<style scoped lang="stylus">
.home {
  min-height: 100vh;
  background-color: #fff;

  .login-title {
    padding-top: 100px;
    margin-bottom: 50px;
    padding-left: 20px;
    font-size: 21px;
    color: #333;
    font-weight: bold;
  }

  .login-form {
    display: flex;
    align-items: center;
    justify-content: center;

    .van-form {
      width: 100%;
    }

    .submit-btn {
      position: absolute;
      bottom: 0;
    }
  }
}

.test {
  background-color: navBgColor;
}
</style>
