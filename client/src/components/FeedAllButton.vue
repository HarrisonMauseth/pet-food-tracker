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
      this.$store.commit('IS_LOADING')
      if (this.logs.length > 0) {
        logService
          .logMultiple(this.logs)
          .then((response) => {
            this.$store.commit('IS_LOADED')
            if (response.status === 201) {
              this.$emit('logged-all-pets', response.data)
            }
          })
          .catch(() => {
            this.$store.commit('IS_LOADED')
          })
          .finally(() => {
            this.$store.commit('IS_LOADED')
          })
      } else {
        alert('No feeding event to log.')
        this.$store.commit('IS_LOADED')
      }
    }
  }
}
</script>

<style scoped>
.feed-all {
  align-items: center;
  background-color: var(--black);
  border-radius: 20px;
  border: 3px solid;
  cursor: pointer;
  display: flex;
  font-size: 30px;
  height: 60px;
  justify-content: center;
  margin: 20px 0;
  margin: auto;
  transition: 0.8s;
  width: 25%;
}
.feed-all:hover {
  background-color: var(--hover);
  border: 3px solid var(--hover);
}
@media (max-width: 1024px) {
  .feed-all {
    width: 100%;
  }
}
</style>
