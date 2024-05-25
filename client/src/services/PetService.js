import axios from 'axios'

export default {
  createNewPet(pet) {
    return axios.post('/pets', pet)
  },
  getAllPets() {
    return axios.get('/pets')
  },
  getPetById(id) {
    return axios.get(`/pets/${id}`)
  },
  updatePet(id, pet) {
    return axios.put(`/pets/${id}`, pet)
  },
  deletePet(id) {
    return axios.delete(`/pets/${id}`)
  }
}
