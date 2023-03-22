<template>
  <div style="text-align: center;width: 100%;display: flex;flex-direction: row;">
    <!-- 盒子一   -->
    <div style="width: 15%;">

    </div>

    <div style="text-align: center;width: 70%;flex-direction: column-reverse;">
      <div style="height: 600px;">
        <!-- 滚动加载    -->
        <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-distance="100" style="height:600px;overflow:auto;list-style-type:none;">
          <li v-for="i in count" class="infinite-list-item">

            <!-- 卡片显示论坛内容     -->
            <el-card class="box-card">
              <div slot="header" class="clearfix" style="display: flex;flex-direction: row;">
                <div class="card_head_div">
                  <img class="card_head_photo" src="http://localhost:9090/file/getfile/6b32793bf8d54029aeeacd10dbd7aa3c.JPG" style="border-radius: 50%;height: 35px;width: 35px;"/>
                </div>
                <div class="card_head_div">
                  <span>用户名</span>
                </div>

                <div class="card_head_div">
                  <span>帖子标题</span>
                </div>

                <div class="card_head_div">
                  <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                </div>

              </div>
              <div v-for="o in 4" :key="o" class="text item">
                {{'列表内容 ' + i }}
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
      count: 0
    }
  },
  methods: {
    load () {
      this.count += 2
    },
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
