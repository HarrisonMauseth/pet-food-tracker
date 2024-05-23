<template>
  <header>
    <nav v-if="$store.state.token">
      <span id="home" @click="navigateToDashboard">
        <font-awesome-icon :icon="['fas', 'fa-house-chimney']" title="Home" class="icon" />
        Home
      </span>
      <span id="pets">
        <font-awesome-icon icon="fa-solid fa-paw" title="Pets" class="icon" />
        Pets
      </span>
      <span id="logout" @click="logout">
        <font-awesome-icon icon="fa-solid fa-door-open" title="Logout" class="icon"  />
        Logout
      </span>
    </nav>
  </header>
  <RouterView />
</template>

<script>
export default {
  methods: {
    logout() {
      this.$router.push({ name: "logout" })
    },
    navigateToDashboard() {
      this.$router.push({ name: "dashboard"})
    }
  }
}
</script>

<style scoped>
.icon {
  font-size: 30px;
}
nav {
  display: flex;
  justify-content: space-evenly;
  width: 100vw;
  position: fixed;
  top: 0;
  left: 0;
}
span {
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: 0.2s;
}

span:hover {
  color: forestgreen;
  text-decoration: underline;
}

@media (max-width: 1024px) {
  nav {
    position: fixed;
    bottom: 0;
  }
}
</style>
