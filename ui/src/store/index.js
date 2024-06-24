import { createStore } from 'vuex'

export default createStore({
  state: {
    userInfo: {
      userType: '',
      username: '',
      userId: null,
    },
    authenticationStatus: false,
    allCompanyListings: [],
    authenticationToken: null,
    balance: 0,
    companyId: null,
  },
  getters: {
    getUserInfo: (state) => state.userInfo,
    getAuthenticationStatus: (state) => state.authenticationStatus,
    getAllCompanyListings: (state) => state.allCompanyListings,
    getAuthenticationToken: (state) => state.authenticationToken,
    getBalance: (state) => state.balance,
    getCompanyId: (state) =>  state.companyId,
  },
  mutations: {
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo;
    },
    SET_AUTHENTICATION_STATUS(state, status) {
      state.authenticationStatus = status;
    },
    SET_ALL_COMPANY_LISTINGS(state, listings) {
      state.allCompanyListings = listings;
    },
    SET_AUTHENTICATION_TOKEN(state, token) {
      state.authenticationToken = token;
    },
    SET_BALANCE(state, balance) {
      state.balance = balance;
    },
    SET_COMPANYID(state, id) {
      state.companyId = id;
    }
  },
  actions: {
    updateUserInfo({ commit }, userInfo) {
      commit('SET_USER_INFO', userInfo);
    },
    updateAuthenticationStatus({ commit }, status) {
      commit('SET_AUTHENTICATION_STATUS', status);
    },
    updateAllCompanyListings({ commit }, listings) {
      commit('SET_ALL_COMPANY_LISTINGS', listings);
    },
    updateAuthenticationToken({ commit }, token) {
      commit('SET_AUTHENTICATION_TOKEN', token)
    },
    updateBalance({ commit }, balance) {
      commit('SET_BALANCE', balance)
    },
    updateCompanyId({ commit }, id) {
      commit('SET_COMPANYID', id)
    }
  },
  modules: {
    // Here you can add your Vuex modules
  },
})
