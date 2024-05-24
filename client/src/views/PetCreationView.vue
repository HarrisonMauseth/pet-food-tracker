<template>
  <div id="pet-creation">
    <form>
      <h1>Please Provide Information</h1>
      <div class="fields">
        <label for="pet-name">Pet Name *</label>
        <input
          type="text"
          id="pet-name"
          placeholder="Name"
          v-model="pet.name"
          required
          autocomplete="off"
        />
        <label for="pet-type">Pet Type *</label>
        <select id="pet-type" name="type" v-model="type" required>
          <option disabled value="">---</option>
          <option value="bearded dragon">Bearded Dragon</option>
          <option value="cat">Cat</option>
          <option value="chameleon">Chameleon</option>
          <option value="chicken">Chicken</option>
          <option value="chinchilla">Chinchilla</option>
          <option value="dog">Dog</option>
          <option value="ferret">Ferret</option>
          <option value="fish">Fish</option>
          <option value="frog">Frog</option>
          <option value="gerbil">Gerbil</option>
          <option value="guinea pig">Guinea Pig</option>
          <option value="hamster">Hamster</option>
          <option value="hedgehog">Hedgehog</option>
          <option value="hermit crab">Hermit Crab</option>
          <option value="horse">Horse</option>
          <option value="iguana">Iguana</option>
          <option value="lizard">Lizard</option>
          <option value="mouse">Mouse</option>
          <option value="newt">Newt</option>
          <option value="parrot">Parrot</option>
          <option value="pig">Pig</option>
          <option value="rabbit">Rabbit</option>
          <option value="rat">Rat</option>
          <option value="scorpion">Scorpion</option>
          <option value="snake">Snake</option>
          <option value="spider">Spider</option>
          <option value="tarantula">Tarantula</option>
          <option value="tortoise">Tortoise</option>
          <option value="turtle">Turtle</option>
          <option value="other">Other</option>
        </select>
        <label for="pet-birthday">Pet Birthday</label>
        <input type="date" name="birthday" v-model="birthday" />
        <label for="pet-notes">Notes</label>
        <textarea id="pet-notes" name="notes" cols="60" rows="10" v-model.lazy="notes"></textarea>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pet: {
        name: '',
        shortened_name: null,
        type: '',
        birthday: null,
        image_path: null,
        notes: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
