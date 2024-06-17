<template>
  <div class="register-container">
    <div id="register">
      <form v-on:submit.prevent="register">
        <h1>Create Account</h1>
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
          <label for="confirmPassword">Confirm Password</label>
          <input
            type="password"
            id="confirmPassword"
            placeholder="Confirm Password"
            v-model="user.confirmPassword"
            required
            autocomplete="off"
          />
          <div></div>
          <div id="create">
            <button type="submit">Create Account</button>
          </div>
        </div>
        <hr />
        <div id="sign-in">
          Have an account?
          <router-link v-bind:to="{ name: 'login' }">Sign in!</router-link>
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
        confirmPassword: '',
        role: 'user'
      }
    }
  },
  methods: {
    error(msg) {
      alert(msg)
    },
    success(msg) {
      alert(msg)
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.error('Password & Confirm Password do not match')
      } else {
        this.$store.commit('IS_LOADING')
        authService
          .register(this.user)
          .then((response) => {
            this.$store.commit('IS_LOADED')
            if (response.status == 201) {
              this.success('Thank you for registering, please sign in.')
              this.$router.push({
                path: '/login'
              })
            }
          })
          .catch((error) => {
            this.$store.commit('IS_LOADED')
            const response = error.response
            if (!response) {
              this.error(error)
            } else if (response.status === 400) {
              if (response.data.errors) {
                // Show the validation errors
                let msg = 'Validation error: '
                for (let err of response.data.errors) {
                  msg += `'${err.field}':${err.defaultMessage}. `
                }
                this.error(msg)
              } else {
                this.error(response.data.message)
              }
            } else {
              this.error(response.data.message)
            }
          })
      }
    }
  }
}
</script>

<style scoped>
*:not(h1) {
  background-color: var(--black-soft);
  color: var(--font-color);
}
.register-container {
  align-items: center;
  background-color: transparent;
  display: flex;
  justify-content: center;
  min-height: calc(100vh - 12rem);
}
#register {
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
input {
  border-radius: 5px;
  border: 1px solid var(--black-mute);
  font-size: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 8px;
  width: 95%;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
#create {
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
#sign-in {
  text-align: center;
  width: 100%;
}
#sign-in a {
  color: var(--link);
  text-decoration: none;
}
#sign-in a:hover {
  color: var(--hover-link);
  text-decoration: underline;
}
@media (max-width: 720px) {
  .register-container {
    min-height: calc(100vh - 8rem);
  }
}
</style>
