import { createRouter, createWebHashHistory } from 'vue-router';
import ClothHome from './components/ClothesHome.vue'
import ClothesDetails from "./components/ClothesDetails.vue";
import Register from "./components/Register.vue"
import Login from "./components/Login.vue";
import Cart from "./components/Cart.vue";
import Personal from "./components/Personal.vue";
import Order from "./components/Order.vue";
//创建路由实例
const router = createRouter({
    history: createWebHashHistory(),
    routes:[
        {
            path: '/',
            name: '首页',
            meta:{
                title: '首页'
            },
            component: ClothHome
        },
        {
            path: '/clothDetails',
            name: '服装详情',
            meta:{
                title: '服装详情'
            },
            component: ClothesDetails
        },
        {
            path: '/register',
            name: '注册',

            meta:{
                title: '注册'
            },
            component: Register
        },
        {
            path: '/login',
            name: '登录',
            meta:{
                title: '登录'
            },
            component: Login
        },
        {
            path: '/cart',
            name: '购物车',
            //添加路由守卫
            meta:{
                title: '购物车',
                requiresAuth: true
            },
            component: Cart
        },
        {
            path: '/order',
            name: '订单',
            //添加路由守卫
            meta:{
                title: '订单',
                requiresAuth: true
            },
            component: Order
        },
        {
            path: '/personal',
            name: '个人中心',
            meta:{
                title: '个人中心',
                requiresAuth: true
            },
            component: Personal
        }
    ]
})
router.beforeEach((to, from, next) => {
    const auth = require('./auth').default // 引入认证守卫
    // 判断当前路由是否需要认证
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 如果需要认证，但用户未登录，则重定向到登录页面（或者弹出提示）
        if (!auth.isAuthenticated()) {
            alert("请先登录！")
        } else {
            next(); // 已登录，正常进入路由
        }
    } else {
        // 如果不需要认证，则直接进入路由
        next();
    }
})
export default router