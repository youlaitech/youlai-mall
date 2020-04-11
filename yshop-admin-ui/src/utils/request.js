import axios from 'axios'
import {MessageBox, Message, Loading} from 'element-ui'
import {getToken} from '@/utils/auth'
import store from '@/store'
import _ from 'lodash'
import $ from 'jquery'


axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 客户端信息Base64加密 client:secret
axios.defaults.headers.post['Authorization'] = 'Basic Y2xpZW50OnNlY3JldA==';

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  withCredentials: true, // send cookies when cross-domain request
  timeout: 50000
})

/** 请求拦截器 */
service.interceptors.request.use(
  config => {
    if (config.headers.showLoading !== false) { // 不等false 就显示加载Loading
      let target = config.headers.loadingTarget
      if (!target) { // Loading 显示区域为空则判断默认区域
        if (config.method === "get") {
          if (config.url.indexOf("page") !== -1) {
            target = "#dataTable"
          }
        }else if(config.method=="delete"){
          target= "#dataTable"
        }
        if(!target){
          target="#dataForm"
        }
      }
      showLoading(target);
    }
    if (store.getters.token) {
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    if (config.headers.showLoading !== false) {  // 请求设置显示Loading,错误回调关闭Loading
      hideLoading();
    }
    Message.error('请求超时!');
    return Promise.reject(error)
  }
)

/** 响应拦截器 */
service.interceptors.response.use(
  response => {
    if (response.config.headers.showLoading !== false) { // 设置显示Loading，响应后则隐藏
      hideLoading();
    }
    const responseData = response.data
    if (responseData.code != undefined) {
      // if the custom code is not 20000, it is judged as an error.
      if (responseData.code !== 0) {
        Message({
          message: responseData.message || 'error',
          type: 'error',
          duration: 5 * 1000
        })

        // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
        if (responseData.code === 50008 || responseData.code === 50012 || responseData.code === 50014) {
          // to re-login
          MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }).then(() => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
          })
        }
        return Promise.reject(res.message || 'error')
      } else {
        return responseData
      }
    } else {
      return responseData
    }
  },
  error => {
    if (error.config.headers.showLoading !== false) { // 设置显示Loading，响应后则隐藏
      hideLoading();
    }

    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)


// 请求加载时Loading...
let loading;
let needLoadingRequestCount = 0;

//显示loading
function showLoading(target) {
  if (target) {
    let targetDom = $(target.substr(0, 1) + target.substr(1, target.length - 1))
    if (targetDom && targetDom.length > 0) {
      // 后面这个判断很重要，因为关闭时加了抖动，此时loading对象可能还存在，
      // 但needLoadingRequestCount已经变成0.避免这种情况下会重新创建个loading
      if (needLoadingRequestCount === 0 && !loading) {
        loading = Loading.service({
          lock: true,
          text: "Loading...",
          background: 'rgba(255, 255, 255, 0.5)',
          target: target
        });
      }
      needLoadingRequestCount++;
    }
  }
}

//隐藏loading
function hideLoading() {
  needLoadingRequestCount--;
  needLoadingRequestCount = Math.max(needLoadingRequestCount, 0); //做个保护
  if (needLoadingRequestCount === 0) {
    //关闭loading
    toHideLoading();
  }
}

//防抖：将 300ms 间隔内的关闭 loading 便合并为一次。防止连续请求时， loading闪烁的问题。
const toHideLoading = _.debounce(() => {
  if (loading) {
    loading.close();
    loading = null;
  }
}, 300);

export default service
