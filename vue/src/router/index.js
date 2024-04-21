import Vue from 'vue'
import VueRouter from 'vue-router'
import home from "@/views/manager/Home";
Vue.use(VueRouter)

// Fix the issue of frequent clicking on navigation bar or bottom navigation tabBar in vue-router version 3.0 and above.
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/managerHome',
    name:'Manager',
    component: () => import('../views/Manager.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: 'SystemHomepage' }, component:() => import('../views/manager/Home') },
    ]

  },
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    redirect: '/home',  // Redirect to the homepage
    children: [
      { path: '403', name: 'NoAuth', meta: { name: 'NoPermission' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: 'SystemHomepage' }, component:() => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: 'AdminData' }, component: () => import('../views/manager/Admin') },
      { path: 'user', name: 'User', meta: { name: 'UserData' }, component: () => import('../views/manager/User') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: 'PersonalData' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: 'ChangePassword' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: 'NoticeData' }, component: () => import('../views/manager/Notice') },
      { path: 'pet', name: 'Pet', meta: { name: 'PetData' }, component: () => import('../views/manager/Pet') },
      { path: 'adopt', name: 'Adopt', meta: { name: 'Adopt Data' }, component: () => import('../views/manager/Adopt') },
      { path: 'foster', name: 'Foster', meta: { name: 'Foster Data' }, component: () => import('../views/manager/Foster') },
      { path: 'goods', name: 'Goods', meta: { name: 'Goods Data' }, component: () => import('../views/manager/Goods') },
      { path: 'orders', name: 'Orders', meta: { name: 'Orders Data' }, component: () => import('../views/manager/Orders') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'uhome', name: 'Uhome', meta: { name: 'User Homepage' }, component: () => import('../views/front/Uhome') },
      { path: 'person', name: 'Person', meta: { name: 'Personal Data' }, component: () => import('../views/front/Person') },
      { path: 'ufoster', name: 'UFoster', meta: { name: 'User foster Data' }, component: () => import('../views/front/Foster') },
      { path: 'uadopt', name: 'UAdopt', meta: { name: 'User adopt Data' }, component: () => import('../views/front/Adopt') },
      { path: 'ugoods', name: 'UGoods', meta: { name: 'Goods Data' }, component: () => import('../views/front/Goods') },
      { path: 'uorder', name: 'UOrders', meta: { name: 'Orders Data' }, component: () => import('../views/front/UOrders') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: 'Login' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: 'Register' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: 'NotAccess' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// // Routing Guard
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//
//   if (to.path === '/home') {
//     next('/front/home')
//
//     // if (user.role) {
//     //   if (user.role === 'USER') {
//     //     console.log(to)
//     //
//     //     next('/front/home')
//     //   } else {
//     //     next('/home')
//     //   }
//     // } else {
//     //   next('/login')
//     // }
//   } else {
//     next()
//   }
// })

export default router
