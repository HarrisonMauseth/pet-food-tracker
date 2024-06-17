<template>
  <font-awesome-icon
    v-bind:style="{ opacity: spin ? 1 : 0 }"
    icon="fa-solid fa-rotate"
    class="load-spinner fa-spin"
  />
</template>

<script>
export default {
  props: {
    spin: {
      type: Boolean,
      required: true
    }
  }
}
</script>

<style scoped>
.load-spinner {
  transition-duration: 800ms;
  transition-property: opacity;
}
</style>
