import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */

/**
 * 静态公共路由，无需权限
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard'),
      meta: {title: '首页', icon: 'dashboard'}
    }]
  },

  {
    path: '/pms',
    component: Layout,
    redirect: '/pms/spu',
    meta: {title: '商品管理', icon: 'product'},
    alwaysShow: true,
    children: [
      {
        path: 'spu',
        name: 'spuIndex',
        component: () => import('@/views/pms/spu'),
        meta: {title: '商品列表', icon: 'product-list'},
        children:[
          {
            path: 'edit/:id',
            name: 'spuEdit',
            component: () => import('@/views/pms/spu/edit'),
            meta: {title: '修改商品'},
            hidden: true
          },
        ]
      },
      {
        path: 'add',
        name: 'spuAdd',
        component: () => import('@/views/pms/spu/add'),
        meta: {title: '添加商品', icon: 'product-add'}
      },
      {
        path: 'category',
        name: '商品分类',
        component: () => import('@/views/pms/category'),
        meta: {title: '商品分类', icon: 'list'}
      },
      {
        path: 'brand',
        name: '品牌管理',
        component: () => import('@/views/pms/brand'),
        meta: {title: '品牌管理', icon: 'product-brand'}
      }
    ]
  },

  {
    path: '/oms',
    component: Layout,
    redirect: '/oms/order',
    meta: {title: '订单管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'order',
        name: 'order',
        component: () => import('@/views/oms/order'),
        meta: {title: '订单列表', icon: 'product-list'}
      },
      {
        path: 'orderDetail',
        name: 'orderDetail',
        component: () => import('@/views/oms/order/detail'),
        meta: {title: '订单详情', icon: 'product-list'},
        hidden:true
      },
      {
        path: 'return',
        name: 'return',
        component: () => import('@/views/oms/return'),
        meta: {title: '退货管理', icon: 'product-list'}
      },
      {
        path: 'setting',
        name: 'setting',
        component: () => import('@/views/oms/setting'),
        meta: {title: '订单设置', icon: 'product-list'}
      }
    ]
  },

  {
    path: '/sms',
    component: Layout,
    redirect: '/sms/ad',
    meta: {title: '营销管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'seckillPromotion',
        name: 'seckillPromotion',
        component: () => import('@/views/sms/seckill/promotion'),
        meta: {title: '秒杀活动', icon: 'setting'}
      },
      {
        path: 'seckillSession',
        name: 'seckillSession',
        component: () => import('@/views/sms/seckill/session'),
        meta: {title: '秒杀时间段'},
        hidden: true
      },
      {
        path: 'seckillSku',
        name: 'seckillSku',
        component: () => import('@/views/sms/seckill/sku'),
        meta: {title: '秒杀商品'},
        hidden: true
      },
      {
        path: 'ad',
        name: 'ad',
        component: () => import('@/views/sms/ad'),
        meta: {title: '广告列表', icon: 'setting'}
      }
    ]
  },


  {
    path: '/ums',
    component: Layout,
    redirect: '/ums/user',
    meta: {title: '用户管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/ums/user'),
        meta: {title: '会员管理', icon: 'setting'}
      },
      {
        path: 'address',
        name: 'address',
        component: () => import('@/views/ums/address'),
        meta: {title: '收货地址', icon: 'setting'},
      }
    ]
  },
  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}

]

const createRouter = () => new Router({
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
