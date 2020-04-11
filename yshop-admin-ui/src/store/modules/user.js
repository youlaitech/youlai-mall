import {login, logout, getInfo} from '@/api/system/user'
import {getToken, setToken, removeToken} from '@/utils/auth'
import { resetRouter } from '@/router'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  },
  actions: {
    login({commit}, userInfo) {
      const {username, password} = userInfo
      return new Promise((resolve, reject) => {
        login({username: username.trim(), password: password}).then(response => {
          commit('SET_TOKEN', response.access_token)
          setToken(response.token_type+' '+response.access_token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    getInfo({commit, state}) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const { data } = response

          if (!data) {
            reject('Verification failed, please Login again.')
          }

          const { userName, avatar } = data

          commit('SET_NAME', userName)
          commit('SET_AVATAR', avatar!=null?avatar:"")
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },

    logout({commit}) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          removeToken()
          resetRouter()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // remove token
    resetToken({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
