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
.add-pet:hover {
  background-color: var(--hover);
  border: 3px solid var(--hover);
}
@media (max-width: 1024px) {
  .add-pet {
    width: 100%;
  }
}
</style>
