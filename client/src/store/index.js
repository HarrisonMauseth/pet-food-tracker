import { createStore as _createStore } from "vuex";
import axios from "axios";

export function createStore(currentToken, currentUser) {
    let store = _createStore({
      state: {
        token: currentToken || "",
        user: currentUser || {},
        message: "something",
        isLoading: false,
      },
      mutations: {
        SET_AUTH_TOKEN(state, token) {
          state.token = token;
          localStorage.setItem("token", token);
          axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        },
        SET_USER(state, user) {
          state.user = user;
          localStorage.setItem("user", JSON.stringify(user));
        },
        LOGOUT(state) {
          localStorage.removeItem("token");
          localStorage.removeItem("user");
          state.token = "";
          state.user = {};
          axios.defaults.headers.common = {};
        },
        SET_MESSAGE(state, message) {
          state.message = message;
        },
        CLEAR_MESSAGE(state) {
          state.message = "";
        },
        IS_LOADING(state) {
          state.isLoading = true;
        },
        IS_LOADED(state) {
          state.isLoading = false;
        },
      },
    });
    return store;
  }