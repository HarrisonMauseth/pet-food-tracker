<template>
  <div class="pet-details">
    <img :src="imagePath" @error="getDefaultImage" />
    <div class="info">
      <h2 class="name">{{ pet.name }}</h2>
      <font-awesome-icon
        icon="fa-solid fa-pencil"
        class="icon"
        title="Edit"
        @click="navigateToUpdatePet"
      />
      <p class="pet-birthday">{{ pet.birthday }}</p>

      <p class="pet-type">Pet type: {{ pet.type }}</p>
    </div>

    <div class="pet-notes">
      <h3>Notes</h3>
      <p class="pet-notes-content">{{ pet.notes }}</p>
    </div>
  </div>
</template>

<script>
import petService from '../services/PetService'
export default {
  data() {
    return {
      imagePath: '',
      pet: []
    }
  },
  methods: {
    getImage() {
      let path = '../public/img/' + this.pet.image_path
      this.imagePath = path
    },
    getDefaultImage() {
      this.imagePath = '../public/img/default.png'
    },
    navigateToUpdatePet() {
      this.$router.push({ name: 'updatePet', params: { id: this.pet.id } })
    },
    loadPet() {
      petService
        .getPetById(this.$route.params.id)
        .then((response) => {
          this.pet = response.data
          this.getImage()
        })
        .catch((error) => alert(error))
    }
  },
  mounted() {
    this.loadPet()
  }
}
</script>

<style scoped>
.pet-details {
  display: grid;
  grid-template-rows: 1fr 1fr 2fr;
  grid-template-columns:
    2fr 1fr,
    1fr;
  column-gap: 20px;
  row-gap: 20px;
  grid-template-areas:
    'photo header header'
    'photo . .'
    'notes notes notes';
  border: 3px solid;
  border-radius: 30px;
  padding: 30px;
  height: 75vh;
  background-color: var(--black-soft);
  max-width: 2560px;
  margin-left: auto;
  margin-right: auto;
}
img {
  width: 640px;
  height: 480px;
  grid-area: photo;
  margin: auto;
  border-radius: 30px;
  object-fit: cover;
}
.info {
  grid-area: header;
  display: grid;
  grid-template-rows: 1fr 1fr;
  grid-template-columns: 1fr 1fr;
  grid-template-areas:
    'name icon'
    'type birthday';
}
.name {
  color: var(--header);
  font-size: 40px;
  grid-area: name;
}
.icon {
  grid-area: icon;
}
.pet-type {
  grid-area: type;
}
.pet-birthday {
  grid-area: birthday;
}
h3 {
  color: var(--header);
}
.pet-notes {
  grid-area: notes;
}

</style>
