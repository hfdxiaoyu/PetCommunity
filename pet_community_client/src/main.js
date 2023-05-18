import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
//全局注册编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

//引入elementui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import request from './utils/request'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.prototype.request=request

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
