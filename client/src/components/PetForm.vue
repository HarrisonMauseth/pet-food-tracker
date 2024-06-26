<template>
  <div class="pet-form-container">
    <div id="pet-form">
      <form @submit.prevent="handleSubmit">
        <h1>Pet Information</h1>
        <div class="fields">
          <label for="pet-name">Pet Name <span class="required">*</span></label>
          <input
            type="text"
            id="pet-name"
            placeholder="Name"
            v-model="pet.name"
            required
            autocomplete="off"
          />
          <label for="pet-type">Pet Type <span class="required">*</span></label>
          <select id="pet-type" name="type" v-model="pet.type" required>
            <option disabled value="">--- Please Select ---</option>
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
            <option value="other">Other...</option>
          </select>
          <label for="pet-birthday">Pet Birthday</label>
          <input type="date" name="birthday" v-model="pet.birthday" :max="today" />
          <label for="pet-notes">Notes</label>
          <textarea
            id="pet-notes"
            name="notes"
            cols="60"
            rows="10"
            v-model.lazy="pet.notes"
          ></textarea>
          <div class="submit">
            <button type="button" class="delete" v-if="showDeleteButton" @click="handleDelete">
              Delete Pet
            </button>
            <button type="submit">{{ buttonText }}</button>
          </div>
        </div>
        <hr />
        <div class="info">
          <span class="required">* denotes required field</span>
          <span class="reset" @click="resetForm">Reset</span>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    initialPet: {
      type: Object,
      required: true
    },
    buttonText: {
      type: String,
      default: 'Submit'
    },
    onSubmit: {
      type: Function,
      required: true
    },
    showDeleteButton: {
      type: Boolean,
      default: false
    },
    onDelete: {
      type: Function,
      required: false
    }
  },
  data() {
    return {
      pet: { ...this.initialPet }
    }
  },
  watch: {
    initialPet: {
      immediate: true,
      handler(newVal) {
        this.pet = { ...newVal }
      }
    }
  },
  methods: {
    resetForm() {
      this.pet = { ...this.initialPet }
    },
    handleSubmit() {
      this.onSubmit(this.pet)
    },
    handleDelete() {
      this.onDelete(this.pet)
    }
  },
  computed: {
    today() {
      const today = new Date(Date.now())
      const year = today.getFullYear()
      const month = String(today.getMonth() + 1).padStart(2, '0')
      const day = String(today.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    }
  }
}
</script>

<style scoped>
*:not(h1) {
  background-color: var(--black-soft);
  color: var(--font-color);
}
.pet-form-container {
  align-items: center;
  background-color: transparent;
  display: flex;
  justify-content: center;
  margin: 0;
  min-height: calc(100vh - 7.875rem);
  padding: 0;
}
#pet-form {
  align-items: center;
  border-radius: 10px;
  border: 3px solid;
  box-shadow: var(--shadow);
  display: flex;
  flex-direction: column;
  max-width: 500px;
  padding: 20px;
}
h1 {
  margin-bottom: 20px;
  text-align: center;
}
.fields {
  margin-bottom: 20px;
  width: 100%;
}
label {
  padding-left: 8px;
}
#pet-name,
#pet-type,
input[type='date'] {
  border-radius: 5px;
  border: 1px solid var(--black-mute);
  font-family: var(--default-fonts);
  font-size: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 8px;
  width: 100%;
}
input[type='date']::-webkit-calendar-picker-indicator {
  filter: invert(1);
}
textarea {
  border-radius: 5px;
  border: 1px solid var(--black-mute);
  font-family: var(--default-fonts);
  font-size: 15px;
  margin-bottom: 10px;
  margin-top: 5px;
  padding: 8px;
  resize: none;
  width: 100%;
}
.submit {
  display: flex;
  justify-content: space-around;
  text-align: center;
  width: 100%;
}
button {
  background-color: var(--black);
  border-radius: 15px;
  border: none;
  cursor: pointer;
  font-size: 17px;
  padding: 10px 20px;
  width: 40%;
}
.delete {
  background-color: darkred;
  font-weight: bold;
}
button:hover {
  background-color: var(--hover);
}
.required {
  color: darkred;
  font-size: 18px;
  font-style: italic;
}
.info {
  align-items: baseline;
  display: flex;
  justify-content: space-between;
  padding-top: 1rem;
}
.reset {
  color: var(--link);
  cursor: pointer;
  padding-right: 2rem;
  transition: 0.4s;
}
.reset:hover {
  color: var(--hover-link);
  text-decoration: underline;
}
@media (max-width: 720px) {
  .pet-form-container {
    min-height: calc(100vh - 12.25rem);
  }
}
</style>
