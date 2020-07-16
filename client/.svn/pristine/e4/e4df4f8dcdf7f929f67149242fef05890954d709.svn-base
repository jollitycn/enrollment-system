//是否开发环境，否则为测试或线上环境
const env = process.env.NODE_ENV === "development" ? "dev" : "pro";
const config = {
  dev: {
    //上传图片的图片目录
    imgDir: `/static/`
  },
  pro: {
    //上传图片的图片目录
    imgDir: `/wechat/static/`
  }
};

export const imgDir = config[env].imgDir;
export const webImgDir = "http://educiot-recruit-miniapp-test.szis.com.cn:9003/wechat";

