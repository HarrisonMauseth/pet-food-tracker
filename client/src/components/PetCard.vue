<template>
  <div class="pet-card">
    <img :src="imagePath" @error="getDefaultImage" @click="navigateToPetDetails" />
    <section class="info">
      <h2 class="name">{{ name }}</h2>
      <p class="time">{{ timeSinceLastTimestamp }}</p>
    </section>
    <feed-button :logData="defaultLog" @logged-food="handleLoggedFood" />
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
import FeedButton from './FeedButton.vue'
export default {
  components: {
    FeedButton
  },
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
  props: ['id', 'name', 'image', 'allPetsFedResponse'],
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
        .catch(() => {
          // eat the error as there is logic in place for no logs
        })
    },
    handleLoggedFood(log) {
      this.logs.unshift(log)
    }
  },
  created() {
    this.getImage()
    this.getLogs()
    this.interval = setInterval(() => {
      this.$forceUpdate()
    }, 1000)
  },
  mounted() {
    this.$emit('card-created', this.id)
  },
  beforeUnmount() {
    clearInterval(this.interval)
  },
  watch: {
    allPetsFedResponse(logs) {
      if (logs) {
        const matchingLogs = logs.filter((log) => log.pet_id === this.id)
        if (matchingLogs.length > 0) {
          this.logs.unshift(matchingLogs[0])
        }
      }
    }
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
        const weeksDifference = Math.floor(daysDifference / 7)

        if (weeksDifference > 0) {
          if (weeksDifference > 1) {
            return `${weeksDifference} weeks ago`
          } else return `${weeksDifference} week ago`
        } else if (daysDifference > 0) {
          if (daysDifference === 1) {
            return `${daysDifference} day ago`
          } else return `${daysDifference} days ago`
        } else if (hoursDifference > 0) {
          if (hoursDifference === 1) {
            return `${hoursDifference} hour ago`
          } else return `${hoursDifference} hours ago`
        } else if (minutesDifference > 0) {
          if (minutesDifference === 1) {
            return `${minutesDifference} minute ago`
          } else return `${minutesDifference} minutes ago`
        } else {
          if (secondsDifference === 1) {
            return `${secondsDifference} second ago`
          } else return `${secondsDifference} seconds ago`
        }
      } else return 'No feedings logged'
    }
  }
}
</script>

<style scoped>
.pet-card {
  align-items: center;
  background-color: var(--color-background-soft);
  border-radius: 20px;
  border: 3px solid;
  display: flex;
  height: 100px;
  justify-content: space-between;
  margin: 20px 0;
  width: 100%;
}
.pet-card > img {
  border-radius: 50%;
  cursor: pointer;
  height: 75px;
  margin-left: 20px;
  object-fit: cover;
  width: 75px;
}
.info {
  flex-basis: 20%;
}
.name {
  color: var(--header);
  font-weight: 500;
}
.time {
  font-style: italic;
}
.icon {
  margin-right: 20px;
  transition: 0.8s;
}
.icon:hover {
  color: var(--link);
}
@media (max-width: 720px) {
  .pet-card {
    column-gap: 20px;
    display: grid;
    grid-template-areas:
      'photo info icon'
      'button button button';
    grid-template-columns: 1fr 2fr 1fr;
    grid-template-rows: 1fr 1fr;
    height: 200px;
    padding: 10px 0;
    row-gap: 20px;
  }
  .pet-card > img {
    grid-area: photo;
  }
  .info {
    grid-area: info;
  }
  .icon {
    grid-area: icon;
    justify-self: right;
  }
}
</style>
