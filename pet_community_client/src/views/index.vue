<template>
  <div style="text-align: center;width: 100%;display: flex;flex-direction: row;">
    <!-- 盒子一   -->
    <div style="width: 15%;">

    </div>

    <div style="text-align: center;width: 70%;flex-direction: column-reverse;">
      <div style="height: 600px;">
        <!-- 滚动加载    -->
        <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-distance="300" style="height:600px;overflow:auto;list-style-type:none;">
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
                    <span>{{i.nickName}}</span>
                  </div>
                  <div class="card_head_div">
                    访问量：{{i.viewCount}}
                  </div>
                  <div class="card_head_div">
                    创建时间：{{i.createtime}}
                  </div>
                  <div class="card_head_div">
                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                  </div>
                </div>


              </div>
              <div class="text item">
                <div  style="display: flex;flex-direction: row">
                  <div>
                    <img :src="i.img" style="width: 200px;height: 200px;">
                  </div>
                  <div>
                    {{i.content}}
                  </div>

                </div>

              </div>

            </el-card>

          </li>
        </ul>
      </div>

    </div>

    <!--  盒子三  -->
    <div style="width: 15%;">

    </div>
  </div>
</template>

<script>
export default {
  name: "index",
  data () {
    return {
      pageNum: 1,
      pageSize: 3,
      contentList: [], //请求到的所有数据
      total: 1,
      //是否还有
      hasMore: true,
      count: 0
    }
  },
  methods: {
    //鼠标滑动加载数据的方法
    load () {
      //判断是否到底
      this.isHasMore()

      if (this.hasMore){
        this.request.get('/bbsContent/indexPage',{
          params:{
            pageNum:this.pageNum,
            pageSize:this.pageSize,
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
    }

  },//初始化页面时请求的参数
  created() {
    //调用加载的方法
    this.load()
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
  text-align: center;
}
</style>
