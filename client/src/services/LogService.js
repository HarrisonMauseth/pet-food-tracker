import axios from 'axios'

export default {
  getLogsByPetId(id) {
    return axios.get(`/log/pet/${id}`)
  },
  log(log) {
    return axios.post(`/log`, log)
  },
  logMultiple(logs) {
    return axios.post(`/log/multiple`, logs)
  },
  updateLog(id, log) {
    return axios.put(`/log/${id}`, log)
  },
  deleteLog(id) {
    return axios.delete(`/log/${id}`)
  },
  getAllLogs() {
    return axios.get(`/log`)
  }
}
