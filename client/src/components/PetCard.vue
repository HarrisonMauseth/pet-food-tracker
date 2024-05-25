<template>
  <div class="pet-card">
    <img :src="imagePath" @error="getDefaultImage" />
    <section class="info">
      <h2 class="name">{{ name }}</h2>
      <p>Last fed: 2 hours ago</p>
    </section>
    <button id="feed" title="Feed">Feed Now</button>
    <font-awesome-icon icon="fa-solid fa-pencil" class="icon" title="Edit" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      imagePath: ''
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
    }
  },
  created() {
    this.getImage()
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
.info p {
  font-style: italic;
}
#feed {
  flex-basis: 40%;
  height: 60px;
  background-color: var(--color-background);
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
