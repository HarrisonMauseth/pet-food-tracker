<template>
  <div class="feed-all" @click="feedAllPets">
    <p>Feed All</p>
  </div>
</template>

<script>
import petService from '../services/PetService.js'
export default {
    methods: {
        // feedAllPets() {
        //     this.$store.state.pets.forEach(pet => {
        //         petService.
        //     });
        // }
    },
    data() {
        return {
        defaultLog: {
        log_id: 0,
        user_id: 0,
        pet_id: this.id,
        food_type: 'Default food',
        portion_size: 1.0,
        portion_units: 'cup',
        notes: 'Default notes'
            },
        }
    }
}
</script>
