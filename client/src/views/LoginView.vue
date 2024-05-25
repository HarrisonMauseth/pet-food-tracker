<template>
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
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit('SET_AUTH_TOKEN', response.data.token)
            this.$store.commit('SET_USER', response.data.user)
            this.$router.push('/')
          }
        })
        .catch((error) => {
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
* {
  background-color: #0f111a;
  color: #adb1ca;
}
#login {
  align-items: center;
  border-radius: 10px;
  border: 3px solid #222633;
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
  color: #e88c59;
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
  border: 1px solid #222633;
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
  background-color: #0a0c12;
  border-radius: 15px;
  border: none;
  cursor: pointer;
  padding: 10px 20px;
  width: 75%;
}
button:hover {
  background-color: #46096e;
}
#register {
  text-align: center;
  width: 100%;
}
#register a {
  color: #00a5b5;
  text-decoration: none;
}
#register a:hover {
  color: #c792ea;
  text-decoration: underline;
}
</style>
