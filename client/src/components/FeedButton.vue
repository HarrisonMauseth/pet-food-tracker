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
        .catch(() => {
          alert('Something went wrong. Please try again.')
        })
    }
  }
}
</script>

<style scoped>
#feed {
  background-color: var(--black);
  border-radius: 15px;
  border: 3px solid;
  color: var(--font-color);
  cursor: pointer;
  flex-basis: 40%;
  font-size: 20px;
  height: 60px;
}
#feed:hover {
  background-color: var(--hover);
  border: 3px solid var(--hover);
}
@media (max-width: 720px) {
  #feed {
    grid-area: button;
    margin: 0 auto;
    width: 90%;
  }
}
</style>
