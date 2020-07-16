/**
 * 防抖
 * @param fn {function} 回调函数
 * @param delay 延迟时间数，单位毫秒，默认200毫秒
 * @author lmh
 */
export const debounce = (fn, delay) => {

    delay = delay || 200;
    let timer;
    return function() {
        let th = this;
        let args = arguments;
        if (timer) {
            clearTimeout(timer);
        }
        timer = setTimeout(function() {
            timer = null;
            fn.apply(th, args);
        }, delay);
    };
}

/**
 * 防抖改造函数，第一个参数相同只会执行一次
 * @param fn {function} 回调函数
 * @param delay 延迟时间数，单位毫秒，默认200毫秒
 * @author lmh
 */
export const debounce2 = (fn, delay) => {
    delay = delay || 200;
    let timer;
    let cache = null;
    return function(a, b, c) {
        let th = this;
        // let args = arguments;
        console.log('a:', a, ',b==', b, 'c==', c)
            // console.log('当前参数：', args)
        if (timer && cache === a) {
            clearTimeout(timer);
        }
        cache = a;
        timer = setTimeout(function() {
            timer = null;
            let args = [a, b, c]
            fn.apply(th, args);
        }, delay);
    };
}



/**
 * 节流
 * @param fn {function} 回调函数
 * @param delay 延迟时间数，单位毫秒，默认200毫秒
 * @author lmh
 */
export const throttle = (fn, interval) => {
    let last;
    let timer;
    interval = interval || 200;
    return function() {
        let th = this;
        let args = arguments;
        let now = new Date();
        if (last && now - last < interval) {
            clearTimeout(timer);
            timer = setTimeout(function() {
                last = now;
                fn.apply(th, args);
            }, interval);
        } else {
            last = now;
            fn.apply(th, args);
        }
    }
}