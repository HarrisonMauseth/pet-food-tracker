<template>
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
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.success('Thank you for registering, please sign in.')
              this.$router.push({
                path: '/login'
              })
            }
          })
          .catch((error) => {
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
* {
  background-color: #0f111a;
  color: #adb1ca;
}
#register {
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
input {
  border-radius: 5px;
  border: 1px solid #222633;
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
#sign-in {
  text-align: center;
  width: 100%;
}
#sign-in a {
  color: #00a5b5;
  text-decoration: none;
}
#sign-in a:hover {
  color: #c792ea;
  text-decoration: underline;
}
</style>
