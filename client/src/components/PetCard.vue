<template>
  <div class="pet-card">
    <img src="../../public/img/bucky.jpg" />
    <section class="info">
      <h2 class="name">Bucky</h2>
      <p>Last fed: 2 hours ago</p>
    </section>

    <button id="feed">Feed</button>
    <font-awesome-icon icon="fa-solid fa-pencil" class="icon" />
  </div>
</template>

<script>
export default {
  props: ['id', 'name']
}
</script>

<style scoped>
.pet-card {
  border: 1px solid yellow;
  border-radius: 20px;
  width: 90vw;
  /* max-width: 1900px; */
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pet-card > img {
  width: 75px;
  height: 75px;
  object-fit: cover;
  border-radius: 50%;
  margin-left: 20px;
}
.info {
  flex-basis: 20%;
}
#feed {
  flex-basis: 40%;
  height: 60px;
  background-color: #0f111a;
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
}
</style>
