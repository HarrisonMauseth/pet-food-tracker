<template>
  <div class="pet-card">
    <img :src="imagePath" @error="getDefaultImage" @click="navigateToPetDetails" />
    <section class="info">
      <h2 class="name">{{ name }}</h2>
      <p class="time">{{ timeSinceLastTimestamp }}</p>
    </section>
    <button type="button" id="feed" title="Feed" @click="logFeeding">Feed Now</button>
    <font-awesome-icon
      icon="fa-solid fa-pencil"
      class="icon"
      title="Edit"
      @click="navigateToUpdatePet"
    />
  </div>
</template>

<script>
import logService from '../services/LogService.js'
export default {
  data() {
    return {
      imagePath: '',
      logs: [],
      defaultLog: {
        log_id: 0,
        user_id: 0,
        pet_id: this.id,
        food_type: 'Default food',
        portion_size: 1.0,
        portion_units: 'cup',
        notes: 'Default notes'
      },
      interval: null
    }
  },
  props: ['id', 'name', 'image'],
  methods: {
    getImage() {
      let path = '../public/img/' + this.image
      this.imagePath = path
    },
    getDefaultImage() {
      this.imagePath = '../public/img/default.png'
    },
    navigateToUpdatePet() {
      this.$router.push({ name: 'updatePet', params: { id: this.id } })
    },
    navigateToPetDetails() {
      this.$router.push({ name: 'petDetails', params: { id: this.id } })
    },
    getLogs() {
      logService
        .getLogsByPetId(this.id, this.defaultLog)
        .then((response) => {
          if (response.status === 200) {
            this.logs = response.data
          }
        })
        .catch((error) => {
          console.error(error)
        })
    },
    logFeeding() {
      logService
        .log(this.defaultLog)
        .then((response) => {
          if (response.status === 201) {
            this.$router.push({ name: 'home' })
          }
        })
        .catch((error) => {
          console.error(error)
          alert('Something went wrong. Please try again. ' + error)
        })
    }
  },
  created() {
    this.getImage()
    this.getLogs()
    this.interval = setInterval(() => {
      this.$forceUpdate()
    }, 1000)
  },
  beforeUnmount() {
    clearInterval(this.interval)
  },
  computed: {
    timeSinceLastTimestamp() {
      if (this.logs.length > 0) {
        const timestampDate = new Date(this.logs[0].time_fed)
        const currentTime = new Date()

        const timeDifference = currentTime - timestampDate
        const secondsDifference = Math.floor(timeDifference / 1000)
        const minutesDifference = Math.floor(secondsDifference / 60)
        const hoursDifference = Math.floor(minutesDifference / 60)
        const daysDifference = Math.floor(hoursDifference / 24)

        if (daysDifference > 0) {
          return `${daysDifference} days ago`
        } else if (hoursDifference > 0) {
          return `${hoursDifference} hours ago`
        } else if (minutesDifference > 0) {
          return `${minutesDifference} minutes ago`
        } else {
          return `${secondsDifference} seconds ago`
        }
      } else return 'No feedings logged'
    }
  }
}
</script>

<style scoped>
.pet-card {
  background-color: var(--color-background-soft);
  border: 3px solid;
  border-radius: 20px;
  width: 100%;
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 20px 0;
}
.pet-card > img {
  width: 75px;
  height: 75px;
  object-fit: cover;
  border-radius: 50%;
  margin-left: 20px;
  cursor: pointer;
}
.info {
  flex-basis: 20%;
}
.time {
  font-style: italic;
}
#feed {
  flex-basis: 40%;
  height: 60px;
  background-color: var(--black);
  color: #adb1ca;
  border-radius: 15px;
  border: none;
  cursor: pointer;
  font-size: 20px;
}
#feed:hover {
  background-color: #46096e;
}
.icon {
  margin-right: 20px;
  transition: 0.8s;
}
.icon:hover {
  color: var(--link);
}
</style>
