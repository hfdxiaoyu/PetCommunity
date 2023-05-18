import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: ()=>import('../views/index')
    },{
      path: '/in',
      name: 'index',
      component: ()=>import('../views/index')
    },{
      path: '/petDetails',
      name: 'petDetails',
      component: ()=>import('../views/petDetails')
    },{
      path:'/write',
      name:'write',
      component:()=>import('../views/BBSArticleWrite')
    }
  ]
})
