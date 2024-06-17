<template>
  <header>
    <nav v-if="$store.state.token">
      <span id="home" @click="navigateToDashboard" title="Home">
        <font-awesome-icon :icon="['fas', 'fa-house-chimney']" class="icon" />
        Home
      </span>
      <loading-spinner id="spinner" :spin="isLoading" />
      <!-- <span id="pets" title="Pets">
        <font-awesome-icon icon="fa-solid fa-paw" class="icon" />
        Pets
      </span> -->
      <span id="logout" @click="logout" title="Logout">
        <font-awesome-icon icon="fa-solid fa-door-open" class="icon" />
        Logout
      </span>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
</template>

<script>
import LoadingSpinner from './components/LoadingSpinner.vue';

export default {
  components: {
    LoadingSpinner
  },
  computed: {
    isLoading() {
      return this.$store.state.isLoading;
    }
  },
  methods: {
    logout() {
      this.$router.push({ name: 'logout' })
    },
    navigateToDashboard() {
      this.$router.push({ name: 'dashboard' })
    }
  }
}
</script>

<style scoped>
.icon {
  font-size: 30px;
}
nav {
  align-items: center;
  background-color: var(--black);
  display: flex;
  justify-content: space-evenly;
  left: 0;
  padding-bottom: 0.5rem;
  padding-top: 0.5rem;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 99;
}
span {
  align-items: center;
  color: var(--link);
  cursor: pointer;
  display: flex;
  flex-direction: column;
  transition: 0.4s;
}
span:hover {
  color: var(--hover-link);
  text-decoration: underline;
}
#spinner {
  font-size: 30px;
}
main {
  position: relative;
  top: 4rem;
}

@media (max-width: 720px) {
  nav {
    bottom: 0;
    top: auto;
  }
  main {
    padding-bottom: 6rem;
    top: 2rem;
  }
}
</style>
