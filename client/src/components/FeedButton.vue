<template>
  <button type="button" id="feed" title="Feed" @click="logFeeding">Feed Now</button>
</template>

<script>
import logService from '../services/LogService.js'

export default {
  props: ['logData'],
  methods: {
    logFeeding() {
      logService
        .log(this.logData)
        .then((response) => {
          if (response.status === 201) {
            this.$emit('logged-food', response.data)
          }
        })
        .catch((error) => {
          console.error(error)
          alert('Something went wrong. Please try again. ' + error)
        })
    }
  }
}
</script>

<style scoped>
#feed {
  flex-basis: 40%;
  height: 60px;
  background-color: var(--black);
  color: #adb1ca;
  border-radius: 15px;
  border: none;
  cursor: pointer;
  font-size: 20px;
}
#feed:hover {
  background-color: #46096e;
}
</style>
