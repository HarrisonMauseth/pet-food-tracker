<template>
  <div class="feed-all" @click="feedAllPets">
    <p>Feed All</p>
  </div>
</template>

<script>
import logService from '../services/LogService.js'
export default {
  props: ['logs'],
  methods: {
    feedAllPets() {
      if (this.logs.length > 0) {
        logService
          .logMultiple(this.logs)
          .then((response) => {
            if (response.status === 201) {
              this.$emit('logged-all-pets', response.data)
            }
          })
          .catch((error) => {
            console.error(error)
            alert('Something went wrong. Please try again. ' + error)
          })
      } else {
        alert('No feeding event to log.')
      }
    }
  }
}
</script>

<style scoped>
.feed-all {
  border: 3px solid;
  border-radius: 20px;
  background-color: var(--color-background-soft);
  height: 60px;
  margin: 20px 0;
  width: 25%;
  margin: auto;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
  transition: 0.8s;
}
.feed-all:hover {
  border: 3px solid var(--hover);
  background-color: var(--hover);
}
</style>
