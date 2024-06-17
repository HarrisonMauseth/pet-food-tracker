<template>
  <feed-all :logs="logAllPets" @logged-all-pets="handleAllPetsLoggedResponse" />
  <pet-card
    v-for="pet in filteredPets"
    :key="pet.pet_id"
    :id="pet.pet_id"
    :name="pet.name"
    :image="pet.image_path"
    :allPetsFedResponse="petLogResponse"
    @card-created="handleCreatedCard"
  />
  <add-pet />
</template>

<script>
import PetCard from '@/components/PetCard.vue'
import petService from '@/services/PetService'
import AddPet from '@/components/AddPetButton.vue'
import FeedAll from '@/components/FeedAllButton.vue'
export default {
  components: {
    PetCard,
    AddPet,
    FeedAll
  },
  data() {
    return {
      petIds: [],
      petLogs: [],
      petLogResponse: null
    }
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
          if (error.response) {
            this.$router.push({ name: 'logout' })
          }
        })
    },
    petsAreLoaded() {
      return this.$store.pets.length > 0
    },
    handleCreatedCard(petId) {
      this.petIds.push(petId)
    },
    handleAllPetsLoggedResponse(logs) {
      this.petLogResponse = logs
    }
  },
  computed: {
    filteredPets() {
      return this.$store.state.pets
    },
    logAllPets() {
      let allLogs = []
      this.petIds.forEach((id) => {
        const logToAdd = {
          log_id: 0,
          user_id: 0,
          pet_id: id,
          food_type: 'Default food',
          portion_size: 1.0,
          portion_units: 'cup',
          notes: 'Default notes'
        }
        allLogs.push(logToAdd)
      })
      return allLogs
    }
  },
  created() {
    this.loadPets()
  }
}
</script>

<style scoped></style>
