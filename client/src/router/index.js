import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import DashboardView from '../views/DashboardView.vue'
import LoginView from '../views/LoginView.vue'
import LogoutView from '../views/LogoutView.vue'
import RegisterView from '../views/RegisterView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: { name: 'dashboard' }
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
      meta: {
        requiresAuth: false
      }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  },
]

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

router.beforeEach((to) => {
  // Get the Vuex store
  const store = useStore()

  // Clear error notifications from previous view
  store.commit('CLEAR_MESSAGE')

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some((x) => x.meta.requiresAuth)

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return { name: 'login' }
  }
  // Otherwise, do nothing and they'll go to their next destination
})

export default router
