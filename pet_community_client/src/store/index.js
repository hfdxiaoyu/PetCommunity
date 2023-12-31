import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const index = new Vuex.Store({
  state: {
    currentPathName: '',
    userimg: '', //用户头像
    isLogin: false,//是否登录
    theme: '', //主题
    pet: '', //宠物类型
  },
  mutations: {
    setPath (state) {
      state.currentPathName = localStorage.getItem("currentPathName")
    },
  }
})

export default index
