<template>
  <div id="pet-update">
    <pet-form
      :initialPet="pet"
      :onSubmit="updatePet"
      buttonText="Update Pet"
      :showDeleteButton="true"
      :onDelete="deletePet"
    />
  </div>
</template>

<script>
import PetForm from '@/components/PetForm.vue'
import petService from '@/services/PetService'

export default {
  components: {
    PetForm
  },
  data() {
    return {
      pet: []
    }
  },
  methods: {
    loadPet(id) {
      petService.getPetById(id).then((response) => {
        if (response.status === 200) {
          this.pet = response.data
        }
      })
    },
    updatePet(updatedPet) {
      this.$store.commit('IS_LOADING')
      petService
        .updatePet(this.$route.params.id, updatedPet)
        .then((response) => {
          this.$store.commit('IS_LOADED')
          if (response.status === 200) {
            alert('Update successful!')
            this.$router.push({ name: 'dashboard' })
          }
        })
        .catch((error) => {
          this.$store.commit('IS_LOADED')
          alert('Unexpected error. Please try again in a little bit.')
          console.log(error)
        })
    },
    deletePet() {
      const petId = this.$route.params.id
      this.$store.commit('IS_LOADING')
      petService
        .deletePet(petId)
        .then((response) => {
          this.$store.commit('IS_LOADED')
          if (response.status === 204) {
            alert('Pet deleted successfully!')
            this.$router.push({ name: 'dashboard' })
          }
        })
        .catch((error) => {
          this.$store.commit('IS_LOADED')
          alert('Unexpected error. Please try again in a little bit.')
          console.error(error)
        })
    }
  },
  mounted() {
    this.loadPet(this.$route.params.id)
  }
}
</script>
