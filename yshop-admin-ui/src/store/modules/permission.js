import {constantRoutes} from '@/router'
import Layout from '@/layout'
import {getRouters} from '@/api/ums'

const permission = {
  state: {
    routes: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.routes = constantRoutes.concat(routes) //基本配置路由 衔接 根据权限匹配后产生的路由
    }
  },
  actions: {
    generateRoutes({commit}) {
      return new Promise(resolve => {
        getRouters().then(res => {
          const accessedRoutes = filterAsyncRoutes(res.data)
          commit('SET_ROUTES', accessedRoutes)
          resolve(accessedRoutes)
        })
      })
    }
  }
}

export const filterAsyncRoutes = (asyncRouterMap) => { // 遍历后台传来的路由字符串，转换为组件对象
  return asyncRouterMap.filter(route => {
    if (route.component) {
      if (route.component === "Layout") { // Layout组件特殊处理
        route.component = Layout
      } else {
        route.component = loadView(route.component)
      }
    }
    if (route.children && route.children.length) {
      route.children = filterAsyncRoutes(route.children)
    }
    return true
  })
}


export const loadView = (view) => { // 路由懒加载
  return () => import(`@/views/${view}`)
}

export default permission
