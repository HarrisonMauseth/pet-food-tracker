<template>
  <div id="pet-creation">
    <PetForm :initialPet="initialPet" :onSubmit="addPet" buttonText="Add Pet" />
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
      initialPet: {
        name: '',
        shortened_name: null,
        type: '',
        birthday: null,
        image_path: null,
        notes: ''
      }
    }
  },
  methods: {
    addPet(pet) {
      console.log('addPet called with:', pet);
      this.$store.commit('IS_LOADING')
      petService
        .createNewPet(pet)
        .then((response) => {
          this.$store.commit('IS_LOADED')
          if (response.status === 201) {
            alert("Pet added successfully!")
            this.$router.push({ name: 'dashboard' })
          }
        })
        .catch((error) => {
          console.error('Error adding pet:', error);
        })
    }
  }
}
</script>