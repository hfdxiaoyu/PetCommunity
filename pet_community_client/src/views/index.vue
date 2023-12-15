<template>
  <div style="text-align: center;width: 100%;display: flex;flex-direction: row;">
    <!-- 盒子一   -->
    <div style="width: 10%;">

    </div>

    <div style="text-align: center;width: 70%;display: grid; grid-template-columns: repeat(4,1fr);">
      <div style="height: 600px;">
        <!-- 滚动加载    -->
        <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-distance="300" style="height:600px;overflow:auto;list-style-type:none; display: grid; grid-template-columns: repeat(5,1fr); gap: 10px;">
          <li v-for="i in contentList" class="infinite-list-item">

            <!-- 卡片显示论坛内容     -->
            <el-card class="box-card">
              <div slot="header" class="clearfix" style="display: flex;flex-direction: column;">
                <div class="card_head_div">
                  <span>{{ i.title }}</span>
                  <hr style="color: #eeeeee;">
                </div>
                <div style="display: flex;flex-direction: row;">
                  <div class="card_head_div">
                    <img class="card_head_photo" :src="i.avatar" style="border-radius: 50%;height: 35px;width: 35px;"/>
                  </div>
                  <div class="card_head_div">
                    <span><p style="font-size: 10px;">{{i.nickName}}</p></span>
                  </div>
                  <div class="card_head_div">
                    <i class="el-icon-view"></i>
                  </div>
                  <div class="card_head_div">
                    <p style="font-size: 10px;">{{i.viewCount}}</p>
                  </div>
                  <div class="card_head_div" v-if="i.createtime != null">
                    <p style="font-size: 3px;"> {{creDateFormat(i.createtime)}} </p>
                  </div>
<!--                  <div class="card_head_div">-->
<!--                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>-->
<!--                  </div>-->
                </div>

              </div>
              <div class="text item">
                <div  style="display: flex;flex-direction: column">
                  <div>
<!--                    <img :src="i.img" style="width: 200px;height: 200px;">-->
                    <div class="demo-image">
                      <div class="block" v-for="fit in fits" :key="fit">
                        <el-image
                          style="width: 200px; height: 200px"
                          :src="i.img"
                          :fit="fit"></el-image>
                      </div>
                    </div>
                  </div>
                  <div>
                    <p style="font-size: 3px;">
                      {{i.content}}
                    </p>
                    <i class="el-icon-chat-line-square" @click="toPetDetails(i.contentId)"></i>
                  </div>

                </div>

              </div>

            </el-card>

          </li>
        </ul>
      </div>

    </div>

    <!--  盒子三  -->
    <div style="width: 10%;">

    </div>
  </div>
</template>

<script>
import util1 from '../utils/util1.js'

export default {
  name: "index",
  data () {
    return {
      pageNum: 1,
      pageSize: 10,
      contentList: [], //请求到的所有数据
      total: 1,
      //是否还有
      hasMore: true,
      count: 0,
      fits:['scale-down'],
      theme: '', //主题
      pet: '',//宠物标签
    }
  },
  methods: {
    //鼠标滑动加载数据的方法
    load (them,pet) {
      //给自动加载的方法用
      them=this.theme
      pet=this.pet
      //判断是否到底
      this.isHasMore()

      if (this.hasMore){
        this.request.get('/bbsContent/indexPage',{
          params:{
            pageNum:this.pageNum,
            pageSize:this.pageSize,
            theme : them,
            pet : pet,
          }
        }).then(
          res => {
            //获取数据并且拼接进数组
            this.contentList = this.contentList.concat(res.data.data.records)
            this.total = res.data.data.total
            // this.pageNum = this.contentList.length+1
            // console.log("请求到的数据是：",res)
            // console.log("总数：",this.total)
            // console.log("请求到的数据data是：",res.data.data.records)
          }

        )

      } else {
        console.log("我也是有底线的哦")
      }

      this.pageNum += 1
      // console.log("显示内容数据：",this.contentList[0].content)
      // console.log("拼接后的数据：",this.contentList)
    },
    isHasMore(){
      // console.log("isHasMore:",this.hasMore)
      // console.log("数组条数：",this.contentList.length)

      if (this.contentList.length >= this.total)
        this.hasMore = false;
      else
        this.hasMore = true;
    },
    creDateFormat(date){//对时间进行格式化处理
      var date1 = util1.dateFormatStr(date);
      return date1
    },
    toPetDetails(contenid){ //跳转文章详细信息页面
        if (this.$store.state.isLogin){
           //带参数route跳转
          this.$router.push({
            path:'/petDetails',
            query:{
              contenid:contenid
            }
          })
        } else
          this.$message.error("登录后才可以查看帖子详细信息")
    },
    reloadInit(){ //标签改变后重置页面参数
      this.contentList=[] //初始化页面数组
      this.pageNum= 1
      this.pageSize= 10
      this.total=1
    }

  },//初始化页面时请求的参数
  created() {
    //调用加载的方法
    this.load(this.theme,this.pet)

  },
  computed:{
    getThemUrl() {
      this.reloadInit()
      //不为空返回url为空返回空
      console.log('我是计算属性监听到的them:',this.$store.state.theme)
      this.theme=this.$store.state.theme
      return this.$store.state.theme
    },
    getPetValue(){
      this.reloadInit()
      this.pet=this.$store.state.pet
      return this.$store.state.pet
    }
  },
  watch:{ //监听属性
    getThemUrl:{
      //初次加载就监听
      immediate:true,
      handler(newVal,oldVal){
        console.log("监听到数据，我加载啦,数据是：",this.$store.state.theme)
        this.load(this.theme,this.pet)
      }

    },
    getPetValue:{
      immediate:true,
      handler(newVal,oldVal){
        console.log("监听到数据，我加载啦,数据是：",this.$store.state.pet)
        this.load(this.theme,this.pet)
      }
    }
  }

}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  padding: 3px 5px;
  width: 99%;
}
/*取消去掉点之后的空白*/
ul,li{
  padding:0;
  margin:0;
}
/*隐藏滚动条*/
::-webkit-scrollbar {
  display: none; /* Chrome Safari */
}

/*卡片头部头像样式*/
.card_head_photo{
  /*设置为圆形*/
  border-radius: 50%;
  height: 35px;
  width: 35px;
  text-align: left;
}
/*卡片头部盒子大小*/
.card_head_div{
  height: 35px;
  padding: 3px 5px;
  /*text-align: center;*/
  display: flex;
  /*实现水平居中*/
  justify-content: center;
  /*实现垂直居中*/
  align-items: center;
}

.center{
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
