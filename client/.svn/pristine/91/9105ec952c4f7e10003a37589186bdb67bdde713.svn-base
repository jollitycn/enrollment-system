<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title" >重置密码</div>
            <div v-show="isShow">
                <el-form
                    :model="msgVerifyForm"
                    :rules="msgVerifyFormRules"
                    ref="msgVerifyForm"
                    :label-width=" isShow ? '0px' : '80px'"
                    class="ms-content"
                >
                    <el-form-item prop="mobile">
                        <el-input v-model.trim="msgVerifyForm.mobile" maxlength="11"z placeholder="请输入手机号">
                            <el-button slot="prepend" icon="el-icon-lx-mobilefill"></el-button>
                            <!-- 打开图形验证码 -->
                            <el-button slot="append" @click="openMsgVerify" :disabled="!allowSendMsg" >
                                <span v-show="allowSendMsg" :class="{ 'send-msg': allowSendMsg }">
                                    {{text}}
                                </span>
                                <span v-show="!allowSendMsg">{{sendMsgInterval}} 秒重新发送</span>
                            </el-button>
                        </el-input>
                    </el-form-item>
                    <!-- 手机验证码输入 -->
                    <el-form-item prop="msgCode">
                        <el-input
                            maxlength="6"
                            placeholder="请输入验证码"
                            v-model.trim="msgVerifyForm.msgCode"
                            @keyup.enter.native="msgVerify"
                        >
                            <el-button slot="prepend" icon="el-icon-lx-pic"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item >
                        <div class="login-btn">
                            <el-button type="primary" @click="msgVerify">确定</el-button>
                        </div>
                        <p class="login-tips"><el-link href="#/login" :underline="false" type="primary" ><i class="el-icon-back"></i> 返回登录</el-link></p>
                    </el-form-item>

                </el-form>
                
            </div>

            <div v-show="!isShow">
                <el-form
                    :model="changePwdForm"
                    :rules="changePwdFormRules"
                    ref="changePwdForm"
                    :label-width=" isShow ? '0px' : '80px'"
                    class="ms-content"
                >
                <el-form-item prop="mobile" label="用户名">
                    <el-input v-model.trim="changePwdForm.mobile" maxlength="11" disabled></el-input>
                </el-form-item>

                <el-form-item prop="password" label="重置密码">
                    <el-input
                        show-password
                        type="password"
                        placeholder="请输入密码"
                        v-model.trim="changePwdForm.password"
                        maxlength="32"
                    ></el-input>
                </el-form-item>

                <el-form-item prop="password1" label="确认密码">
                    <el-input
                        show-password
                        type="password"
                        placeholder="请再输入一次"
                        v-model.trim="changePwdForm.password1"
                        maxlength="32"
                    ></el-input>
                    </el-form-item>
                    <el-form-item >
                        <div class="login-btn">
                            <el-button type="primary" @click="changePwd">更改密码</el-button>
                        </div>
                        <p class="login-tips"><el-link href="#/login" :underline="false" type="primary"><i class="el-icon-back"></i> 返回登录</el-link></p>
                    </el-form-item>
                </el-form>
            </div>
        </div>

        <!-- 图片验证码获取验证 -->
        <el-dialog title="安全验证" :close-on-click-modal="false" :visible.sync="dialogMsgVerifyVisible" width="20%">
            <el-form ref="imgVerifyForm" :model="imgVerifyForm" :rules="imgVerifyFormRules">
                <el-form-item label="点击图片刷新" :label-width="formLabelWidth">
                    <img :src="codeImg" class="login-img-img" @click="refreshValidateCode" />
                </el-form-item>
                <el-form-item prop="randImageValidateCode" label="图形验证码" :label-width="formLabelWidth">
                    <el-input @keyup.enter.native="imgCodeVerify" maxlength="4" v-model="imgVerifyForm.randImageValidateCode" autocomplete="off"></el-input>
                    <el-button slot="prepend" icon="el-icon-lx-pic"></el-button>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeMsgVerify">取 消</el-button>
                <el-button type="primary" @click="imgCodeVerify">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

 <script>
import { msgVerify, codeVerify, getValidateCodeImage,changePassword } from '@/api/index';
import config from '@/config';
import md5 from 'js-md5';

export default {
    name: 'RetrievePassword',
    data: function() {
        const validatePass = (rule, value, callback) => {
            if (this.changePwdForm.password1 !== '') {
              this.$refs['changePwdForm'].validateField('password1');
            }
            callback();
          }
          const validatePass2 = (rule, value, callback) => {
            if (value !== this.changePwdForm.password) {
              callback(new Error('两次输入密码不一致!'));
            } else {
              callback();
            }
          }
        return {
            text: '获取验证码',
            isShow: true,
            codeImg: config.rootPath + 'validateCode/getValidateCodeImage?' + Math.random(),
            imgVerifyForm: {
                mobile: '',
                randImageValidateCode: '',
            },
            imgVerifyFormRules:{
                mobile: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[34578]\d{9}$/, message: '请输入正确手机号', trigger: 'blur' }
                ],
                randImageValidateCode: [{ required: true, message: '请输入图形验证码', trigger: 'blur' }]
            },
            formLabelWidth: '120px',
            dialogMsgVerifyVisible: false,
            msgVerifyForm: {
                mobile: '',
                msgCode: '',
            },
            msgVerifyFormRules:{
                mobile: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[34578]\d{9}$/, message: '请输入正确手机号', trigger: 'blur' }
                ],
                msgCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
            },
            changePwdForm:{
                userId: '',
                mobile: '',
                password: '',
                password1: '',
            },
            changePwdFormRules:{
                mobile: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[34578]\d{9}$/, message: '请输入正确手机号', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min:6,max:32, message: '请输入最少6位，最多32位的密码', trigger: 'blur' },
                    { validator: validatePass, trigger: 'blur' }
                ],
                password1: [
                    { required: true, message: '请再次输入密码', trigger: 'blur' },
                    { min:6,max:32, message: '请输入最少6位，最多32位的密码', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur' }
                ],
            },
            sendMsgInterval:config.sendMsgInterval,
            allowSendMsg:true,
            timer:undefined,
        };
    },
    methods: {
        openMsgVerify() {
            //校验手机号
            this.msgVerifyFormRules.msgCode[0].required = false;
            this.$refs['msgVerifyForm'].validate(valid=>{
                if(valid){
                     //清空表单
                    this.elFormReset('imgVerifyForm');
                    //初始化
                    this.imgVerifyForm.mobile = this.msgVerifyForm.mobile
                    //弹出显示
                    this.dialogMsgVerifyVisible = true;
                }
            });
        },
        closeMsgVerify() {
            this.dialogMsgVerifyVisible = false;
        },
        /**
         * 图片验证码刷新
         */
        refreshValidateCode() {
            this.codeImg = config.rootPath + 'validateCode/getValidateCodeImage?' + Math.random();
        },
        /**
         * 图片验证码验证
         * 验证成功后获取短信验证码
         */
        imgCodeVerify() {
            this.$refs['imgVerifyForm'].validate(valid=>{
                if(valid){
                    let params = {...this.imgVerifyForm}
                    codeVerify(params).then(res => {
                        //隐藏弹框
                        this.dialogMsgVerifyVisible = false;
                        //开启倒计时
                        this.countDown();

                        this.$message.success('验证码已发送，请留意短信消息');
                    }); 
                }
            });
        },
        /**
         * 开启倒计时
         */
        countDown(){
            //恢复默认倒计时
            this.sendMsgInterval =config.sendMsgInterval;
            //显示倒计时
            this.allowSendMsg = false;
            if(this.timer){
                clearInterval(timer)
            }
            this.timer = setInterval(() => {
                if(this.sendMsgInterval<=0){
                    clearInterval(this.timer);
                    this.allowSendMsg = true;
                }
                this.sendMsgInterval--;
            }, 1000);
        },
        
        /**
         * 短信验证
         */
        msgVerify() {
            this.msgVerifyFormRules.msgCode[0].required = true;
            this.$refs['msgVerifyForm'].validate(valid => {
                if (valid) {
                    let params = {...this.msgVerifyForm}
                    msgVerify(params).then(res=>{
                        console.log('msgVerify:',res)
                        //清空表单
                        this.elFormReset('changePwdForm')
                        //初始化
                        this.changePwdForm.mobile = this.msgVerifyForm.mobile;
                        this.changePwdForm.userId = res.data
                        //显示修改密码表单
                        this.isShow = false;
                    })
                }
            });
        },
        /**
         * 修改密码
         */
        changePwd(){
            this.$refs['changePwdForm'].validate(valid => {
                if (valid) {
                    const {userId,password} = this.changePwdForm;
                    let params = {
                        userId,
                        password:md5(password)
                    }
                    changePassword(params).then(res=>{
                        if(res.retCode === 0){
                            this.$message.success('已成功修改密码，请使用新密码登录系统');
                            setTimeout(()=>{
                                this.$router.push({path:'/login'})
                            },1000)
                        }
                    })

                }
            });
        },
    }
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
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
.login-img-img {
    width: 97px;
    height: 30px;
    top: 1px;
    right: 1px;
}

.send-msg{
    color: #409EFF;
}

</style>
