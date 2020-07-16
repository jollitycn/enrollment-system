import { Toast, } from 'vant'
import { failTest } from './failText'
import { errorText } from './errorText'
import R from '@/router';
import { debounce2 } from './index'

export const Message = (message)=>{
    Toast(message);
}

Message.error = (message) => {
    Toast.fail(message);
};
Message.success = (message) => {
    Toast.success(message);
};
Message.warning = (message) => {
    Toast(message);
};
Message.close = (closeAll) => {
    Toast.clear(closeAll);
};

Message.loading = (message='加载中...') => {
    Toast.loading({
        duration: 5000,
        message: message,
        forbidClick: true,
    });
};



class Me {
    constructor() {
        //防抖函数改造，同样的消息200毫秒内只执行一次
        let _error = this.error;
        let _success = this.success;
        let _fail = this.fail;
        this.error = debounce2(_error)
        this.success = debounce2(_success)
        this.fail = debounce2(_fail)
    }
    // 成功提示
    success(title) {
        Message.success(title)
    }
    // 失败提示
    error(code, title) {
        if (errorText[code]) {
            //登录信息失效，直接跳转
            if (errorText[code].path) {
                localStorage.clear();
                R.push({ path: errorText[code].path })
            }
            Message.error({
                message: title || errorText[code].title,
                type: 'error',
                onClose: () => {
                    // R.push({path : errorText[code].path})
                }
            })
        } else {
            Message.error(title)
        }
    }
    //系统一次提示
    fail(code) {
        if (failTest[code]) {
            Message.error(failTest[code])
        } else {
            Message.error('服务器繁忙')
        }
    }
}
const M = new Me()

export { M }