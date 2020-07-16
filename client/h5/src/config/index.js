// 
//是否开发环境，否则为测试或线上环境
const env = process.env.NODE_ENV === 'development' ? 'dev' : 'pro';
const config = {
  dev: {
    //系统所有接口的根路径
    rootPath: '/',
    //上传图片的图片目录
    imgDir: `static/`,
    webImgDir: "http://49.234.150.30:8603"
  },
  pro: {
    //系统所有接口的根路径
    rootPath: '/app/',
    //上传图片的图片目录
    imgDir: `static/`,
    webImgDir: "http://49.234.150.30:8603"
  }
}

export default {
  ...config[env]
}