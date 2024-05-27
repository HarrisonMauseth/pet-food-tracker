<template>
  <div class="pet-details">
    <img :src="imagePath" @error="getDefaultImage" />
    <h2 class="name">{{ name }}</h2>
    <font-awesome-icon
  icon="fa-solid fa-pencil"
  class="icon"
  title="Edit"
  @click="navigateToUpdatePet"
/>
<p class="pet-type">Pet type: {{ pet.type }}</p>


  </div>
</template>

<script>
export default {
    data() {
        return {
            pet: {...this.initialPet}
        }
    },
    props: {
        initialPet: {
            type: Object,
            required: true
        }
    }
}
</script>

<style scoped></style>
