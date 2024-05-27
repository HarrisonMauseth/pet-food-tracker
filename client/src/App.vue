<template>
  <header>
    <nav v-if="$store.state.token">
      <span id="home" @click="navigateToDashboard" title="Home">
        <font-awesome-icon :icon="['fas', 'fa-house-chimney']" class="icon" />
        Home
      </span>
      <span id="pets" title="Pets">
        <font-awesome-icon icon="fa-solid fa-paw" class="icon" />
        Pets
      </span>
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
export default {
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
  background-color: var(--black);
  display: flex;
  justify-content: space-evenly;
  width: 100%;
  position: fixed;
  top: 0;
  padding-top: .5rem;
  padding-bottom: .5rem;
  left: 0;
  z-index: 99;
}
span {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: 0.4s;
  color: var(--link);
}
span:hover {
  color: var(--hover-link);
  text-decoration: underline;
}
main {
  position: relative;
  top: 4rem;
}

@media only screen and (max-width: 1024px) {
  nav {
    top: auto;
    bottom: 0;
  }
  main {
    top: 1rem;
    bottom: 2rem;
  }
}
</style>
