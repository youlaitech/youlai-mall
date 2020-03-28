import router from './router'
import store from './store'
import {getToken} from '@/utils/auth'
import getPageTitle from '@/utils/get-page-title'

import {Message} from 'element-ui'

import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

NProgress.configure({showSpinner: false})

const whiteList = ['/login'] //配置白名单

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name

      if (hasGetUserInfo) {
        next()
      } else {
        try {
          // get user info
          await store.dispatch('getInfo')
           /* .then(() => {
            store.dispatch('generateRoutes').then(accessRoutes => {
              router.addRoutes(accessRoutes) //动态添加后台获取可访问路由数据
              next({...to, replace: true})  // hack方法 确保addRoutes已完成
            })
          })*/
           next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
