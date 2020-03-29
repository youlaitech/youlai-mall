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
      component: () => import('@/views/dashboard/index'),
      meta: {title: 'Dashboard', icon: 'dashboard'}
    }]
  },


  {
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    name: '系统管理',
    meta: {title: '系统管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: () => import('@/views/system/user'),
        meta: {title: '用户管理', icon: 'peoples'}
      },
    ]
  },
  {
    path: '/shop',
    component: Layout,
    redirect: '/shop/brand',
    name: '商品管理',
    meta: {title: '商品管理', icon: 'product'},
    alwaysShow: true,
    children: [
      {
        path: 'list',
        name: '商品列表',
        component: () => import('@/views/pms/list'),
        meta: {title: '商品列表', icon: 'product-list'}
      },
      {
        path: 'add',
        name: '添加商品',
        component: () => import('@/views/pms/add'),
        meta: {title: '添加商品', icon: 'product-add'}
      },
      {
        path: 'update',
        name: '修改商品',
        component: () => import('@/views/pms/update'),
        meta: {title: '修改商品'},
        hidden: true
      },
      {
        path: 'attribute',
        name: '商品类型',
        component: () => import('@/views/pms/attribute/type'),
        meta: {title: '商品类型', icon: 'data'}
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
    path: '/order',
    component: Layout,
    redirect: '/order/setting',
    name: '订单管理',
    meta: {title: '订单管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'orderList',
        name: 'orderList',
        component: () => import('@/views/order/list'),
        meta: {title: '订单列表', icon: 'product-list'}
      },
      {
        path: 'orderDetail',
        name: 'orderDetail',
        component: () => import('@/views/order/list/orderDetail'),
        meta: {title: '订单详情'},
        hidden:true
      },
      {
        path: 'deliverOrderList',
        name: 'deliverOrderList',
        component: () => import('@/views/order/list/deliverOrderList'),
        meta: {title: '发货列表'},
        hidden:true
      },
      {
        path: 'setting',
        name: 'setting',
        component: () => import('@/views/order/setting'),
        meta: {title: '订单设置', icon: 'setting'}
      },
      {
        path: 'reason',
        name: 'reason',
        component: () => import('@/views/order/apply/reason'),
        meta: {title: '退货原因设置', icon: 'setting'}
      },
      {
        path: 'returnApply',
        name: 'returnApply',
        component: () => import('@/views/order/apply'),
        meta: {title: '退货申请处理', icon: 'setting'}
      },
      {
        path: 'returnApplyDetail',
        name: 'returnApplyDetail',
        component: () => import('@/views/order/apply/detail'),
        meta: {title: '退货详情'},
        hidden: true
      }
    ]
  },


  {
    path: '/marketing',
    component: Layout,
    redirect: '/marketing/seckill',
    name: '营销管理',
    meta: {title: '营销管理', icon: 'setting'},
    alwaysShow: true,
    children: [
      {
        path: 'seckill',
        name: 'seckill',
        component: () => import('@/views/marketing/seckill'),
        meta: {title: '秒杀活动列表'}
      },
      {
        path: 'session',
        name: 'seckillSession',
        component: () => import('@/views/marketing/seckill/session'),
        meta: {title: '秒杀时间段列表'},
        hidden:true
      },
      {
        path: 'goods',
        name: 'seckillGoods',
        component: () => import('@/views/marketing/seckill/goods'),
        meta: {title: '秒杀商品列表'},
        hidden:true
      },
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
