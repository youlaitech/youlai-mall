// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

import ElementUI from 'element-ui'
import Pagination from '@/components/Pagination'
import permission from './directive/permission'
import locale from 'element-ui/lib/locale/lang/zh-CN'


import './assets/icons'
import './permission'
import 'normalize.css/normalize.css'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/styles/index.scss'


Vue.use(ElementUI, {locale}) //国际化
Vue.use(permission)

Vue.component('Pagination', Pagination)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})


import {parseTime, resetForm, addDateRange} from "@/utils/index";

import {formatDate} from "@/utils/date";
// 全局方法挂载
Vue.prototype.resetForm = resetForm
Vue.prototype.parseTime = parseTime

Vue.prototype.addDateRange = addDateRange
Vue.prototype.formatDate = formatDate

// 全局变量声明定义
Vue.prototype.uploadAction = process.env.VUE_APP_BASE_API + '/files'

//  全局引入 过滤器
import '@/utils/filter'
