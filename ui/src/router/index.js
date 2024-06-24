import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import store from '@/store'

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/company-dashboard/:id/:username',
    name: 'company-dashboard',
    component: () => import('../views/company-user/CompanyDashboardView.vue')
  },
  {
    path: '/customer-dashboard/:id/:username',
    name: 'customer-dashboard',
    component: () => import('../views/customer/CustomerDashboardView.vue')
  },
    {
    path: '/admin-dashboard/:id/:username',
    name: 'admin-dashboard',
    component: () => import('../views/administrator/AdminDashboardView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from) => {
  if (to.name != 'login') {
    return store.getters.getAuthenticationStatus;
  } else {
    return true;
  }
})

export default router
