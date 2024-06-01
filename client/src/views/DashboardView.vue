<template>
  <pet-card
    v-for="pet in filteredPets"
    :key="pet.pet_id"
    :id="pet.pet_id"
    :name="pet.name"
    :image="pet.image_path"
  />
  <add-pet />
</template>

<script>
import PetCard from '@/components/PetCard.vue'
import petService from '@/services/PetService'
import AddPet from '@/components/AddPet.vue'
export default {
  components: {
    PetCard,
    AddPet
  },
  methods: {
    loadPets() {
      this.$store.commit('IS_LOADING')
      if (this.petsAreLoaded) {
        this.$store.commit('CLEAR_PETS')
      }
      petService
        .getAllPets()
        .then((response) => {
          this.$store.commit('IS_LOADED')
          this.$store.commit('LOAD_PETS', response.data)
        })
        .catch((error) => {
          this.$store.commit('IS_LOADED')
          console.log(error)
        })
    },
    petsAreLoaded() {
      return this.$store.pets.length > 0;
    },
  },
  computed: {
    filteredPets() {
      return this.$store.state.pets
    }
  },
  created() {
    this.loadPets()
  }
}
</script>

<style scoped>

</style>
