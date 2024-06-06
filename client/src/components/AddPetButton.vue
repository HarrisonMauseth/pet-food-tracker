<template>
  <div class="add-pet" @click="navigateToPetCreation">
    <p>Add New Pet</p>
  </div>
</template>

<script>
export default {
  methods: {
    navigateToPetCreation() {
      this.$router.push({ name: 'createPet' })
    }
  }
}
</script>

<style scoped>
.add-pet {
  border: 3px solid;
  border-radius: 20px;
  background-color: var(--black);
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
.add-pet:hover {
  border: 3px solid var(--hover);
  background-color: var(--hover);
}
@media (max-width: 1024px) {
  .add-pet {
    width: 100%;
  }
}
</style>
