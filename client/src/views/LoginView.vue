<template>
  <div class="login-container">
    <div id="login">
      <form v-on:submit.prevent="login">
        <h1>Please Sign In</h1>
        <div id="fields">
          <label for="username">Username</label>
          <input
            type="text"
            id="username"
            placeholder="Username"
            v-model="user.username"
            required
            autofocus
            autocomplete="off"
          />
          <label for="password">Password</label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            v-model="user.password"
            required
            autocomplete="off"
          />
          <div class="submit"><button type="submit">Sign in</button></div>
        </div>
        <hr />
        <div id="register">
          Need an account?
          <router-link v-bind:to="{ name: 'register' }">Register!</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService'

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        remember_me: false
      }
    }
  },
  methods: {
    login() {
      this.$store.commit('IS_LOADING')
      authService
        .login(this.user)
        .then((response) => {
          this.$store.commit('IS_LOADED')
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token)
            this.$store.commit('SET_USER', response.data.user)
            this.$router.push('/')
          }
        })
        .catch((error) => {
          this.$store.commit('IS_LOADED')
          const response = error.response
          if (!response) {
            alert(error)
          } else if (response.status === 401) {
            alert('Invalid username and password!')
          } else {
            alert(response.message)
          }
        })
    }
  }
}
</script>

<style scoped>
*:not(h1) {
  background-color: var(--black-soft);
  color: var(--font-color);
  margin: 0;
  padding: 0;
}
.login-container {
  align-items: center;
  background-color: transparent;
  display: flex;
  justify-content: center;
  min-height: calc(100vh - 12rem);
}
#login {
  align-items: center;
  border-radius: 10px;
  border: 3px solid;
  box-shadow: var(--shadow);
  display: flex;
  flex-direction: column;
  margin-top: 50px;
  margin: auto;
  padding: 20px;
  width: 300px;
}
h1 {
  margin-bottom: 20px;
  text-align: center;
}
#fields {
  margin-bottom: 20px;
  width: 100%;
}
label {
  padding-left: 8px;
}
input[type='text'],
input[type='password'] {
  border-radius: 5px;
  border: 1px solid var(--black-mute);
  font-size: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 8px;
  width: 95%;
}
.submit {
  text-align: center;
  width: 100%;
}
button {
  background-color: var(--black);
  border-radius: 15px;
  border: none;
  cursor: pointer;
  font-size: 17px;
  padding: 10px 20px;
  width: 75%;
}
button:hover {
  background-color: var(--hover);
}
#register {
  text-align: center;
  width: 100%;
}
#register a {
  color: var(--link);
  text-decoration: none;
}
#register a:hover {
  color: var(--hover-link);
  text-decoration: underline;
}
@media (max-width: 720px) {
  .login-container {
    min-height: calc(100vh - 8rem);
  }
}
</style>
