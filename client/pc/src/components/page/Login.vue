<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title" >ER招生系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="account">
                    <el-input tabindex="1" v-model.trim="param.account" placeholder="请输入账号">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="pwd">
                    <el-input  
                        @keyup.enter.native="submitForm()"
                        tabindex="2"
                        show-password
                        type="password"
                        placeholder="请输入密码"
                        v-model.trim="param.pwd"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
              <!-- <div class="login-img">
                <el-form-item prop="randImageValidateCode">
                  <el-input tabindex="3" maxlength="4" v-model.trim="param.randImageValidateCode" placeholder="验证码区分大小写" @keyup.enter.native="submitForm()">
                    <el-button slot="prepend" icon="el-icon-lx-pic"></el-button>
                  </el-input>
                </el-form-item>
                <img :src="codeImg" class="login-img-img" @click="refreshValidateCode">
              </div> -->
              <div class="login-btn">
                    <el-button tabindex="4" type="primary" :loading="loading" @click="submitForm()">登录</el-button>
                </div>
                <!-- <p class="login-tips"><el-link tabindex="5" :underline="false" type="primary" @click="getPsw">忘记密码？</el-link></p> -->
            </el-form>
        </div>
    </div>
</template>

<script>
import { login} from '../../api/index'; // 测试接口可删
import md5 from 'js-md5';
import config from '@/config';
export default {
    name: 'Login',
    data: function() {
        return {
            loading:false,
            codeImg: config.rootPath+'validateCode/getValidateCodeImage?' + Math.random(),
            param: {
                account: '',
                pwd: '',
                randImageValidateCode: ''
            },
            rules: {
                account: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                pwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                // randImageValidateCode: [{ required: true, message: '请输入图形验证码', trigger: 'blur' }]
            },
        };
    },
    methods: {
        /**
         *  登录
         */
        login() {
            let param = Object.assign({},this.param);
            // param.pwd = md5(this.param.pwd);
            this.loading = true;
            login(param).then(res => {
                this.loading = false;
                console.log(res);
                this.$message.success(`登录成功`);
                localStorage.setItem("ACCESS_TOKEN",res.data.token);
                localStorage.setItem('menus',JSON.stringify(res.data.menus))
                delete res.data.userName
                localStorage.setItem('user', JSON.stringify(res.data));
                //特殊处理，兼容获取userInfo方式获取schoolid
                const userInfo = {...res.data.apiInfo};
                userInfo.schoolid = userInfo.schoolId;
                localStorage.setItem("userInfo",JSON.stringify(userInfo))
                localStorage.setItem('ms_username', userInfo.userName);
                localStorage.setItem("tokenTime", (new Date().getTime()));
                this.$router.push('/');
            }).catch(err => {
                this.loading = false;
                console.error('登录失败：',err)
            })
        },
        refreshValidateCode () {
            this.codeImg = config.rootPath+'validateCode/getValidateCodeImage?' + Math.random()
        },
        submitForm() {
            this.$refs.login.validate(valid => {
                if (valid) {
                    this.login()
                }
            });
        },
        getPsw () {
            this.$router.push({path:'/retrieve-password'});
            console.log(1)
        }
    },
    created() {

    }
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: cover;
    background-repeat: no-repeat;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #000;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.login-img {
  position: relative;
}
.login-img-img {
  width: 97px;
  height: 30px;
  position: absolute;
  top: 1px;
  right: 1px;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
