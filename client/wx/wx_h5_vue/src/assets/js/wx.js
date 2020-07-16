import axios from "axios"
export default {
  wxShowMenu: function() {
    let url = location.href.split("#")[0];
    let u =  process.env.NODE_ENV === "development" ? "/" : "wechat/";
    axios
      .get(
         u + "scan/wx", //请求你们公司后台的接口 获取相关的配置
        {
          params: {
            urlSigna: url
          },
          headers:{
            token: localStorage.getItem("token")
          }
        }
      )
      .then(res => {
        let getMsg = res.data.data;
        let wx = window.wx;
        wx.config({
          debug: false, //生产环境需要关闭debug模式  测试环境下可以设置为true  可以在开发者工具中查看问题
          appId: getMsg.wxAppId, //appId通过微信服务号后台查看
          timestamp: "" + getMsg.timesTamp, //生成签名的时间戳
          nonceStr: "" + getMsg.noncestr, //生成签名的随机字符串
          signature: getMsg.signature, //签名
          jsApiList: [
            //需要调用的JS接口列表
            'scanQRCode' // 扫一扫
          ]
        });
        //
        wx.error(function(res) {
          alert("微信注入信息失败,请联系开发人员")
          console.log(res,"微信注入信息失败");
          // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
        });
        wx.ready(function(res) {
          console.log(res,"初始化完成");
          wx.checkJsApi({
            jsApiList: ['scanQRCode'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res2) {
              console.log(res2);
              // 以键值对的形式返回，可用的api值true，不可用为false
              // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
            },
            fail: function (err2) {
              console.log(err2);
            }
          })
        });
      })
      .catch(error => {
        // alert(error);
        console.log(error);
      });
  }
};
