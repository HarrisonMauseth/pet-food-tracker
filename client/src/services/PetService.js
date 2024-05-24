import axios from 'axios'

export default {
  createNewPet(pet) {
    return axios.post('/pets', pet)
  },
  getAllPets() {
    return axios.get('/pets')
  },
  updatePet(id) {
    return axios.put(`/pets/${id}`)
  },
  deletePet(id) {
    return axios.delete(`/pets/${id}`)
  }
}
